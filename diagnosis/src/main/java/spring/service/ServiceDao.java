package spring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.EntityClasses.Addtest;
import spring.EntityClasses.Inventory;
import spring.EntityClasses.Parameter;
import spring.EntityClasses.ParameterTable;
import spring.EntityClasses.PatientDetails;
import spring.EntityClasses.Payments;
import spring.EntityClasses.Registration;
import spring.EntityClasses.ReportData;
import spring.EntityClasses.StockTotal;
import spring.repositoryDao.CrudOperations;

@Service
public class ServiceDao implements CrudService{

	@Autowired
	private CrudOperations  register;
	
	@Override
	public void create(Registration reg) {
		register.create(reg);
	}
	
	public void createPatient(PatientDetails pd)
	{
		register.createPatient(pd);
	}
	@Override
	public void delete(long id) {
		
		register.delete(id);
	}

	@Override
	public void update(String id,String name,String username) {
		register.update(id,name,username);
	}
	
	@Override
	public void updatePatient(long id,String name,int age,String test,String dob) {
		register.updatePatient(id,name,age,test,dob);
	}

	@Override
	public boolean getDetails(String id) {
		
		return register.getDetails(id);
	}

	@Override
	public List<Registration> findAll() {
		return register.findAll();
	}

	
	@Override
	public boolean UserExist(String user)
	{
		return register.UserExist(user);
	}
	
	@Override
	public boolean User_Password(String username, String password) {
		return register.User_Password(username, password);
	}

	@Override
	public List<PatientDetails> getAllPatientDetails() {
		return register.getAllPatientDetails();
	}

	@Override
	public PatientDetails getPatient(long id) {
		return register.getPatient(id);
	}
	
	public List<PatientDetails> printPatientDetails(){
		return register.printPatientDetails();
	}
	@Override
	public List<ReportData> reportDetails(long id) {
		
		return register.reportDetails(id);
	}
	
	public List<ReportData> getReportDetails()
	{
			return register.getReportDetails();
	}

	@Override
	public List<Registration> getUserName_Id(String user) {
		
		return register.getUserName_Id(user);
	}
	
	@Override
	public List<Payments> getPaymentsDetails(){
		return register.getPaymentsDetails();
	}
	
	public List<Payments> getdetails_DateSearch(String date)
	{
		return register.getdetails_DateSearch(date);
	}

	@Override
	public void createInventory(Inventory i) {
		register.createInventory(i);
	}

	@Override
	public List<Inventory> getInventorydetails() {
		
		return register.getInventorydetails();
	}

	@Override
	public void updateInventory(Inventory i) {
		register.updateInventory(i);
		
	}

	@Override
	public List<StockTotal> getTotalStockDetails() {
		
		return register.getTotalStockDetails();
	}

	@Override
	public void addTest(Addtest t) {
		register.addTest(t);
	}
	
	public List<Addtest> getTestDetails(){
		return register.getTestDetails();
	}
	
	public void updateTest(long sno,String testName,long cost) {
		register.updateTest(sno,testName,cost);
	}
	public List<Parameter> getTestResultsByPatientIdAndTestName(Long patientId, Long id){
		return register.getTestResultsByPatientIdAndTestName(patientId, id);
	}
	public Long getTestID(Long patientid) {
		return register.getTestID(patientid);
	}
	
	public Map<String, ParameterTable> getTestJsonObjects(String testname) {
		return register.getTestJsonObjects(testname);
	}
	
	public void saveTest(Addtest t) {
        register.saveTest(t);
	}
	public void saveParameterTable(ParameterTable t) {
		register.saveParameterTable(t);
	}
	public boolean checkTestValue(String test) {
		return register.checkTestValue(test);
	}
}
