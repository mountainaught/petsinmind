package com.petsinmind;

import com.petsinmind.messages.Message;
import com.petsinmind.users.Caretaker;
import com.petsinmind.users.PetOwner;
import com.petsinmind.users.User;

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

}