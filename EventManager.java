import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class EventManager {
    private static final ArrayList<String> student = new ArrayList<>();
    private static final HashMap<String, String> students = new HashMap<>();
    static ArrayList<String> studyPrograms = new ArrayList<>();
    private static Scanner scanner;


    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        showMainMenu();
    }

    private static void showMainMenu() {
        System.out.println("\nEvent Management System");
        System.out.println("1. Sign in");
        System.out.println("2. Print program");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter your name: ");
                String input = scanner.nextLine();
                signIn(input);
                break;

            case "2":
                System.out.println();
                printProgram();
                break;

            case "0":
                exit();
                break;

            /*case "12":
                Event.truncateTables();
                System.out.println("Tables have been cleared.");
                break;
*/
            default:
                System.out.println("Invalid choice. Please try again.");
                showMainMenu();
                break;
        }
    }

    static void showUserMainMenu(String name) {
        students.put(name, "null");
        System.out.println("\n" + name + "'s profile");
        System.out.println("1. Edit Profile");
        System.out.println("2. Show data");
        System.out.println("3. Print program");
        System.out.println("4. Invite guests");
        System.out.println("5. Show Students In Programs");
        System.out.println("0. Exit");

        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter your study program: ");
                String sProgram = scanner.nextLine();
                System.out.println("Account updated!");
                Event.updateStudent(name, sProgram);
                showUserMainMenu(name);
                break;

            case "2":
                System.out.println("Display information");
                System.out.println(name + " ");
                showUserMainMenu(name);
                break;

            case "3":
                System.out.println();
                printProgram();
                showUserMainMenu(name);


                break;

            case "4":
                System.out.println("You can invite 4 guests, how many do you want to add?");
                int amountOfGuests = scanner.nextInt();
                if (amountOfGuests <= 4) {
                    addGuests(amountOfGuests, name);
                    showUserMainMenu(name);
                } else {
                    System.out.println("That's too many!");
                    showUserMainMenu(name);

                    return;

                }

            case "5":
                System.out.println("Which program do you want to see?");
                System.out.println("1. E-Business");
                System.out.println("2. Front End");
                System.out.println("3. Back End");
                System.out.println("4. Cybersecurity");

                switch (scanner.nextLine()) {
                    case "1":
                        showStudent("E-Business");
                        break;

                    case "2":
                        showStudent("Front End");
                        break;

                    case "3":
                        showStudent("Back End");
                        break;

                    case "4":
                        showStudent("Cybersecurity");
                        break;
                }
                showUserMainMenu(name);

                break;

            case "0":
                exit();
                break;

            default:
                System.out.println("Invalid choice. Please try again.");
                showUserMainMenu(name);
                break;
        }
    }

    private static void signIn(String name) {
        student.add(name);
        System.out.println("Welcome " + name + "!");
        Event.registerStudent(name, "null");
        showUserMainMenu(name);
    }

    private static void showStudent(String program) {
        Connection connection = Event.getConnectionToEventDB();

        try {
            String query = "SELECT * FROM students WHERE program = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, program);

            ResultSet rs = preparedStatement.executeQuery();
            System.out.println();
            System.out.println("[" + program + "]");
            while (rs.next()) {
                String firstName = rs.getString("name");
                System.out.println("Name: " + firstName);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printProgram() {
        int totalParticipants = Event.countAllParticipants();

        System.out.println("Event Program: ");
        System.out.println("Introduction: 30 minutes");

        System.out.println("Study Programs: ");
        getPrograms();
        int totalDuration = 1 + Event.countStudents();
        System.out.println("\nTotal Duration: " + totalDuration + " minutes");
        int speeches = 1 + Event.countResponsibles();
        System.out.println("- Program Responsible's speeches: " + speeches + " minutes");
        System.out.println("- Participants: " + totalParticipants);

        System.out.println("5-minute break between study programs");
        System.out.println("Closing remarks: 15 minutes");
    }

    public static void getPrograms() {
        Connection connection = Event.getConnectionToEventDB();

        try {
            String query = "SELECT DISTINCT programName, programResponsible FROM studyPrograms;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String programName = resultSet.getString("programName");
                String programResponsible = resultSet.getString("programResponsible");
                System.out.println("[Program Name]: " + programName + ", [Responsible]: " + programResponsible);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve programs: " + e.getMessage());
        }
    }

    private static void exit() {
        System.out.println("Exiting the program...");
        scanner.close();
        System.exit(0);
    }

    public static void addProgram(String programName) {
        studyPrograms.add(programName);
    }

    public static void addGuests(int amountOfGuests, String invitedBy) {
        for (int i = 0; i < amountOfGuests; i++) {
            System.out.print("Please enter guest " + (i + 1) + "'s name: ");
            String name = scanner.next();

            Event.insertIntoGuestList(name, invitedBy);
        }
    }
}
