package spring.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import spring.EntityClasses.Addtest;
import spring.EntityClasses.Inventory;
import spring.EntityClasses.Parameter;
import spring.EntityClasses.ParameterTable;
import spring.EntityClasses.PatientDetails;
import spring.EntityClasses.Payments;
import spring.EntityClasses.Registration;
import spring.EntityClasses.ReportData;
import spring.EntityClasses.StockTotal;
import spring.EntityClasses.Test;
import spring.service.CrudService;

@Controller
public class LogController {

	@GetMapping("/")
    public String dynamiclogin() {
        return "login";
    }
	
	@Autowired
	public CrudService service;
	
//  registration validation id
	@PostMapping("/check/{id}")
	public ResponseEntity<String> check_Id(Model m,@PathVariable("id") String id)
	{    
		 boolean obj=service.getDetails(id);
		 if(obj) {
		 	  return ResponseEntity.ok("found");
		 }
		 else
		 return ResponseEntity.ok("notfound");
	}

//	registration validation username
	@PostMapping("/checkUser/{user}")
	public ResponseEntity<String> check_User(@PathVariable("user") String username)
	{    
		 boolean obj=service.UserExist(username);
		 if(obj) {
		 	  return ResponseEntity.ok("found");
		 }
		 else
		 return ResponseEntity.ok("notfound");
	}
	
	
	
//	---------------------------------------------------------Main------------------------------------------------------------
		private String user;
		String id;
		
		@GetMapping("/patTab")
		public String MoveToPatient(Model m)
		{
			List<Addtest> li=service.getTestDetails();
			m.addAttribute("test",li);
			return "PatientDetailsJSP";
		}
		
		@GetMapping("/logout")
		public String login()
		{
			return "login";
		}
	
	//	login details submission
		@PostMapping(path="/login",consumes="application/json")
		public ResponseEntity<String> register(@RequestBody Registration reg)
		{
			service.create(reg);
			return ResponseEntity.ok("login");
		}
		
		@GetMapping(path="/loginDir")
		public String loginDir()
		{
			return "login";
		}
		
		@GetMapping(path="/registrationPage")
		public String registrationPage()
		{
			return "registration";
		}
	
	//	Login validation	
		@PostMapping("/checkLoginDetails/{user}/{pass}")
		public ResponseEntity<String> dashboard01(@PathVariable("user") String username,@PathVariable("pass") String pass)
		{
			boolean obj=service.User_Password(username, pass);
			if(obj)
			 return ResponseEntity.ok("found");
			else return ResponseEntity.ok("notfound"); 
		}

//	Dashboard
	
		@GetMapping("/dashboard/{user}")
		public String dashboardWithDetails(@PathVariable("user") String username,Model model) {
			user=username;
			return "redirect:/dashboard"; 
		}

	 @GetMapping(path="/dashboard")
	    public String goToDashboard(Model model) {
		    List<Registration> li=service.getUserName_Id(user);
		    if(li.size()!=0) {
			 	model.addAttribute("name",li.get(0).getEmpname());
			 	model.addAttribute("empid",li.get(0).getEmpid());
		    }
		    else
		    {
		    	model.addAttribute("name","");
			 	model.addAttribute("empid","");
		    }
	        return "dashboard"; 
	    }
	
//  Patient Details submission and printing
	 
	@PostMapping(path="/addPatient")
	public ResponseEntity<String> addPatient(@RequestBody PatientDetails pd)
	{
		service.createPatient(pd);
		return ResponseEntity.ok("found");
	}
	
	@GetMapping(path="/sendFormData")
	public String sendFormData(Model m,PatientDetails pd)
	{
		List<PatientDetails> li=service.getAllPatientDetails();
		m.addAttribute("list",li);
		return "printPatientDetails" ;
	}

//  PatientDetails
	 
	@GetMapping(path="/navigateTotests")
	public String navigateTotests(Model m,PatientDetails pd)
	{
		List<PatientDetails> li=service.getAllPatientDetails();
		m.addAttribute("list",li);
		return "printPatientDetails";
	}
	
	@GetMapping(path="/reportPage/{id}")
	public String goToReport(RedirectAttributes redirectAttributes,Model m,@PathVariable("id") long id)
	{
		service.reportDetails(id);
		List<PatientDetails> li=service.getAllPatientDetails();
		redirectAttributes.addFlashAttribute("list",li);
		return "redirect:/navigateTotests";
	}
	
	@GetMapping(path="/Refreshreport")
	public String RefreshReport(Model m)
	{
		List<ReportData> li=service.getReportDetails();
		m.addAttribute("list",li);
		return "reports";
	}
	
//	Payments
	
	@GetMapping(path="/goPayment")
	public String goPayment(Model m)
	{
		List<Payments> li=service.getPaymentsDetails();		
		long cash=0,card=0,upi=0;		
	    for (Payments payment : li) {
	        if ("cash".equalsIgnoreCase(payment.getModeOfPayment())) {
	            cash ++;
	        } else if ("upi".equalsIgnoreCase(payment.getModeOfPayment())) {
	            upi ++;
	        } else if ("card".equalsIgnoreCase(payment.getModeOfPayment())) {
	            card ++;
	        }
	    }
		m.addAttribute("list",li);
		m.addAttribute("cash",cash);
		m.addAttribute("card",card);
		m.addAttribute("upi",upi);
		return "payments";
	}
	
	 @GetMapping("/filterPaymentsByDate")
	 public @ResponseBody Map<String, Object> filterPaymentsByDate(@RequestParam("date") String date) {
		 	List<Payments>paymentsList;
		 	if(date!="") {
		     paymentsList = service.getdetails_DateSearch(date);
		 	}
		 	else {
		 		paymentsList=service.getdetails_DateSearch(date);
		 	}
		    double totalCash = 0;
		    double totalUPI = 0;
		    double totalCard = 0;
		    for (Payments payment : paymentsList) {
		        if ("cash".equalsIgnoreCase(payment.getModeOfPayment())) {
		            totalCash ++;
		        } else if ("upi".equalsIgnoreCase(payment.getModeOfPayment())) {
		            totalUPI ++;
		        } else if ("card".equalsIgnoreCase(payment.getModeOfPayment())) {
		            totalCard ++;
		        }
		    } 
		    Map<String, Object> response = new HashMap<>();
		    response.put("payments", paymentsList);
		    response.put("cash", totalCash);
		    response.put("upi", totalUPI);
		    response.put("card", totalCard);

		    return response;
	    }
//	 Inventory
	 
		 @GetMapping(path="/displayInventory")
		 public String inventory(Model m)
		 {
			 List<Inventory> li=service.getInventorydetails();
			 m.addAttribute("list",li);
			 return "inventory";
		 }
		 
		 @PostMapping(path="/addinventory")
		 public String addinventory(Inventory i)
		 {
			 service.createInventory(i);
			 System.out.println("stored...");
			 return "redirect:/displayInventory";
		 }
	 
	     @GetMapping("/inventoryXLsheet")
	     public ResponseEntity<List<Inventory>> getInventoryData() {
	         List<Inventory> li=service.getInventorydetails();
	         return ResponseEntity.ok(li);
	     }
	     
	     @GetMapping(path="/stockinventoryDetails")
	     public String stockinventory(Model m)
	     {
	    	 List<StockTotal> li=service.getTotalStockDetails();
	    	 m.addAttribute("list",li);
	    	 return "stockinventory";
	     }
	     
	     @PostMapping(path="/updateStock")
	     public String updateStock(Inventory i)
	     {
	    	 service.updateInventory(i);
	    	 return "redirect:stockinventoryDetails";
	     }
	     
// new test
	     @GetMapping(path="newtest")
	     public String newtest()
	     {
	    	 return "new_test";
	     }
	     
	     
	     
	     @GetMapping(path="/getTestData")
	     public @ResponseBody List<Map<String,Object>> getTestData() { 
	         List<Addtest> testData = service.getTestDetails();
	         List<Map<String,Object>> li=new ArrayList<Map<String,Object>>();
	         for(Addtest l:testData) {
	         Map<String,Object> mp=new HashMap<String,Object>();
	         mp.put("testName", l.getTestName());
	         mp.put("cost", l.getCost());
	         li.add(mp);
	         }
	         System.out.println(li);
	         return li;
	     }
	     
//employees
	     @GetMapping(path="/employees")
	     public String employees(Model m)
	     {
	    	 List<Registration> li=service.findAll();
	    	 m.addAttribute("employees",li);
	    	 return "employees";
	     }
	     
	     @PostMapping(path = "/updateemployee")
	     public ResponseEntity<String> updateEmployee(@RequestBody Map<String, Object> payload) {
	    	 	System.out.println("call came ");
	    	    String newId = payload.get("old_employee_id").toString();
	    	    System.out.println(newId);
	    	    String employeeName = payload.get("employee_name").toString();
	    	    String username = payload.get("username").toString();

	    	    service.update(newId,employeeName,username);

	         return ResponseEntity.ok("Employee updated successfully");
	     }
//checkpatients
	     
	     @GetMapping(path="/checkpatients")
	     public String checkpatients(Model m)
	     {
	    	 List<PatientDetails> p=service.printPatientDetails();
	    	 m.addAttribute("patientdetails",p);
	    	 return "checkPatient";
	     }
	     @PostMapping(path="/updatePatient")
	 	public String updatePatient(@RequestParam long patient_id, @RequestParam String patient_name,@RequestParam int age,@RequestParam String test,@RequestParam String dob)
	 	{ 
	    	service.updatePatient(patient_id,patient_name, age, test, dob);
	 		return "redirect:/checkpatients";
	 	}
	     
//testIcon
	     @GetMapping(path="/addtesticon")
	     public String getTestData(Model m)
	     {
	    	 List<Addtest> li=service.getTestDetails();
	    	 m.addAttribute("test",li);
	    	 return "addtestIcon";
	     }
	     @PostMapping(path="/updateTest")
	     public String updateTest(@RequestParam long sno, @RequestParam String testName, @RequestParam long cost)
	     {
	    	 service.updateTest(sno,testName,cost);
	    	 return "redirect:/addtesticon";
	     }
	     
//	     report data saving
	          
	     @PersistenceContext
	     private EntityManager entityManager;

	     @Transactional
	     @PostMapping("/generatereport")
	     public ResponseEntity<String> submitReport(@RequestBody Map<String, Object> data) {
	         Long patientId = Long.valueOf((String) data.get("patient_id"));
	         
	         @SuppressWarnings("unchecked")
			 Map<String, Object> report = (Map<String, Object>) data.get("report");
	         String testName = (String) data.get("testName");
	         String testDate = (String) data.get("date");
	         String patientName=(String) data.get("patientName");
	         System.out.println("patientName ----------------->>>> "+patientName);
	         long age=Long.parseLong(data.get("age")+"");
	         String refBy=(String) data.get("refBy");
	         
	         Test t=new Test();
	          t.setTestName(testName);
	         entityManager.persist(t);
	         for (Map.Entry<String, Object> entry : report.entrySet()) {
	             String parameterLabel = entry.getKey();
	             @SuppressWarnings("unchecked")
				Map<String, String> parameterData = (Map<String, String>) entry.getValue();
	             Parameter p=new Parameter();
	             p.setParameterLabel(parameterLabel);
	             p.setReferenceRange(parameterData.get("reference"));
	             p.setPattestid(t.getTestID());
	             p.setPatientid(patientId);
	             p.setAge(age);
	             p.setRefBy(refBy);
	             p.setPatientName(patientName);
	             
	             p.setReportDate(testDate);		
	             p.setTestDate(testDate);
	             p.setTestName(testName);
	             p.setTest(t);
	             p.setUnits(parameterData.get("units"));
	             p.setResultValue(parameterData.get("value"));
	             entityManager.persist(p);
	             
	         }

	         return ResponseEntity.ok("Report submitted successfully");
	     }
    
	     @PostMapping(path="/report/data")
	     public @ResponseBody Map<String, Object>  getTestResultsByPatientIdAndTestName(@RequestBody Map<String, Object> requestData)
	     {
	    	 Long patientid=((Number) requestData.get("patientId")).longValue();
	    	 Long testid=service.getTestID(patientid);
	    	 List<Parameter> results = service.getTestResultsByPatientIdAndTestName(testid, patientid);	         
	         List<List<String>> li = new ArrayList<>();
	         for (Parameter parameter : results) {
	        	 List<String>fieldsList =new ArrayList<String>();
	             fieldsList.add(parameter.getParameterLabel());
	             fieldsList.add(parameter.getReferenceRange());
	             fieldsList.add(parameter.getUnits());
	             fieldsList.add(String.valueOf(parameter.getResultValue()));
	             li.add(fieldsList);
	         }         
	         Map<String,Object> mp=new HashMap<String,Object>();
	         mp.put("patientName", results.get(0).getPatientName());
	         mp.put("testDate", results.get(0).getTestDate());
	         mp.put("age", results.get(0).getAge());
	         Calendar calendar = Calendar.getInstance();
             SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
             String dateStr = formatter.format(calendar.getTime());
	         mp.put("reportDate", dateStr);
	         mp.put("refBy", results.get(0).getRefBy());
	         mp.put("testName", results.get(0).getTestName());
	         mp.put("report", li);
	         return mp;
	     }

	     // test saving
	     
	     @PostMapping("/add_test")
	     public ResponseEntity<String> addTest(@RequestBody Map<String, Object> parametersData) {
	        	 
	        	 String testName = (String) parametersData.get("testName");
	             String sampleType = (String) parametersData.get("sampleType");
	             int cost = Integer.parseInt((String) parametersData.get("cost"));
	             String reportFormat = (String) parametersData.get("reportFormat");
	             String reportUnits = (String) parametersData.get("reportUnits");
	        	 
	        	 Addtest test = new Addtest();
	             test.setTestName(testName);
	             test.setSampleType(sampleType);
	             test.setCost(cost);
	             test.setReportFormat(reportFormat);
	             test.setReportUnits(reportUnits);
 
	             @SuppressWarnings("unchecked")
				List<Map<String, String>> p = (List<Map<String, String>>) parametersData.get("parameters");

	             List<ParameterTable> parameters = new ArrayList<>();
	             for (Map<String, String> paramMap : p) {
	                 ParameterTable parameter = new ParameterTable();
	                 parameter.setLabel(paramMap.get("parameterName"));
	                 parameter.setResult(paramMap.get("result"));
	                 parameter.setReference(paramMap.get("referenceValues"));
	                 parameter.setUnits(paramMap.get("units"));
	                 parameter.setTest(test); // Set the reference to Addtest

	                 parameters.add(parameter);
	             }             
	             test.setParameters(parameters);	             
	             service.saveTest(test);	             
	             return ResponseEntity.ok("Test added successfully");

	         } 
// adding new test to old json object
	     	 
	     	@GetMapping("/getNewTest/{test}")
	     	public @ResponseBody Map<String,ParameterTable> getNewTest(@PathVariable("test") String test)
	     	{
	     		System.out.println("test came "+test);
	     		test=test.replace("25", "");
	     		System.out.println(test);
	     		Map<String, ParameterTable> m=service.getTestJsonObjects(test);
	     		System.out.println(m);
	     		return m;
	     	}
	     	
	     	@GetMapping("/checkTestValue/{test}")
	     	public ResponseEntity<String> checkTestValue(@PathVariable("test") String test)
	     	{
	     		
	     		if(service.checkTestValue(test))
	     			return ResponseEntity.ok("found");
	     		return ResponseEntity.ok("notfound");
	     	}
}
