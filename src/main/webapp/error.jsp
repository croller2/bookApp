<%-- 
    Document   : error
    Created on : Sep 19, 2017, 9:03:12 PM
    Author     : chris.roller
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    <jsp:include page="resources/partialPages/scripts.jsp"></jsp:include>

        <title>Uh oh</title>
    </head>
    <body>
    <jsp:include page="resources/partialPages/navbar.jsp"></jsp:include>

        <h1>Woah What Happened!</h1>
        <h3>${errorMessage}</h3>
    </body>
</html>
