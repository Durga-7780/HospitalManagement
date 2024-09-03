/**
 * 
 */

 var check_id=document.getElementsByClassName("empid")[0];
    	var errorMessage1=document.getElementsByClassName("errorMessage1")[0];
    	var errorMessage2=document.getElementsByClassName("errorMessage2")[0];
    	var UserName=document.getElementsByClassName("username")[0];
    	var btn=document.getElementsByClassName("btn")[0];
    	
    	
    	check_id.addEventListener('input',()=>{
    		const empIdValue=check_id.value;
	    	if(empIdValue){
	    		fetch('check/'+empIdValue,{
	    			method:'POST',
	    			headers:{
	    				'Content-Type':'application/json'
	    			}
	    		}).then(res=>{
	    			if(!res.ok)
	    				 throw new Error("network error "+res.statusText);
	    			return res.text();
	    		}).then(data=>{
	    			if(data=="found"){
	    				errorMessage1.style.display='inline';
	    				errorMessage1.textContent='user id already exists!';
	    				
	    			}
	    			else errorMessage1.style.display='none';
	    		}).catch(err=>{
	    			console.error(err);
	    		}) 
          	}
    	})
    	
    	UserName.addEventListener('input',()=>{
			var empUsername=UserName.value;
			if(empUsername){
	    		fetch('checkUser/'+empUsername,{
	    			method:'POST',
	    			headers:{
	    				'Content-Type':'application/json'
	    			}
	    		}).then(res=>{
	    			if(!res.ok)
	    				 throw new Error("network error "+res.statusText);
	    			return res.text();
	    		}).then(data=>{
	    			if(data=="found"){
	    				errorMessage2.style.display='inline';
	    				errorMessage2.textContent='user name already exists!';
	    			}
	    			else errorMessage2.style.display='none';
	    		}).catch(err=>{
	    			console.error(err);
	    		})
          	}
		})

		
		btn.addEventListener('click',()=>{
		 if(errorMessage1.style.display==='none' && errorMessage2.style.display==='none'){
			
			const form=document.getElementById('registrationForm');
			const formData = new FormData(form);
   			const registrationData = Object.fromEntries(formData.entries());
			fetch('login',{
				method:'POST',
				headers:{
					'Content-Type':'application/json'
				},
				body:JSON.stringify(registrationData)
			}).then(res=>{
				if(!res.ok)
				 throw new Error('Network error')
				else return res.text();
			}).then(data=>{
				if(data=='login')
				 window.location.href='loginDir';
			}).catch(err=>{
				console.log(err);
			})
		}
		else window.location.href='registrationPage';
		})