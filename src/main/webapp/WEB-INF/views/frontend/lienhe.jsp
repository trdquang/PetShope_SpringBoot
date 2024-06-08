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
            <div class="main__breadcrumb">
                <div class="container">
                    <div class="bread-crumb">
						
                    </div>
                </div>
            </div>
            <div class="main__contact">
                <div class="container">
                    <div class="row">
                        <div class="col-12 col-lg-6">
                            <div class="contact__title">
                                <h2 class="title">Liên lạc</h2>
                                <img alt="" src="${classpath }/frontend/img/pet_add.jpg" width="400px">
                            </div>
                            <div class="contact__form">
<!--                                 <form class="form"> -->
<!--                                     <div class="row"> -->
<!--                                         <div class="col-md-6"> -->
<!--                                             <div class="contact__form-input"> -->
<!--                                                 <input type="text" class="form-input" placeholder="Your name..."> -->
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="col-md-6"> -->
<!--                                             <div class="contact__form-input"> -->
<!--                                                 <input type="email" class="form-input" placeholder="Email..."> -->
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="col-12"> -->
<!--                                             <div class="contact__form-input"> -->
<!--                                                 <textarea cols="30" rows="5" class="form__textarea" name="message" -->
<!--                                                     placeholder="Message"></textarea> -->
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                         <div class="col-12"> -->
<!--                                             <div class="contact__btn"> -->
<!--                                                 <a href="#">Send</a> -->
<!--                                             </div> -->
<!--                                         </div> -->
<!--                                     </div> -->
<!--                                 </form> -->
                            </div>
                        </div>
                        <div class="col-12 col-lg-6">
                            <div class="contact__title">
                                <h2 class="title">Liên hệ với chúng tôi</h2>
                            </div>
                            <p class="contact__description">Liên hệ với chúng tôi tại PetShopE là cách tốt nhất để chia sẻ ý kiến, 
                            đặt câu hỏi hoặc nhận được hỗ trợ từ đội ngũ chuyên gia của chúng tôi. Chúng tôi luôn sẵn lòng lắng nghe
                             và phản hồi nhanh chóng để đảm bảo rằng bạn có trải nghiệm mua sắm tốt nhất và nuôi dưỡng thú cưng của bạn 
                             được tốt nhất có thể. Hãy liên hệ với chúng tôi ngay hôm nay để biết thêm thông tin và được hỗ trợ!</p>
                            <ul class="contact__address">
                                <li><i class='bx bx-phone'></i> 03281748222</li>
                                <li><i class='bx bx-envelope'></i> pet@gmail.com</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main__map">
                <div class="row">
                    <div class="col-12">
                        <iframe
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1612.1966650274296!2d105.78817454726801!3d20.980166368165296!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135acce762c2bb9%3A0xbb64e14683ccd786!2zSOG7jWMgVmnhu4duIENOIELGsHUgQ2jDrW5oIFZp4buFbiBUaMO0bmcgLSBIw6AgxJDDtG5n!5e0!3m2!1svi!2s!4v1647935982673!5m2!1svi!2s"
                            width="100%" height="450" style="border:0;" allowfullscreen="" loading="lazy"></iframe>
                    </div>
                </div>
            </div>
        </main>
        
        
        <%-- Footer --%>
        <jsp:include page="/WEB-INF/views/frontend/layout/footer.jsp"></jsp:include>
        
        <div class="scroll__top">
            <i class='bx bx-up-arrow-alt'></i>
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