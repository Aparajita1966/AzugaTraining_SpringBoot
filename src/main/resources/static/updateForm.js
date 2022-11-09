 fetch("http://localhost:8080/museum/" + window.location.search.replace(/\?/g, '')).then(
    res => {
       res.json().then(
          data => {
             console.log(data);
             document.getElementById('objectId').value = data.objectID;
             document.getElementById('objectName').value = data.objectName;
             document.getElementById('isHighlight').value = data.highlight;
             document.getElementById('accessionNumber').value = data.accessionNumber;
          }
       )
    }
 )

 function update_object() {
    var objectId = document.getElementById("objectId").value;
    var objectName = document.getElementById("objectName").value;
    var isHighlight = document.getElementById("isHighlight").value;
    var accessionNumber = document.getElementById("accessionNumber").value;

    var letters = /^[A-Za-z]+$/;
    //other validations required code
    if (objectName == '') {
       alert("Enter details correctly");
    } else {
       var data = {
          "objectID": objectId,
          "objectName": objectName,
          "highlight": isHighlight,
          "accessionNumber": accessionNumber
       };
       var putMethod = {
          method: 'PUT',
          headers: {
             'Content-type': 'application/json; charset=UTF-8'
          },
          body: JSON.stringify(data)
       };
       var updateUrl = "http://localhost:8080/museum/" + objectId;
       fetch(updateUrl, putMethod)
          .then(res => res.text())
          .then(res => {
             console.log(res);
             if (res === "Success") {
                alert("Your object Id has been updated successfully... Redirecting to Museum Details Page");
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