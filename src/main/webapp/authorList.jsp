<%-- 
    Document   : authorList
    Created on : Sep 19, 2017, 8:35:48 PM
    Author     : chris.roller
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <jsp:include page="resources/partialPages/javascript.jsp"></jsp:include>
        <jsp:include page="resources/partialPages/stylesheets.jsp"></jsp:include>

        <title>Author List</title>
    </head>
    <body>
        <jsp:include page="resources/partialPages/navbar.jsp"></jsp:include>
        <div style="margin-top: 15%" class="container-fluid wrapper">
            <div class="row">
                <div class="col-xs-6 col-xs-offset-3">
                    <table class="table-striped">
                        <c:forEach var="author" items="${authors}">
                            <tr>
                                <td>${author.authorId}</td>
                                <td>${author.authorName}</td>
                                <td><fmt:formatDate pattern= "yyyy-MM-dd" value="${author.dateAdded}"/></td>
                            </tr>
                        </c:forEach>    
                    </table>    
                </div>
            </div>

        </div>

    </body>
</html>