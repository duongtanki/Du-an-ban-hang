
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="col-sm-3">
    <div class="card bg-light mb-3">
        <div class="card-header bg-success text-white text-uppercase">Advanced search</div>
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