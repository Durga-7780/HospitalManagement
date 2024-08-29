<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payments</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
    <style>
        body {
            background: #f8f9fa;
        }

        .container {
            margin-top: 3px;
        }

        .table {
            box-shadow: 0 14px 18px rgba(0,0,0,0.1);
            border-radius: 10px;
            background: #fff;
            margin-top: 2px;
        }
        .table-container {
            max-height: 380px; 
            overflow-y: auto; 
        }

        h1 {
            font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
            font-weight: bolder;
            margin-bottom: 20px;
        }

        .totals-table {
            margin-top: 20px;
            background: #fff;
        }

        .totals-table th, .totals-table td {
            text-align: center;
        }

        .logout-btn {
            position: absolute;
            top: 10px;
            right: 10px;
        }

        .date-input {
            position: absolute;
            top: 10px;
            right: 130px;
            width: 150px;
            font-size: 14px;
        }

        .backBtn {
            position: absolute;
            top: 10px;
            left: 10px;
        }

        .no-data-msg {
            text-align: center;
            font-style: italic;
            color: #999;
        }
    </style>
</head>
<body>
    <a class="btn btn-outline-danger backBtn" onclick="goToDashboard()"><i class="bi bi-arrow-left"></i></a>
    <a href="logout" class="btn btn-outline-danger logout-btn" style="font-weight: bolder;">Logout</a>
    <div class="container">
        <h1 class="text-center">Payments</h1>
        <input type="date" id="payment-date" class="form-control date-input">
        <div class="table-container">
            <table class="table table-striped" id="payments-table">
                <thead>
                    <tr>
                        <th scope="col">Patient Name</th>
                        <th scope="col">Cost</th>
                        <th scope="col">Mode of Payment</th>
                        <th scope="col">Date</th>
                    </tr>
                </thead>
                <tbody id="payments-tbody">
                    <!-- Data will be inserted dynamically -->
                   <c:forEach var="obj" items="${list}">
                    <tr>
                        <td><c:out value="${obj.patientName}"/></td>
                        <td><c:out value="${obj.cost}"/></td>
                        <td><c:out value="${obj.modeOfPayment}"/></td>
                        <td><c:out value="${obj.date}"/></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p id="no-data-msg" class="no-data-msg" style="display: none;">No data found for the selected date.</p>
        </div>
        <h1 class="text-center">Total Sum</h1>
        <div class="totals-table">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">Mode of Payment</th>
                        <th scope="col">Total Received</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Cash</td>
                        <td id="total-cash"><c:out value="${cash}"/></td>
                    </tr>
                    <tr>
                        <td>UPI</td>
                        <td id="total-upi"><c:out value="${upi}"/></td>
                    </tr>
                    <tr>
                        <td>Card</td>
                        <td id="total-card"><c:out value="${card}"/></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <script>
    	function goToDashboard()
    	{
    		window.location.href='dashboard';
    	}
    	
    	
    	document.addEventListener('DOMContentLoaded', function() {
    	    const dateInput = document.getElementById('payment-date');
    	    const noDataMsg = document.getElementById('no-data-msg');
    	    const paymentsTbody = document.getElementById('payments-tbody');
    	    const paymentsTable = document.getElementById('payments-table');

    	    dateInput.addEventListener('change', (e) => {
    	        e.preventDefault(); 
    	        
    	        const selectedDate = dateInput.value;
    	        if (selectedDate || selectedDate=='') {
    	            fetch(`filterPaymentsByDate?date=` + selectedDate)
    	                .then(response => response.json())
    	                .then(data => {            
							 paymentsTbody.innerHTML = '';    
    	                    if (data.payments && data.payments.length > 0) {
    	                        data.payments.forEach(payment => {
    	                            
    	                            const row = document.createElement('tr');
    	                            const td1=document.createElement('td');
    	                            const td2=document.createElement('td');
    	                            const td3=document.createElement('td');
    	                            const td4=document.createElement('td');
    	                            
    	                            td1.innerHTML=payment.patientName;
    	                            td2.innerHTML=payment.cost;
    	                            td3.innerHTML=payment.modeOfPayment;
    	                            td4.innerHTML=payment.date;
    	                            
    	                            row.appendChild(td1);
    	                            row.appendChild(td2);
    	                            row.appendChild(td3);
    	                            row.appendChild(td4);
    	                            
    	                           
    	                            document.getElementById('payments-tbody').appendChild(row);
    	                        });

    	                        noDataMsg.style.display = 'none';
    	                        paymentsTable.style.display = 'table'; 
    	                    } else {
    	                        noDataMsg.style.display = 'block'; 
    	                        paymentsTable.style.display = 'none'; 
    	                    }

    	                    // Update totals
    	                    document.getElementById('total-cash').textContent = data.cash;
    	                    document.getElementById('total-upi').textContent = data.upi;
    	                    document.getElementById('total-card').textContent = data.card;
    	                })
    	                .catch(error => console.error('Error fetching payment data:', error));
    	        }
    	        else {
    	        	
    	        }
    	    });
    	});


    </script>
</body>
</html>