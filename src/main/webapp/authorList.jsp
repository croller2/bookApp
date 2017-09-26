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
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    <jsp:include page="resources/partialPages/stylesheets.jsp"></jsp:include>
    <jsp:include page="resources/partialPages/javascript.jsp"></jsp:include>

        <title>Our Authors</title>
    </head>
    <body>
    <jsp:include page="resources/partialPages/navbar.jsp"></jsp:include>
        
        <div class="container-fluid">
            <div class="row main">
                <div class="col-xs-6 col-xs-offset-3">
                    <table class="table-striped text-center">
                        <thead>
                            <tr>
                                <th>Author ID</th>
                                <th>Author Name</th>
                                <th>Date Added</th>
                                <th>Edit Beer</th>
                                <th>Delete Beer</th>
                            </tr>
                        </thead>
                    <c:forEach var="author" items="${authors}">
                        <tr>
                            <td>${author.authorId}</td>
                            <td>${author.authorName}</td>
                            <td><fmt:formatDate pattern= "yyyy-MM-dd" value="${author.dateAdded}"/></td>
                            <td>
                                <form class="form-inline" action="AuthorControlleraction=edit?id=${author.authorId}" method="POST">
                                    <button class="btn btn-primary">Edit</button>
                                </form>
                            </td>
                            <td>
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#deleteBeer${author.authorId}">
                                    Delete
                                </button>
                                <!-- Modal -->
                                <div class="modal fade" id="deleteBeer${author.authorId}" z-index="1" role="dialog">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title">Delete - ${author.authorName}?</h4>
                                            </div>
                                            <div class="modal-body">
                                                <h3>Are you sure you want to delete ${author.authorName}?</h3>
                                                <form class="form-inline" action="AuthorController?action=delete?id=${author.authorId}" method="POST">
                                                    <button class="btn btn-danger">Delete</button>
                                                </form>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>    
                </table>    
            </div>
        </div>                         

    </div>

</body>
</html>