

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="col-sm-3">
    <div class="card bg-light mb-3">
        <div class="card-header bg-primary text-white text-uppercase"><i class="fa fa-list"></i> Categories</div>
        <ul class="list-group category_block">
            <c:forEach begin="1" end="3" var="o">
                <li class="list-group-item text-white"><a href="#">Giày adidas</a></li>
            </c:forEach>

        </ul>
    </div>
    <div class="card bg-light mb-3">
        <div class="card-header bg-success text-white text-uppercase">Last product</div>
        <div class="card-body">
            <c:if test="${not empty lastProduct}">
                <img class="img-fluid" src="${lastProduct.image}" alt="${lastProduct.name}" />
                <h5 class="card-title">${lastProduct.name}</h5>
                <p class="card-text">${lastProduct.description}</p>
                <p class="bloc_left_price">${lastProduct.price} $</p>
            </c:if>
        </div>
    </div>
</div>


