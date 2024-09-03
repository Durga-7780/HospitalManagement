/**
 * 
 */


	function goToDashboard() {
	      window.location.href = "dashboard";
	  }
	
        var input = document.getElementById('searchBar');
		input.addEventListener('input',()=>{
            var filter = input.value.toLowerCase();
            var cards = document.getElementsByClassName('patient-card');

            Array.from(cards).forEach(function(card) {
                var name = card.getAttribute('data-name').toLowerCase();
                if (name.includes(filter)) {
                    card.style.display = '';
                } else {
                    card.style.display = 'none';
                }
            });
        })
        
				var testFields = {}
             function setPatientId(id,test) {
				    document.getElementById('patientId').value = id;
				    const formContainer = document.getElementById('formContainer');
				    formContainer.innerHTML = '';
					  
							fetch('getNewTest/'+test+"25",{
								method:'GET',
								headers:{
									'Content-Type':'application/json'
								}
							}).then(res=>{
								if(!res.ok)
									throw new Error('Response not got')
								return res.json();
							}).then(data=>{
								const keys = Object.keys(data);
								if(keys.length>0){
									testFields[test]=data;
									if (testFields[test]) {
										let table = `<table class="table table-bordered">
												      <thead>
												      <tr>
												      <th>Parameters</th>
												      <th>Results</th>
												      <th>Reference Values</th>
												       <th>Units</th>
												       </tr>
												       </thead>
												       <tbody>`;
												         Object.values(testFields[test]).forEach(field => {
												            table += `<tr>
												                        <td>${field.label}</td>
												                        <td><input type="text" class="form-control" id="${field.label}" data-reference="${field.reference}" data-units="${field.units}"></td>
												                        <td>${field.reference}</td>
												                        <td>${field.units}</td>
												                      </tr>`;
												        });
												        table += `</tbody></table>`;
												        formContainer.innerHTML = table;
												    }
													}else {
								                formContainer.innerHTML =`<div class="form-group"><label for="reportText">Report</label><textarea class="form-control" id="reportText" rows="5"></textarea></div>`;
								            }
								            formContainer.innerHTML += `<div class="form-group"><label for="additionalNotes">Additional Notes</label><textarea class="form-control" id="additionalNotes" rows="3"></textarea></div>`;

							}).catch(e=>console.error(e))
				            }
        
        function submitReport() {
            var patientId = document.getElementById('patientId').value;
			console.log("submitreport ",patientId)
			const test = document.getElementById('card-' + patientId).getAttribute('data-test');
			const testdate=document.getElementById('date').value;
			const age=document.getElementById('age').value;
			console.log(age)
			const refBy=document.getElementById('refBy').value;
			const patientName=document.getElementById('patientName').value;
			console.log(patientName)
            const data = {
                patient_id: patientId,
				patientName:patientName,
				age:age,
				refBy:refBy,
				testName:test,
				date:testdate,
                report: {}
            };
			
            if (testFields[test]) {
                Object.values(testFields[test]).forEach(field => {
                    const inputElement = document.getElementById(field.label);
                    data.report[field.label] = {
                        value: inputElement.value,
                        reference: inputElement.getAttribute('data-reference'),
                        units: inputElement.getAttribute('data-units')
                    };
                });
            } else {
				 	const reportTextElement = document.getElementById('reportText');
			        if (reportTextElement) { 
			            data.report = reportTextElement.value;
			        } else {
			            data.report = ''; 
			        }
               }

            data.additionalNotes = document.getElementById('additionalNotes').value;
			console.log(data)
            fetch('generatereport', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
            .then(response =>{ return response.text()})
            .then(result => {
                alert(result);
                document.getElementById('card-' + patientId).remove();
                $('#reportModal').modal('hide');
                window.location.href='reportPage/'+patientId;
            })
            .catch(error => console.error('Error:', error))
        }
		
		
        