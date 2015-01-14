<%-- Filename: userTransaction.jsp
Description: Jsp file for the Transactions on part of an end user
This jsp file does not cover Teller Transactions
Created by Team USA --%>


<%@ page import="model.Account" %>
<%@ page import="model.User" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Transaction</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/userStyle.css">

    <style>
        input, select {
            color: #000 !important;
        }
    </style>
    <%
        User user = (User) request.getSession().getAttribute("user");
        Account account = (Account) request.getAttribute("account");
        DecimalFormat df = new DecimalFormat("0.00"); // For formatting the balance for this account
        String error = (String) request.getAttribute("error");
    %>
</head>
<body>

<nav class="navbar navbar-inverse" role="navigation" style="margin: 0px;">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav">
                <li><a href="dashboard">Dashboard</a></li>
                <li><a href="addAccount">Add Account</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout">Logout</a> </li>
            </ul>
            <p class="navbar-text navbar-right"> Signed in as <strong><%= user.getUsername() %></strong></p>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


<% if(error != null && !error.isEmpty()) { %>

    <div class="alert alert-danger">
        <%= error %>
    </div>

<% } else { %>

<div id = "mainPanel" class="container" style="position:relative; margin-top: 0px;">
    <div class="col-md-1 col-lg-1 col-sm-2 col-md-offset-1 col-lg-offset-1 col-sm-offset-2">
        <table>
            <div class="col-md-7">
                <h4 style="color:white;">
                    <%= user.getFirstname() %>
                </h4>
                <h5><a style="color:#33FFFF;" href="logout">Logout</a></h5>
            </div>
        </table>
    </div>
    <div class="col-md-5 col-lg-5 col-sm-6 col-md-offset-5 col-lg-offset-5 col-sm-offset-2">
        <BR>
        <!--
                        <img src = "arrow.png" class="deposit">
                        <img src = "arrow.png" class="withdraw">
                        <img src = "arrow.png" class="transfer">
        -->


        <div class="transfer col-md-4 col-lg-4 col-sm-4">
            <h4 style="border:5px solid black;padding:5px;border-radius:10px;">$<%= df.format(account.getBalance()) %></h4><h4 style="color:white;">Balance</h4>
        </div>

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
        <br />
        <form action="transfer" method="post">
            <p>
                <label>Recipient: </label>
                <br />
                <input type="text" placeholder="Recipient Account Number" name="receiver" />
            </p>
            <p>
                <label>Amount: </label>
                <br />
                <input type="text" placeholder="Amount in dollars" name="amount" >
            </p>

            <input type="hidden" name="sender" value="<%= account.getNumber() %>" />
            <input type="submit" class="btn btn-primary" value="Transfer" />
            <a href="dashboard" class="btn btn-default">Cancel</a>
        </form>
    </div>
</div>

<% } %>
</body>
</html>