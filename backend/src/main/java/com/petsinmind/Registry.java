package com.petsinmind;

import com.petsinmind.messages.AppointmentMessage;
import com.petsinmind.messages.JobOfferMessage;
import com.petsinmind.messages.Message;
import com.petsinmind.messages.TicketMessage;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;
import com.petsinmind.users.SystemAdmin;
import com.petsinmind.users.User;

import java.sql.*;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class Registry {
    private static Registry instance = null;
    private Map<String, String[]> users; // Stores username and an array of user details

    // The database connection
    private static Connection connection;

    private Registry() {
        users = new HashMap<>(); // Initialize the user storage
    }

    public static Registry getInstance() throws SQLException {
        if (instance == null) {
            instance = new Registry();
        }

        connection = DriverManager.getConnection(
                Config.get("db.url"),
                Config.get("db.user"),
                Config.get("db.password")
        );

        return instance;
    }

//    // Method to authenticate a user
//    // draft ---> will return user class
//    public boolean authenticateUser(String username, String password) {
//        if (users.containsKey(username) && users.get(username)[0].equals(password)) {
//            System.out.println("Login successful!");
//            return true;
//        } else {
//            System.out.println("Invalid username or password.");
//            return false;
//        }
//    }
//
//    // Method to add a new user
//    public boolean addUser(String username, String password, String email, String phoneNumber, String firstName, String lastName) {
//        // Check if the user already exists
//        if (users.containsKey(username)) {
//            System.out.println("User already exists.");
//            return false;
//        } else {
//            // Create an array to store user details
//            // draft ---> will store user class instead of array
//            String[] newUser = {password, email, phoneNumber, firstName, lastName};
//            users.put(username, newUser);
//            System.out.println("User registered successfully!");
//            return true;
//        }
//    }

    // Close the connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //******************************************//
    //            SQL Read Functions            //
    //******************************************//

    public User findUser(User user) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String[] idType = (Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
                .matcher(user.getUserID().toString()).matches()) ?
                new String[]{"UserID", user.getUserID().toString()} :
                new String[]{"UserEmail", user.getUserEmail()};

        if (user.getClass() == Caretaker.class) {
            ps = connection.prepareStatement("SELECT * FROM caretaker WHERE ? = ?");
            ps.setString(1,idType[0]);
            ps.setString(2, idType[1]);
            rs = ps.executeQuery();

            if (rs.next()) {
                Caretaker ct = new Caretaker();
                ct = (Caretaker) parseUser(rs, ct);
                ct = getCaretaker(rs, ct);
                return ct;
            } else {
                System.out.println("❌ No caretaker found with ID: " + user.getUserID());
                return null;
            }

        } else if (user.getClass() == PetOwner.class) {
            ps = connection.prepareStatement("SELECT * FROM petowner WHERE ? = ?");
            ps.setString(1,idType[0]);
            ps.setString(2, idType[1]);
            rs = ps.executeQuery();

            if (rs.next()) {
                PetOwner pt = new PetOwner();
                pt = (PetOwner) parseUser(rs, pt);
                pt = getPetOwner(rs, pt);
                return pt;
            } else {
                System.out.println("❌ No caretaker found with ID: " + user.getUserID());
                return null;
            }

        } else if (user.getClass() == SystemAdmin.class) {
            ps = connection.prepareStatement("SELECT * FROM systemadmin WHERE ? = ?");
            ps.setString(1,idType[0]);
            ps.setString(2, idType[1]);
            rs = ps.executeQuery();

            if (rs.next()) {
                SystemAdmin sa = new SystemAdmin();
                sa = (SystemAdmin) parseUser(rs, sa);
                return sa;
            } else {
                System.out.println("❌ No caretaker found with ID: " + user.getUserID());
                return null;
            }
        }

        return user;
    }

    private User parseUser(ResultSet rs, User user) throws SQLException {
        user.setUserID(UUID.fromString(rs.getString("UserID")));
        user.setUserName(rs.getString("UserName"));
        user.setUserPassword(rs.getString("UserPassword"));
        user.setUserEmail(rs.getString("UserEmail"));
        user.setPhoneNumber(rs.getString("PhoneNumber"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));

        return user;
    }

    private Caretaker getCaretaker(ResultSet rs, Caretaker ct) throws SQLException {
        // From Customer
        ct.setLocation(rs.getString("Location"));
        ct.setPay(rs.getFloat("Pay"));
        return ct;
    }

    private PetOwner getPetOwner(ResultSet rs, PetOwner pt)  throws SQLException {
        pt.setLocation(rs.getString("Location"));
        pt.setTicketIDs(idListParser(rs.getArray("ListTicketIDs")));
        pt.setAppointmentIDs(idListParser(rs.getArray("ListAppointmentIDs")));

        List<Pet> pets = new ArrayList<>();
        for (UUID petID : idListParser(rs.getArray("ListPetIDs"))) { getPet(new Pet(petID)); }
        pt.setPetList(pets);

        pt.setJobOfferIDs(idListParser(rs.getArray("ListJobOfferIDs")));
        return pt;
    }

    public Pet getPet(Pet pet) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM pet WHERE PetID = ?");
        ps.setString(1, pet.getPetID().toString());
        rs = ps.executeQuery();

        if (rs.next()) {
            pet.setPetID(UUID.fromString(rs.getString("PetID")));
            pet.setName(rs.getString("Name"));
            pet.setType(rs.getString("Type"));
            pet.setSize(rs.getString("Size"));
            pet.setAge(Integer.valueOf(rs.getString("Age")));
            pet.setOwnerID(UUID.fromString(rs.getString("PetownerID")));
            return pet;
        }

        return null;
    }

    public Appointment getAppointment(Appointment appointment) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM appointment WHERE AppointmentID = ?");
        ps.setString(1, appointment.getAppointmentId().toString());
        rs = ps.executeQuery();

        if (rs.next()) {
            appointment.setAppointmentId(UUID.fromString(rs.getString("AppointmentID")));

            Caretaker ct = (Caretaker) findUser(new Caretaker(UUID.fromString(rs.getString("UserID"))) {});
            appointment.setCaretaker(ct);

            PetOwner pt = (PetOwner) findUser(new PetOwner(UUID.fromString(rs.getString("PetownerID"))));
            appointment.setPetOwner(pt);

            for ( UUID petID : idListParser(rs.getArray("PetIDsList")) ) { appointment.addPet(getPet(new Pet(petID))); }

            appointment.setStartDate(GregorianCalendar.from(ZonedDateTime.ofInstant(rs.getDate("Startdate").toInstant(), ZoneId.systemDefault())));
            appointment.setEndDate(GregorianCalendar.from(ZonedDateTime.ofInstant(rs.getDate("Enddate").toInstant(), ZoneId.systemDefault())));

            return appointment;
        }

        return null;
    }

    public JobOffer getJobOffer(JobOffer jobOffer) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM joboffer WHERE AppointmentID = ?");
        ps.setString(1, jobOffer.getJobOfferID().toString());
        rs = ps.executeQuery();

        if (rs.next()) {
            jobOffer.setJobOfferID(UUID.fromString(rs.getString("JobOfferID")));

            PetOwner pt = (PetOwner) findUser(new PetOwner(UUID.fromString(rs.getString("PetownerID"))));
            jobOffer.setPetOwner(pt);

            jobOffer.setStartDate(dateToCalendar(rs.getDate("Startdate")));
            jobOffer.setEndDate(dateToCalendar(rs.getDate("Enddate")));

            for (UUID ctID : idListParser(rs.getArray("AcceptedcaretakerIDs")) ) { jobOffer.addAcceptedCaretaker(new Caretaker(ctID)); }
            for (UUID ctID : idListParser(rs.getArray("RejectedcaretakerIDs")) ) { jobOffer.addAcceptedCaretaker(new Caretaker(ctID)); }

            jobOffer.setType(rs.getString("Type"));

            return jobOffer;
        }

        return null;
    }

    public Payment getPayment(Payment payment) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM payment WHERE PaymentID = ?");
        ps.setString(1, payment.getPaymentID().toString());
        rs = ps.executeQuery();

        if (rs.next()) {
            payment.setPaymentID(UUID.fromString(rs.getString("PaymentID")));
            payment.setPaymentMethod(rs.getString("Method"));
            payment.setPaymentDate(dateToCalendar(rs.getDate("Date")));
            payment.setPaymentAmount(rs.getFloat("Amount"));
            payment.setPaymentCurrency(rs.getString("Currency"));
            payment.setSenderID(rs.getString("SenderID"));
            payment.setReceiverID(rs.getString("ReceiverID"));
            return payment;
        }
        return null;
    }

    public List<Payment> getPayments(User user) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Payment> payments = new ArrayList<>();

        ps = connection.prepareStatement("SELECT * FROM payment WHERE (SenderID = ? OR ReceiverID = ?)");
        ps.setString(1, user.getUserID().toString());
        ps.setString(2, user.getUserID().toString());
        rs = ps.executeQuery();

        while (rs.next()) {
            Payment pay = getPayment(new Payment(UUID.fromString(rs.getString("PaymentID"))));
            payments.add(pay);
        }

        return payments;
    }

    public List<Review> getReviews(Caretaker caretaker) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM review WHERE CaretakerID = ?");
        ps.setString(1, caretaker.getUserID().toString());
        rs = ps.executeQuery();

        List<Review> reviews = new ArrayList<>();

        while (rs.next()) {
            Review review = new Review();

            review.setDetails(rs.getString("Details"));
            review.setRating(rs.getInt("Rating"));
            review.setAppointment(getAppointment(new Appointment(UUID.fromString(rs.getString("AppointmentID")) )));
            review.setCaretaker( (Caretaker) findUser(new Caretaker(UUID.fromString(rs.getString("CaretakerID")))));
            review.setPetOwner( (PetOwner) findUser(new PetOwner(UUID.fromString(rs.getString("PetownerID")))));
            reviews.add(review);
        }

        return reviews;
    }

    public List<Ticket> getTickets(User user) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM ticket WHERE CustomerID = ?");
        ps.setString(1, user.getUserID().toString());
        rs = ps.executeQuery();

        List<Ticket> tickets = new ArrayList<>();

        while (rs.next()) {
            Ticket ticket = new Ticket();

            ticket.setTicketID(UUID.fromString(rs.getString("TicketID")));
            ticket.setTitle((rs.getString("Title")));
            ticket.setDetails(rs.getString("Details"));
            ticket.setDate(dateToCalendar(rs.getDate("Date")));
            ticket.setCustomerID(UUID.fromString(rs.getString("CustomerID")));

            List<UUID> sysList = new ArrayList<>(idListParser(rs.getArray("SystemadminIDs")));
            ticket.setEmployeeIDs(sysList);

            ticket.setStatus(rs.getBoolean("Status"));
        }

        return tickets;
    }

    public List<Message> getMessages(User user) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM Message WHERE (SenderID = ? OR ReceiverID = ?)");
        ps.setString(1, user.getUserID().toString());
        ps.setString(2, user.getUserID().toString());

        rs = ps.executeQuery();

        List<Message> messages = new ArrayList<>();

        while (rs.next()) {
            String messageType = rs.getString("type");
            Message msg = switch (messageType) {
                case "AppointmentMessage" -> new AppointmentMessage(rs.getString("Details"),
                        UUID.fromString(rs.getString("SenderID")),
                        UUID.fromString(rs.getString("ReferenceID")),
                        UUID.fromString(rs.getString("ReceiverID")),
                        dateToCalendar(rs.getDate("Date")));
                case "JobOfferMessage" -> new JobOfferMessage(rs.getString("Details"),
                        UUID.fromString(rs.getString("SenderID")),
                        UUID.fromString(rs.getString("ReferenceID")),
                        UUID.fromString(rs.getString("ReceiverID")),
                        dateToCalendar(rs.getDate("Date")));
                case "TicketMessage" -> new TicketMessage(rs.getString("Details"),
                        UUID.fromString(rs.getString("SenderID")),
                        UUID.fromString(rs.getString("ReferenceID")),
                        UUID.fromString(rs.getString("ReceiverID")),
                        dateToCalendar(rs.getDate("Date")));
                default -> null;
            };

            messages.add(msg);
        }
        return messages;
    }

    public Application getApplication(Application app) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM application WHERE UserEmail= ?");
        ps.setString(1, app.getEmail());
        rs = ps.executeQuery();

        if (rs.next()) {
            return new Application(rs.getString("FirstName"), rs.getString("LastName"),
                    rs.getString("UserName"), rs.getString("UserPassword"), rs.getString("UserEmail"),
                    rs.getBlob("UserCV"), rs.getString("PhoneNumber"));
        }
        return null;
    }

    //******************************************//
    //           SQL Write Functions            //
    //******************************************//

    public boolean createUser(User user) {

    }

    public boolean editUser(User user) {

    }

    public boolean deleteUser(User user) {

    }

    public boolean createPet(Pet pet, PetOwner petOwner) {

    }

    public boolean editPet(Pet pet, PetOwner petOwner) {

    }

    public boolean deletePet(Pet pet, PetOwner petOwner) {

    }

    public boolean createAppointment(Appointment appointment) {

    }

    public boolean deleteAppointment(Appointment appointment) {

    }

    public boolean createJobOffer(JobOffer jobOffer) {

    }

    public boolean deleteJobOffer(JobOffer jobOffer) {

    }

    public boolean editAvailability(Caretaker ct) {

    }

    public boolean createPayment(Payment payment) {

    }

    public boolean createApplication(Application app) {

    }

    public static void insertCaretaker(Connection conn, Caretaker ct) {
        try {
            String sql = "INSERT INTO caretaker (UserID, UserName, UserPassword, UserEmail, PhoneNumber, FirstName, LastName, Location, Pay) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ct.getUserID().toString());
            ps.setString(2, ct.getUserEmail());
            ps.setString(3, ct.getUserPassword());
            ps.setString(4, ct.getUserName());
            ps.setString(5, ct.getPhoneNumber());
            ps.setString(6, ct.getFirstName());
            ps.setString(7, ct.getLastName());
            ps.setString(8, ct.getLocation());
            ps.setFloat(9, ct.getPay());

            int rows = ps.executeUpdate();
            System.out.println("✅ Rows inserted: " + rows);

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //******************************************//
    //             Implementations              //
    //******************************************//

    public List<Caretaker> findAvailableCaretakers(JobOffer jobOffer) {

    }

    public checkUserName(String userName) {

    }

    //******************************************//
    //             Helper Functions             //
    //******************************************//

    public Calendar dateToCalendar(Date date) {
        return GregorianCalendar.from(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    public List<UUID> idListParser(Array idList) throws SQLException {
        return (List<UUID>) idList.getArray();
    }
}