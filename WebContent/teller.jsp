<%-- Filename: teller.jsp
Created by Team USA --%>

<%@ page import="model.User" %>
<%@ page import="model.Account" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Teller</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/dashboardStyle.css">

    <%
        User user = (User) session.getAttribute("user");
    %>
</head>

<body>

<nav class="navbar navbar-inverse" role="navigation" style="margin: 0px;">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav">
                <li class="active"><a href="teller">Dashboard<span class="sr-only">(current)</span></a></li>
                <li><a href="tellerTransaction">Make Transaction</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout">Logout</a> </li>
            </ul>
            <p class="navbar-text navbar-right"> Signed in as <strong><%= user.getUsername() %></strong></p>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<div id = "mainPanel" class="container" style="position: relative; margin-top: 0px;">
    <div class="col-md-1 col-lg-1 col-sm-2 col-md-offset-1 col-lg-offset-1 col-sm-offset-2">
        <BR>
        <BR>
        <h2>Teller</h2>
        <a href="logout">Logout</a>
        <BR>
        <BR>
    </div>
    <div class="col-md-5 col-lg-5 col-sm-6 col-md-offset-5 col-lg-offset-5 col-sm-offset-2">
        <BR>
        <a href="tellerTransaction" id="submit_transaction">
            <div class="withdraw col-md-4 col-lg-4 col-sm-4">
                <img src = "img/arrow.png" >
                Make Transaction
            </div>
        </a>

        <div class="transfer col-md-4 col-lg-4 col-sm-4" style="color:#19FFA3">
        </div>
    </div>
</div>


<div class = "container" style="margin-top:5%;">
    <div class="col-md-12 col-lg-12 col-sm-12">
        <h4 class="col-md-offset-1 col-lg-offset-1" style="width:100%; background-color:white; color:black;">All Accounts</h4>
    </div>
    <div id = "displayBox" class = "col-md-offset-1 col-lg-offset-1 col-md-10 col-lg-10">


        <BR>
        <table cellspacing="2px" class="table">
            <tbody>
            <tr>
                <th>Account Number</th>
                <th>Account Type</th>
                <th>Username</th>
                <th>Balance</th>
                <th></th>
            </tr>

            <%
                List<Account> accountList = (List<Account>) request.getAttribute("accounts"); %>

            <%
                for (Account anAccountList : accountList) {
            %>
            <tr>
                <td>
                    <%= anAccountList.getNumber() %>
                </td>
                <td>
                    <%= anAccountList.getAccountType() %>
                </td>
                <td>
                    <%= anAccountList.getUser().getUsername() %>
                </td>
                <td>
                    <%= anAccountList.getBalance()%>
                </td>
                <td>
                    <a class="btn btn-default" href="tellerAccount?id=<%= anAccountList.getNumber() %>">View Account</a>
                </td>
            </tr>
            <% }%>
            </tbody>
        </table>


    </div>
</div>
</body>
</html>