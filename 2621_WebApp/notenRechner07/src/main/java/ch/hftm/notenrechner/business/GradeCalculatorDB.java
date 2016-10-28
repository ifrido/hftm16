package ch.hftm.notenrechner.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ch.hftm.notenrechner.model.Exam;
import ch.hftm.notenrechner.model.Student;

/**
 * @author Dominik Tschumi
 * @version 1.0
 */

@Startup
@Singleton(name="GradeCalculatorDB")
public class GradeCalculatorDB {

	private List<Student> students = new ArrayList<Student>();
	
	/**
	 * Initialisierung der Datensätze
	 */
	@PostConstruct
	public void init() {
		students.add(new Student(1, "Andrea", "A", 1001));
		students.add(new Student(2, "Berta", "B", 2002));
		students.add(new Student(3, "Cindy", "C", 3003));
		students.add(new Student(4, "Daniele", "D", 4004));

		Random random = new Random();
		for (Student student : students) {
			for (int i = 1; i <= 3; i++) {
				int maxPoints = random.nextInt(20 - 10) + 10;
				int reachedPoints = random.nextInt(maxPoints - 8) + 8;
				student.getExams().add(
						new Exam("Prüfung_" + i, maxPoints, reachedPoints, calculateGrade(maxPoints, reachedPoints)));
			}
		}
	}

	
	
	/**
	 * @return Liste mit allen vorhandenen Studenten  inkl. Prüfungen
	 */
	public List<Student> getAllStudents() {
		return students;
	}

	
	
	/**
	 * @param studentID 	StudentenID welche mit dem userID des Applikationsserves übereinstimmt
	 * @return				Student inkl. Prüfungen mit der angegebenen StudentenID
	 */
	public Student getStudent(int studentID) {
		for (Student student : students) {
			if (student.getStudentID() == studentID) {
				return student;
			}
		}
		return null;
	}

	
	
	/**
	 * @param student   Student zum Persistieren
	 * @return			Liste aller Studenten inkl dem gerade hinzugefügten
	 */
	public List<Student> addStudent(Student student) {
		students.add(student);
		System.out.println("GradeCalculatorDB.addStudent");
		return students;
	}

	
	

	public List<Exam> addExam(int studentID, Exam exam) {
		exam.setGrade(calculateGrade(exam.getMaxPoints(), exam.getReachedPoints()));
		getStudent(studentID).getExams().add(exam);
		System.out.println("GradeCalculatorDB.addExam");
		return getStudent(studentID).getExams();
	}

	

	public List<Exam> getExams(int studentID) {
		return getStudent(studentID).getExams();
	}

	

	public double calculateGrade(double maxPoints, double reachedPoints) {
		return Math.round((reachedPoints / maxPoints * 5 + 1) * 100) / 100.0;
	}

}
