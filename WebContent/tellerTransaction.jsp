<%-- Filename: tellerTransaction.jsp
Description: This file contains the necessary jsp
for the teller transaction page. This does not cover the normal
end user transactions, only those of teller accounts.
Created by Team USA --%>

<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teller Transaction</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/dashboardStyle.css">

    <style>
        input, select{
            color: #000 !important;
        }
    </style>

    <%
        User user = (User) session.getAttribute("user");
    %>
</head>

<body>

<nav class="navbar navbar-inverse" role="navigation" style="margin: 0px;">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav">
                <li ><a href="teller">Dashboard</a></li>
                <li class="active"><a href="tellerTransaction">Make Transaction<span class="sr-only">(current)</span></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout">Logout</a> </li>
            </ul>
            <p class="navbar-text navbar-right"> Signed in as <strong><%= user.getUsername() %></strong></p>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div id = "mainPanel" class="container" style="position:relative; margin-top: 0px;">
    <div class="col-md-1 col-lg-1 col-sm-2 col-md-offset-1 col-lg-offset-1 col-sm-offset-2">
        <BR>
        <BR>
        Teller
        <a href="logout">Logout</a>
        <BR>
        <BR>
    </div>
    <div class="col-md-5 col-lg-5 col-sm-6 col-md-offset-5 col-lg-offset-5 col-sm-offset-2">
        <BR>
        <div class="transfer col-md-4 col-lg-4 col-sm-4" style="color:#19FFA3">

        </div>
    </div>

</div>


<div class = "container" style="margin-top:5%;">
    <div id = "transferBox" class = "col-md-offset-4 col-lg-offset-4 col-md-4 col-lg-4">
        <% if(request.getAttribute("error") != null && !((String) request.getAttribute("error")).isEmpty()) { %>
            <div class="alert alert-danger"><p><%= request.getAttribute("error") %></p></div>
        <% } %>
        <% if(request.getAttribute("success") != null && !((String) request.getAttribute("success")).isEmpty()) { %>
            <div class="alert alert-success"><p><%= request.getAttribute("success") %></p></div>
        <% } %>
        <% if(request.getAttribute("accountError") != null && !((String) request.getAttribute("accountError")).isEmpty()) { %>
            <div class="alert alert-danger"><p><%= request.getAttribute("accountError") %></p></div>
        <% } %>
        <% if(request.getAttribute("debitError") != null && !((String) request.getAttribute("debitError")).isEmpty()) { %>
            <div class="alert alert-danger"><p><%= request.getAttribute("debitError") %></p></div>
        <% } %>
        <form action="tellerTransaction" method="POST" id="transaction_form">
            <p>
                <label>Account Number:</label>
                <input type="text" placeholder="Account Number" name="accountNumber" >
            </p>
            <p>
                <label>Transaction Type:</label>
                <br />
                <select name="transactionType">
                    <option disabled>Choose a transaction type:</option>
                    <option value="credit">Credit</option>
                    <option value="debit">Debit</option>
                </select>
            </p>
            <p>
                <label>Amount:</label>
                <br />
                <input type="text" placeholder="Amount" name="amount">
            </p>
            <input type="submit" class="btn btn-primary" value="Submit" />
            <a href="teller" class="btn btn-default">Cancel</a>
        </form>
    </div>
</div>
</body>
</html>