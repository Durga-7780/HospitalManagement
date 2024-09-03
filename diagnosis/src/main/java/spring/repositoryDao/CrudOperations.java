package spring.repositoryDao;

import java.util.List;
import java.util.Map;

import spring.EntityClasses.Addtest;
import spring.EntityClasses.Inventory;
import spring.EntityClasses.Parameter;
import spring.EntityClasses.ParameterTable;
import spring.EntityClasses.PatientDetails;
import spring.EntityClasses.Payments;
import spring.EntityClasses.Registration;
import spring.EntityClasses.ReportData;
import spring.EntityClasses.StockTotal;

public interface CrudOperations {
	void create(Registration reg);
	void delete(long id);
	void update(String id,String name,String username);
	void updatePatient(long id,String name,int age,String test,String dob);
	public boolean getDetails(String id);
	List<PatientDetails> printPatientDetails();
	public PatientDetails getPatient(long id);
	List<ReportData> reportDetails(long id);
	public List<ReportData> getReportDetails();
	List<Registration> findAll();
	boolean UserExist(String user);
	boolean User_Password(String username,String password);
	void createPatient(PatientDetails p);
	List<PatientDetails> getAllPatientDetails();
	List<Registration> getUserName_Id(String user);
	List<Payments> getPaymentsDetails();
	List<Payments>getdetails_DateSearch(String date);
	void createInventory(Inventory i);
	List<Inventory> getInventorydetails();
	void updateInventory(Inventory i);
	List<StockTotal> getTotalStockDetails();
	void addTest(Addtest t);
	List<Addtest> getTestDetails();
	void updateTest(long sno,String testName,long cost);
	List<Parameter> getTestResultsByPatientIdAndTestName(Long patientId, Long id);
	Long getTestID(Long patientid);
	Map<String, ParameterTable> getTestJsonObjects(String test);
	void saveTest(Addtest t);
	void saveParameterTable(ParameterTable t);
	public boolean checkTestValue(String test);
}
