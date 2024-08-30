package ServiceClasses;

import java.util.List;
import java.util.Map;

import EntityClasses.Addtest;
import EntityClasses.Inventory;
import EntityClasses.Parameter;
import EntityClasses.ParameterTable;
import EntityClasses.PatientDetails;
import EntityClasses.Payments;
import EntityClasses.Registration;
import EntityClasses.ReportData;
import EntityClasses.StockTotal;

public interface CrudService {
	void create(Registration reg);
	void delete(long id);
	void update(String id,String name,String username);
	void updatePatient(long id,String name,int age,String test,String dob);
	public boolean getDetails(String id);
	List<Registration> findAll();
	List<PatientDetails> getAllPatientDetails();
	List<PatientDetails> printPatientDetails();	// for setting icon
	List<ReportData> reportDetails(long id);
	public List<ReportData> getReportDetails();
	public PatientDetails getPatient(long id);
	boolean UserExist(String u);
	boolean User_Password(String u,String p);
	public void createPatient(PatientDetails d);
	List<Registration> getUserName_Id(String user);
	List<Payments> getPaymentsDetails();
	List<Payments> getdetails_DateSearch(String date);
	void createInventory(Inventory i);
	List<Inventory> getInventorydetails();
	void updateInventory(Inventory pd);
	List<StockTotal> getTotalStockDetails();
	void addTest(Addtest t);
	List<Addtest> getTestDetails();
	void updateTest(long sno,String testName,long cos);
	List<Parameter> getTestResultsByPatientIdAndTestName(Long patientId, Long id);
	Long getTestID(Long patientid);
	Map<String, ParameterTable> getTestJsonObjects(String t);
	void saveTest(Addtest t);
	void saveParameterTable(ParameterTable t);
	public boolean checkTestValue(String test);
    
}
