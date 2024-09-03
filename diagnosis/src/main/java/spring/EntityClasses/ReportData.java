package spring.EntityClasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ReportData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long sno;
	private long patientid; 
	private String patientName;
	private long age;
	private String refBy;
	private String date;
	private String test;
	private String otherTest;
	private String report;
}
