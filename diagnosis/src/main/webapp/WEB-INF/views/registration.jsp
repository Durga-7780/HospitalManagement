<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee Registration</title>
    <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
    <style>
        body {
            background-color: #F3EBF6;
            font-family: 'Ubuntu', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .register-card {
            background-color: #FFFFFF;
            width: 400px;
            height: auto;
            margin: 7em auto;
            border-radius: 1.5em;
            box-shadow: 0px 11px 35px 2px rgba(0, 0, 0, 0.14);
            text-align: center;
            padding-top: 40px;
            padding-bottom: 40px;
        }
        .register-card h2 {
            color: #8C55AA;
            font-weight: bold;
            font-size: 23px;
        }
        .register-card input[type="text"],
        .register-card input[type="password"] {
            width: 76%;
            color: rgb(38, 50, 56);
            font-weight: 700;
            font-size: 14px;
            letter-spacing: 1px;
            background: rgba(136, 126, 126, 0.04);
            padding: 10px 20px;
            border: none;
            border-radius: 20px;
            outline: none;
            box-sizing: border-box;
            border: 2px solid rgba(0, 0, 0, 0.02);
            margin-bottom: 27px;
            margin-left: 2%;
            text-align: center;
            font-family: 'Ubuntu', sans-serif;
        }
        .register-card input[type="text"]:focus,
        .register-card input[type="password"]:focus {
            border: 2px solid rgba(0, 0, 0, 0.18) !important;
        }
        .register-card label {
            color: #E1BEE7;
            font-size: 14px;
            font-weight: 900;
            margin-left: 3%;
        }
        .register-card .btn {
            cursor: pointer;
            border-radius: 5em;
            color: #fff;
            background: linear-gradient(to right, #9C27B0, #E040FB);
            border: 0;
            padding-left: 40px;
            padding-right: 40px;
            padding-bottom: 10px;
            padding-top: 10px;
            font-family: 'Ubuntu', sans-serif;
            margin-left: 5%;
            font-size: 13px;
            box-shadow: 0 0 20px 1px rgba(0, 0, 0, 0.04);
        }
        .register-card .forgot {
            text-shadow: 0px 0px 3px rgba(117, 117, 117, 0.12);
            color: #E1BEE7;
            padding-top: 15px;
        }
        .register-card a {
            text-shadow: 0px 0px 3px rgba(117, 117, 117, 0.12);
            color: #E1BEE7;
            text-decoration: none;
        }
        @media (max-width: 600px) {
            .register-card {
                border-radius: 0px;
            }
        }
        .errorMessage1,.errorMessage2{
        	display:none;
        	color:red;
        }
    </style>
</head>
<body>
      <div class="register-card">
        <h2>Employee Registration</h2>
       <!-- <p style="color: red;" id="error-message" style="display: none;"></p> <!-- Error message placeholder -->
        <form id=registrationForm>
          <!--  <input type="hidden" name="csrf_token" value="YOUR_CSRF_TOKEN">  Example hidden tag for CSRF protection -->
            
            <label for="employee_name">Employee Name</label>
            <input type="text" id="employee_name" name="empname" size="32"  required><br>
            
            <label for="employee_id">Employee ID</label>
            <input type="text" id="employee_id" name="empid" size="32" class="empid" required><br>
            
            
            <label for="username">Username</label><br>
            <input type="text" id="username" name="username" size="32" class="username" required><br>
            
            <label for="password">Password</label><br>
            <input type="password" id="password" name="password" size="32" required><br>
            
            <input type="submit" class="btn" value="Register">
        </form>
        <p class="forgot">Already have an account? <a href="loginDir">Login here</a></p>
        <span class="errorMessage1">Error</span>
        <span class="errorMessage2">Error</span>
    </div>
    <script src="/js/registration.js">
    	
    </script>
</body>
</html>