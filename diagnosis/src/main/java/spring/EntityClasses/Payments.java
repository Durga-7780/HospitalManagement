package spring.EntityClasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Payments {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long sno;
	private String patientName;
	private long cost;
	private String modeOfPayment;
	private String date;
}
