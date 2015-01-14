<%-- Filename: accountCreation.jsp
Description: This file contains the necessary jsp
for the user to create a new account. The user 
will need to submit an account type and an initial amount.
Created by Team USA --%>

<%@ page import="model.User" %>
<!DOCTYPE html>

<!-- This is the page displayed that the user interacts with -->
<!-- in order to create a new account -->

<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="css/dashboardStyle.css">
  <link rel="shortcut icon" href="/favicon.ico?v=1.1">

  <!-- the title of the page -->

  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
  <title>EveryOneRich Bank - Add Account</title>
  <%
    User user = (User) session.getAttribute("user");
  %>

  <style type = "text/css">
    .label
    {
      display: inline-block;
      width: 100px;
      color: Black;
      font-size: 12px;
    }

    .heading
    {
      width: 100px;
      color: Black;
      font-size: 20px;
      font-family: sans-serif;
    }
  </style>
</head>
<body>

<!-- the navigation bar at the top of the page. offers access to -->
<!-- the dashboard and logout pages and displays "Add Account", -->
<!-- highlighted to signify current page, and the user's username -->

<nav class="navbar navbar-inverse" role="navigation" style="margin: 0px;">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

      <ul class="nav navbar-nav">
        <li><a href="dashboard">Dashboard</a></li>
        <li  class="active"><a href="addAccount">Add Account<span class="sr-only">(current)</span></a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="logout">Logout</a> </li>
      </ul>
      <p class="navbar-text navbar-right"> Signed in as <strong><%= user.getUsername() %></strong></p>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

<form method="post" action="addAccount">

  <!-- This is what is displayed in the middle of the page for the create -->
  <!-- account form that is filled out by the user -->

  <div class="jumbotron" style="position: relative;background:#00b8b6;">
    <a href="#" style="color:white"><H2 style = "text-align: center"> EveryOneRich Bank </H2></a>
  </div>

  <div class = "container jumbotron col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8" style="background:#00b8b6; ">
    <div class = "col-md-12 col-lg-12">
      <div class = "label heading col-md-offset-4 col-md-4 col-lg-offset-4 col-lg-4">
        Add Account
      </div>
    </div>
    <div class = "col-lg-offset-1 col-lg-4 col-md-offset-1 col-md-4">

      <!-- Displays the option of picking an account type between checking and savings. -->
      <!-- handles error if neither are picked -->

      <BR>
      <div class="row fieldColumn">
        <div class="col-md-6 col-lg-6">
          <label class="label">Account Type: </label>
        </div>

        <div class="col-md-6 col-lg-6">
          <select name="accountType">
            <option disabled="disabled" selected="selected" value="<%= (request.getAttribute("accountType") == null ? "" : request.getAttribute("accountType")) %>">Select Account Type</option>
            <option value="checking">Checking</option>
            <option value="savings">Savings</option>
          </select>
        </div>
        <p>
          <%= (request.getAttribute("accountTypeError") == null ? "" : request.getAttribute("accountTypeError")) %>
        </p>
      </div>

      <!-- Displays the user's initial deposit input field -->
      <!-- handles error if no initial deposit -->

      <div class = "row fieldColumn">
        <div class="col-md-6 col-lg-6">
          <label class = "label">Initial Deposit: </label>
        </div>
        <div class="col-md-6 col-lg-6">
          <input type="text" name="deposit" value =  "<%= (request.getAttribute("deposit") == null ? "" : request.getAttribute("deposit")) %>" />
        </div>
        <p>
          <%= (request.getAttribute("depositError") == null ? "" : request.getAttribute("depositError")) %>
        </p>
      </div>

      <!-- (option) TODO add overdraft protection here -->
      
      <!-- submit button to submit the fields entered by the user -->
      
      <div class = "row fieldColumn">
        <div class="col-md-offset-6 col-lg-offset-6 col-md-6 col-lg-6">
          <button type="submit" id="submit" style="margin-top: 8px">Submit</button>
        </div>
      </div>

    </div>
  </div>
</form>
</body>
</html>
