<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Patient Details</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	    <style>
        .card {
            width: 100%;
            margin-bottom: 20px;
            box-shadow: 0 0 10px gray;
            background-color: transparent;
            border-radius: 10px;
            position: relative;
        }
        .search-bar {
            position: absolute;
            top: 10px;
            right: 100px;
            padding: 5px;
            width: 145px;
            border: 1px solid black;
            border-radius: 15px;
            background: transparent;
        }
        body {
            background-size: cover;
            background-position: center;
            font-family: 'Arial', sans-serif;
        }
        .btn-card {
            position: absolute;
            bottom: 10px;
            right: 10px;
        }
        .logout-btn {
            position: absolute;
            top: 10px;
            right: 10px;
        }
        .backBtn {
            position: absolute;
            top: 10px;
            left: 10px;
        }
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 52px;
        }
    </style>
</head>
<body>
	<div class="header-container">
        <a id="backBtn" class="btn btn-outline-danger backBtn" onclick="goToDashboard()"><i class="bi bi-arrow-left"></i></a>
        <input type="text" id="searchBar" class="search-bar" placeholder="Search by name..." >
        <a href="logout" class="btn btn-outline-danger logout-btn" style="font-weight: bolder;">Logout</a>
        <div class="col-12">
            <h1 class="text-center mb-4" style="font-style: italic; font-family:Impact, Haettenschweiler, 'Arial Narrow Bold', sans-serif;">Patient Details</h1>
        </div>
    </div>
    <div class="container mt-4">
        <div class="row">
        </div>
        <c:if test="${empty list}">
		    <p>No patient details available.</p>
		</c:if>
        <div class="row" id="patientsContainer"> 
          <c:forEach items="${list}" var="obj">  
	            <div class="col-md-4 patient-card" id="card-${obj.id}" data-name="${obj.patientName}" data-test="${obj.test}">
	                <div class="card">
	                    <div class="card-body">
	                        <h5 class="card-title"><c:out value="${obj.patientName}"/></h5>
	                        <p class="card-text">Age: <c:out value="${obj.age}"/></p>
	                        <p class="card-text">Sex: <c:out value="${obj.sex}"/></p>
	                        <p class="card-text">Ref by: <c:out value="${obj.refBy}"/></p>
	                        <p class="card-text">Date: <c:out value="${obj.date}"/></p>
	                        <p class="card-text">Test: <c:out value="${obj.test}"/></p>
	                        <input type='hidden' value="${obj.date}" id="date"/>
	                        <input type='hidden' value="${obj.age}" id="age"/>
	                        <input type='hidden' value="${obj.refBy}" id="refBy"/>
	                        <input type='hidden' value="${obj.patientName}" id="patientName"/>
	                        <c:if test="not empty ${obj.otherTest}">
	                        <p class="card-text">Other Test: <c:out value="${obj.otherTest}"/></p>
	                        </c:if>
	                        <button class="btn btn-primary btn-card" data-toggle="modal" data-target="#reportModal" onclick="setPatientId(${obj.id},'${obj.test}')">Report</button>
	                    </div>
	                </div>
	            </div>
           </c:forEach>    
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="reportModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="reportModalLabel">Enter Report</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="reportForm">
                        <div id="formContainer"></div>
                        <input type="hidden" id="patientId">
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="submitReport()">Submit</button>
                </div>
            </div>
        </div>
    </div>
    
    <script src="/js/TestDetails.js"></script>

</body>
</html>