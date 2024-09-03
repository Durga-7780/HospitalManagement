<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>Employee Login</title>
    <link href="https://fonts.googleapis.com/css?family=Ubuntu" rel="stylesheet">
	<style>
        body {
            background-color: #F3EBF6;
            font-family: 'Ubuntu', sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .header {
            display: flex;
            align-items: center;
            margin-top: 20px;
        }
        .header img {
            margin-right: 10px;
            height: 100px;
            width: 100px;
        }
        .header h1 {
            font-size: 4.5em;
        }
        .login-card {
            background-color: #f7f6f6;
            /* background-image: url('/static/logo.png'); */
            background-size: cover;
            background-position: center;
            width: 400px;
            height: auto;
            margin: 2em auto;
            border-radius: 1.5em;
            box-shadow: 0px 11px 35px 2px rgba(0, 0, 0, 0.14);
            text-align: center;
            padding-top: 40px;
            padding-bottom: 40px;
            margin-bottom: 10%;
        }
        .login-card h2 {
            color: #8C55AA;
            font-weight: bold;
            font-size: 23px;
        }
        .login-card input[type="text"],
        .login-card input[type="password"] {
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
            margin-left: 5%;
            text-align: center;
            font-family: 'Ubuntu', sans-serif;
        }
        .login-card input[type="text"]:focus,
        .login-card input[type="password"]:focus {
            border: 2px solid rgba(0, 0, 0, 0.18) !important;
        }
        .login-card label {
            color: #E1BEE7;
            font-size: 14px;
            font-weight: 700;
            margin-left: 5%;
        }
        .login-card .btn {
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
        .login-card .forgot {
            text-shadow: 0px 0px 3px rgba(117, 117, 117, 0.12);
            color: #E1BEE7;
            padding-top: 15px;
        }
        .login-card a {
            text-shadow: 0px 0px 3px rgba(117, 117, 117, 0.12);
            color: #E1BEE7;
            text-decoration: none;
        }
        @media (max-width: 600px) {
            .login-card {
                border-radius: 0px;
            }
            .header {
                flex-direction: column;
                align-items: center;
            }
            .header img {
                margin-bottom: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <img src="/images/somuIT.png" alt="Logo" height="50" width="55">
        <h1 style="font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;">SOMU DIAGNOSTICS</h1><br>
    </div>
    <div class="login-card">
        <h2>Employee Login</h2>
        <form> 
           <!-- <input type="hidden" name="csrf_token" value="YOUR_CSRF_TOKEN"> <!-- Example hidden tag for CSRF protection -->
            
            <label for="username">Username</label>
            <input type="text" id="username" name="username" size="32" required>
            
            <label for="password">Password</label>
            <input type="password" id="password" name="password" size="32" required>
            
            <input type="submit" class="btn" value="Login">
        </form>
        <p class="forgot">Don't have an account? <a href="registrationPage">Register here</a></p>
        <p class="error" style="display: none;">Invalid credentials, please try again.</p>
    </div>
    <script src="/js/login.js"></script>
</body>
</html>