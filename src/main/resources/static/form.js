function create_object() {
	var objectId = document.getElementById("objectId").value;
	var objectName = document.getElementById("objectName").value;
	var isHighlight = document.getElementById("isHighlight").value;
	var accessionNumber = document.getElementById("accessionNumber").value;
	//Code for password validation
	var letters = /^[A-Za-z]+$/;
	//other validations required code
	if (objectId == '' || objectName == '') {
		alert("Enter each details correctly");
	} else if (document.getElementById("objectId").value.length > 6) {
		alert("ObjectId maximum length is 6");
	} else {
		var data = {
			"objectID": objectId,
			"objectName": objectName,
			"highlight": isHighlight,
			"accessionNumber": accessionNumber
		};
		var postMethod = {
			method: 'POST',
			headers: {
				'Content-type': 'application/json; charset=UTF-8'
			},
			body: JSON.stringify(data)
		};
		var addUrl = "http://localhost:8080/museum";
		fetch(addUrl, postMethod)
			.then(res => res.text())
			.then(res => {
				console.log(res);
				if (res === "Success") {
					alert("Your object Id has been created successfully... Redirecting to Museum Details Page");
					window.location = "http://localhost:8080/index.html";
				} else {
					alert(res);
				}
			})
			.catch(err => {
				console.log(err);
				alert(err);
			})
	}
}