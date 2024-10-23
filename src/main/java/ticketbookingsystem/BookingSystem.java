package ticketbookingsystem;

import cipheredUser.Cipher;
import java.util.Scanner;

public class BookingSystem {
    private ShowManager showManager;
    private Scanner scanner;
    private DataManager dataManager;
    private Cipher cipher;

    public BookingSystem() {
        this.showManager = new ShowManager();
        this.scanner = new Scanner(System.in);
        this.dataManager = new DataManager();
        this.cipher = new Cipher();  // 使用 Cipher 类进行密码加密和验证
        initializeShows();
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("Welcome to the Movie Ticket Booking System");
            System.out.println("1. Login as Customer");
            System.out.println("2. Create New Customer");
            System.out.println("3. Exit");
            String input = getValidatedInput();  // Unified input method that allows "exit"
            
            switch (input) {
                case "1":
                    loginAsCustomer();
                    break;
                case "2":
                    createNewCustomer();
                    break;
                case "3":
                    System.out.println("Exiting system...");
                    dataManager.exit();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void initializeShows() {
        showManager.addMovieShow(new MovieShow("Inception", "2024-09-01", "8:00 PM", "Sci-Fi", 50));
        showManager.addMovieShow(new MovieShow("The Dark Knight", "2024-09-02", "6:00 PM", "Action", 40));
    }

    // 登录现有用户
    private void loginAsCustomer() {
        System.out.print("Enter your username (or type 'exit' to quit): ");
        String username = getValidatedInput();
        if (username.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        // Find if user exists
        Customer customer = dataManager.findCustomerByUsername(username);

        if (customer == null) {
            System.out.println("Username not found. Please create a new account.");
        } else {
            verifyCustomerLogin(customer);
        }
    }

    // 创建新用户并加密密码
    private void createNewCustomer() {
        System.out.print("Enter your username (or type 'exit' to quit): ");
        String username = getValidatedInput();
        if (username.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        System.out.print("Enter your email address (or type 'exit' to quit): ");
        String email = getValidatedInput();
        if (email.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        System.out.print("Enter your password (or type 'exit' to quit): ");
        String password = getValidatedInput();
        if (password.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        // 使用 Cipher 类加密密码并保存用户到数据库
        dataManager.saveUser(username, email, password, cipher);
        System.out.println("Account created successfully!");
    }

    // 验证用户密码
    private void verifyCustomerLogin(Customer customer) {
        System.out.print("Enter your password (or type 'exit' to quit): ");
        String password = getValidatedInput();
        if (password.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        // 验证密码是否正确
        if (dataManager.checkUserPassword(customer.getName(), password, cipher)) {
            System.out.println("Login successful!");
            displayCustomerMenu(customer);
        } else {
            System.out.println("Incorrect password. Please try again.");
        }
    }

    // 显示用户登录后的菜单
    private void displayCustomerMenu(Customer customer) {
        boolean loggedIn = true;
        while (loggedIn) {
            customer.displayMenu();
            String choice = getValidatedInput();

            switch (choice) {
                case "1":
                    bookTicket(customer);
                    break;
                case "2":
                    viewBookings(customer);
                    break;
                case "exit":
                    System.out.println("Exiting system...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Returning to main menu.");
                    loggedIn = false;
            }
        }
    }

    // 预定电影票
    private void bookTicket(Customer customer) {
        System.out.println("Available Movie Shows:");
        for (MovieShow show : showManager.getAvailableMovieShows()) {
            System.out.println(show);
        }

        System.out.print("Enter movie name (or type 'exit' to quit): ");
        String movieName = getValidatedInput();
        if (movieName.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        System.out.print("Enter show date (yyyy-mm-dd) (or type 'exit' to quit): ");
        String date = getValidatedInput();
        if (date.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        System.out.print("Enter show time (e.g., 8:00 PM) (or type 'exit' to quit): ");
        String time = getValidatedInput();
        if (time.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

        MovieShow selectedShow = showManager.selectMovieShow(movieName, date, time);
        if (selectedShow != null) {
            selectedShow.displayAvailableSeats();
            System.out.print("Enter seat number (or type 'exit' to quit): ");
            String seatNumber = getValidatedInput();
            if (seatNumber.equalsIgnoreCase("exit")) {
                System.exit(0);
            }

            if (selectedShow.isSeatAvailable(seatNumber)) {
                selectedShow.bookSeat(seatNumber);
                Ticket ticket = new Ticket(selectedShow, seatNumber, 12.00); // Example price
                Booking booking = new Booking(customer, ticket);
                booking.confirmBooking();
                dataManager.saveBooking(booking);
            } else {
                System.out.println("Seat not available. Please choose another seat.");
            }
        } else {
            System.out.println("Show not found or fully booked.");
        }
    }

    // 查看预定信息
    private void viewBookings(Customer customer) {
    System.out.println("Viewing Bookings for " + customer.getName());
    dataManager.loadBookings(customer.getName());  // Load bookings for the specific customer
}

    // 输入验证
    private String getValidatedInput() {
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Exiting system...");
            System.exit(0);
        }
        return input;
    }
}