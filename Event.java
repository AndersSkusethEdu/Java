import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Event {
    public static Connection getConnectionToEventDB() {
        String url = "jdbc:mysql://localhost:3306/eventDB";
        String username = "eventDB";
        String password = "eventDBpassword";

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Returnerer null da koden klager om den ikke er der.
    }

    public static void registerStudent(String name, String program) {
        Connection connection = getConnectionToEventDB();

        try {
            String query = "INSERT INTO students (name, program) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, program);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student added to register successfully.");
            } else {
                System.out.println("Failed to add student to register.");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Welcome back " + name + "!");
            EventManager.showUserMainMenu(name);
        }
    }

    public static void registerTeacher(String name, String program) {
        Connection connection = getConnectionToEventDB();

        try {
            String query = "INSERT INTO teachers (name, studyProgram) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, program);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Teacher added to register successfully.");
            } else {
                System.out.println("Failed to add student to register.");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Welcome back " + name + "!");
            EventManager.showUserMainMenu(name);
        }
    }

    public static int countStudents() {
        Connection connection = getConnectionToEventDB();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT name) AS totalStudents FROM students";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int totalStudents = resultSet.getInt("totalStudents");
                return totalStudents;
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int countGuests() {
        Connection connection = getConnectionToEventDB();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT guestName) AS totalGuests FROM guests";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int totalGuests = resultSet.getInt("totalGuests");
                return totalGuests;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int countTeachers() {
        Connection connection = getConnectionToEventDB();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT name) AS totalTeachers FROM teachers";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int totalTeachers = resultSet.getInt("totalTeachers");
                return totalTeachers;
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int countResponsibles() {
        Connection connection = getConnectionToEventDB();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT(DISTINCT programResponsible) AS totalResponsibles FROM studyPrograms";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int totalResponsibles = resultSet.getInt("totalResponsibles");
                return totalResponsibles;
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int countAllParticipants() {
        int totalParticipants = countStudents() + countGuests() + countTeachers() + countResponsibles();
        return totalParticipants;
    }

    public static void insertProgramsToArray() {
        Connection connection = getConnectionToEventDB();
        EventManager.studyPrograms.clear();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT program FROM students";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String program = resultSet.getString("program");
                EventManager.addProgram(program);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoGuestList(String guestName, String invitedBy) {
        Connection connection = getConnectionToEventDB();

        try {
            String query = "INSERT INTO guests (guestName, invitedBy) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, guestName);
            preparedStatement.setString(2, invitedBy);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Guest(s) added to register successfully.");
            } else {
                System.out.println("Failed to add Guest(s) to register.");
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(String name, String studyProgram) {
        Connection connection = getConnectionToEventDB();

        try {
            String query = "UPDATE students SET program = ? WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studyProgram);
            preparedStatement.setString(2, name);

            String query2 = "INSERT INTO studyPrograms (programName) VALUES (?)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement2.setString(1, studyProgram);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student added to register successfully.");
            } else {
                System.out.println("Failed to add student to register.");
            }
            EventManager.addProgram(studyProgram);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*Denne funksjonen er bare her for å rydde opp i tabellene mens  jeg testet produkte.
    public static void truncateTables() {
        Connection connection = getConnectionToEventDB();

        try {
            Statement statement = connection.createStatement();
            String[] tables = {"students", "guests"};

            for (String table : tables) {
                String query = "TRUNCATE TABLE " + table;
                statement.executeUpdate(query);
                System.out.println("Content removed from table: " + table);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    Her er funksjonen for å fylle databasene med navn og studieretninger

    private final boolean populateStudentDB() {
        String fileName = "studentnames.txt";
        ArrayList<String> studentNames = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                String studyProgram = Event.getRandomStudyProgram();
                Event.registerStudent(name, studyProgram);

            }
        } catch (FileNotFoundException e) {
            System.out.println("Failed to open the file: " + fileName);
            return false;
        }

        System.out.println(studentNames);
        return true;
    }

    private final boolean populateTeacherDB() {
        String fileName = "teachernames.txt";

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                String studyProgram = Event.getRandomStudyProgram();
                Event.registerTeacher(name, studyProgram);

            }
        } catch (FileNotFoundException e) {
            System.out.println("Failed to open the file: " + fileName);
            return false;
        }
        return true;

    public static String getRandomStudyProgram() {
        String[] STUDY_PROGRAMS = {"E-Business", "Front End", "Back End", "Cybersecurity"};

        Random random = new Random();
        int randomIndex = random.nextInt(STUDY_PROGRAMS.length);
        return STUDY_PROGRAMS[randomIndex];
    }
    }*/
}