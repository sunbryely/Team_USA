<%-- filename: login.jsp
Description: jsp file for the Account Login page.
Created by Team USA --%>

<!DOCTYPE html>
<html>
<head>
    <title>
        EveryOneRich Bank - Login
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
        <div class = "label heading col-md-offset-5 col-md-4 col-lg-offset-5 col-lg-4">
            Login
        </div>
    </div>
    <div class="col-lg-offset-1 col-lg-4 col-md-offset-1 col-md-4">
        <form method="post" action="login">
            <div class = "col-md-4 col-lg-4">
                <label class = "label">UserID: </label>
            </div>
            <div class = "col-md-8 col-lg-8">
                <input type="text" name="userID">
            </div>
            <div class = "col-md-4 col-lg-4">
                <label class = "label">Password: </label>
            </div>
            <div class = "col-md-8 col-lg-8">
                <input type="password" name="password">
            </div>
            <% if(request.getAttribute("errorMessage") != null && !((String) request.getAttribute("errorMessage")).isEmpty()) { %>
            <div class = "col-md-12 col-lg-12">
                <div class="alert alert-danger"><%= request.getAttribute("errorMessage") %></div>
            </div>
            <% } %>
            <div class = "col-md-offset-4 col-lg-offset-4 col-md-8 col-lg-8"  style="margin-top: 15px;">
                <input type="submit" class="btn btn-info" value="Login" />
            </div>
        </form>
    </div>
    <div class = "col-lg-offset-2 col-lg-4 col-md-offset-2 col-md-4">
        <a href="register">Click here to Create an Account if you don't have one</a>
        <br />
        <a href="forgotPassword">Click here if you have forgotten your Password</a>
    </div>
</div>
</body>

</html>