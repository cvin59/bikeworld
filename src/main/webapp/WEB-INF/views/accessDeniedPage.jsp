
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<script src="resources/themes/lbd/assets/js/jquery-1.10.2.js" type="text/javascript"></script>
<link href="resources/themes/lbd/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Access Denied Page</title>
</head>
<body>
Dear <strong>${user}</strong>, You are not authorized to access this page
<a href="<c:url value="/logout"/>">Logout</a>

</body>
</html>
