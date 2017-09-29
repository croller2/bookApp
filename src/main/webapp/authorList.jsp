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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    <jsp:include page="resources/partialPages/scripts.jsp"></jsp:include>

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
                                <th>Edit Author</th>
                                <th>Delete Author</th>
                            </tr>
                        </thead>
                    <c:forEach var="author" items="${authors}">
                        <tr>
                            <td>${author.authorId}</td>
                            <td>${author.authorName}</td>
                            <td><fmt:formatDate pattern= "yyyy-MM-dd" value="${author.dateAdded}"/></td>
                            <td>
                                <button class="btn btn-primary" data-toggle="modal" data-target="#editAuthor${author.authorId}">Edit</button>

                                <div class="modal fade" id="editAuthor${author.authorId}" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</span></button>
                                                <h4 class="modal-title">Edit - ${author.authorName}</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form class="form" action="authorController?action=edit&id=${author.authorId}" method="POST">
                                                    <div class="form-group">
                                                        <label for="authorName" >Author Name: </label>
                                                        <input type="text" value="${author.authorName}" class="form-control" name="author_${author.authorName}" id="author_${author.authorName}">
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="dateAdded" >Date Added: </label>
                                                        <input type="date" value="<fmt:formatDate pattern= "yyyy-MM-dd" value="${author.dateAdded}"/>" class="form-control" name="author_${author.dateAdded}" id="author_${author.dateAdded}">
                                                    </div>
                                                    <button type="submit" class="btn btn-primary">Submit Changes</button>
                                                </form>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#deleteAuthor${author.authorId}">
                                    Delete
                                </button>
                                <!-- Modal -->
                                <div class="modal fade" id="deleteAuthor${author.authorId}" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title">Delete - ${author.authorName}?</h4>
                                            </div>
                                            <div class="modal-body">
                                                <h3>Are you sure you want to delete ${author.authorName}?</h3>
                                                <form class="form-inline" action="authorController?action=delete&id=${author.authorId}" method="POST">
                                                    <button type="submit" class="btn btn-danger">Delete</button>
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