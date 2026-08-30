import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class EduScore {

    private Map<String, List<Double>> studentGrades;

    public EduScore() {
        studentGrades = new HashMap<>();
    }

    public void addGrade(String studentName, double grade)
            throws InvalidGradeException {

        if (grade < 0.0 || grade > 100.0) {
            throw new InvalidGradeException(
                    "Grade must be between 0 and 100."
            );
        }

        studentGrades.putIfAbsent(studentName, new ArrayList<>());

        studentGrades.get(studentName).add(grade);
    }

    public double getStudentAverage(String studentName) {

        if (!studentGrades.containsKey(studentName)) {
            throw new NoSuchElementException(
                    "Student does not exist: " + studentName
            );
        }

        List<Double> grades = studentGrades.get(studentName);

        double total = 0;

        for (double grade : grades) {
            total += grade;
        }

        return total / grades.size();
    }

    public double getHighestGrade(String studentName) {

        if (!studentGrades.containsKey(studentName)) {
            throw new NoSuchElementException(
                    "Student does not exist: " + studentName
            );
        }

        List<Double> grades = studentGrades.get(studentName);

        double highest = grades.get(0);

        for (double grade : grades) {
            if (grade > highest) {
                highest = grade;
            }
        }

        return highest;
    }


    public Map<String, List<Double>> getStudentGrades() {
        return studentGrades;
    }
}
