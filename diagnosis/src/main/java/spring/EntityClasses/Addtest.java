package spring.EntityClasses;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Addtest {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long test_id; 

    private String testName;
    private String sampleType;
    private long cost;
    private String reportFormat;
    private String reportUnits;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ParameterTable> parameters = new ArrayList<>();
}

