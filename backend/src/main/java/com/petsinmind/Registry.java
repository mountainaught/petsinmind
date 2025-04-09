package com.petsinmind;

import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;
import com.petsinmind.users.SystemAdmin;
import com.petsinmind.users.User;
import com.petsinmind.Application;
import com.petsinmind.Config;
import com.petsinmind.Appointment;
import com.petsinmind.JobOffer;
import com.petsinmind.Payment;
import com.petsinmind.Pet;
// import com.petsinmind.users.Application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
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
                Config.get("db.password"));

        return instance;
    }

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

    // ******************************************//
    // SQL Read Functions //
    // ******************************************//

    public User findUser(User user) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String[] idType = (Pattern
                .compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
                .matcher(user.getUserID().toString()).matches())
                        ? new String[] { "UserID", user.getUserID().toString() }
                        : new String[] { "UserEmail", user.getUserEmail() };

        if (user.getClass() == Caretaker.class) {
            ps = connection.prepareStatement("SELECT * FROM caretaker WHERE ? = ?");
            ps.setString(1, idType[0]);
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
            ps.setString(1, idType[0]);
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
            ps.setString(1, idType[0]);
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

    private PetOwner getPetOwner(ResultSet rs, PetOwner pt) throws SQLException {
        pt.setLocation(rs.getString("Location"));
        pt.setTicketIDs(idListParser(rs.getArray("ListTicketIDs")));
        pt.setAppointmentIDs(idListParser(rs.getArray("ListAppointmentIDs")));

        List<Pet> pets = new ArrayList<>();
        for (UUID petID : idListParser(rs.getArray("ListPetIDs"))) {
            getPet(new Pet(petID));
        }
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

            Caretaker ct = (Caretaker) findUser(new Caretaker(UUID.fromString(rs.getString("UserID"))) {
            });
            appointment.setCaretaker(ct);

            PetOwner pt = (PetOwner) findUser(new PetOwner(UUID.fromString(rs.getString("PetownerID"))));
            appointment.setPetOwner(pt);
            List<Pet> pets = new ArrayList<>();
            for (UUID petID : idListParser(rs.getArray("PetIDsList"))) {
                pets.add(getPet(new Pet(petID)));
            }
            appointment.setPets(pets);

            appointment.setStartDate(GregorianCalendar
                    .from(ZonedDateTime.ofInstant(rs.getDate("Startdate").toInstant(), ZoneId.systemDefault())));
            appointment.setEndDate(GregorianCalendar
                    .from(ZonedDateTime.ofInstant(rs.getDate("Enddate").toInstant(), ZoneId.systemDefault())));

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

            for (UUID ctID : idListParser(rs.getArray("AcceptedcaretakerIDs"))) {
                jobOffer.addAcceptedCaretaker(new Caretaker(ctID));
            }
            for (UUID ctID : idListParser(rs.getArray("RejectedcaretakerIDs"))) {
                jobOffer.addAcceptedCaretaker(new Caretaker(ctID));
            }

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
            payment.setSenderID(UUID.fromString(rs.getString("SenderID")));
            payment.setReceiverID(UUID.fromString(rs.getString("ReceiverID")));
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
            review.setAppointment(getAppointment(new Appointment(UUID.fromString(rs.getString("AppointmentID")))));
            review.setCaretaker((Caretaker) findUser(new Caretaker(UUID.fromString(rs.getString("CaretakerID")))));
            review.setPetOwner((PetOwner) findUser(new PetOwner(UUID.fromString(rs.getString("PetownerID")))));
            reviews.add(review);
        }

        return reviews;
    }

    public Ticket getTicket(Ticket ticket) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM ticket WHERE TicketID = ?");
        ps.setString(1, ticket.getTicketID().toString());
        rs = ps.executeQuery();

        if (rs.next()) {
            ticket.setTitle(rs.getString("Title"));
            ticket.setDetails(rs.getString("Details"));
            ticket.setDate(dateToCalendar(rs.getDate("Date")));
            ticket.setCustomerID(UUID.fromString(rs.getString("CustomerID")));
            ticket.setEmployeeIDs(idListParser(rs.getArray("SystemadminIDs")));
            ticket.setStatus(rs.getBoolean("Status"));
            return ticket;
        }
        return null;
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
            Message msg = new Message(rs.getString("Details"),
                    UUID.fromString(rs.getString("SenderID")),
                    UUID.fromString(rs.getString("ReceiverID")),
                    UUID.fromString(rs.getString("ReferenceID")),
                    rs.getDate("Date") != null ? Calendar.getInstance() : null);

            messages.add(msg);
        }
        return messages;
    }

    public List<Message> getMessages(String ID) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement("SELECT * FROM Message WHERE ReferenceID =?");
        ps.setString(1, ID);

        rs = ps.executeQuery();

        List<Message> messages = new ArrayList<>();

        while (rs.next()) {
            Message msg = new Message(rs.getString("Details"),
                    UUID.fromString(rs.getString("SenderID")),
                    UUID.fromString(rs.getString("ReceiverID")),
                    UUID.fromString(rs.getString("ReferenceID")),
                    rs.getDate("Date") != null ? Calendar.getInstance() : null);

            messages.add(msg);
        }
        return messages;
    }

    public Application getApplication(Application app, String outputPath) {
        String sql = "SELECT * FROM application WHERE UserName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, app.getUserName());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                app.setFirstName(rs.getString("FirstName"));
                app.setLastName(rs.getString("LastName"));
                app.setUserEmail(rs.getString("UserEmail"));
                app.setUserPassword(rs.getString("UserPassword"));
                app.setPhoneNumber(rs.getString("PhoneNumber"));

                // ✅ Download the PDF using your existing method
                downloadApplicationCV(app.getUserName(), outputPath);

                return app;
            } else {
                System.out.println("❌ Application not found for: " + app.getUserName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ******************************************//
    // SQL Write Functions //
    // ******************************************//

    public boolean createUser(User user) throws SQLException {
        PreparedStatement ps = null;
        if (user instanceof Caretaker) {
            Caretaker caretaker = (Caretaker) user;

            String userid = caretaker.getUserID().toString();
            String username = caretaker.getUserName();
            String userpassword = caretaker.getUserPassword();
            String useremail = caretaker.getUserEmail();
            String phonenumber = caretaker.getPhoneNumber();
            String firstname = caretaker.getFirstName();
            String lastname = caretaker.getLastName();
            List<String> ticketIDs = caretaker.getTicketIDsAsString();
            List<String> jobofferIDs = caretaker.getJobOfferIDsAsString();
            String location = caretaker.getLocation();
            float pay = caretaker.getPay();
            List<String> appointmentIDs = caretaker.getAppointmentIDsAsString();

            Gson gson = new Gson();
            String ticketIDsJson = gson.toJson(ticketIDs);
            String jobofferIDsJson = gson.toJson(jobofferIDs);
            String appointmentIDsJson = gson.toJson(appointmentIDs);
            String sql = "INSERT INTO caretaker " +
                    "(UserID, UserName, UserPassword, UserEmail, PhoneNumber, FirstName, LastName, " +
                    "ListTicketIDs, ListJobOfferIDs, Location, Pay, ListAppointmentIDs) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setString(1, userid); // UserID
            ps.setString(2, username); // UserName
            ps.setString(3, userpassword); // UserPassword
            ps.setString(4, useremail); // UserEmail
            ps.setString(5, phonenumber); // PhoneNumber
            ps.setString(6, firstname); // FirstName
            ps.setString(7, lastname); // LastName
            ps.setString(8, ticketIDsJson); // ListTicketIDs (stored as JSON)
            ps.setString(9, jobofferIDsJson); // ListJobOfferIDs (stored as JSON)
            ps.setString(10, location); // Location
            ps.setFloat(11, pay); // Pay
            ps.setString(12, appointmentIDsJson); // ListAppointmentIDs (stored as JSON)

            // Execute the insert statement
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }
        } else if (user instanceof PetOwner) {
            PetOwner petowner = (PetOwner) user;

            String userid = petowner.getUserID().toString();
            String username = petowner.getUserName();
            String userpassword = petowner.getUserPassword();
            String useremail = petowner.getUserEmail();
            String phonenumber = petowner.getPhoneNumber();
            String firstname = petowner.getFirstName();
            String lastname = petowner.getLastName();
            List<String> ticketIDs = petowner.getTicketIDsAsString();
            List<String> jobofferIDs = petowner.getJobOfferIDsAsString();
            List<String> petIDs = petowner.getPetIDs();
            List<String> appointmentIDs = petowner.getAppointmentIDsAsString();
            String location = petowner.getLocation();

            Gson gson = new Gson();
            String ticketIDsJson = gson.toJson(ticketIDs);
            String jobofferIDsJson = gson.toJson(jobofferIDs);
            String petIDsJson = gson.toJson(petIDs);
            String appointmentIDsJson = gson.toJson(appointmentIDs);

            String sql = "INSERT INTO petowner " +
                    "(UserID, UserName, UserPassword, UserEmail, PhoneNumber, FirstName, LastName, " +
                    "ListTicketIDs, ListJobOfferIDs, ListPetIDs, Location, ListAppointmentIDs) " +
                    "VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setString(1, userid); // UserID
            ps.setString(2, username); // UserName
            ps.setString(3, userpassword); // UserPassword
            ps.setString(4, useremail); // UserEmail
            ps.setString(5, phonenumber); // PhoneNumber
            ps.setString(6, firstname); // FirstName
            ps.setString(7, lastname); // LastName
            ps.setString(8, ticketIDsJson); // ListTicketIDs (stored as JSON)
            ps.setString(9, jobofferIDsJson); // ListJobOfferIDs (stored as JSON)
            ps.setString(10, petIDsJson); // ListPetIDs (stored as JSON)
            ps.setString(11, location); // Location
            ps.setString(12, appointmentIDsJson); // ListAppointmentIDs (stored as JSON)

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }
        } else if (user instanceof SystemAdmin) {
            SystemAdmin sysadmin = (SystemAdmin) user;
            String userid = sysadmin.getUserID().toString();
            String username = sysadmin.getUserName();
            String userpassword = sysadmin.getUserPassword();
            String useremail = sysadmin.getUserEmail();
            String phonenumber = sysadmin.getPhoneNumber();
            String firstname = sysadmin.getFirstName();
            String lastname = sysadmin.getLastName();

            String sql = "INSERT INTO systemadmin " +
                    "(UserID, UserName, UserPassword, UserEmail, PhoneNumber, FirstName, LastName) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setString(1, userid); // UserID
            ps.setString(2, username); // UserName
            ps.setString(3, userpassword); // UserPassword
            ps.setString(4, useremail); // UserEmail
            ps.setString(5, phonenumber); // PhoneNumber
            ps.setString(6, firstname); // FirstName
            ps.setString(7, lastname); // LastName
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false; // Invalid user type
        }
    }

    public boolean editUser(User user) throws SQLException {
        PreparedStatement ps = null;
        if (user instanceof Caretaker) {
            Caretaker caretaker = (Caretaker) user;
            // Upload the image and get the generated image ID
            String userid = caretaker.getUserID().toString();
            String username = caretaker.getUserName();
            String userpassword = caretaker.getUserPassword();
            String useremail = caretaker.getUserEmail();
            String phonenumber = caretaker.getPhoneNumber();
            String firstname = caretaker.getFirstName();
            String lastname = caretaker.getLastName();
            List<String> ticketIDs = caretaker.getTicketIDsAsString();
            List<String> jobofferIDs = caretaker.getJobOfferIDsAsString();
            String location = caretaker.getLocation();
            float pay = caretaker.getPay();
            List<String> appointmentIDs = caretaker.getAppointmentIDsAsString();

            Gson gson = new Gson();
            String ticketIDsJson = gson.toJson(ticketIDs);
            String jobofferIDsJson = gson.toJson(jobofferIDs);
            String appointmentIDsJson = gson.toJson(appointmentIDs);
            String sql = "UPDATE caretaker SET UserName = ?,UserPassword = ?,UserEmail = ?, PhoneNumber = ?, FirstName = ?, LastName = ?,ListTicketIDs = ?,ListJobOfferIDs = ?, Location = ?, Pay = ?,ListAppointmentIDs = ? WHERE UserID = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username); // UserName
            ps.setString(2, userpassword); // UserPassword
            ps.setString(3, useremail); // UserEmail
            ps.setString(4, phonenumber); // PhoneNumber
            ps.setString(5, firstname); // FirstName
            ps.setString(6, lastname); // LastName
            ps.setString(7, ticketIDsJson); // ListTicketIDs (stored as JSON)
            ps.setString(8, jobofferIDsJson); // ListJobOfferIDs (stored as JSON)
            ps.setString(9, location); // Location
            ps.setFloat(10, pay); // Pay
            ps.setString(11, appointmentIDsJson); // ListAppointmentIDs (stored as JSON)
            ps.setString(12, userid); // UserID

            // Execute the insert statement
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("Update failed.");
            }
        } else if (user instanceof PetOwner) {
            PetOwner petowner = (PetOwner) user;

            String userid = petowner.getUserID().toString();
            String username = petowner.getUserName();
            String userpassword = petowner.getUserPassword();
            String useremail = petowner.getUserEmail();
            String phonenumber = petowner.getPhoneNumber();
            String firstname = petowner.getFirstName();
            String lastname = petowner.getLastName();
            List<String> ticketIDs = petowner.getTicketIDsAsString();
            List<String> jobofferIDs = petowner.getJobOfferIDsAsString();
            List<String> petIDs = petowner.getPetIDs();
            List<String> appointmentIDs = petowner.getAppointmentIDsAsString();
            String location = petowner.getLocation();

            Gson gson = new Gson();
            String ticketIDsJson = gson.toJson(ticketIDs);
            String jobofferIDsJson = gson.toJson(jobofferIDs);
            String petIDsJson = gson.toJson(petIDs);
            String appointmentIDsJson = gson.toJson(appointmentIDs);

            String sql = "UPDATE petowner SET " +
                    "(UserName =?, UserPassword =?, UserEmail =?, PhoneNumber=?, FirstName=?, LastName=?, " +
                    "ListTicketIDs=?, ListJobOfferIDs=?, ListPetIDs=?, Location=?,ListAppointmentIDs=?) " +
                    "WHERE UserID = ?;";
            ps = connection.prepareStatement(sql);

            ps.setString(1, username); // UserName
            ps.setString(2, userpassword); // UserPassword
            ps.setString(3, useremail); // UserEmail
            ps.setString(4, phonenumber); // PhoneNumber
            ps.setString(5, firstname); // FirstName
            ps.setString(6, lastname); // LastName
            ps.setString(7, ticketIDsJson); // ListTicketIDs (stored as JSON)
            ps.setString(8, jobofferIDsJson); // ListJobOfferIDs (stored as JSON)
            ps.setString(9, petIDsJson); // ListPetIDs (stored as JSON)
            ps.setString(10, location); // Location
            ps.setString(11, appointmentIDsJson); // ListAppointmentIDs (stored as JSON)
            ps.setString(12, userid); // UserID

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("Update failed.");
            }
        } else if (user instanceof SystemAdmin) {
            SystemAdmin sysadmin = (SystemAdmin) user;
            String userid = sysadmin.getUserID().toString();
            String username = sysadmin.getUserName();
            String userpassword = sysadmin.getUserPassword();
            String useremail = sysadmin.getUserEmail();
            String phonenumber = sysadmin.getPhoneNumber();
            String firstname = sysadmin.getFirstName();
            String lastname = sysadmin.getLastName();

            String sql = "UPDATE systemadmin SET" +
                    "(UserName=?, UserPassword=?, UserEmail=?, PhoneNumber=?, FirstName=?, LastName=?) " +
                    "WHERE UserID = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, username); // UserName
            ps.setString(2, userpassword); // UserPassword
            ps.setString(3, useremail); // UserEmail
            ps.setString(4, phonenumber); // PhoneNumber
            ps.setString(5, firstname); // FirstName
            ps.setString(6, lastname); // LastName
            ps.setString(7, userid); // UserID
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful.");
            } else {
                System.out.println("Update failed.");
            }
        } else {
            return false; // Invalid user type
        }
        return true; // Update successful
    }

    public boolean deleteUser(User user) throws SQLException {
        PreparedStatement ps = null;
        if (user instanceof Caretaker) {
            Caretaker caretaker = (Caretaker) user;
            String userid = caretaker.getUserID().toString();
            String sql = "DELETE FROM caretaker WHERE UserID = ?;";
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, userid); // UserID
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Delete successful.");
                    return true;
                } else {
                    System.out.println("Delete failed.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (user instanceof PetOwner) {
            PetOwner petowner = (PetOwner) user;
            String userid = petowner.getUserID().toString();
            String sql = "DELETE FROM petowner WHERE UserID = ?;";
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, userid); // UserID
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Delete successful.");
                    return true;
                } else {
                    System.out.println("Delete failed.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (user instanceof SystemAdmin) {
            SystemAdmin sysadmin = (SystemAdmin) user;
            String userid = sysadmin.getUserID().toString();
            String sql = "DELETE FROM systemadmin WHERE UserID = ?;";
            try {
                ps = connection.prepareStatement(sql);
                ps.setString(1, userid); // UserID
                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Delete successful.");
                    return true;
                } else {
                    System.out.println("Delete failed.");
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false; // Invalid user type
        }
        return false; // User type not recognized
    }

    public boolean createPet(Pet pet, PetOwner petOwner) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO pet (PetID, Name, Type, Size, Age, PetownerID) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pet.getPetID().toString()); // PetID
            ps.setString(2, pet.getName()); // PetName
            ps.setString(3, pet.getType()); // PetType
            ps.setString(4, pet.getSize()); // PetBreed
            ps.setInt(5, pet.getAge()); // PetAge
            ps.setString(6, petOwner.getUserID().toString()); // PetOwnerID

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert successful.");
                return true;
            } else {
                System.out.println("Insert failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    public boolean editPet(Pet pet, PetOwner petOwner) throws SQLException {
        PreparedStatement ps = null;
        String sql = "UPDATE pet SET Name = ?, Type = ?, Size = ?, Age = ? WHERE PetID = ?;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pet.getName()); // PetName
            ps.setString(2, pet.getType()); // PetType
            ps.setString(3, pet.getSize()); // PetBreed
            ps.setInt(4, pet.getAge()); // PetAge
            ps.setString(5, pet.getPetID().toString()); // PetID

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful.");
                return true;
            } else {
                System.out.println("Update failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Update failed
    }

    public boolean deletePet(Pet pet, PetOwner petOwner) throws SQLException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM pet WHERE PetID = ?;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, pet.getPetID().toString()); // PetID

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Delete successful.");
                return true;
            } else {
                System.out.println("Delete failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Delete failed
    }

    public boolean createReview(Review review) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO review (Details, Rating, AppointmentID, CaretakerID, PetownerID) VALUES (?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, review.getDetails()); // Details
            ps.setInt(2, review.getRating()); // Rating
            ps.setString(3, review.getAppointment().getAppointmentId().toString()); // AppointmentID
            ps.setString(4, review.getCaretaker().getUserID().toString()); // CaretakerID
            ps.setString(5, review.getPetOwner().getUserID().toString()); // PetOwnerID

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Review inserted successfully!");
                return true;
            } else {
                System.out.println("Insert failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    public boolean createMessage(Message message) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO Message (Details, SenderID, ReferenceID, ReceiverID, Date) VALUES (?, ?, ?, ?, ?);";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, message.getDetails()); // Details
            ps.setString(2, message.getSenderIDString()); // SenderID
            ps.setString(3, message.getReferenceIDString()); // ReferenceID
            ps.setString(4, message.getReceiverIDString()); // ReceiverID
            ps.setDate(5, new Date(message.getDate().getTimeInMillis())); // Date

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Message inserted successfully!");
                return true;
            } else {
                System.out.println("Insert failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Insert failed
        }
    }

    public boolean createAppointment(Appointment appointment) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO appointment (AppointmentID, CaretakerID, PetownerID, PetIDsList, Startdate, Enddate, Type)"
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        try {
            List<String> petIDs = appointment.getPetIDs();
            Gson gson = new Gson();
            String petIDsJson = gson.toJson(petIDs); // Convert the list to JSON
            Date startDate = new Date(appointment.getStartDate().getTimeInMillis());
            Date endDate = new Date(appointment.getEndDate().getTimeInMillis());
            ps = connection.prepareStatement(sql);
            ps.setString(1, appointment.getAppointmentId().toString()); // AppointmentID
            ps.setString(2, appointment.getCaretaker().getUserID().toString()); // CaretakerID
            ps.setString(3, appointment.getPetOwner().getUserID().toString()); // PetOwnerID
            ps.setString(4, petIDsJson); // PetIDsList (stored as JSON)
            ps.setDate(5, startDate); // Startdate
            ps.setDate(6, endDate); // Enddate
            ps.setString(7, appointment.getType()); // Type

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Appointment inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    public boolean deleteAppointment(Appointment appointment) throws SQLException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM appointment WHERE AppointmentID = ?;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, appointment.getAppointmentId().toString()); // AppointmentID

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Delete successful.");
                return true;
            } else {
                System.out.println("Delete failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Delete failed
    }

    public boolean createJobOffer(JobOffer jobOffer) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO joboffer (JobofferID, PetownerID, Startdate, Enddate, AcceptedcaretakerIDs, RejectedcaretakerIDs, Type, PetIDs)"
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            List<String> petIDs = jobOffer.getPetIDsAsString();
            List<String> acceptedCaretakerIDs = jobOffer.getAcceptedCaretakerIDs();
            List<String> rejectedCaretakerIDs = jobOffer.getRejectedCaretakerIDs();

            Gson gson = new Gson();
            String petIDsJson = gson.toJson(petIDs); // Convert the list to JSON
            String acceptedCaretakerIDsJson = gson.toJson(acceptedCaretakerIDs); // Convert the list to JSON
            String rejectedCaretakerIDsJson = gson.toJson(rejectedCaretakerIDs); // Convert the list to JSON
            Date startDate = new Date(jobOffer.getStartDate().getTimeInMillis());
            Date endDate = new Date(jobOffer.getEndDate().getTimeInMillis());
            ps = connection.prepareStatement(sql);
            ps.setString(1, jobOffer.getJobOfferID().toString()); // JobofferID
            ps.setString(2, jobOffer.getPetOwner().getUserID().toString()); // PetOwnerID
            ps.setDate(3, startDate); // Startdate
            ps.setDate(4, endDate); // Enddate
            ps.setString(5, acceptedCaretakerIDsJson); // AcceptedcaretakerIDs (stored as JSON)
            ps.setString(6, rejectedCaretakerIDsJson); // RejectedcaretakerIDs (stored as JSON)
            ps.setString(7, jobOffer.getType()); // Type
            ps.setString(8, petIDsJson); // PetIDs (stored as JSON)

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Job offer inserted successfully!");
                return true;
            } else {
                System.out.println("Insert failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    public boolean editJobOffer(JobOffer jobOffer) throws SQLException {
        PreparedStatement ps = null;
        String sql = "UPDATE joboffer SET Startdate = ?, Enddate = ?, AcceptedcaretakerIDs = ?, RejectedcaretakerIDs = ? WHERE JobofferID = ?;";
        try {
            List<String> acceptedCaretakerIDs = jobOffer.getAcceptedCaretakerIDs();
            List<String> rejectedCaretakerIDs = jobOffer.getRejectedCaretakerIDs();

            Gson gson = new Gson();
            String acceptedCaretakerIDsJson = gson.toJson(acceptedCaretakerIDs); // Convert the list to JSON
            String rejectedCaretakerIDsJson = gson.toJson(rejectedCaretakerIDs); // Convert the list to JSON
            Date startDate = new Date(jobOffer.getStartDate().getTimeInMillis());
            Date endDate = new Date(jobOffer.getEndDate().getTimeInMillis());
            ps = connection.prepareStatement(sql);
            ps.setDate(1, startDate); // Startdate
            ps.setDate(2, endDate); // Enddate
            ps.setString(3, acceptedCaretakerIDsJson); // AcceptedcaretakerIDs (stored as JSON)
            ps.setString(4, rejectedCaretakerIDsJson); // RejectedcaretakerIDs (stored as JSON)
            ps.setString(5, jobOffer.getJobOfferID().toString()); // JobofferID

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Update successful.");
                return true;
            } else {
                System.out.println("Update failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Update failed
    }

    public boolean deleteJobOffer(JobOffer jobOffer) throws SQLException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM joboffer WHERE JobofferID = ?;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, jobOffer.getJobOfferID().toString()); // JobofferID

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Delete successful.");
                return true;
            } else {
                System.out.println("Delete failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Delete failed
    }

    public boolean editAvailability(Caretaker ct) {
        String deleteSQL = "DELETE FROM availability WHERE UserID = ?";
        String insertSQL = "INSERT INTO availability (UserID, Day, Hour) VALUES (?, ?, ?)";

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
            deleteStmt.setString(1, ct.getUserID().toString());
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to clear previous availability.");
            e.printStackTrace();
            return false;
        }

        try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
            boolean[][] availability = ct.getAvailability();
            for (int day = 0; day < 7; day++) {
                for (int hour = 0; hour < 24; hour++) {
                    if (availability[day][hour]) {
                        insertStmt.setString(1, ct.getUserID().toString());
                        insertStmt.setInt(2, day);
                        insertStmt.setInt(3, hour);
                        insertStmt.addBatch();
                    }
                }
            }
            insertStmt.executeBatch();
            System.out.println("✅ Availability updated.");
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Failed to insert availability.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAvailabilitypoints(Caretaker ct) throws SQLException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM availability WHERE CaretakerID = ?;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, ct.getUserID().toString()); // CaretakerID

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Delete successful.");
                return true;
            } else {
                System.out.println("Delete failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Delete failed
    }

    public boolean addAvailabilitypoints(Caretaker ct) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO availability (CaretakerID, Hour, Day) " +
                "VALUES (?, ?, ?);";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, ct.getUserID().toString()); // CaretakerID
            boolean[][] availability = ct.getAvailability(); // 2D array for availability points
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 7; j++) {
                    if (availability[i][j]) {
                        ps.setInt(2, i); // Hour
                        ps.setInt(3, j); // Day
                        ps.addBatch();
                    }
                }
            }

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Availability points inserted successfully!");
                return true;
            } else {
                System.out.println("Insert failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    public boolean createPayment(Payment payment) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO payment (PaymentID, Method, Date, Amount, Currency, SenderID, ReceiverID) " +
                "VALUES (?, ?, ?, ?, ?,?,?);";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, payment.getPaymentID().toString()); // PaymentID
            ps.setString(2, payment.getPaymentMethod()); // Method
            ps.setDate(3, new Date(payment.getPaymentDate().getTimeInMillis())); // Date
            ps.setFloat(4, payment.getPaymentAmount()); // Amount
            ps.setString(5, payment.getPaymentCurrency()); // Currency
            ps.setString(6, payment.getSenderID().toString()); // SenderID
            ps.setString(7, payment.getReceiverID().toString()); // ReceiverID

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Payment inserted successfully!");
                return true;
            } else {
                System.out.println("Insert failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed
    }

    public boolean createTicket(Ticket ticket) throws SQLException {
        PreparedStatement ps = null;
        String sql = "INSERT INTO ticket (TicketID, Title, Details, Date, CustomerID, SystemadminIDs, Status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        try {
            String TicketId = ticket.getTicketID().toString();
            String Title = ticket.getTitle();
            String Details = ticket.getDetails();
            Date Date = new Date(ticket.getDate().getTimeInMillis());
            String CustomerID = ticket.getCustomerID().toString();
            List<String> EmployeeIDs = ticket.getEmployeeSIDs();

            Gson gson = new Gson();
            String EmployeeIDsJson = gson.toJson(EmployeeIDs); // Convert the list to JSON

            ps = connection.prepareStatement(sql);
            ps.setString(1, TicketId); // TicketID
            ps.setString(2, Title); // Title
            ps.setString(3, Details); // Details
            ps.setDate(4, Date); // Date
            ps.setString(5, CustomerID); // CustomerID
            ps.setString(6, EmployeeIDsJson); // SystemadminIDs (stored as JSON)
            ps.setBoolean(7, false); // Status

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Ticket inserted successfully!");
                return true;
            } else {
                System.out.println("Insert failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Insert failed

    }

    public boolean editTicket(Ticket ticket) throws SQLException {
        PreparedStatement ps = null;
        String sql = "UPDATE ticket SET Title = ?, Details = ?, Date = ?, CustomerID = ?, SystemadminIDs = ?, Status =? WHERE TicketID = ?;";
        try {
            String TicketId = ticket.getTicketID().toString();
            String Title = ticket.getTitle();
            String Details = ticket.getDetails();
            Date Date = new Date(ticket.getDate().getTimeInMillis());
            String CustomerID = ticket.getCustomerID().toString();
            List<String> EmployeeIDs = ticket.getEmployeeSIDs();
            boolean status = ticket.getStatus();

            Gson gson = new Gson();
            String EmployeeIDsJson = gson.toJson(EmployeeIDs); // Convert the list to JSON

            ps = connection.prepareStatement(sql);
            ps.setString(1, Title); // Title
            ps.setString(2, Details); // Details
            ps.setDate(3, Date); // Date
            ps.setString(4, CustomerID); // CustomerID
            ps.setString(5, EmployeeIDsJson); // SystemadminIDs (stored as JSON)
            ps.setBoolean(6, status); // Status
            ps.setString(7, TicketId); // TicketID

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Ticket updated successfully!");
                return true;
            } else {
                System.out.println("Update failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Update failed
    }

    public boolean deleteTicket(Ticket ticket) throws SQLException {
        PreparedStatement ps = null;
        String sql = "DELETE FROM ticket WHERE TicketID = ?;";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, ticket.getTicketID().toString()); // TicketID

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Delete successful.");
                return true;
            } else {
                System.out.println("Delete failed.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Delete failed
    }

    public boolean createApplication(Application app, String pdfPath) {
        String sql = "INSERT INTO application (FirstName, LastName, UserName, UserPassword, UserEmail, PhoneNumber) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, app.getFirstName());
            stmt.setString(2, app.getLastName());
            stmt.setString(3, app.getUserName());
            stmt.setString(4, app.getUserPassword());
            stmt.setString(5, app.getUserEmail());
            stmt.setString(6, app.getPhoneNumber());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Application inserted.");
                return uploadApplicationCV(app.getUserName(), pdfPath);
            } else {
                System.out.println("❌ Failed to insert application.");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteApplication(Application app) {
        String sql = "DELETE FROM application WHERE UserName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, app.getUserName());

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "✅ Application deleted." : "❌ Failed to delete application.");
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ******************************************//
    // Implementations //
    // ******************************************//

    public boolean uploadApplicationCV(String userName, String filePath) {
        String sql = "UPDATE application SET UserCV = ? WHERE UserName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
                FileInputStream fis = new FileInputStream(filePath)) {

            stmt.setBinaryStream(1, fis, fis.available());
            stmt.setString(2, userName);

            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "✅ CV uploaded." : "❌ Failed to upload CV.");
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean downloadApplicationCV(String userName, String outputPath) {
        String sql = "SELECT UserCV FROM application WHERE UserName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                InputStream input = rs.getBinaryStream("UserCV");

                if (input == null) {
                    System.out.println("⚠️ No CV found for caretaker.");
                    return false;
                }

                FileOutputStream output = new FileOutputStream(outputPath);
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }

                input.close();
                output.close();
                System.out.println("✅ CV saved to: " + outputPath);
                return true;
            } else {
                System.out.println("❌ Caretaker not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean userNameExists(String userName) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT * FROM (caretaker OR petowner OR systemadmin) WHERE UserName");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return userName.equals(rs.getString("UserName"));
        }

        return false;
    }

    private String fetchGoogleApiKey() {
        String apiKey = "";
        String sql = "SELECT APIKEY FROM API WHERE Name = 'GeoAPI'";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                apiKey = rs.getString("APIKEY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

    public double getDistanceInKm(String origin, String destination, String apiKey) throws IOException {
        String urlStr = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric" +
                "&origins=" + URLEncoder.encode(origin, "UTF-8") +
                "&destinations=" + URLEncoder.encode(destination, "UTF-8") +
                "&key=" + apiKey;

        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject element = json.getAsJsonArray("rows")
                .get(0).getAsJsonObject()
                .getAsJsonArray("elements")
                .get(0).getAsJsonObject();

        if (element.get("status").getAsString().equals("OK")) {
            return element.getAsJsonObject("distance").get("value").getAsDouble() / 1000.0; // meters to km
        }
        return Double.MAX_VALUE;
    }

    public List<Caretaker> findAvailableCaretakers(JobOffer jobOffer) {
        List<Caretaker> availableCaretakers = new ArrayList<>();

        try {
            Calendar start = jobOffer.getStartDate();
            int day = start.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
            int hour = start.get(Calendar.HOUR_OF_DAY);

            // Step 1: Get all caretakers who are available at that day/hour
            PreparedStatement avStmt = connection.prepareStatement(
                    "SELECT DISTINCT c.* FROM caretaker c " +
                            "JOIN availability a ON c.UserID = a.CaretakerID " +
                            "WHERE a.Day = ? AND a.Hour = ?");
            avStmt.setInt(1, day);
            avStmt.setInt(2, hour);
            ResultSet rs = avStmt.executeQuery();

            String apiKey = fetchGoogleApiKey();

            while (rs.next()) {
                System.out.println(
                        "\n? Checking caretaker: " + rs.getString("FirstName") + " " + rs.getString("LastName"));
                String caretakerID = rs.getString("UserID");
                String caretakerLocation = rs.getString("Location");
                System.out.println("    Location: " + caretakerLocation);
                System.out.println("  ? Checking availability for Day=" + day + ", Hour=" + hour);

                // Step 2: Calculate distance
                double distance = getDistanceInKm(jobOffer.getLocation(), caretakerLocation, apiKey);
                System.out.println("Distance to " + caretakerLocation + ": " + distance + " km");
                if (distance > 10.0)
                    continue;

                // Step 3: Add to list
                Caretaker ct = new Caretaker();
                ct.setUserID(UUID.fromString(caretakerID));
                ct.setLocation(caretakerLocation);
                ct.setPay(rs.getFloat("Pay"));
                ct.setFirstName(rs.getString("FirstName"));
                ct.setLastName(rs.getString("LastName"));

                availableCaretakers.add(ct);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableCaretakers;
    }

    // ******************************************//
    // Helper Functions //
    // ******************************************//

    public Calendar dateToCalendar(Date date) {
        return GregorianCalendar.from(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    public List<UUID> idListParser(Array idList) throws SQLException {
        return (List<UUID>) idList.getArray();
    }
}