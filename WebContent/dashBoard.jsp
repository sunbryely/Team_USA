<%-- Filename: dashboard.jsp
Description: This file handles the page that the user 
can interact with once logged in to their account.
Created by Team USA --%>

<%@ page import="model.Account" %>
<%@ page import="model.User" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/dashboardStyle.css">
  <link rel="shortcut icon" href="/favicon.ico?v=1.1">

  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  <title>Everyone Rich Bank - Dashboard</title>
  <%
    User user = (User) session.getAttribute("user");
    List<Account> accounts = (List<Account>) request.getAttribute("accounts");
    String closureError = (String) request.getAttribute("closureError");
    DecimalFormat df = new DecimalFormat("#.00"); // For formatting the balance for this account
  %>
</head>
<body>

<!-- navigation bar for access to Add Account and logout -->
<!-- highlights the Dashboard option -->

<nav class="navbar navbar-inverse" role="navigation" style="margin: 0px;">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

      <ul class="nav navbar-nav">
        <li class="active"><a href="dashboard">Dashboard<span class="sr-only">(current)</span></a></li>
        <li><a href="addAccount">Add Account</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="logout">Logout</a> </li>
      </ul>
      <p class="navbar-text navbar-right"> Signed in as <strong><%= user.getUsername() %></strong></p>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<div class="jumbotron" style="position: relative;background:#00b8b6;">


  <!-- container that provides the user options to logout or close account -->
  
  <div class="container">
    <div class="row">

      <table>
        <div class="col-md-7">
          <h4 style="color:white;">
            <%= user.getFirstname() %>
          </h4>
          <h5><a style="color:#33FFFF;" href="logout">Logout</a></h5>
        </div>
      </table>
    </div>
    <div class="row">
      <%if(closureError != null && !closureError.isEmpty()) { %>
      <div class="alert alert-danger"><%= closureError %></div>
      <%} %>

      <form method="post" action="dashboard">
        <input type="hidden" name="closeAccount" value="true" />
        <input type="submit" value="Close User Account" />
      </form>
    </div>
  </div>
</div>

<!-- This lists all of the user's accounts in another field below -->
<div class="container">
  <h4 style="margin-top:30px;">All Accounts</h4>
  <div class="jumbotron" style="background:#00b8b6;">

    <% if( request.getAttribute("accountClosed") != null &&
            !((String) request.getAttribute("accountClosed")).isEmpty() )
    { %>
    <div class="alert alert-success"><%= (String) request.getAttribute("accountClosed") %></div>
    <% } %>
    <% if(accounts == null || accounts.isEmpty()) { %>
    <div class="alert alert-danger">No accounts yet.</div>
    <% } else { %>

    <table class="table">
      <tbody>

      <tr>
        <th><strong>Account Number</strong></th>
        <th><strong>Account Type</strong></th>
        <th><strong>Balance</strong></th>
        <th><strong>Date Created</strong></th>
        <th></th>
      </tr>

      <% for(Account account : accounts) { %>
      <tr>
        <td><%= account.getNumber() %></td>
        <td><%= (account.getAccountType().equalsIgnoreCase("checking") ? "Checking" : "Savings") %></td>
        <td><%= df.format(account.getBalance()) %></td>
        <td><%= account.getdateCreated().toString() %></td>
        <td><a class="btn btn-default" href="account?id=<%= account.getNumber() %>">View Account</a></td>
      </tr>
      <% } // End for %>

      </tbody>
    </table>

    <% } // end if %>

    <a class="btn btn-info" href="addAccount">Add Account</a>
  </div>
</div>

</body>
</html>
