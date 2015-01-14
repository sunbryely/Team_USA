<%-- Filename: tellerAcount.jsp
Description: This file is the jsp for the teller account dashboard
Created by Team USA --%>

<%@ page import="model.User" %>
<%@ page import="model.Account" %>
<%@ page import="model.Transaction" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/dashboardStyle.css">
  <link rel="shortcut icon" href="/favicon.ico?v=1.1">

  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  <%
    DecimalFormat df =       new DecimalFormat("0.00"); // For formatting the balance for this account
    User user =              (User) session.getAttribute("user");
    Account account =        (Account) request.getAttribute("account");
    String error =           (String) request.getAttribute("error");
    String closureError =    (String) request.getAttribute("closureError");
    boolean needsPenalty =   (request.getAttribute("needsPenalty") != null &&
            (request.getAttribute("needsPenalty").equals("true")));
    boolean needsInterest =  (request.getAttribute("needsInterest") != null &&
            (request.getAttribute("needsInterest").equals("true")));
    Double interestToApply = (Double) request.getAttribute("interestAmount");
    String interestAmount =  (interestToApply != null ? df.format(interestToApply) : "");
    List<Transaction> transactionList = (List<Transaction>) request.getAttribute("transactions");
  %>

  <title>Teller View Account</title>
</head>
<body>

<nav class="navbar navbar-inverse" role="navigation" style="margin: 0px;">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

      <ul class="nav navbar-nav">
        <li><a href="teller">Dashboard</a></li>
        <li><a href="tellerTransaction">Make Transaction</a></li>
        <li class="active"><a href="tellerAccount?id=<%= (account != null) ? account.getNumber() : "" %>">Account<span class="sr-only">(current)</span></a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="logout">Logout</a> </li>
      </ul>
      <p class="navbar-text navbar-right"> Signed in as <strong><%= user.getUsername() %></strong></p>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<% if(error != null && !error.isEmpty() ) { %>
<div class="jumbotron" style="position:relative; background:#00b8b6;">
  <div class="container">
    <div class="row">
      <div class="alert alert-danger"><%= error %></div>
    </div>
  </div>
</div>

<% } else if(account == null || user == null) { %>
<div class="jumbotron" style="position:relative;background:#00b8b6;">
  <div class="container">
    <div class="row">
      <div class="alert alert-danger">Unexpected error trying to fetch the account, please try again.</div>
    </div>
  </div>
</div>
<% } else { %>

<div id = "mainPanel" class="container" style="position: relative; margin-top: 0px;">
  <div class="col-md-1 col-lg-1 col-sm-2 col-md-offset-1 col-lg-offset-1 col-sm-offset-2">
    <BR>
    <BR>
    <h2><%= account.getUser().getUsername() %></h2>
    <BR>
    <BR>
  </div>
  <div class="col-md-5 col-lg-5 col-sm-6 col-md-offset-5 col-lg-offset-5 col-sm-offset-2">
    <BR>
    <a href="tellerTransaction" id="submit_transaction">
      <div class="transfer col-md-4 col-lg-4 col-sm-4">
        <img src = "img/arrow.png" >
        Transfer
      </div>
    </a>
    <div class="transfer col-md-4 col-lg-4 col-sm-4">
      <br><h4 style="border:5px solid black;padding:5px;border-radius:10px;">$<%= df.format(account.getBalance()) %></h4><h4 style="color:white;">Balance</h4>
    </div>
    <div class="transfer col-md-4 col-lg-4 col-sm-4">
      <form method="post" action="account">
        <input type="hidden" name="closeAccount" value="true" />
        <input type="hidden" name="id" value="<%= account.getNumber() %>" />
        <input type="submit" style="margin-top: 38px; color:black;"value="Close Account" />
      </form>

      <%if(closureError != null && !closureError.isEmpty()) { %>
      <div class="alert alert-danger"><%= closureError %></div>
      <%} %>
    </div>
  </div>
</div>


<div class="container" style="margin-top:5%;">
  <div class = "col-md-offset-1 col-lg-offset-1 col-md-10 col-lg-10">
    <% if(needsPenalty) { %>
    <p>Penalty amount to apply: <%= Account.LOW_BALANCE_PENALTY_AMOUNT %></p>
    <form action="tellerAccount" method="post">
      <input type="hidden" name="applyPenalty" value="true" />
      <input type="hidden" name="id" value="<%= account.getNumber() %>" />
      <input type="submit" class="btn btn-primary" value="Apply Penalty"/>
    </form>
    <% } %>

    <% if(needsInterest) { %>
    <p>Interest amount to apply: <%= interestAmount %></p>
    <form action="tellerAccount" method="post">
      <input type="hidden" name="applyInterest" value="true" />
      <input type="hidden" name="id" value="<%= account.getNumber() %>" />
      <input type="submit" class="btn btn-primary" value="Apply Interest"/>
    </form>
    <% } %>
  </div>
</div>

<div class = "container" style="margin-top:5%;">
  <h4 style="margin-top:30px;">Recent Transactions</h4>
  <div id = "displayBox" class = "col-md-offset-1 col-lg-offset-1 col-md-10 col-lg-10">

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