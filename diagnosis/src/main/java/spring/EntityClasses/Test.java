package spring.EntityClasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Test {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long testID;
    private String testName;
}
