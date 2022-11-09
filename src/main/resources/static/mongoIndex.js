document.addEventListener('DOMContentLoaded', init, false);

let data, table, sortCol;
let sortAsc = false;
const pageSize = 5;
let curPage = 1;

async function init() {
// Select the table (well, tbody)
  table = document.querySelector('#pager tbody');
  // get the cats
  let resp = await fetch('http://localhost:8080/mongo');
  data = await resp.json();
  renderTable();

  // listen for sort clicks
  document.querySelectorAll('#pager thead tr th').forEach(t => {
     t.addEventListener('click', sort, false);
  });
}

function renderTable() {
  // create html
  let result = '';
  data.forEach(itemData => {
     result += `<tr>
     <td>${itemData.objectID}</td>
     <td>${itemData.objectName}</td>
     <td>${itemData.isHighlight}</td>
     <td>${itemData.accessionNumber}</td>
     <td>${itemData.accessionYear}</td>
     <td>${itemData.isPublicDomain}</td>
     <td>${itemData.department}</td>
     <td>${itemData.title}</td>
     <td>${itemData.artistRole}</td>
     <td><input type=button value=Edit onclick=openUpdatePage(this)></td>
     <td><input type=button value=Delete onclick=deleteRow(this)></td>
     </tr>`;
  });
  table.innerHTML = result;
  var pager = new Pager('pager', 5);
  pager.init();
  pager.showPageNav('pager', 'pageNavPosition');
  pager.showPage(1);
}

function sort(e) {
  let thisSort = e.target.dataset.sort;
  if(sortCol === thisSort) sortAsc = !sortAsc;
  sortCol = thisSort;
  console.log('sort dir is ', sortAsc);
  data.sort((a, b) => {
    if(a[sortCol] < b[sortCol]) return sortAsc?1:-1;
    if(a[sortCol] > b[sortCol]) return sortAsc?-1:1;
    return 0;
  });
  renderTable();
}

function getObject() {
	fetch("http://localhost:8080/mongo").then(
    	res => {
    		res.json().then(
    			data => {
    				console.log(data);
    				if (data.length > 0) {
    					var temp = "";
    					data.forEach((itemData) => {
    						temp += "<tr>";
    						temp += "<td>" + itemData.objectID + "</td>";
    						temp += "<td>" + itemData.objectName + "</td>";
    						temp += "<td>" + itemData.isHighlight + "</td>";
    						temp += "<td>" + itemData.accessionNumber + "</td>";
    						temp += "<td>" + itemData.accessionYear + "</td>";
    						temp += "<td>" + itemData.isPublicDomain + "</td>";
    						temp += "<td>" + itemData.department + "</td>";
    						temp += "<td>" + itemData.title + "</td>";
    						temp += "<td>" + itemData.artistRole + "</td>";
    						temp += "<td>" + "<input type=button value=Edit onclick=openUpdatePage(this)>" + "</td>";
    						temp += "<td>" + "<input type=button value=Delete onclick=deleteRow(this)>" + "</td>";
    						temp += "</tr>";
    					});
    					document.getElementById('data').innerHTML = temp;
    					var pager = new Pager('pager', 5);

                        pager.init();
                        pager.showPageNav('pager', 'pageNavPosition');
                        pager.showPage(1);
    				}
    			}
    		).catch(err => {
    			console.log(err);
    			alert(err);
    		})
    	}
    )
}
var btn = document.getElementById("addBtn");
btn.addEventListener("click", addObject);

function addObject() {
	var objectIdValue = document.getElementById("object-id").value;
	var addUrl = "http://localhost:8080/mongo/add/" + objectIdValue;
	fetch(addUrl)
		.then(res => res.text())
		.then(res => {
			console.log(res);
			if (res === "Success") {
				document.getElementById('object-id').value = '';
				alert("Your object Id has been added successfully...");
				getObject();
			} else {
				alert(res);
			}
		})
		.catch(err => {
			console.log(err);
			alert(err);
		})
}

var btn = document.getElementById("updateBtn");
btn.addEventListener("click", updateObject);

function updateObject() {
	var objectIdValue = document.getElementById("objectId").value;
	var box = document.getElementById('field');
	var dropDownKey = box.options[box.selectedIndex].value;
	var dropDownValue = document.getElementById("fieldValue").value;
	var json = "{" + "\"" + dropDownKey + "\"" + " : " + "\"" + dropDownValue + "\"" + "," + "\"" + "objectID" + "\"" + " : " + "\"" + objectIdValue + "\"" + "}";
	const putMethod = {
		method: 'PUT',
		headers: {
			'Content-type': 'application/json; charset=UTF-8'
		},
		body: json // We send data in JSON format
	}
	var updateUrl = "http://localhost:8080/mongo/" + objectIdValue;
	fetch(updateUrl, putMethod)
		.then(res => res.text())
		.then(res => {
			console.log(res);
			if (res === "Success") {
				document.getElementById('object-id').value = '';
				document.getElementById('fieldValue').value = '';
				alert("Your object Id has been added successfully...");
				getObject();
			} else {
				alert(res);
			}
		})
		.catch(err => {
			console.log(err);
			alert(err);
		})
}

function deleteRow(r) {
	var i = r.parentNode.parentNode.rowIndex;
	var id = document.getElementById("pager").rows[i].cells[0].innerHTML;
	const deleteMethod = {
		method: 'DELETE',
		headers: {
			'Content-type': 'application/json; charset=UTF-8'
		}
	}
	var deleteUrl = "http://localhost:8080/mongo/" + id;
	fetch(deleteUrl, deleteMethod)
		.then(res => res.text()) // or res.json()
		.then(res => console.log(res))
	document.getElementById("pager").deleteRow(i);
	alert("Your object Id has been deleted successfully...");
	var pager = new Pager('pager', 5);
          pager.init();
          pager.showPageNav('pager', 'pageNavPosition');
          pager.showPage(1);
}

var insertButton = document.getElementById("insertBtn")
insertButton.addEventListener("click", openInsertPage)

function openInsertPage() {
	window.location.href = "mongoform.html"
}

function openUpdatePage(r) {
	var i = r.parentNode.parentNode.rowIndex;
	var id = document.getElementById("pager").rows[i].cells[0].innerHTML;
	window.location.href = "mongoUpdateForm.html" + "?" + id;
}

function Pager(tableName, itemsPerPage) {
    'use strict';

    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;

    this.showRecords = function (from, to) {
        let rows = document.getElementById(tableName).rows;

        // i starts from 1 to skip table header row
        for (let i = 1; i < rows.length; i++) {
            if (i < from || i > to) {
                rows[i].style.display = 'none';
            } else {
                rows[i].style.display = '';
            }
        }
    };

    this.showPage = function (pageNumber) {
        if (!this.inited) {
            // Not initialized
            return;
        }

        let oldPageAnchor = document.getElementById('pg' + this.currentPage);
        oldPageAnchor.className = 'pg-normal';

        this.currentPage = pageNumber;
        let newPageAnchor = document.getElementById('pg' + this.currentPage);
        newPageAnchor.className = 'pg-selected';

        let from = (pageNumber - 1) * itemsPerPage + 1;
        let to = from + itemsPerPage - 1;
        this.showRecords(from, to);

        let pgNext = document.querySelector('.pg-next'),
            pgPrev = document.querySelector('.pg-prev');

        if (this.currentPage == this.pages) {
            pgNext.style.display = 'none';
        } else {
            pgNext.style.display = '';
        }

        if (this.currentPage === 1) {
            pgPrev.style.display = 'none';
        } else {
            pgPrev.style.display = '';
        }
    };

    this.prev = function () {
        if (this.currentPage > 1) {
            this.showPage(this.currentPage - 1);
        }
    };

    this.next = function () {
        let rows = document.getElementById(tableName).rows;
        let records = (rows.length - 1);
        this.pages = Math.ceil(records / itemsPerPage);
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    };

    this.init = function () {
        let rows = document.getElementById(tableName).rows;
        let records = (rows.length - 1);

        this.pages = Math.ceil(records / itemsPerPage);
        this.inited = true;
    };

    this.showPageNav = function (pagerName, positionId) {
            if (!this.inited) {
                // Not initialized
                return;
            }

            let element = document.getElementById(positionId),
                pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal pg-prev">«</span>';

            for (let page = 1; page <= this.pages; page++) {
                pagerHtml += '<span id="pg' + page + '" class="pg-normal pg-next" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span>';
            }

            pagerHtml += '<span onclick="' + pagerName + '.next();" class="pg-normal">»</span>';

            element.innerHTML = pagerHtml;
    };
}

function search() {
  var box = document.getElementById('searchfield');
  var dropDownKey = box.options[box.selectedIndex].value;
  var itemsPerPage = 3;
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  if(filter){
   table = document.getElementById("pager");
     tr = table.getElementsByTagName("tr");
     var records = 0;
     for (i = 0; i < tr.length; i++) {
       td = tr[i].getElementsByTagName("td")[dropDownKey];
       if (td) {
         txtValue = td.textContent || td.innerText;
         if (txtValue.toUpperCase().indexOf(filter) > -1) {
           tr[i].style.display = "";
           records ++;
         } else {
           tr[i].style.display = "none";
         }
       }
     }
     showPageNavi('pager', 'pageNavPosition', Math.ceil(records / itemsPerPage))
     var pager = new Pager('pager', 3);
     pager.showPage(1);
  }else{
    var pager = new Pager('pager', 5);
      pager.init();
      pager.showPageNav('pager', 'pageNavPosition');
      pager.showPage(1);
  }
}

function showPageNavi(pagerName, positionId, pages) {
            let element = document.getElementById(positionId),
                pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal pg-prev">«</span>';

            for (let page = 1; page <= pages; page++) {
                pagerHtml += '<span id="pg' + page + '" class="pg-normal pg-next" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span>';
            }

            pagerHtml += '<span onclick="' + pagerName + '.next();" class="pg-normal">»</span>';

            element.innerHTML = pagerHtml;
};