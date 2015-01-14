<%-- Filename: forgotPassword.jsp
Description: jsp for the password recovery page. This page allows users
to answer their security question to get a password.
Created by Team USA --%>

<!DOCTYPE html>
<html>
<head>
  <title>
    EveryOneRich Bank - Forgot Password
  </title>

  <link rel="shortcut icon" href="/favicon.ico?v=1.1">

  <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">

  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>

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

  <%
    boolean initialPageLoad = request.getAttribute("initialPageLoad") != null && request.getAttribute("initialPageLoad").equals("true");
    String userId = (String) request.getAttribute("userId");
    String securityQuestion = (String) request.getAttribute("securityQuestion");
    boolean stepTwoLoad = request.getAttribute("stepTwoLoad") != null && request.getAttribute("stepTwoLoad").equals("true");
    boolean stepThreeLoad = request.getAttribute("stepThreeLoad") != null && request.getAttribute("stepThreeLoad").equals("true");
    boolean successLoad = request.getAttribute("successLoad") != null && request.getAttribute("successLoad").equals("true");
  %>
</head>

<body style="background: url(img/usa2.jpg) no-repeat 50% 75%  fixed;  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;">
<div class="jumbotron" style="background: black; opacity: 0.8; color: white; height:100px">
  <a href="/"><H3 style="position:fixed;text-align: center; top:20px; left:42%;"> EveryOneRich Bank </H3></a>
</div>


<div class = "container well col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8" style = "opacity: 0.9; margin-top:75px;">
  <div class = "col-md-12 col-lg-12">
    <div class = "label heading col-md-offset-5 col-md-4 col-lg-offset-5 col-lg-4">
      Forgot Password
    </div>
  </div>
  <div class="col-lg-offset-1 col-lg-4 col-md-offset-1 col-md-4">

    <% if(initialPageLoad) { %>
      <form method="post" action="forgotPassword">
        <div class = "col-md-4 col-lg-4">
          <label class = "label">UserID: </label>
        </div>
        <div class = "col-md-8 col-lg-8">
          <input type="text" name="userId">
        </div>
        <% if(request.getAttribute("errorMessage") != null && !((String) request.getAttribute("errorMessage")).isEmpty()) { %>
        <div class = "col-md-12 col-lg-12">
          <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
        </div>
        <% } %>
        <div class = "col-md-offset-4 col-lg-offset-4 col-md-8 col-lg-8"  style="margin-top: 15px;">
          <input type="hidden" name="pageLoad" value="initialPageLoad" />
          <input type="submit" class="btn btn-info" value="Submit" />
        </div>
      </form>

    <% } else if(stepTwoLoad) { %>
      <form method="post" action="forgotPassword">
        <div class="form-group">
          <label class = "label">Answer your security question: </label>

          <% if(securityQuestion != null) { %>
          <p>
            <% if(securityQuestion.equals("petName")) { %>
              What is your Pet's Name?
            <% } else if(securityQuestion.equals("homeTown")) { %>
              What is your Home Town?<
            <% } else if(securityQuestion.equals("favTeacher")) { %>
              Name of your favorite teacher?
            <% } %>
          </p>
          <% } %>

          <input type="text" name="securityAnswer">


        <% if(request.getAttribute("errorMessage") != null && !((String) request.getAttribute("errorMessage")).isEmpty()) { %>
            <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
        <% } %>
        </div>
          <input type="hidden" name="userId" value="<%= (userId != null ? userId : "") %>" />
          <input type="hidden" name="pageLoad" value="stepTwoLoad" />
          <input type="submit" class="btn btn-info" value="Submit" />
      </form>

    <% } else if(stepThreeLoad) {%>
      <form method="post" action="forgotPassword">
        <div class="form-group">
          <label class = "label">Create a new password: </label>

          <input type="password" name="password">
          <div class="help-text">Must be longer than 6 characters long and must contain a number.</div>
          <% if(request.getAttribute("errorMessage") != null && !((String) request.getAttribute("errorMessage")).isEmpty()) { %>

            <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>

          <% } %>
          <input type="hidden" name="userId" value="<%= (userId != null ? userId : "") %>" />
          <input type="hidden" name="pageLoad" value="stepThreeLoad" />
          <input type="submit" class="btn btn-info" value="Submit" />
        </div>
      </form>
    <% } else if(successLoad) {%>

      <h3>Successfully changed your password!</h3>
      <p><a href="login">Login</a></p>
    <% } %>
  </div>
</div>
</body>

</html>