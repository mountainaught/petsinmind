package com.petsinmind;

import com.petsinmind.messages.Message;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;
import com.petsinmind.users.SystemAdmin;
import com.petsinmind.users.User;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import com.google.gson.Gson;

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

    // // Method to authenticate a user
    // // draft ---> will return user class
    // public boolean authenticateUser(String username, String password) {
    // if (users.containsKey(username) && users.get(username)[0].equals(password)) {
    // System.out.println("Login successful!");
    // return true;
    // } else {
    // System.out.println("Invalid username or password.");
    // return false;
    // }
    // }
    //
    // // Method to add a new user
    // public boolean addUser(String username, String password, String email, String
    // phoneNumber, String firstName, String lastName) {
    // // Check if the user already exists
    // if (users.containsKey(username)) {
    // System.out.println("User already exists.");
    // return false;
    // } else {
    // // Create an array to store user details
    // // draft ---> will store user class instead of array
    // String[] newUser = {password, email, phoneNumber, firstName, lastName};
    // users.put(username, newUser);
    // System.out.println("User registered successfully!");
    // return true;
    // }
    // }

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

        if (user.getClass() == Caretaker.class) {
            ps = connection.prepareStatement("SELECT * FROM caretaker WHERE UserID = ?");
            ps.setString(1, user.getUserID().toString());
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

    public Pet getPet(Pet pet) {

    }

    public Appointment getAppointment(Appointment appointment) {

    }

    public JobOffer getJobOffer(JobOffer jobOffer) {

    }

    public Payment getPayment(Payment payment) {

    }

    public List<Payment> getPayments(User user) {

    }

    public List<Review> getReviews(Caretaker caretaker) {

    }

    public List<Ticket> getTickets(User user) {

    }

    public List<Message> getMessages(UUID ref) {

    }

    public Application getApplication(Application app) {

    }

    // ******************************************//
    // SQL Write Functions //
    // ******************************************//
    public int uploadImage(Connection connection, File imageFile, String imageFor) {
        int generatedImageID = -1; // Return -1 if the image upload fails
        String sql = "INSERT INTO images (image_data, content_type, image_for) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                FileInputStream fis = new FileInputStream(imageFile)) {
            ps.setBinaryStream(1, fis, (int) imageFile.length());
            String contentType = guessContentType(imageFile);
            ps.setString(2, contentType); // Set the content type based on the file extension
            ps.setString(3, imageFor); // Specify the purpose of the image (e.g.,"profile", "pet", etc.)

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                // Retrieve the auto-generated keys
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedImageID = rs.getInt(1); // Get the generated image ID
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return generatedImageID;
    }

    private String guessContentType(File file) {
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        // Fallback
        return "application/octet-stream";
    }

    public boolean createUser(User user, File imageFile) throws SQLException {
        PreparedStatement ps = null;
        if (user instanceof Caretaker) {
            Caretaker caretaker = (Caretaker) user;
            // Upload the image and get the generated image ID
            int imageID = 2;
            if (imageFile != null) {
                imageID = uploadImage(connection, imageFile, "caretaker");
            }

            String userid = caretaker.getUserID().toString();
            String username = caretaker.getUserName();
            String userpassword = caretaker.getUserPassword();
            String useremail = caretaker.getUserEmail();
            String phonenumber = caretaker.getPhoneNumber();
            String firstname = caretaker.getFirstName();
            String lastname = caretaker.getLastName();
            List<String> ticketIDs = caretaker.getTicketIDs();
            List<String> jobofferIDs = caretaker.getJobOfferIDs();
            String location = caretaker.getLocation();
            float pay = caretaker.getPay();
            List<String> appointmentIDs = caretaker.getAppointmentIDs();

            Gson gson = new Gson();
            String ticketIDsJson = gson.toJson(ticketIDs);
            String jobofferIDsJson = gson.toJson(jobofferIDs);
            String appointmentIDsJson = gson.toJson(appointmentIDs);
            String sql = "INSERT INTO caretaker " +
                    "(UserID, UserName, UserPassword, UserEmail, PhoneNumber, FirstName, LastName, " +
                    "ListTicketIDs, ListJobOfferIDs, Location, Pay, IMAGEID, ListAppointmentIDs) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
            ps.setInt(12, imageID); // IMAGEID
            ps.setString(13, appointmentIDsJson); // ListAppointmentIDs (stored as JSON)

            // Execute the insert statement
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            } else {
                return false;
            }
        } else if (user instanceof PetOwner) {
            PetOwner petowner = (PetOwner) user;
            int imageID = 2;
            if (imageFile != null) {
                imageID = uploadImage(connection, imageFile, "caretaker");
            }

            String userid = petowner.getUserID().toString();
            String username = petowner.getUserName();
            String userpassword = petowner.getUserPassword();
            String useremail = petowner.getUserEmail();
            String phonenumber = petowner.getPhoneNumber();
            String firstname = petowner.getFirstName();
            String lastname = petowner.getLastName();
            List<String> ticketIDs = petowner.getTicketIDs();
            List<String> jobofferIDs = petowner.getJobOfferIDs();
            List<String> petIDs = petowner.getPetIDs();
            List<String> appointmentIDs = petowner.getAppointmentIDs();
            String location = petowner.getLocation();

            Gson gson = new Gson();
            String ticketIDsJson = gson.toJson(ticketIDs);
            String jobofferIDsJson = gson.toJson(jobofferIDs);
            String petIDsJson = gson.toJson(petIDs);
            String appointmentIDsJson = gson.toJson(appointmentIDs);

            String sql = "INSERT INTO petowner " +
                    "(UserID, UserName, UserPassword, UserEmail, PhoneNumber, FirstName, LastName, " +
                    "ListTicketIDs, ListJobOfferIDs, ListPetIDs, Location, IMAGEID, ListAppointmentIDs) " +
                    "VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
            ps.setInt(12, imageID); // IMAGEID
            ps.setString(13, appointmentIDsJson); // ListAppointmentIDs (stored as JSON)

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
            String sql = "INSERT INTO caretaker (UserID, UserName, UserPassword, " +
                    "UserEmail, PhoneNumber, FirstName, LastName, Location, Pay) " +
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

    // ******************************************//
    // Implementations //
    // ******************************************//

    public List<Caretaker> findAvailableCaretakers(JobOffer jobOffer) {

    }

}