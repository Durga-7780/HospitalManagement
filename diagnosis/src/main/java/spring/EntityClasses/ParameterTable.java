package spring.EntityClasses;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ParameterTable {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String label;
	    private String result;
	    private String reference;
	    private String units;

	    @ManyToOne
	    @JoinColumn(name = "test_id")
	    private Addtest test;
	    
	   public ParameterTable(String parameterName,String result,String referenceValues,String units)
	   {
		this.label=parameterName;
		this.reference=referenceValues;
		this.result=result;
		this.units=units;
	   }
	}
