<!-- Jquery -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<!-- ScrollReveal -->
<script src="${classpath}/frontend/js/scrollreveal.min.js"></script>
<!-- Slider -->
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>

<script src="${classpath}/frontend/js/app.js"></script>
<script src="${classpath}/frontend/js/mobileMenu.js"></script>

<script>
    document.getElementById("thucung").addEventListener("click", function() {
        var dropdown = document.getElementById("thucungDropdown");
        dropdown.classList.toggle("hidden");
    });
</script>