<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
    <title>Spring Security Tutorial</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

<body>
    <form action="/registration" method="get">
        <button class="btn btn-md btn-warning btn-block" type="Submit">Go To Registration Page</button>
    </form>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign In</h3>
                </div>
                <div class="panel-body">
                    <form action="/login" method="POST" class="form-signin">
                        <h3 class="form-signin-heading" value="Welcome"></h3>
                        <br/>

                        <input type="text" id="username" title="username"  placeholder="Username"
                               class="form-control" /> <br/>
                        <input type="password"  placeholder="Password"
                               id="password" title="password" class="form-control" /> <br />
                        <c:if test="${param.error}">
                            <div align="center">
                                <p style="font-size: 20; color: #FF1C19;">Email or Password invalid, please verify</p>
                            </div>
                        </c:if>
                        <button class="btn btn-lg btn-primary btn-block" title="Submit" value="Login" type="Submit" th:text="Login"></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
