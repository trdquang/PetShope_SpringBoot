<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PetShop</title>
    <%--CSS--%>
    <jsp:include page="/WEB-INF/views/frontend/layout/css.jsp"></jsp:include>
   
</head>

<body>
    <div class="wrapper">

    	<%-- Header --%>
		<jsp:include page="/WEB-INF/views/frontend/layout/header.jsp"></jsp:include>

        <main class="main">
            <!-- Sale -->
            <div class="main__products-sale">
                <div class="container">
                    <div class="row">
                        <div class="col-12 col-lg-6">
                            <a href="./danhmuc.html" class="banner-sale">
                                <img src="./img/oder-1.jpg" alt="">
                            </a>
                        </div>
                        <div class="col-12 col-lg-6 block none">
                            <a href="./danhmuc.html" class="banner-sale">
                                <img src="./img/oder-2.jpg" alt="">
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            </div>	
			<!-- Tìm kiếm---------------------------------------------------------------------------------------- -->
			<div class="row">	
				<form action="${classpath }/index" method="get" class="row">	
					<div class="col-md-2"></div>
					<div class="col-md-3">
						<input type="text" class="form-control" id="keyWord"
								name="keyWord" placeholder="Search keyword" value="${keyWord }" />		
					</div>
					
					<div class="col-md-2">
						<input class="form-control" type="number" 
							id="beginPrice" name="beginPrice" placeholder="begin price" value="${beginPrice }"/>		
					</div>
					<div class="col-md-2">
						<input class="form-control" type="number" 
							id="endPrice" name="endPrice" placeholder="end price" value="${endPrice }"/>		
					</div>
					
					<div class="col-md-1">
						<button type="submit" id="btnSearch" name="btnSearch" class="btn btn-primary">Search</button>
					</div>
				</form>	
						
			</div>
			<br> <br> <br>
			<!-- Hết tìm kiếm -------------------------------------------------------------------------------------->
											
            <!-- Danh mục sản phẩm -->
            <div class="main__products">
                <div class="container">
                    <div class="main__products-title">
                        <p style="text-align: center;">Danh sách sản phẩm</p>
                    </div>
                    <div class="main__products-content">
                        <div class="row">
                        <c:forEach items="${products }" var="product">
                            <div class="col-12 col-lg-3 col-md-6">
                                <div class="product">
                                    <div class="thumb">
                                        <a href="${classpath }/product-detail/${product.id}" class="image">
                                            <img src="${classpath }/UploadFiles/${product.avatar}" class="fit-img zoom-img">
                                        </a>
                                        <span class="badges">
                                            <!-- <span class="sale">-20%</span> -->
                                            <span class="new">new</span>

                                        </span>
                                    </div>
                                    <div class="content">
                                        <a href="${classpath }/product-detail/${product.id}" class="content-link">
                                            <h5 class="title">${product.name }</h5>
                                        </a>
                                        <span class="price">
                                            <!-- <span class="old">20.000.000đ</span> -->
                                            <span class="new">
                                            	<fmt:formatNumber value="${product.price }" minFractionDigits="0"></fmt:formatNumber>
                                            	<sup>VNĐ</sup>
                                            </span>
                                        </span>
                                        <span class="symbol">
                                            <a href="#"><i class='bx bx-heart'></i></a>
											<!--nút thêm giỏ hàng -->
                                            <button type="button" onclick="addToCart(${product.id}, 1, '${product.name}')"><i class='bx bx-cart'></i></button>
                                        </span>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>
                        </div>
                    </div>
<!--                     <div class="main__products-more"> -->
<!--                         <a href="./danhmuc.html">Xem thêm</a> -->
<!--                     </div> -->
                </div>
        <%-- Phan trang ---------------------------------------------------------------------%> 
	            <div class="col-md-11 " style="text-align: right;">
				    <ul class="pagination justify-content-end">
				        <c:if test="${currentPage > 1}">
				            <li class="page-item">
				                <a class="page-link" href="?page=${currentPage - 1}&keyWord=${keyWord}&beginPrice=${beginPrice }&endPrice=${endPrice}" tabindex="-1">Previous</a>
				            </li>
				        </c:if>
				        <c:forEach begin="1" end="${totalPages}" var="pageNumber">
				            <c:choose>
				                <c:when test="${pageNumber == currentPage}">
				                    <li class="page-item active"><a class="page-link" href="?page=${pageNumber}&keyWord=${keyWord}
				                    					&beginPrice=${beginPrice }&endPrice=${endPrice}">${pageNumber}</a></li>
				                </c:when>
				                <c:otherwise>
				                    <li class="page-item"><a class="page-link" href="?page=${pageNumber}&keyWord=${keyWord}
				                    					&beginPrice=${beginPrice }&endPrice=${endPrice}">${pageNumber}</a></li>
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
				        <c:if test="${currentPage < totalPages}">
				            <li class="page-item">
				                <a class="page-link" href="?page=${currentPage + 1}">Next</a>
				            </li>
				        </c:if>
				    </ul>
				</div>
                
            </div>
<!--             ------------------------------------------------------------------------------------------------------------ -->
            
            
            <!-- main bottom -->
            <div class="main__bottom">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-4 none block">
                            <div class="main__bottom-wrap">
                                <div class="wrap__icon"><i class='bx bxs-truck'></i></div>
                                <div class="wrap__content">
                                    <h4 class="title">FREE SHIPPING</h4>
                                    <p>Free shipping on all order</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 none block">
                            <div class="main__bottom-wrap">
                                <div class="wrap__icon"><i class='bx bx-headphone'></i></div>
                                <div class="wrap__content">
                                    <h4 class="title">ONLINE SUPPORT</h4>
                                    <p>Online live support 24/7</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 none block">
                            <div class="main__bottom-wrap">
                                <div class="wrap__icon"><i class='bx bx-bar-chart-alt'></i></div>
                                <div class="wrap__content">
                                    <h4 class="title">MONEY RETURN</h4>
                                    <p>Back guarantee under 5 days</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        
        
        
        <%-- Footer --%>
        <jsp:include page="/WEB-INF/views/frontend/layout/footer.jsp"></jsp:include>
        
        <div class="scroll__top">
            <i class='bx bx-up-arrow-alt'></i>
        </div>
        

        <!-- mobile menu -->
        <div class="mobile__menu-wrapper">
            <div class="mobile__menu-body">
                <div class="mobile__btn-close">
                    <i class='bx bx-x'></i>
                </div>
                <div class="mobile__content">
                    <div class="mobile__search-box">
<!--                         <form > -->
<!--                             <input type="text" placeholder="Tìm kiếm..." class="mobile__search-input" name="searchKey"> -->
<!--                             <button type="submit" class="mobile__search-btn"> -->
<!--                                 <i class='bx bx-search' ></i> -->
<!--                             </button> -->
<!--                         </form> -->
                    </div>
                    <div class="mobile__navigation">
                        <ul class="mobile__nav">
                            <li class="mobile__children"><a href="./index.html" class="mobile__link">Trang chủ</li>
                            <li class="mobile__children">
                                <a href="#" class="mobile__link">Thú cưng
                                    <i class='bx bx-chevron-down'></i>
                                </a>
                                <ul class="mobile__submenu">
                                    <li class="mobile__submenu-item"><a href="./danhmuc.html"
                                            class="mobile__submenu-link">Chó
                                            Corgi</a></li>
                                    <li class="mobile__submenu-item"><a href="./danhmuc.html"
                                            class="mobile__submenu-link">Chó
                                            Beagle</a></li>
                                    <li class="mobile__submenu-item"><a href="./danhmuc.html"
                                            class="mobile__submenu-link">Chó Alaska
                                            Malamute</a></li>
                                    <li class="mobile__submenu-item"><a href="./danhmuc.html"
                                            class="mobile__submenu-link">Chó Golden
                                            Retriever</a></li>
                                    <li class="mobile__submenu-item"><a href="./danhmuc.html"
                                            class="mobile__submenu-link"> Chó Husky
                                            Siberian</a></li>
                                </ul>
                            </li>
                            <li class="mobile__children"><a href="#" class="mobile__link">Phụ kiện</a></li>
                            <li class="mobile__children">
                                <a href="#" class="mobile__link">Dịch vụ <i class='bx bx-chevron-down'></i></a>
                                <ul class="mobile__submenu">
                                    <li class="mobile__submenu-item"><a href="#" class="mobile__submenu-link">Spa</a>
                                    </li>
                                    <li class="mobile__submenu-item"><a href="#" class="mobile__submenu-link">Chăm sóc
                                            thú cưng</a></li>
                                </ul>
                            </li>
                            <li class="mobile__children"><a href="./lienhe.html" class="mobile__link">Liên hệ </a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%-- js --%>
    <jsp:include page="/WEB-INF/views/frontend/layout/js.jsp"></jsp:include>
    
    <!-- Add to cart -->
	<script type="text/javascript">
		addToCart = function(_productId, _quantity, _productName) {		
			alert("Thêm "  + _quantity + " sản phẩm '" + _productName + "' vào giỏ hàng ");
			let data = {
				id: _productId, //lay theo id
				quantity: _quantity,
				name: _productName,
			};
				
			//$ === jQuery
			jQuery.ajax({
				url : "/add-to-cart",
				type : "POST",
				contentType: "application/json",
				data : JSON.stringify(data),
				dataType : "json", //Kieu du lieu tra ve tu controller la json
				
				success : function(jsonResult) {
					alert(jsonResult.code + ": " + jsonResult.message);
					let totalProducts = jsonResult.totalCartProducts;
					$("#totalCartProducts").html(totalProducts);
				},
				
				error : function(jqXhr, textStatus, errorMessage) {
					alert(jsonResult.code + ': Đã có lỗi xay ra...!')
				},
			});
		}
	</script>
</body>

</html>