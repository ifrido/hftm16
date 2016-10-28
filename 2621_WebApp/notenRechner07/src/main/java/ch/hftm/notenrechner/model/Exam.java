package ch.hftm.notenrechner.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "exam")
@XmlType(propOrder = { "description", "maxPoints", "reachedPoints", "grade" })
public class Exam {

	private String description;
	private int maxPoints;
	private int reachedPoints;
	private double grade;

	public Exam() {

	}

	public Exam(String description, int maxPoints, int reachedPoints, double grade) {
		super();
		this.description = description;
		this.maxPoints = maxPoints;
		this.reachedPoints = reachedPoints;
		this.grade = grade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	public int getReachedPoints() {
		return reachedPoints;
	}

	public void setReachedPoints(int reachedPoints) {
		this.reachedPoints = reachedPoints;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

}
