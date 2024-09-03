package spring.EntityClasses;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Parameter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parameterID;
    
    private String parameterLabel;
    private String referenceRange;
    private String units;
    private String resultValue;
    
    
    private Long pattestid;
    private long patientid;
    
    
    private String patientName;
    private String testDate;
    private long age;
    private String reportDate;
    private String refBy;
    private String testName;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="testID")
    private Test test;
}
