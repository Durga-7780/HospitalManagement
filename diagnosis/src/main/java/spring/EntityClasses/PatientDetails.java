package spring.EntityClasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class PatientDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id; 
	private String patientName;
	private String phoneNumber;
	private long age;
	private String sex;
	private String refBy;
	private String date;
	private String test;
	private long cost;
	private String modeOfPayment;
	private String otherTest;
	private String report;
	private String sampleType;
}
