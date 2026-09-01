import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    // Create input tools and the EduScore system
    Scanner scanner = new Scanner(System.in);
    EduScore eduScore = new EduScore();

    // Display the system heading
    displayHeading();

    // Get the number of students
    int numberOfStudents = getNumberOfStudents(scanner);

    // Enter grades for each student
    for (int studentIndex = 0;
         studentIndex < numberOfStudents;
         studentIndex++) {

        enterStudentGrades(scanner, eduScore, studentIndex);
    }

    // Display the final results
    displayFinalReport(eduScore);

    // Close the scanner
    scanner.close();
  }

  // Displays the program heading
  private static void displayHeading() {

    System.out.println("================================");
    System.out.println("       EDUSCORE SYSTEM");
    System.out.println("================================");
  }

  // Gets the number of students from the user
  private static int getNumberOfStudents(Scanner scanner) {

    System.out.print("How many students would you like to enter? ");

    try {
        return scanner.nextInt();

    } catch (InputMismatchException e) {

        System.out.println(
                "Invalid input. Please enter a whole number."
        );

        return 0;
    }
  }

  // Enters grades for one student
  private static void enterStudentGrades(
        Scanner scanner,
        EduScore eduScore,
        int studentIndex) {

    scanner.nextLine();

    System.out.println("\nStudent " + (studentIndex + 1));

    System.out.print("Enter student name: ");
    String studentName = scanner.nextLine();

    System.out.print(
            "How many grades for " + studentName + "? "
    );

    int numberOfGrades;

    try {
        numberOfGrades = scanner.nextInt();

    } catch (InputMismatchException e) {

        System.out.println("Invalid number of grades.");
        scanner.nextLine();
        return;
    }

    // Enter each grade
    for (int gradeIndex = 0;
         gradeIndex < numberOfGrades;
         gradeIndex++) {

        enterGrade(scanner, eduScore, studentName, gradeIndex);
    }
  }

  // Gets and stores a student's grade
  private static void enterGrade(
        Scanner scanner,
        EduScore eduScore,
        String studentName,
        int gradeIndex) {

    System.out.print(
            "Enter grade " + (gradeIndex + 1) + ": "
    );

    try {

        double grade = scanner.nextDouble();

        eduScore.addGrade(studentName, grade);

    } catch (InvalidGradeException e) {

        System.out.println(
                "Invalid grade: " + e.getMessage()
        );

    } catch (InputMismatchException e) {

        System.out.println(
                "Invalid input. Please enter a number."
        );

        scanner.nextLine();
    }
  }

  // Displays the final student report
  private static void displayFinalReport(EduScore eduScore) {

    System.out.println("\n================================");
    System.out.println("          FINAL REPORT");
    System.out.println("================================");

    for (Map.Entry<String, List<Double>> entry :
            eduScore.getStudentGrades().entrySet()) {

        String studentName = entry.getKey();

        double highestGrade =
                eduScore.getHighestGrade(studentName);

        double averageGrade =
                eduScore.getStudentAverage(studentName);

        System.out.println("\nStudent: " + studentName);
        System.out.println("Highest Grade: " + highestGrade);
        System.out.printf("Average: %.2f%n", averageGrade);
    }
  }
}
