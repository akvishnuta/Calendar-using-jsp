<%-- 
    Document   : Calendar
    Created on : 14 Jul, 2017, 12:15:43 PM
    Author     : akhil
--%>

<%@page import="java.time.LocalDate"%>
<%@page import="java.time.Month"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="backends.Months" %>
<!DOCTYPE html>

<html>
     <head>
        <title>Calendar</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="styles/css/bootstrap.css" rel="stylesheet"/>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <br><hr>
                <div class="col-md-3 col-md-offset-4">
                    <label>Month : </label>
                    <select id = 'monthSelect'  class='form-controls'  onchange='sendRequest()'>
                        <%for(int i=0;i<Month.values().length;i++){ 
                            if(LocalDate.now().getMonth().getValue()-1==i){%>
                        <%= "<option selected='"+ Month.values()[i] +"' >"+(Month.values()[i]) +"</option>"%>
                        <%}else{%>
                            <%="<option>"+(Month.values()[i]) +"</option>"%>
                        <%}}%>                       
                    </select>
                </div>
                    
                    <div class="col-md-2">
                    <label>Year : </label>
                    <select id = 'yearSelect' value= '<%=LocalDate.now().getYear()%>' class='form-controls' onclick='sendRequest()'>
                        <%for(int i=1990;i<2025;i++){ 
                          if(LocalDate.now().getYear()==i){%>
                          <%= "<option selected='"+i+"'>"+i+"</option>"%>
                          <%}else{%>
                        <%= "<option>"+ i +"</option>"%>
                        <%}}%>                      
                    </select>
                </div>  
            </div>
            
            <div class="row">
                <div class="col-md-3">
                    
                </div>
                <div class="col-md-6">
                    <hr>
                    <div id="updateElement"></div>
                </div>
                <div class="col-md-3">
                    
                </div>
            </div>
                    
        </div>
        
        <script type="text/javascript">
            var req;
            var toUpdate = document.getElementById('updateElement');
            var monthSelect= document.getElementById('monthSelect');
            //alert("toUpdate is " + toUpdate);

            if (window.XMLHttpRequest) {
                req = new XMLHttpRequest();
            } else if (window.ActiveXObject) {
                req = new ActiveXObject("Microsoft.XMLHTTP");
            } else {
                alert("Updates not supported");
            }

            function sendRequest() {
                // alert("sending request...");

                req.onreadystatechange = function () {
                    if (req.readyState == 4 && req.status == 200) {
                        // alert("got a reply");
                        toUpdate.innerHTML = req.responseText;
                        //sendRequest(); // send the next request
                       
                    }
                }
                var selectedMonth = document.getElementById('monthSelect').options[monthSelect.selectedIndex].value;
                var selectedYear = document.getElementById('yearSelect').options[yearSelect.selectedIndex].value;
                req.open("POST", "UpdateCalendar?month="+selectedMonth+"&year="+selectedYear, true);
                
                req.send()
            }

            sendRequest();
        </script>
        
        
    </body>
</html>
