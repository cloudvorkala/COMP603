package ticketbookingsystem;

import cipheredUser.Cipher;
import java.sql.*;

public class DataManager {
    private static final String DB_URL = "jdbc:derby:ticketDB;create=true";  // Derby Database URL

    // Ensure database and tables are created
    public DataManager() {
        createDatabaseAndTable();
    }

    // Create USERS and BOOKINGS tables if they don't exist
    private void createDatabaseAndTable() {
    try (Connection conn = DriverManager.getConnection(DB_URL);
         Statement stmt = conn.createStatement()) {

        // 获取数据库的元数据
        DatabaseMetaData dbMetaData = conn.getMetaData();

        // 检查 USERS 表是否存在
        ResultSet usersTable = dbMetaData.getTables(null, null, "USERS", null);
        if (!usersTable.next()) {  // 如果表不存在，则创建表
            String createUsersTableSQL = "CREATE TABLE USERS ("
                    + "username VARCHAR(255) PRIMARY KEY,"
                    + "email VARCHAR(255),"
                    + "encrypted_password VARCHAR(255))";
            stmt.executeUpdate(createUsersTableSQL);
            System.out.println("USERS table created successfully.");
        } else {
            System.out.println("USERS table already exists.");
        }

        // 检查 BOOKINGS 表是否存在
        ResultSet bookingsTable = dbMetaData.getTables(null, null, "BOOKINGS", null);
        if (!bookingsTable.next()) {  // 如果表不存在，则创建表
            String createBookingsTableSQL = "CREATE TABLE BOOKINGS ("
                    + "username VARCHAR(255),"
                    + "movie_name VARCHAR(255),"
                    + "show_date VARCHAR(255),"
                    + "show_time VARCHAR(255),"
                    + "seat_number VARCHAR(10),"
                    + "price DOUBLE,"
                    + "FOREIGN KEY (username) REFERENCES USERS(username))";
            stmt.executeUpdate(createBookingsTableSQL);
            System.out.println("BOOKINGS table created successfully.");
        } else {
            System.out.println("BOOKINGS table already exists.");
        }

    } catch (SQLException ex) {
        System.out.println("Error initializing database: " + ex.getMessage());
    }
}


    // Save booking to the database
    public void saveBooking(Booking booking) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO BOOKINGS (username, movie_name, show_date, show_time, seat_number, price) VALUES (?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, booking.getCustomer().getName());
            ps.setString(2, booking.getTicket().getMovieShow().getMovieName());
            ps.setString(3, booking.getTicket().getMovieShow().getDate());
            ps.setString(4, booking.getTicket().getMovieShow().getTime());
            ps.setString(5, booking.getTicket().getSeatNumber());
            ps.setDouble(6, booking.getTicket().getPrice());
            ps.executeUpdate();

            System.out.println("Booking saved successfully.");

        } catch (SQLException ex) {
            System.out.println("Error saving booking: " + ex.getMessage());
        }
    }

    // Load bookings for a specific customer
    public void loadBookings(String username) {
    try (Connection conn = DriverManager.getConnection(DB_URL);
         PreparedStatement ps = conn.prepareStatement(
            "SELECT movie_name, show_date, show_time, seat_number, price FROM BOOKINGS WHERE username = ?")) {

        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        System.out.println("Bookings for " + username + ":");
        while (rs.next()) {
            String movieName = rs.getString("movie_name");
            String showDate = rs.getString("show_date");
            String showTime = rs.getString("show_time");
            String seatNumber = rs.getString("seat_number");
            double price = rs.getDouble("price");

            System.out.println("Movie: " + movieName + ", Date: " + showDate + ", Time: " + showTime +
                               ", Seat: " + seatNumber + ", Price: $" + price);
        }

    } catch (SQLException ex) {
        System.out.println("Error loading bookings: " + ex.getMessage());
    }
}

    // Save user with encrypted password into the database
    public void saveUser(String username, String email, String plainTextPassword, Cipher cipher) {
        String encryptedPassword = cipher.encryptMessage(plainTextPassword);  // Encrypt password using Cipher class
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO USERS (username, email, encrypted_password) VALUES (?, ?, ?)")) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, encryptedPassword);  // Save the encrypted password
            ps.executeUpdate();

            System.out.println("User saved successfully.");

        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23505")) {  // SQL state 23505 corresponds to a unique constraint violation (duplicate username)
                System.out.println("Error: Username already exists.");
            } else {
                System.out.println("Error saving user: " + ex.getMessage());
            }
        }
    }

    // Find user by username in the database
    public Customer findCustomerByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(
                "SELECT username, email, encrypted_password FROM USERS WHERE username = ?")) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String encryptedPassword = rs.getString("encrypted_password");

                // Return a new Customer object with the retrieved data
                return new Customer(username, email, encryptedPassword);
            } else {
                // Return null if user is not found
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("Error finding customer: " + ex.getMessage());
            return null;
        }
    }

    // Check user password
     // Check user password by decrypting the stored password
    public boolean checkUserPassword(String username, String inputPassword, Cipher cipher) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(
                "SELECT encrypted_password FROM USERS WHERE username = ?")) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedEncryptedPassword = rs.getString("encrypted_password");

                // Use Cipher to check if the input password matches the decrypted stored password
                return cipher.checkPassword(username, inputPassword);
            } else {
                System.out.println("User not found.");
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Error checking password: " + ex.getMessage());
            return false;
        }
    }
    //testing 
    public void viewUsersTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM USERS")) {

            System.out.println("USERS table data:");
            while (rs.next()) {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String encryptedPassword = rs.getString("encrypted_password");
                System.out.println("Username: " + username + ", Email: " + email + ", Encrypted Password: " + encryptedPassword);
            }

        } catch (SQLException ex) {
            System.out.println("Error viewing USERS table: " + ex.getMessage());
        }
    }

    // Exit and close connections if necessary (optional for embedded Derby DB)
    public void exit() {
        System.out.println("Exiting system...");
    }
}