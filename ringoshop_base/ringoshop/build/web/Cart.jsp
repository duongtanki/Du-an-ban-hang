<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Shoes" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Giỏ hàng</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    </head>
    <body>
        <jsp:include page="Menu.jsp" />
        <section class="jumbotron text-center">
            <div class="container">
                <h1 class="jumbotron-heading">Giỏ hàng</h1>
            </div>
        </section>
        <div class="shopping-cart">
            <div class="px-4 px-lg-0">
                <div class="pb-5">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12 p-5 bg-white rounded shadow-sm mb-5">
                                <!-- Shopping cart table -->
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th class="border-0 bg-light">Sản phẩm</th>
                                                <th class="border-0 bg-light">Đơn giá</th>
                                                <th class="border-0 bg-light">Chi tiết</th>
                                                <th class="border-0 bg-light">Xoá</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${sessionScope.cart}" var="shoe" varStatus="status">
                                                <tr>
                                                    <td>
                                                        <div class="p-2">
                                                            <img src="${shoe.image}" width="70" class="img-fluid rounded shadow-sm">
                                                            <div class="ml-3 d-inline-block align-middle">
                                                                <h5 class="mb-0">
                                                                    <a href="#" class="text-dark d-inline-block">${shoe.name}</a>
                                                                </h5>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td class="align-middle"><strong>${shoe.price} $</strong></td>
                                                    <td class="align-middle">${shoe.description}</td>
                                                    <td class="align-middle">
                                                        <form action="removefromcart" method="post">
                                                            <input type="hidden" name="index" value="${status.index}" />
                                                            <button type="submit" class="btn btn-danger">Xóa</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                                <!-- End -->
                            </div>
                        </div>
                        <div class="row py-5 p-4 bg-white rounded shadow-sm">
                            <div class="col-lg-6">
                                <div class="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">Thông tin giao hàng</div>
                                <form action="checkout" method="post" class="p-4">
                                    <div class="form-group">
                                        <label for="fullname">Họ và tên</label>
                                        <input type="text" class="form-control" name="fullname" required />
                                    </div>
                                    <div class="form-group">
                                        <label for="phone">Số điện thoại</label>
                                        <input type="text" class="form-control" name="phone" required />
                                    </div>
                                    <div class="form-group">
                                        <label for="address">Địa chỉ</label>
                                        <textarea class="form-control" name="address" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-dark rounded-pill py-2 btn-block">Đặt hàng</button>
                                </form>
                            </div>
                            <div class="col-lg-6">
                                <div class="bg-light rounded-pill px-4 py-3 text-uppercase font-weight-bold">Thành tiền</div>
                                <div class="p-4">
                                    <ul class="list-unstyled mb-4">
                                        <li class="d-flex justify-content-between py-3 border-bottom">
                                            <strong class="text-muted">Tổng tiền hàng</strong>
                                            <strong>${total} $</strong>
                                        </li>
                                        <li class="d-flex justify-content-between py-3 border-bottom">
                                            <strong class="text-muted">Phí vận chuyển</strong>
                                            <strong>Miễn phí</strong>
                                        </li>
                                        <li class="d-flex justify-content-between py-3 border-bottom">
                                            <strong class="text-muted">VAT</strong>
                                            <strong>${vat} $</strong>
                                        </li>
                                        <li class="d-flex justify-content-between py-3 border-bottom">
                                            <strong class="text-muted">Tổng thanh toán</strong>
                                            <h5 class="font-weight-bold">${total + vat} $</h5>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </body>
</html>
