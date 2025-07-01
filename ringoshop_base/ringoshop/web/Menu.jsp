<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.Item" %>
<!--begin of menu-->
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="home">Shoes</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault"
                aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault">
            <ul class="navbar-nav mr-auto">
                <c:if test="${not empty sessionScope.account and sessionScope.account.role == 1}">
                    <li class="nav-item">
                        <a class="nav-link" href="#">Manager Account</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Manager Product</a>
                    </li>
                </c:if>
            </ul>

            <form action="search" method="get" class="form-inline my-2 my-lg-0">
                <div class="input-group input-group-sm">
                    <input name="name" type="text" class="form-control" placeholder="Search...">
                    <div class="input-group-append">
                        <button type="submit" class="btn btn-secondary btn-number">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
            
            <%
                List<Item> cart = (List<Item>) session.getAttribute("cart");
                int cartSize = (cart != null) ? cart.size() : 0;
            %>

            <a class="btn btn-success btn-sm ml-3" href="Cart.jsp">
                <i class="fa fa-shopping-cart"></i> Cart
                <span class="badge badge-light"><%= cartSize %></span>
            </a>
            

            <!-- Login/Logout section -->
            <ul class="navbar-nav ml-3">
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <li class="nav-item">
                            <a class="nav-link" href="#">Hello, ${sessionScope.account.username}</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="logout">Logout</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="Login.jsp">Login</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<!-- Banner section -->

<!--end of menu-->
