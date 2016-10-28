package ch.hftm.notenrechner.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "studentID", "firstname", "lastname", "pin", "exams" })
public class Student {

	private int studentID;
	private String firstname;
	private String lastname;
	private int pin;
	private List<Exam> exams;

	public Student() {

	}

	public Student(int studentID, String firstname, String lastname, int pin) {
		super();
		this.studentID = studentID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.pin = pin;
		this.setExams(new ArrayList<Exam>());
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	// XmLElementWrapper generates a wrapper element around XML representation
	@XmlElementWrapper(name = "exams")
	// XmlElement sets the name of the entities
	@XmlElement(name = "exam")
	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

}
