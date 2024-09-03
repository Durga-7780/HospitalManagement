package spring.repositoryDao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.EntityClasses.Addtest;
import spring.EntityClasses.Inventory;
import spring.EntityClasses.Parameter;
import spring.EntityClasses.ParameterTable;
import spring.EntityClasses.PatientDetails;
import spring.EntityClasses.Payments;
import spring.EntityClasses.Registration;
import spring.EntityClasses.ReportData;
import spring.EntityClasses.StockTotal;


@Repository
@Transactional
public class RegistrationDao implements CrudOperations {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Registration reg) {
        em.persist(reg);
    }

    @Override
    public void createPatient(PatientDetails pd) {
        em.persist(pd);
        Payments p = new Payments();
        p.setPatientName(pd.getPatientName());
        p.setCost(pd.getCost());
        p.setDate(pd.getDate());
        p.setModeOfPayment(pd.getModeOfPayment());
        em.persist(p);
    }

    @Override
    public void delete(long id) {
        PatientDetails pd = em.find(PatientDetails.class, id);
        if (pd != null) {
            em.remove(pd);
        }
    }

    @Override
    public void updatePatient(long id, String name, int age, String test, String dob) {
        PatientDetails p = em.find(PatientDetails.class, id);
        if (p != null) {
            p.setAge(age);
            p.setPatientName(name);
            p.setTest(test);
            p.setDate(dob);
            em.merge(p);

            List<ReportData> rd = em.createQuery("SELECT rd FROM ReportData rd WHERE rd.patientid = :id", ReportData.class)
                    .setParameter("id", id)
                    .getResultList();
            if (rd.isEmpty()) {
                System.out.println("No data found for patient ID " + id);
            } else {
            	for(ReportData r:rd) {
		            r.setPatientName(name);
		            r.setAge(age);
		            r.setTest(test);
		            r.setDate(dob);
		            em.merge(r);
            	}
            }
            List<Parameter> pr = em.createQuery("SELECT p FROM Parameter p WHERE p.patientid = :id", Parameter.class)
                    .setParameter("id", id)
                    .getResultList();
            if(pr!=null) {
	            for (Parameter pd : pr) {
	                pd.setAge(age);
	                pd.setPatientName(name);
	                em.merge(pd);
	            }
            }
            
            List<Payments> py=em.createQuery("Select p From Payments p where p.patientName=:pat",Payments.class).setParameter("pat",p.getPatientName()).getResultList();
            for(Payments pp:py)
            {
            
            	pp.setPatientName(p.getPatientName());
            	em.persist(pp);
            }
        }
    }

    @Override
    public void update(String id, String name, String username) {
        em.createQuery("UPDATE Registration r SET r.empname = :name, r.username = :user WHERE r.empid = :pid")
                .setParameter("name", name)
                .setParameter("user", username)
                .setParameter("pid", id)
                .executeUpdate();
    }

    @Override
    public boolean getDetails(String id) {
        Long count = em.createQuery("SELECT COUNT(r.empid) FROM Registration r WHERE r.empid = :temp", Long.class)
                .setParameter("temp", id)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<Registration> findAll() {
        return em.createQuery("SELECT r FROM Registration r", Registration.class).getResultList();
    }

    @Override
    public boolean UserExist(String user) {
        Long count = em.createQuery("SELECT COUNT(r.username) FROM Registration r WHERE r.username = :temp", Long.class)
                .setParameter("temp", user)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean User_Password(String username, String password) {
        Long count = em.createQuery("SELECT COUNT(r.username) FROM Registration r WHERE r.username = :temp AND r.password = :pass", Long.class)
                .setParameter("temp", username)
                .setParameter("pass", password)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<PatientDetails> getAllPatientDetails() {
        return em.createQuery("SELECT p FROM PatientDetails p WHERE p.report IS NULL", PatientDetails.class).getResultList();
    }

    @Override
    public List<PatientDetails> printPatientDetails() {
        return em.createQuery("SELECT p FROM PatientDetails p", PatientDetails.class).getResultList();
    }

    @Override
    public PatientDetails getPatient(long id) {
        return em.find(PatientDetails.class, id);
    }

    @Override
    public List<ReportData> reportDetails(long id) {
        PatientDetails pd = getPatient(id);
        System.out.println("reported patient name "+pd.getPatientName());
        ReportData rd = new ReportData();
        rd.setAge(pd.getAge());
        rd.setDate(pd.getDate());
        rd.setOtherTest(pd.getOtherTest());
        rd.setPatientid(pd.getId());
        rd.setPatientName(pd.getPatientName());
        rd.setRefBy(pd.getRefBy());
        rd.setTest(pd.getTest());
        rd.setReport(pd.getReport());
        em.persist(rd);
        pd.setReport("reported");
        em.merge(pd);
        return em.createQuery("SELECT r FROM ReportData r", ReportData.class).getResultList();
    }

    @Override
    public List<ReportData> getReportDetails() {
        return em.createQuery("SELECT r FROM ReportData r", ReportData.class).getResultList();
    }

    @Override
    public List<Registration> getUserName_Id(String user) {
        return em.createQuery("SELECT r FROM Registration r WHERE r.username = :user", Registration.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Payments> getPaymentsDetails() {
        return em.createQuery("SELECT p FROM Payments p", Payments.class).getResultList();
    }

    @Override
    public List<Payments> getdetails_DateSearch(String date) {
        if (date.isEmpty()) {
            return em.createQuery("SELECT p FROM Payments p", Payments.class).getResultList();
        } else {
            return em.createQuery("SELECT p FROM Payments p WHERE p.date = :dt", Payments.class)
                    .setParameter("dt", date)
                    .getResultList();
        }
    }

    @Override
    public void createInventory(Inventory i) {
        if (i.getQuantity().endsWith("L") || i.getQuantity().endsWith("Kg")) {
            em.persist(i);
            String itemName = i.getApperatus();
            StockTotal s = new StockTotal();
            
            boolean exists = em.createQuery("SELECT COUNT(s) FROM StockTotal s WHERE s.item = :name", Long.class)
                    .setParameter("name", itemName)
                    .getSingleResult() > 0;
                    
            List<StockTotal> list=em.createQuery("select st from StockTotal st", StockTotal.class).getResultList();
            
            int size=list.size();
            System.out.println("size is "+size);
            
            if (size==0 || !exists) {
            	System.out.println("at inventory method");
                s.setItem(i.getApperatus());
                s.setQuantity(i.getQuantity());
                em.persist(s);
            } else{
            	System.out.println("calling method");
                updateInventory(i);
            }
        }
    }

    @Override
    public List<Inventory> getInventorydetails() {
        return em.createQuery("SELECT i FROM Inventory i", Inventory.class).getResultList();
    }

    @Override
    public void updateInventory(Inventory i) {
        if (i.getQuantity().endsWith("L") || i.getQuantity().endsWith("Kg")) {
            StockTotal s = new StockTotal();
            String itemName = i.getApperatus();
            boolean exists = em.createQuery("SELECT COUNT(s) FROM StockTotal s WHERE s.item = :name", Long.class)
                    .setParameter("name", itemName)
                    .getSingleResult() > 0;
                    System.out.println("in method");

			if (exists) {
				System.out.println("in if condition");
			    StockTotal existingItem = em.createQuery("SELECT s FROM StockTotal s WHERE s.item = :name", StockTotal.class)
                    .setParameter("name", itemName)
                    .getSingleResult();

//            StockTotal existingItem = em.createQuery("SELECT s FROM StockTotal s WHERE s.item = :name", StockTotal.class)
//                    .setParameter("name", itemName)
//                    .getResultStream()
//                    .findFirst()
//                    .orElse(null);
//            
            
            
            String quantityStr = i.getQuantity();
            double quantityValue = parseQuantity(quantityStr);
            String quantityWithUnit = quantityValue + (quantityStr.endsWith("L") ? "L" : "Kg");

            if (existingItem != null) {
            	System.out.println("hi hlo");
                String newQuantity = addQuantities(existingItem.getQuantity(), quantityStr);
                System.out.println("get this  "+newQuantity);
                existingItem.setQuantity(newQuantity);
                em.merge(existingItem);
            } else {
                s.setItem(i.getApperatus());
                s.setQuantity(quantityWithUnit);
                em.persist(s);
            }
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
        return em.createQuery("SELECT s FROM StockTotal s ORDER BY s.quantity", StockTotal.class).getResultList();
    }

    @Override
    public void addTest(Addtest t) {
        em.persist(t);
    }

    public List<Addtest> getTestDetails() {
        return em.createQuery("FROM Addtest", Addtest.class).getResultList();
    }

    public void updateTest(long sno, String testName, long cost) {
        Addtest t = em.find(Addtest.class, sno);
        if (t != null) {
            t.setTestName(testName);
            t.setCost(cost);
            em.merge(t);
        }
    }

    public Long getTestID(Long id) {
        String jpql = "SELECT p.pattestid FROM Parameter p WHERE p.patientid = :patientID";
        return em.createQuery(jpql, Long.class)
                 .setParameter("patientID", id)
                 .getSingleResult();
    }

    public List<Parameter> getTestResultsByPatientIdAndTestName(Long testid, Long patientid) {
    	System.out.println("testid "+testid+" ptid "+patientid);
        String jpql = "SELECT p FROM Parameter p JOIN p.test t WHERE t.testID = :testid AND p.patientid = :patid and p.pattestid=:pat";
        TypedQuery<Parameter> query = em.createQuery(jpql, Parameter.class);
        query.setParameter("testid", testid);
        query.setParameter("pat", testid);
        query.setParameter("patid", patientid);
        return query.getResultList();
    }

    public Map<String, ParameterTable> getTestJsonObjects(String testname) {
        List<Addtest> t = em.createQuery("SELECT a FROM Addtest a WHERE a.testName = :t", Addtest.class)
                            .setParameter("t", testname)
                            .getResultList();
        Long testId = t.isEmpty() ? 0L : t.get(0).getTest_id();
        System.out.println("testId " + testId+" getReportunits "+t.get(0).getReportUnits()==null);

        if(t.get(0).getReportUnits()!=null)
        {
	        List<ParameterTable> parameters = getParametersByTestId(testId);
	        System.out.println("parametertable "+parameters);
	        Map<String, ParameterTable> parametersMap = new HashMap<>();
	
	        for (ParameterTable param : parameters) {
	            parametersMap.put(param.getLabel(), param);
	        }
	        System.out.println(parametersMap);
	        return parametersMap;
        }
        else {
        	 Map<String, ParameterTable> mp = new HashMap<>();
        	 mp.put("label",null);
        	return mp;
        }
    }

    public List<ParameterTable> getParametersByTestId(Long testId) {
        String jpql = "SELECT new ParameterTable(p.label, p.result, p.reference, p.units) FROM ParameterTable p WHERE p.test.test_id = :testId";
        return em.createQuery(jpql, ParameterTable.class)
                 .setParameter("testId", testId)
                 .getResultList();
    }

    public void saveTest(Addtest t) {
        em.persist(t);
    }

    public void saveParameterTable(ParameterTable t) {
        
        em.persist(t);
    }
    
    public boolean checkTestValue(String test)
    {
    	List<Addtest> l=em.createQuery("FROM Addtest Where testName=: test",Addtest.class).setParameter("test", test).getResultList();
    	for(Addtest a:l)
    	{
    		if(a.getTestName().equalsIgnoreCase(test))
    			return true;
    	}
    	return false;	
    }
}
