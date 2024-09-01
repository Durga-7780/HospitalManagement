<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reports</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.3.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/qrcodejs/1.0.0/qrcode.min.js"></script>
    <style>
        body {
            background: #f8f9fa;
        }

        .container {
            margin-top: 20px;
        }

        .card {
            margin: 20px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            position: relative;
        }

        .download-btn {
            position: absolute;
            bottom: 10px;
            right: 10px;
            padding: 5px 10px;
            font-size: 0.8em;
        }

        .header {
            display: flex;
            align-items: flex-start; 
            justify-content: center;
            margin-bottom: 5px;
            margin-top: 5px;
        }

        .header img {
            margin-right: 10px;
            height: 57px;
            width: 57px;
        }

        .header h1 {
            font-size: 3.0em;
            font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
            font-weight: bold;
            margin: 0;
        }

        @media (max-width: 600px) {
            .header {
                flex-direction: column;
                align-items: center;
            }

            .header img {
                margin-bottom: 10px;
            }
        }

        .logout-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            margin-left: 10px;
        }

        .backBtn {
            position: absolute;
            top: 10px;
        }

        .filter-container {
            position: absolute;
            top: 10px;
            right: 150px;
            display: flex;
            align-items: center;
        }

        .filter-container input {
            margin-left: 10px;
        }
        .search-bar {
            position: absolute;
            right: 170px;
            padding: 5px;
            width: 145px;
            border: 1px solid black;
            border-radius: 15px;
            background: transparent;
        }
    </style>
</head>
<body>
		<div class="container">
        <a class="btn btn-outline-primary back-btn" onclick="goToDashboard()"><i class="bi bi-arrow-left"></i></a>
        <a href="logout" class="btn btn-outline-danger logout-btn" style="font-weight: bolder;">Logout</a>
        <div class="filter-container">
            <input type="date" id="filter-date" class="form-control">
            <input type="text" id="search-bar" class="search-bar" placeholder="Search by name...">
        </div>
        <div class="header">
            <img src="/images/somuIT.png" alt="Logo">
            <h1>SOMU DIAGNOSTICS</h1>
        </div>
        <div class="row">
            <c:forEach items="${list}" var="obj" varStatus="loop">
	            <div class="col-md-6 patient-card" id="report-${loop.index}">
	                <div class="card">
	                    <div class="card-body">
	                        <h5 class="card-title" style="margin-top: 5px;">Name: <c:out value="${obj.patientName}"/></h5>
	                        <p class="card-text">Age: <c:out value="${obj.age}"/></p>
	                        <c:if test="${not empty obj.test}">
	                        <p class="card-text">Test: <c:out value="${obj.test}"/></p>
	                        </c:if>
	                        <c:if test="${not empty obj.otherTest}">
	                        <p class="card-text">Test: <c:out value="${obj.otherTest}"/></p>
	                        </c:if>
	                        <p class="card-text">Date: <c:out value="${obj.date}"/></p>
	                        <p class="card-text">Referred by: <c:out value="${obj.refBy}"/></p>
	                        <p class="card-text" style="display: none;">Report: <c:out value="${obj.report}"/></p>
	                        <input type="hidden" name="patientId" value="${obj.patientid}"/>
	                        <button class="btn btn-primary download-btn" onclick="downloadPDF(${obj.patientid}, '${obj.test}','report-${loop.index}')">PDF</button>
	                    </div>
	                </div>
	            </div>
            </c:forEach>
        </div>
    </div>
    <script>
    	function goToDashboard()
    	{
    		window.location.href='dashboard';
    	}
    	
    	
    	
    	window.addEventListener('DOMContentLoaded', function () {
    	    const { jsPDF } = window.jspdf;

    	    async function fetchReportData(patientId, testName) {
    	        const response = await fetch('report/data', {
    	            method: 'POST', 
    	            headers: {
    	                'Content-Type': 'application/json', 
    	            },
    	            body: JSON.stringify({ patientId, testName })
    	        });
    	        if (!response.ok) {
    	            throw new Error('Network response was not ok.');
    	        }
    	        return response.json();
    	    }

    	    function downloadPDF(patientId, testName, cardId) {
    	        fetchReportData(patientId, testName)
    	            .then(reportData => {
    	            	console.log(patientId,testName)
    	                const card = document.getElementById(cardId);
    	                const doc = new jsPDF();

    	                const name = card.querySelector('.card-title').innerText.replace('Name: ', '');
    	                const age = card.querySelectorAll('.card-text')[0].innerText.replace('Age: ', '');
    	                const test = card.querySelectorAll('.card-text')[1].innerText.replace('Test: ', '');
    	                const date = card.querySelectorAll('.card-text')[2].innerText.replace('Date: ', '');
    	                const refBy = card.querySelectorAll('.card-text')[3].innerText.replace('Referred by: ', '');
    	                const reportTextElement = card.querySelectorAll('.card-text')[4];
    	                
    	                var reportText = reportTextElement ? reportTextElement.innerText.replace('Report: ', '') : '';
    	                const additionalNotesElement = card.querySelectorAll('.card-text')[5];
    	                const additionalNotes = additionalNotesElement ? additionalNotesElement.innerText.replace('Additional Notes: ', '') : '';

    	                const img = new Image();
    	                img.src = "/images/somuIT.png"; 
    	                img.onload = function () {
    	                    const imgWidth = 30;
    	                    const imgHeight = 25;
    	                    const startX = 13;
    	                    const headingX = 50;
    	                    const startY = 5;
						
    	                    doc.addImage(img, 'PNG', startX, startY, imgWidth, imgHeight);
    	                    doc.setFontSize(34);
    	                    doc.setFont("times", "bold");
    	                    doc.text('SOMU DIAGNOSTICS', headingX, startY + imgHeight / 2);

    	                    doc.setFontSize(12);
    	                    doc.setFont("times", "normal");

    	                    // Add data to PDF
    	                    doc.text('Name: ' + reportData.patientName, 10, startY + 30);
    	                    doc.text('Sample Date: ' + reportData.testDate, 140, startY + 30);
    	                    doc.text('Age & Sex: ' + reportData.age, 10, startY + 40);
    	                    doc.text('Report Date: ' + reportData.reportDate, 140, startY + 40);
    	                    doc.text('Referred by: ' + reportData.refBy, 10, startY + 50);
    	                    doc.text('Test: ' + reportData.testName, 10, startY + 60);

    	                    doc.line(10, startY + 70, 200, startY + 70);

    	                    doc.text('Report:', 10, startY + 80);
    	                    
    	                    let yPosition = startY + 90;
							let xPosition =startX + 90;
    	                    // Example array data
    	                    const arrays = reportData.report;
      
							// Add headers to the PDF
							doc.text('Parameters', 10, yPosition);
							doc.text('Results', 82, yPosition);
							doc.text('Reference Values', 120, yPosition);
							doc.text('Units', 170, yPosition);
							doc.line(10, yPosition + 2, 200, yPosition + 2);
							
							yPosition += 10;
							
							  arrays.forEach(array=>  {
							    doc.text(array[0], 10, yPosition); 
							    doc.text(array[3], 80, yPosition);
								doc.text(array[1], 120, yPosition);
								doc.text(array[2], 170, yPosition);
							    yPosition += 10;
							  })
							 
    	                    // Additional Notes
    	                    if (additionalNotes) {
    	                        const additionalNotesLines = additionalNotes.split('\n');
    	                        yPosition += 10;
    	                        doc.text('Additional Notes:', 10, yPosition);
    	                        yPosition += 10;
    	                        additionalNotesLines.forEach(line => {
    	                            doc.text(line, 10, yPosition);
    	                            yPosition += 10;
    	                        });
    	                    }

    	                    doc.line(10, 260, 200, 260);
    	                    doc.text('Ramalayam Street, Gummaluru, Poduru (M), West Godavari Dist., A.P - 534267', 105, 270, null, null, 'center');
    	                    doc.text('Somu IT Solutions (OPC) Private Limited', 105, 280, null, null, 'center');

    	                    doc.save(reportData.patientName+'.pdf');
    	                };
    	                
    	                img.onerror = function () {
    	                    let yPosition = startY + 80;

    	                    if (reportText) {
    	                        const reportLines = reportText.split('\n');
    	                        reportLines.forEach(line => {
    	                            doc.text(line, 10, yPosition);
    	                            yPosition += 10;
    	                        });
    	                    }

    	                    // Additional Notes
    	                    if (additionalNotes) {
    	                        const additionalNotesLines = additionalNotes.split('\n');
    	                        yPosition += 10;
    	                        doc.text('Additional Notes:', 10, yPosition);
    	                        yPosition += 10;
    	                        additionalNotesLines.forEach(line => {
    	                            doc.text(line, 10, yPosition);
    	                            yPosition += 10;
    	                        });
    	                    }

    	                    // Footer
    	                    doc.line(10, 260, 200, 260);
    	                    doc.text('Ramalayam Street, Gummaluru, Poduru (M), West Godavari Dist., A.P - 534267', 105, 270, null, null, 'center');
    	                    doc.text('Somu IT Solutions (OPC) Private Limited', 105, 280, null, null, 'center');

    	                    doc.save(reportData.patientName+'.pdf');
    	                };
    	            })
    	            .catch(error => console.error('Error fetching report data:', error));
    	    }

    	    window.downloadPDF = downloadPDF;
    	    });

             document.getElementById('filter-date').addEventListener('change', filterPatients);
             document.getElementById('search-bar').addEventListener('input', filterPatients);

             function filterPatients() {
                 const selectedDate = document.getElementById('filter-date').value;
                 const searchTerm = document.getElementById('search-bar').value.toLowerCase();
                 const cards = document.querySelectorAll('.patient-card');

                 cards.forEach(card => {
                     const date = card.querySelector('.card-text:nth-child(4)').innerText.split(': ')[1];
                     const name = card.querySelector('.card-title').innerText.toLowerCase();

                     if ((selectedDate === '' || date.includes(selectedDate)) && name.includes(searchTerm)) {
                         card.style.display = '';
                     } else {
                         card.style.display = 'none';
                     }
                 });
             }
    </script>
</body>
</html>