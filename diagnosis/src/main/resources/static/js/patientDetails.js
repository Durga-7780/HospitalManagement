/**
 * 
 */
    	
       function navigateTotests()
       {
		window.location.href='navigateTotests';
	   }
    	
    	var submit=document.getElementById('submit');
    	
    	submit.addEventListener('click',()=>{
			
		
    		const form=document.getElementById('patientData');
    		
    		if(!form.checkValidity())
    		{
				alert("all the fields must be filled...")
			}
			else{
			const formData = new FormData(form);
   			const patientData = Object.fromEntries(formData.entries());
   		
   			fetch('addPatient',{
				method:'POST',
				headers:{
					'Content-Type':'application/json'
				},
				body:JSON.stringify(patientData)
			})
   			.then(res=>{
				if(!res.ok)
				 throw new Error('Network issue... '+res.statusText);
				return res.text();
			}).then(data=>{
				console.log(data);
				if(data=='found')
				 window.location.href='sendFormData';
			})
			.catch(err=>console.log(err)) 
		  } 			
    	})