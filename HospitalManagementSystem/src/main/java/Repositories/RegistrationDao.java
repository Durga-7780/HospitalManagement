package Repositories;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import EntityClasses.Addtest;
import EntityClasses.Inventory;
import EntityClasses.Parameter;
import EntityClasses.ParameterTable;
import EntityClasses.PatientDetails;
import EntityClasses.Payments;
import EntityClasses.Registration;
import EntityClasses.ReportData;
import EntityClasses.StockTotal;


@Repository
@Transactional
@SuppressWarnings({ "unchecked", "deprecation","rawtypes"})
public class RegistrationDao implements CrudOperations {

	@Autowired
	public SessionFactory fact;
	
	public Session ses()
	{
		return fact.getCurrentSession();
	}	
	@Override
	public void create(Registration reg) {
		ses().save(reg);
	}	
	@Override
	public void createPatient(PatientDetails pd)
	{   
			ses().save(pd);
			Payments p=new Payments();
			p.setPatientName(pd.getPatientName());
			p.setCost(pd.getCost());
			p.setDate(pd.getDate());
			p.setModeOfPayment(pd.getModeOfPayment());
			ses().save(p);
	}	
	@Override
	public void delete(long id) {
		PatientDetails pd=ses().get(PatientDetails.class, id);
		ses().delete(pd);
	}	
	@Override
	public void updatePatient(long id,String name,int age,String test,String dob) {
		PatientDetails p=ses().get(PatientDetails.class, id);
		p.setAge(age);
		p.setPatientName(name);
		p.setTest(test);
		p.setDate(dob);
		ses().update(p);
		ReportData rd=(ReportData) ses().createCriteria(ReportData.class).add(Restrictions.eq("patientid", id)).uniqueResult();
		rd.setPatientName(name);
		rd.setAge(age);
		rd.setTest(test);
		rd.setDate(dob);
		ses().update(rd);
		List<Parameter> pr=ses().createCriteria(Parameter.class).add(Restrictions.eq("patientid", id)).list();	
		for(Parameter pd:pr)
		{
			pd.setAge(age);
			pd.setPatientName(name);
			ses().update(pd);
		}
	}
	@Override
	public void update(String id,String name,String username) {
		System.out.println("id "+id);
		Query query = ses().createQuery("UPDATE Registration SET empname = :name,username=:user WHERE empid = :pid");
		query.setParameter("name", name);
		query.setParameter("user", username);
		query.setParameter("pid", id);
		query.executeUpdate();
	}	
	@Override
	public boolean getDetails(String id) {
		 Object re=ses().createQuery("SELECT empid FROM Registration  Where empid=:temp").setParameter("temp", id).uniqueResult();		 
		 return re!=null;
	}	
	@Override
	public List<Registration> findAll() {
		return ses().createCriteria(Registration.class).list();
	}
	
	@Override
	public boolean UserExist(String user)
	{
		Object id=ses().createQuery("SELECT r.username FROM Registration r Where r.username=:temp").setParameter("temp", user).uniqueResult();
		return id!=null;
	}
	@Override
	public boolean User_Password(String username, String password) {
		Object err=ses().createQuery("SELECT r.username,r.password FROM Registration r Where r.username=:temp and r.password=:pass")
				.setParameter("temp", username).setParameter("pass", password).uniqueResult();
		return err!=null;
	}	
	@Override
	public List<PatientDetails> getAllPatientDetails() {
		return ses().createCriteria(PatientDetails.class).add(Restrictions.isNull("report")).list();	// code changed here ...
	}
	@Override
	public List<PatientDetails> printPatientDetails(){
		return ses().createCriteria(PatientDetails.class).list();
	}
	@Override
	public PatientDetails getPatient(long id) {
		return (PatientDetails) ses().get(PatientDetails.class, id);
	}	
	@Override
	public List<ReportData> reportDetails(long id){
		PatientDetails pd=getPatient(id);
		ReportData rd=new ReportData();
		rd.setAge(pd.getAge());
		rd.setDate(pd.getDate());
		rd.setOtherTest(pd.getOtherTest());
		rd.setPatientid(pd.getId());
		rd.setPatientName(pd.getPatientName());
		rd.setRefBy(pd.getRefBy());
		rd.setTest(pd.getTest());
		rd.setReport(pd.getReport());
		ses().save(rd);
		pd.setReport("reported");		//	code changed here ..... 
		ses().update(pd);
		return ses().createCriteria(ReportData.class).list();
	}
	
	
	public List<ReportData> getReportDetails()
	{
		return ses().createCriteria(ReportData.class).list();
	}

	@Override
	public List<Registration> getUserName_Id(String user) {
		return ses().createCriteria(Registration.class).add(Restrictions.eq("username",user)).list();
	}


	@Override
	public List<Payments> getPaymentsDetails() {
		return ses().createCriteria(Payments.class).list();
	}

	@Override
	public List<Payments> getdetails_DateSearch(String date) {
		List<Payments> l;
		if(date=="")
		l=ses().createCriteria(Payments.class).list();
		else
		l= ses().createCriteria(Payments.class).add(Restrictions.eq("date",date)).list();
		return l;
	}

	@Override
	public void createInventory(Inventory i) {
		
		if(i.getQuantity().endsWith("L") || i.getQuantity().endsWith("Kg"))
		{
		ses().save(i);
	    StockTotal s = new StockTotal();
	    String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = :schemaName AND TABLE_NAME = :tableName";
	    BigInteger count = (BigInteger) ses().createNativeQuery(sql)
	    						  .setParameter("schemaName", "hospital")
	                              .setParameter("tableName", "StockTotal")
	                              .getSingleResult();
	    
		    if (count.longValue() == 0) {
		        s.setItem(i.getApperatus());
		        s.setQuantity(i.getQuantity());
		        ses().save(s);
		    }
		    else updateInventory(i);
		}
	}
	@Override
	public List<Inventory> getInventorydetails() {
		
		return ses().createCriteria(Inventory.class).list();
	}

	@Override
	public void updateInventory(Inventory i) {		
			if(i.getQuantity().endsWith("L") || i.getQuantity().endsWith("Kg"))
			{
				StockTotal s=new StockTotal();
		        String itemName = i.getApperatus();
		        StockTotal existingItem = ses().createQuery("FROM StockTotal WHERE item = :name", StockTotal.class)
		                                        .setParameter("name", itemName)
		                                        .uniqueResult();
		        String quantityStr = i.getQuantity();
		        double quantityValue = parseQuantity(quantityStr);
		        String quantityWithUnit = quantityValue + (quantityStr.endsWith("L") ? "L" : "Kg");
	
		        if (existingItem != null) {
		        	String newQuantity = addQuantities(existingItem.getQuantity(), quantityStr);
		            existingItem.setQuantity(newQuantity);
		            ses().update(existingItem);
		        } else {
		            s.setItem(i.getApperatus());
		            s.setQuantity(quantityWithUnit);
		            ses().save(s);
		        }
			} 
	}
	
	private String addQuantities(String existingQuantity, String newQuantity) {
	    double existingValue = parseQuantity(existingQuantity);
	    double newValue = parseQuantity(newQuantity);
	    double totalValue = existingValue + newValue;
	    String unit = existingQuantity.endsWith("L") ? "L" : "Kg"; 
	    return totalValue + unit;
	}

	private double parseQuantity(String quantity) {
	    if (quantity.endsWith("L")) {
	        return Double.parseDouble(quantity.substring(0, quantity.length() - 1).trim());
	    } else if (quantity.endsWith("Kg")) {
	        return Double.parseDouble(quantity.substring(0, quantity.length() - 2).trim());
	    }
	    throw new IllegalArgumentException("Invalid quantity format: " + quantity);
	}


	@Override
	public List<StockTotal> getTotalStockDetails() {
		
		return ses().createCriteria(StockTotal.class).addOrder(Order.asc("quantity")).list();
	}


	@Override
	public void addTest(Addtest t) {
		ses().save(t);
		
	}

	public List<Addtest> getTestDetails(){
		return ses().createQuery("From Addtest",Addtest.class).list();
	}
	
	public void updateTest(long sno,String testName,long cost) {
		    Addtest t = ses().get(Addtest.class, sno);
		    t.setTestName(testName);
		    t.setCost(cost);
		    ses().update(t);
	}
	
	public Long getTestID(Long id) {		
		 String hql = "SELECT p.pattestid FROM Parameter p WHERE p.patientid=:patientID";
	     Long query = (Long) ses().createQuery(hql).setParameter("patientID", id).uniqueResult();
         return query ;
	}
	
	public List<Parameter> getTestResultsByPatientIdAndTestName(Long testid, Long Patientid){
		   List<Parameter> results=null;
		   String hql = "SELECT p FROM Parameter p JOIN p.test t WHERE t.testID=:testid and p.patientid=:patid";
			
		   Query<Parameter> query = ses().createQuery(hql, Parameter.class);
		   query.setParameter("testid", testid);
		   query.setParameter("patid", Patientid);
		
		   results = query.list();
		   
		return results;
	}	
	public Map<String, ParameterTable> getTestJsonObjects(String testname)
	{
		List<Addtest> t=ses().createCriteria(Addtest.class).add(Restrictions.eq("testName", testname)).list();
		Long testId=(long) 0;
		for(Addtest a:t)
		{
			testId=a.getTest_id();
			break;
		}
		System.out.println("testId "+testId);
		List<ParameterTable> parameters = getParametersByTestId(testId);
	    Map<String, ParameterTable> parametersMap = new HashMap<>();
	    
	    for (ParameterTable param : parameters) {
	        parametersMap.put(param.getLabel(), param);
	    }
	    System.out.println(parametersMap);
	    return parametersMap;
	}
	public List<ParameterTable> getParametersByTestId(Long testId) {
		String hql = "SELECT new ParameterTable(p.label, p.result, p.reference, p.units) " +
	             "FROM ParameterTable p WHERE p.test.test_id = :testId";
	    return ses().createQuery(hql, ParameterTable.class)
	                .setParameter("testId", testId)
	                .list();
	}
	public void saveTest(Addtest t) {
		ses().save(t);
	}	
	public void saveParameterTable(ParameterTable t) {
		System.out.println(t);
		ses().save(t);
	}
}
