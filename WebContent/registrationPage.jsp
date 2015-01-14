<%-- Filename: registrationPage.jsp
Description: The jsp page for the Account Creation for the normal
end user.
Created by Team USA --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>
        EveryOneRich Bank - Create New Account
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
        <div class = "label heading col-md-offset-4 col-md-4 col-lg-offset-4 col-lg-4">
            Create User Login
        </div>
    </div>
    <div class = "col-lg-offset-1 col-lg-4 col-md-offset-1 col-md-4">
        <div class = "row fieldColumn">
            <form method="post" action="register">
                <label class = "label">First Name: </label>

                <input type="text" name="firstName" value =  "<%= (request.getAttribute("firstName") == null ? "" : request.getAttribute("firstName")) %>" />

                <% if(request.getAttribute("firstNameError") != null && !((String) request.getAttribute("firstNameError")).isEmpty()) { %>
                    <div class="alert alert-danger">
                        <%= request.getAttribute("firstNameError") %>
                    </div>
                <% } %>
        </div>
        <div class = "row fieldColumn">
            <label class = "label">Last Name: </label>

            <input type="text" name="lastName" value =  "<%= (request.getAttribute("lastName") == null ? "" : request.getAttribute("lastName")) %>" />

            <% if(request.getAttribute("lastNameError") != null && !((String) request.getAttribute("lastNameError")).isEmpty()) { %>
                <div class="alert alert-danger">
                    <%= (request.getAttribute("lastNameError") == null ? "" : request.getAttribute("lastNameError")) %>
                </div>
            <% } %>
        </div>
        <div class = "row fieldColumn">
            <label class = "label">User ID: </label>

            <input type="text" name="userID" value =  "<%= (request.getAttribute("userID") == null ? "" : request.getAttribute("userID")) %>" />

            <% if(request.getAttribute("userError") != null && !((String) request.getAttribute("userError")).isEmpty()) { %>
                <div class="alert alert-danger">
                    <%= (request.getAttribute("userError") == null ? "" : request.getAttribute("userError")) %>
                </div>
            <% } %>
        </div>
        <div class = "row fieldColumn">
            <label class = "label">Password: </label>

            <input type="password" name="password"/>
            <div class="help-text">Must be longer than 6 characters long and must contain a number.</div>

            <% if(request.getAttribute("passwordError") != null && !((String) request.getAttribute("passwordError")).isEmpty()) { %>
                <div class="alert alert-danger">
                    <%= (request.getAttribute("passwordError") == null ? "" : request.getAttribute("passwordError")) %>
                </div>
            <% } %>
        </div>
        <div class = "row fieldColumn">
            <label class = "label">Address: </label>

            <input type="text" name="address" value =  "<%= (request.getAttribute("address") == null ? "" : request.getAttribute("address")) %>">

            <% if(request.getAttribute("addressError") != null && !((String) request.getAttribute("addressError")).isEmpty()) { %>
                <div class="alert alert-danger">
                    <%= (request.getAttribute("addressError") == null ? "" : request.getAttribute("addressError")) %>
                </div>
            <% } %>
        </div>
        <div class = "row fieldColumn">
            <label class = "label">Phone Number: </label>

            <input type="text" name="phoneNumber" value =  "<%= (request.getAttribute("phoneNumber") == null ? "" : request.getAttribute("phoneNumber")) %>">

            <% if(request.getAttribute("phoneNumberError") != null && !((String) request.getAttribute("phoneNumberError")).isEmpty()) { %>
                <div class="alert alert-danger">
                    <%= (request.getAttribute("phoneNumberError") == null ? "" : request.getAttribute("phoneNumberError")) %>
                </div>
            <% } %>
        </div>
    </div>

    <div class = "col-lg-offset-2 col-lg-4 col-md-offset-2 col-md-4">
        <div class = "row fieldColumn">
            <label> Security Question </label>
            <select name="securityQuestion" style="width: 100%" value="<%= (request.getAttribute("securityQuestion") == null ? "" : request.getAttribute("securityQuestion")) %>">
                <option value="petName">What is your Pet's Name?</option>
                <option value="homeTown">What is your Home Town?</option>
                <option value="favTeacher">Name of your favorite teacher?</option>
            </select>
            <% if(request.getAttribute("securityQuestionError") != null && !((String) request.getAttribute("securityQuestionError")).isEmpty()) { %>
                <div class="alert alert-danger">
                    <%= (request.getAttribute("securityQuestionError") == null ? "" : request.getAttribute("securityQuestionError")) %>
                </div>
            <% } %>
        </div>

        <div class = "row fieldColumn">
            <label class = "label">Answer: </label>

            <input type="text" name="securityAnswer" value="<%= (request.getAttribute("securityAnswer") == null ? "" : request.getAttribute("securityAnswer")) %>">

            <% if(request.getAttribute("securityAnswerError") != null && !((String) request.getAttribute("securityAnswerError")).isEmpty()) { %>
                <div class="alert alert-danger">
                    <%= (request.getAttribute("securityAnswerError") == null ? "" : request.getAttribute("securityAnswerError")) %>
                </div>
            <% } %>
        </div>
        <button type="submit" id="submit">Register</button>
    </div>
</div>


</form>
</body>

</html>