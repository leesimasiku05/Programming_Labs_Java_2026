import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class EduScore {

  // Stores each student's grades
  private final Map<String, List<Double>> studentGrades;

  // Creates an empty grade record
  public EduScore() {
    studentGrades = new HashMap<>();
  }

  // Adds a grade to a student's record
  public void addGrade(String studentName, double grade)
        throws InvalidGradeException {

    // Check that the student name is valid
    if (studentName == null || studentName.trim().isEmpty()) {
        throw new IllegalArgumentException(
                "Student name cannot be empty."
        );
    }

    // Check that the grade is between 0 and 100
    if (grade < 0.0 || grade > 100.0) {
        throw new InvalidGradeException(
                "Grade must be between 0 and 100."
        );
    }

    // Create a grade list if the student is new
    studentGrades
            .computeIfAbsent(studentName, name -> new ArrayList<>())
            .add(grade);
  }

  // Calculates a student's average grade
  public double getStudentAverage(String studentName) {

    List<Double> grades = getGradesForStudent(studentName);

    if (grades.isEmpty()) {
        throw new NoSuchElementException(
                "Student has no grades: " + studentName
        );
    }

    double totalGrades = 0;

    // Add all grades together
    for (double grade : grades) {
        totalGrades += grade;
    }

    return totalGrades / grades.size();
  }

  // Finds a student's highest grade
  public double getHighestGrade(String studentName) {

    List<Double> grades = getGradesForStudent(studentName);

    if (grades.isEmpty()) {
        throw new NoSuchElementException(
                "Student has no grades: " + studentName
        );
    }

    double highestGrade = grades.get(0);

    // Find the highest grade
    for (double grade : grades) {
        if (grade > highestGrade) {
            highestGrade = grade;
        }
    }

    return highestGrade;
  }

  // Gets the grades for a specific student
  private List<Double> getGradesForStudent(String studentName) {

    if (!studentGrades.containsKey(studentName)) {
        throw new NoSuchElementException(
                "Student does not exist: " + studentName
        );
    }

    return studentGrades.get(studentName);
  }

  // Returns all student grades
  public Map<String, List<Double>> getStudentGrades() {
    return studentGrades;
  }
}
