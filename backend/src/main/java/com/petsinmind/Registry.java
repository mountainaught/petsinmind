package com.petsinmind;

import com.petsinmind.messages.Message;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;
import com.petsinmind.users.SystemAdmin;
import com.petsinmind.users.User;
// import com.petsinmind.users.Application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
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
    public int uploadImage(Connection connection, File imageFile, String imageFor) throws SQLException {
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

    private String guessContentType(File file) throws SQLException {
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
            List<String> ticketIDs = caretaker.getListTicketIDs();
            List<String> jobofferIDs = caretaker.getListJobOfferIDs();
            String location = caretaker.getLocation();
            float pay = caretaker.getPay();
            List<String> appointmentIDs = caretaker.getListAppointmentIDs();

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
            List<String> ticketIDs = petowner.getListTicketIDs();
            List<String> jobofferIDs = petowner.getListJobOfferIDs();
            List<String> petIDs = petowner.getPetIDs();
            List<String> appointmentIDs = petowner.getListAppointmentIDs();
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
            List<String> ticketIDs = caretaker.getListTicketIDs();
            List<String> jobofferIDs = caretaker.getListJobOfferIDs();
            String location = caretaker.getLocation();
            float pay = caretaker.getPay();
            List<String> appointmentIDs = caretaker.getListAppointmentIDs();

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
            List<String> ticketIDs = petowner.getListTicketIDs();
            List<String> jobofferIDs = petowner.getListJobOfferIDs();
            List<String> petIDs = petowner.getPetIDs();
            List<String> appointmentIDs = petowner.getListAppointmentIDs();
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
            List<String> petIDs = jobOffer.getPetIDs();
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

    public boolean editAvailability(Caretaker ct) throws SQLException {
        boolean flag = deleteAvailabilitypoints(ct);
        if (flag) {
            return addAvailabilitypoints(ct);
        } else {
            return false;
        }
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

}