package ch.hftm.notenrechner.business;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import ch.hftm.notenrechner.model.Exam;
import ch.hftm.notenrechner.model.Student;


@Singleton(name = "ReportService")
public class ReportingServiceBean implements ReportingServiceLocal {

	@EJB(name = "GradeCalculatorDB")
	GradeCalculatorDB db;

	@Resource
	private TimerService timerService;

	
	@Override
	public void scheduleReport(long interval) {
		timerService.createIntervalTimer(0, interval * 1000, new TimerConfig());
	}

	
	@Override
	public void cancelReports() {
		for (Timer timer : timerService.getTimers()) {
			timer.cancel();
		}
		System.out.println("TIMER CANCELED");
	}

	
	@Timeout
	public void createReport() {
		System.out.println("------------");
		for (Student student : db.getAllStudents()) {
			double sum = 0;
			for (Exam exam : student.getExams()) {
				sum += exam.getGrade();
			}
			double averageGrade = sum / student.getExams().size();
			System.out.println("Student: " + student.getStudentID() + " Notenschnitt: " + averageGrade);
		}
	}

	
	@Override
	public String testService() {
		return "TEST OK!!!";
	}

}
