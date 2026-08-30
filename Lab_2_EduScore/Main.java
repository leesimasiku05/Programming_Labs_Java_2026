import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EduScore eduScore = new EduScore();

        System.out.println("================================");
        System.out.println("       EDUSCORE SYSTEM");
        System.out.println("================================");

        System.out.print("How many students would you like to enter? ");
        int numberOfStudents;

        try {
            numberOfStudents = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a whole number.");
            scanner.close();
            return;
        }

        scanner.nextLine();

        for (int i = 0; i < numberOfStudents; i++) {

            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter student name: ");
            String studentName = scanner.nextLine();

            System.out.print("How many grades for " + studentName + "? ");

            int numberOfGrades;

            try {
                numberOfGrades = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number of grades.");
                scanner.nextLine();
                i--;
                continue;
            }

            for (int j = 0; j < numberOfGrades; j++) {

                System.out.print("Enter grade " + (j + 1) + ": ");

                try {

                    double grade = scanner.nextDouble();

                    eduScore.addGrade(studentName, grade);

                } catch (InvalidGradeException e) {

                    System.out.println("Invalid grade: " + e.getMessage());
                    j--;

                } catch (InputMismatchException e) {

                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();
                    j--;
                }
            }

            scanner.nextLine();
        }

        // Final report
        System.out.println("\n================================");
        System.out.println("          FINAL REPORT");
        System.out.println("================================");

        for (Map.Entry<String, List<Double>> entry :
                eduScore.getStudentGrades().entrySet()) {

            String studentName = entry.getKey();

            double highest = eduScore.getHighestGrade(studentName);
            double average = eduScore.getStudentAverage(studentName);

            System.out.println("\nStudent: " + studentName);
            System.out.println("Highest Grade: " + highest);
            System.out.printf("Average: %.2f%n", average);
        }

        scanner.close();
    }
}
