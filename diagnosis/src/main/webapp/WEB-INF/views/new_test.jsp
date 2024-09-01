<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Test</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.8.1/font/bootstrap-icons.min.css">
    
      <style>
        body {
            background: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .card {
            width: 400px;
            padding: 20px;
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            background: #ffffff;
        }

        .form-group label {
            font-weight: bold;
        }

        .form-control {
            margin-bottom: 15px;
        }

        .form-control:focus {
            border-color: #007bff;
            box-shadow: none;
        }

        .submit-btn {
            margin-top: 15px;
        }

        .header {
            text-align: center;
            margin-bottom: 20px;
        }

        .header h2 {
            font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
            font-size: 2.0em;
        }
        .backBtn {
            position: absolute;
            top: 10px;
            left: 10px;
        }

        .logout-btn {
            position: absolute;
            top: 10px;
            right: 10px;
        }

        .add-row-btn {
            margin-left: 10px;
            cursor: pointer;
            color: #007bff;
        }

        #reportTable {
            margin-top: 15px;
        }
        #error{
        	display:none;
        }
    </style>  
    <script>
    document.addEventListener('DOMContentLoaded', function () {
        const reportFormatSelect = document.getElementById('reportFormat');
        const reportUnitsTextarea = document.getElementById('reportUnitsTextarea');
        const reportTable = document.getElementById('reportTable');
        const addRowBtn = document.getElementById('addRowBtn');
        const dynamicTable = document.getElementById('dynamicTable').getElementsByTagName('tbody')[0];

        // Hide both initially
        reportUnitsTextarea.style.display = 'none';
        reportTable.style.display = 'none';
        addRowBtn.style.display = 'none';

        // Add event listener to the report format dropdown
        reportFormatSelect.addEventListener('change', function () {
            if (this.value === 'textarea') {
                reportUnitsTextarea.style.display = 'block';
                reportTable.style.display = 'none';
                addRowBtn.style.display = 'none';
            } else if (this.value === 'table') {
                reportUnitsTextarea.style.display = 'none';
                reportTable.style.display = 'block';
                addRowBtn.style.display = 'inline'; // Show the add row button
            }
        });

        // Add new row to the table
        var data = [];
        addRowBtn.addEventListener('click', function () {
            var rowCount = dynamicTable.rows.length+1;
            
            console.log(rowCount);
            const newRow = dynamicTable.insertRow();
            const paramCell = newRow.insertCell(0);
            const resltCell = newRow.insertCell(1);
            const refCell = newRow.insertCell(2);
            const unitCell = newRow.insertCell(3);

            paramCell.innerHTML = `<input type="text" name="parameterName${rowCount}" class="form-control">`;
            resltCell.innerHTML = `<input type="text" name="result${rowCount}" class="form-control">`;
            refCell.innerHTML = `<input type="text" name="referenceValues${rowCount}" class="form-control">`;
            unitCell.innerHTML = `<input type="text" name="units${rowCount}" class="form-control">`;
        });
        document.getElementById('submitBtn').addEventListener('click', function (event) {
            const data = []; 
            const rows = document.querySelectorAll('#dynamicTable tbody tr');

            rows.forEach(row => {
                const parameterName = row.querySelector('input[name^="parameterName"]').value;
                const result = row.querySelector('input[name^="result"]').value;
                const referenceValues = row.querySelector('input[name^="referenceValues"]').value;
                const units = row.querySelector('input[name^="units"]').value;

                if (parameterName || result || referenceValues || units) {
                    data.push({
                        parameterName,
                        result,
                        referenceValues,
                        units
                    });
                }
            });

            const requestData = {
                    testName: document.getElementById('testName').value,
                    sampleType: document.getElementById('sampleType').value,
                    cost: document.getElementById('cost').value,
                    reportFormat: document.getElementById('reportFormat').value,
                    reportUnits: document.getElementById('reportUnits').value,
                    parameters: data
                };
            // Convert data to JSON and send to the server
            const jsonData = JSON.stringify(requestData);
            console.log(requestData);

            fetch('add_test', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json' 
                },
                body: jsonData
            })
            .then(response => response.json())
            .then(result => console.log('Success:', result))
            .catch(error => console.error('Error:', error));
        });

    });
        function goToDashboard() {
            window.location.href = "dashboard";
        }
        
        document.addEventListener('DOMContentLoaded', function () {
        var v=document.getElementById("testName");
        v.addEventListener('input',()=>{
        	var val=v.value;
        	console.log(val)
        	fetch('checkTestValue/'+val,{
        		method:'GET',
        		headers:{
        			'Content-Type':'application/json'
        		}
        	}).then(res=>{return res.text()})
        	.then(data=>{
        		console.log(data);
        		if(data=="found"){
        		document.getElementById('error').style.display='inline';
        		setTimeout(()=>{window.location.href='newtest'},3000);
        		}
        		else document.getElementById('error').style.display='none';
        	})
        	.catch(e=>console.error(e))
        });
        });
    </script>   
</head>
<body>
    <a id="backBtn" class="btn btn-outline-danger backBtn" onclick="goToDashboard()"><i class="bi bi-arrow-left"></i></a>
    <a href="logout" class="btn btn-outline-danger logout-btn" style="font-weight: bolder;">Logout</a>
    <div class="card">
        <div class="header">
            <h2>Add New Test</h2>
        </div>
        <form>
            <div class="form-group">
                <label for="testName">Test Name</label>
                <input type="text" id="testName" name="testName" class="form-control" required>
                <span id="error">Test Already Exists</span>
            </div>
            <div class="form-group">
                <label for="sampleType">Type of Sample</label>
                <input type="text" id="sampleType" name="sampleType" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="cost">Cost</label>
                <input type="number" id="cost" name="cost" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="reportFormat">Report Format</label>
                <select id="reportFormat" name="reportFormat" class="form-control" required>
                    <option value="" disabled selected>Select Report Type</option>
                    <option value="textarea">Textarea</option>
                    <option value="table">Table</option>
                </select>
            </div>
            <div class="form-group" id="reportUnitsTextarea">
                <label for="reportUnits">Report Units</label>
                <textarea id="reportUnits" name="reportUnits" class="form-control" rows="4"></textarea>
            </div>
            <div class="form-group" id="reportTable" style="display: none;">
                <label>
                    Report Table 
                    <i id="addRowBtn" class="bi bi-plus add-row-btn"></i>
                </label>
                <table class="table table-bordered" id="dynamicTable">
                    <thead>
                        <tr>
                            <th>Parameter</th>
                            <th>Results</th>
                            <th>Reference Values</th>
                            <th>Units</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>
            </div>
            <button type="submit" class="btn btn-primary submit-btn" id="submitBtn">Submit</button>
        </form>        
    </div>
</body>
</html>