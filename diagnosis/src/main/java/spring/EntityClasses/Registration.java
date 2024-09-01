package spring.EntityClasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

import lombok.Data;

@Entity
@Data
@NamedQuery(name="showid",query="Select r.empid from Registration r where r.username=:user")
public class Registration {
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private long sno;
		
		private String empid;
		private String empname;
		private String username;
		private String password;
}
