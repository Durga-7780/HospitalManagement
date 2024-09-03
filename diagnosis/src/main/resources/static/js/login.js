/**
 * 
 */

 
 var submit=document.getElementsByClassName("btn")[0];
 var error=document.getElementsByClassName("error")[0];

 submit.addEventListener('click',(e)=>{
	e.preventDefault();
	var username=document.getElementById("username");
 	var password=document.getElementById("password");
 	
 	const user=username.value;
 	const pass=password.value;
 	
 	console.log(user,pass);
 	
	if(user&&pass)
	{
		fetch('checkLoginDetails/'+user+"/"+pass,{
			method:'POST',
			headers:{
				'Content-Type': 'application/json'
			}
		}).then(res=>{
			if(!res.ok)
			 throw new Error("Reponse not got "+res.statusText);
			else return res.text();
		}).then(data=>{
			if(data=="found")
			 window.location.href='dashboard/'+user;
			else {
				error.style.display='inline';
			 	setTimeout(()=> error.style.display='none',4000);
			}
		}).catch(err=>console.error(err));
	}
	else alert('please enter fields')
 })