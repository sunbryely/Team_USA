<%-- Filename: account.jsp
Description: This file is for displaying and interacting
with the contents of a user's account. Shows account info
and transaction history, and provides links to dashboard,
add account, logout, transfer, and close account.
Created by Team USA --%>


<%@ page import="model.User" %>
<%@ page import="model.Account" %>
<%@ page import="model.Transaction" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>

<!-- This is the page for controls within a user's account -->

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/dashboardStyle.css">
  <link rel="shortcut icon" href="/favicon.ico?v=1.1">

  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  <%
    User user = (User) session.getAttribute("user");
    Account account = (Account) request.getAttribute("account");
    String error = (String) request.getAttribute("error");
    String closureError = (String) request.getAttribute("closureError");
    List<Transaction> transactionList = (List<Transaction>) request.getAttribute("transactions");
    DecimalFormat df = new DecimalFormat("0.00"); // For formatting the balance for this account
  %>
  
  <!--  the title of the page is "Everyone Rich Bank" with the account type appended --> 
 
  <title>Everyone Rich Bank<%= account != null ? ( " - " + account.getAccountType()) : "" %></title>
</head>
<body>

<!-- Navigation bar at the top of the page -->
<!-- Contains links to dashboard, to add an account, and logout -->

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

<!-- logic to handle errors -->

<% if(error != null && !error.isEmpty() ) { %>
<div class="jumbotron" style="position:relative; background:#00b8b6;">
  <div class="container">
    <div class="row">
      <div class="alert alert-danger"><%= error %></div>
    </div>
  </div>
</div>

<!--  logic to handle errors -->
<% } else if(account == null || user == null) { %>
<div class="jumbotron" style="position:relative;background:#00b8b6;">
  <div class="container">
    <div class="row">
      <div class="alert alert-danger">Unexpected error trying to fetch the account, please try again.</div>
    </div>
  </div>
</div>
<% } else { %>

<!-- if there were no errors, display the account information in -->
<!-- the middle of the page. -->
<!-- includes options to logout, close account, and transfer, -->
<!-- and displays the current amount in the account -->

<div class="jumbotron" style="position: relative;background:#00b8b6;">
  <div class="container">
    <div class="row">

      <table>
        <div class="col-md-6 col-lg-6">
          <h4 style="color:white;">
            <%= user.getFirstname() %>
          </h4>
          <h5><a style="color:#33FFFF;" href="logout">Logout</a></h5>
        </div>
      </table>

      <div class="transferUser transfer col-md-2 col-lg-2">
        <a href="transfer?id=<%= account.getNumber()%>">
          <figure><img src="img/arrow.png" style="width:100px;"/><figcaption style="margin-left: 30px;">Transfer</figcaption></figure>
        </a>
      </div>
      <div class="col-md-2 col-lg-2"><center><br><h4 style="border:5px solid black;padding:5px;border-radius:10px;">$<%= df.format(account.getBalance()) %></h4><h4 style="color:white;">Balance</h4></center></div>
      <div class="col-md-2 col-lg-2">


        <form method="post" action="account">
          <input type="hidden" name="closeAccount" value="true" />
          <input type="hidden" name="id" value="<%= account.getNumber() %>" />
          <input type="submit" style="margin-top: 38px;"value="Close Account" />
        </form>
        
        <!-- error handling for closure errors -->
        
        <%if(closureError != null && !closureError.isEmpty()) { %>
        <div class="alert alert-danger"><%= closureError %></div>
        <%} %>
      </div>
    </div>
  </div>
</div>


<!-- display the user's transaction history, if there is any -->

<div class="container">
  <h4 style="margin-top:30px;">Recent Transactions</h4>
  <div class="jumbotron" style="height:220px;background:#00b8b6;">

    <% if (transactionList == null || transactionList.isEmpty()) { %>
    <p>No transactions</p>
    <% } else { %>
    <table class="table">
      <tbody>

      <tr>
        <th><strong>Transaction Type</strong></th>
        <th><strong>Amount</strong></th>
        <th><strong>Date</strong></th>
      </tr>
      <% for(Transaction transaction : transactionList) {  %>
      <tr>
        <td><%= transaction.getTransaction() %></td>
        <td><%= df.format(transaction.getAmount()) %></td>
        <td><%= transaction.getDate().toLocaleString() %></td>
      </tr>

      <% } %>

      </tbody>
    </table>
    <% } %>

  </div>
</div>

<% } %>

</body>
</html>