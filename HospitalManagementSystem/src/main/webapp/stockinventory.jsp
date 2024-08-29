<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Store Inventory</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.16.9/xlsx.full.min.js"></script>

    <script>
        function goToDashboard() {
            window.location.href = "dashboard";
        }
    </script>

    <style>
        body {
            background: #f8f9fa;
        }
        .container {
            margin-top: 20px;
        }
        .input-container {
            width: 100%;
            margin: 0 auto;
            box-sizing: border-box;
            box-shadow: 0 0 10px gray;
            border-radius: 10px;
            padding: 20px;
            background-color: #fff;
        }
        form {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        input, button {
            flex: 1;
            padding: 10px;
            margin-right: 10px;
            border: 1px solid #000;
            border-radius: 10px;
        }
        button {
            flex: 0 0 auto;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        
        .backBtn {
            font-weight: bolder;
            cursor: pointer;
        }

        .logout-btn {
            font-weight: bolder;
        }
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: -32px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header-container">
            <a id="backBtn" class="btn btn-outline-danger backBtn" onclick="goToDashboard()"><i class="bi bi-arrow-left"></i></a>
            <h1 class="text-center mb-4">Store Inventory</h1>
            <a href="logout" class="btn btn-outline-danger logout-btn">Logout</a>
        </div>
        <div class="input-container">
            <form id="inventoryForm" action="updateStock" method="POST">
                <input type="text" id="apparatusName" name="apperatus" class="form-control" placeholder="Apparatus Name" required>
                <input type="text" id="quantity" name="quantity" class="form-control" placeholder="Quantity (e.g., 25L, 20Kg)" required>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
        <h3>Stored Inventory</h3>
       <c:if test="${not empty list}">
        <table>
            <thead>
                <tr>
                    <th>Apparatus Name</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="obj" items="${list}">
                <tr>
                    <td><c:out value="${obj.item}"/></td>
                    <td><c:out value="${obj.quantity}"/></td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        </c:if>
        <c:if test="${empty list}">
          <p>No data found</p>
        </c:if>
    </div>
</body>
</html>