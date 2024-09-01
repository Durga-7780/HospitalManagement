<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Employees List</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .backBtn {
            position: absolute;
            left: 10px;
        }
        .logout-btn {
            position: absolute;
            right: 10px;
        }
        .table-container {
            height: 85vh; 
            overflow-y: auto;
            margin-top: 20px;
        }
    </style>
    
</head>
<body>
    <a id="backBtn" class="btn btn-outline-danger backBtn" onclick="goToDashboard()"><i class="bi bi-arrow-left"></i></a>
    <a href="logout" class="btn btn-outline-danger logout-btn" style="font-weight: bolder;">Logout</a>
    <h1 class="text-center">Employee Details</h1>
    <div class="container table-container">
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>S.No</th>
                    <th>Name</th>
                    <th>Employee ID</th>
                    <th>Username</th>
                    <th>Edit</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${employees}" var="emp" varStatus="loop"> 
	                <tr>
	                    <form id="myform-${emp.empid}">
				            <input type="hidden" name="old_employee_id" value="${emp.empid}">
	                        <td>${loop.index+1}</td>
	                        <td><input type="text" name="employee_name" value="${emp.empname}" id="employee_name" class="form-control"></td>
	                        <td>${emp.empid}</td>
	                        <td><input type="text" name="username" value="${emp.username}" id="username" class="form-control"></td>
	                        <td><input type="submit" class="btn btn-success" id="submitbtn"  onclick="fun('${emp.empid}')" Value="Submit"></td>
	                        
	                    </form>
	                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <script>
    function goToDashboard() {
        window.location.href = "dashboard";
    }
	
	   
	   function fun(empid) {
		   const form = document.getElementById('myform-'+empid);
		   form.addEventListener('submit', (event) => {
			    event.preventDefault();
			    const formData = new FormData(form);
			    const registrationData = Object.fromEntries(formData.entries());
			    fetch('updateemployee', {
			      method: 'POST',
			      body: JSON.stringify(registrationData),
			      headers: {
			        'Content-Type': 'application/json'
			      }
			    })
			    .then((response) => {
			      return response.text();
			    })
			    .then((data) => {
			      if (data == "Employee updated successfully") {
			        showAlert(data, true)
			      }
			      else showAlert("Not updated", false)
			    })
			    .catch((error) => console.error(error));
			  });
			}
	  
	   function showAlert(message, isSuccess) {
		   let alertBox = document.createElement('div');
		   alertBox.className = `alert ${isSuccess ? 'alert-danger' : 'alert-success'} alert-dismissible fade show`;
		   alertBox.innerHTML = '<span>' + message + '</span><button type="button" class="close" aria-label="Close"><span aria-hidden="true">×</span></button>';
		   
		   let closeButton = alertBox.querySelector('.close');
		   
		   document.body.appendChild(alertBox);
		   
		   setTimeout(() => {
		     document.body.removeChild(alertBox);
		   }, 3000);
		   
		   closeButton.addEventListener('click', () => {
		     document.body.removeChild(alertBox);
		   });
		 }
	   
    </script>
</body>
</html>