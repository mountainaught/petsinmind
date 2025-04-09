package com.petsinmind;

import com.petsinmind.messages.Message;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;
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