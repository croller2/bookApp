<%-- 
    Document   : bookList
    Created on : Nov 16, 2017, 2:37:55 PM
    Author     : chris.roller
--%>

<<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    <jsp:include page="resources/partialPages/scripts.jsp"></jsp:include>

        <title>Our Books</title>
    </head>
    <body>
    <jsp:include page="resources/partialPages/navbar.jsp"></jsp:include>

        <div class="container-fluid">
            <div class="row main">
                
                <div class="col-xs-4 col-xs-offset-4">                   
                    <div class="modal fade" id="addBookForm" role="dialog">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Add a Book</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form" action="books?action=add" method="POST">
                                        <div class="form-group">
                                            <label for="addBookName" >Book Name: </label>
                                            <input type="text" required class="form-control" name="addBookName" id="addBookName">
                                        </div>
                                        <button type="submit" class="btn btn-primary">Add Book</button>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <h3 class="text-center text-area">Add, Update, & Delete Books</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2">
                    <table class="table-striped text-center">
                        <thead>
                            <tr>
                                <th>Book ID</th>
                                <th>Book Name</th>
                                <th>Book Publish Date</th>
                                <th>ISBN</th>
                                <th>Author</th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <td>${book.bookId}</td>
                            <td>${book.bookName}</td>
                            <td><fmt:formatDate pattern= "yyyy-MM-dd" value="${book.bookPublishDate}"/></td>
                            <td>${book.bookIsbn}</td>
                            <td>${book.author.authorName}</td>
                            <td>
                                <button class="btn btn-primary" data-toggle="modal" data-target="#editBook${book.bookId}">Edit</button>

                                <div class="modal fade" id="editBook{book.bookId}" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</span></button>
                                                <h4 class="modal-title">Edit - ${book.bookName}</h4>
                                            </div>
                                            <div class="modal-body">
                                                <form class="form" action="books?action=edit&id=${book.bookId}" method="POST">
                                                    <div class="form-group">
                                                        <label for="bookName" >Book Name: </label>
                                                        <input type="text" value="${book.bookName}" class="form-control" name="bookName_${book.bookId}" id="bookName_${book.bookId}">
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
                                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#deleteBook${book.bookId}">
                                    Delete
                                </button>
                                <!-- Modal -->
                                <div class="modal fade" id="deleteBook${book.bookId}" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title">Delete - ${book.bookName}?</h4>
                                            </div>
                                            <div class="modal-body">
                                                <h3>Are you sure you want to delete ${book.bookName}?</h3>
                                                <form class="form-inline" action="books?action=delete&id=${book.bookId}" method="POST">
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
                        </tbody>
                        <tfoot>
                            <tr>
                                <td>
                                    <button type="button" class="btn btn-primary pull-left" data-toggle="modal" data-target="#addBookForm">
                                        Add Book
                                    </button>
                                </td>
                            </tr>
                        </tfoot>
                </table>    
            </div>

        </div>                        

    </div>

</body>
</html>
