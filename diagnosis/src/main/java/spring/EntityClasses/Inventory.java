package spring.EntityClasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Inventory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long sno;
	private String name;
	private String apperatus;
	private String quantity;
	private String supplier;
	private long cost;
	private String paymentdate;
	
	public Inventory(String apparatus, String quantity) {
        this.apperatus = apparatus;
        this.quantity=quantity;
    }
}