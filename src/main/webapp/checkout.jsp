<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tienda Virtual</title>
<link href="static/css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="static/js/jquery.min.js"></script>
<!-- Custom Theme files -->
<link href="static/css/style.css" rel='stylesheet' type='text/css' />
<link href="static/css/component.css" rel='stylesheet' type='text/css' />
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--webfont-->
<link href='http://fonts.googleapis.com/css?family=Roboto:100,200,300,400,500,600,700,800,900' rel='stylesheet' type='text/css'>
<script src="static/js/jquery.easydropdown.js"></script>
<!-- Add fancyBox main JS and CSS files -->
<script src="static/js/jquery.magnific-popup.js" type="text/javascript"></script>
<link href="static/css/magnific-popup.css" rel="stylesheet" type="text/css">
		<script>
		var id = "";
		var pedidoId = "";
		var formaPago = "";
		var totalPedido = "";
		var totalImpuestos = "";
		var totalEnvio = "";
		var totalVenta = "";
		var estado = "";		
		var tipoConsulta = "";
			$(document).ready(function() {
				$('.popup-with-zoom-anim').magnificPopup({
					type: 'inline',
					fixedContentPos: false,
					fixedBgPos: true,
					overflowY: 'auto',
					closeBtnInside: true,
					preloader: false,
					midClick: true,
					removalDelay: 300,
					mainClass: 'my-mfp-zoom-in'
			});
				
				$.ajax({
					url : '../TiendaVirtual/PedidoController',
					data : {
						"usuarioId":"1",
						"tipoConsulta" : "encontrarActivosUsuario"
					},
					type : 'POST',
					datatype : 'json',
					success : function(data) {
						if(data.numRegistros > 0){
							var listadoPedidos = data.listadoPedidos;
							$.each(listadoPedidos, function(index){
								$("#dataTableContent").append("	<tr class='odd gradeX'>" +
										" <td relation='producto'>"+ listadoPedidos[index].producto +"</td>" +
										" <td relation='precioPublico'>"+ listadoPedidos[index].precioPublico +"</td>" +
										" <td relation='fecha'>"+ listadoPedidos[index].fecha +"</td>" +
										" <td width='175px'>" +
											" <input type='hidden' value='"+ listadoPedidos[index].id +"'/>" +
											" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
			  									" Actualizar" +
											" </button>" +
											" <button type='button' class='btn btn-danger btn-xs eliminar-btn'>" +
											  	"<span id='delete-record'></span> Eliminar" +
											" </button>" +
										"</td>" +
									"</tr>");						
							});
							/* Inicio Controles Actualizar Registro*/
							$(".actualizar-btn").bind({click: function() {
									$("#addButton").trigger("click");
									$("#codigo").val($(this).parent().children().first().val());
									var elem = $(this).parent();
									var bandera = 1;
									do {
										elem = elem.prev();
										if (elem.is("td")){
											var elemCode = elem.attr("relation");
											elementType(elem.text(), elemCode, $("#"+elemCode).attr("type"));
										}else {
											bandera = 0;
										}
									} while (bandera == 1);
								  }
							});
							/* Fin Controles Actualizar Registro*/
							
							/* Inicio Controles Elminar Registro */
							$(".eliminar-btn").bind({click: function() {
									var r = confirm("Seguro que desea eliminar el articulo? " + $(this).parent().parent().children().first().text());
									if (r == true){
										codigo = $(this).parent().children().first().val();
										tipoConsulta = "eliminar";
										$(this).parent().parent().remove();
									}
								}
							});	
							/* Fin Controles Elminar Registro */
						}else{
							$("#dataTableContent").append("<tr><td colspan='4'>No existen Registros</td></tr>");
						}
					}
				});
				
				$("#pagar").click(function() {
					tipoConsulta = "crear";
					$.ajax({
						url : '../TiendaVirtual/VentaController',
						data : {
							"pedidoId" : 1,
							"formaPago" : "EFECTIVO",
							"totalPedido" : 15,
							"totalImpuestos" : 2,
							"totalEnvio" : 0,
							"totalVenta" : 17,
							"estado" : "1",							
							"tipoConsulta" : tipoConsulta
						},
						type : 'POST',
						datatype : 'json', 
						success : function (data) {
							alert("Vendido con Exito!");
							alert("La factura será enviada a su email.");
							window.location = "index.jsp";
						}
					});
				});
		});
		</script>
<!----details-product-slider--->
</head>
<body>
   <div class="single">
	<div class="container">
		<div class="header-top">
      		 <div class="logo">
				<a href="index.jsp"><img src="static/images/logo.png" alt=""/></a>
			 </div>
		   <div class="header_right">
			 <ul class="social">
				<li><a href=""> <i class="fb"> </i> </a></li>
				<li><a href=""><i class="tw"> </i> </a></li>
				<li><a href=""><i class="utube"> </i> </a></li>
				<li><a href=""><i class="pin"> </i> </a></li>
				<li><a href=""><i class="instagram"> </i> </a></li>
			 </ul>
		    <div class="lang_list">
			  <select tabindex="4" class="dropdown">
				<option value="" class="label" value="">En</option>
				<option value="1">English</option>
				<option value="2">French</option>
				<option value="3">German</option>
			  </select>
   			</div>
			<div class="clearfix"></div>
          </div>
          <div class="clearfix"></div>
		 </div>  
		 <div class="apparel_box">
			<ul class="login">
				<li class="login_text"><a href="login.jsp">Login</a></li>
				<li class="wish"><a href="checkout.jsp">Wish List</a></li>
				<div class='clearfix'></div>
		    </ul>
		    <div class="cart_bg">
			  <ul class="cart">
				<i class="cart_icon"></i><p class="cart_desc">$1459.50<br><span class="yellow">2 items</span></p>
			    <div class='clearfix'></div>
			  </ul>
			  <ul class="product_control_buttons">
				 <li><a href="#"><img src="static/images/close.png" alt=""/></a></li>
				 <li><a href="#">Edit</a></li>
			  </ul>
		      <div class='clearfix'></div>
			 </div>
			 <ul class="quick_access">
				<li class="view_cart"><a href="checkout.jsp">View Cart</a></li>
				<li class="check"><a href="checkout.jsp">Checkout</a></li>
				<div class='clearfix'></div>
		     </ul>
			<div class="search">
			   <input type="text" value="Search" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Search';}">
			   <input type="submit" value="">
			</div>
		  </div>
		</div>
    </div>
    <div class="main">
	   <div class="container">
		   <div class="register">
		   
		   	<!-- Datatable -->
	<div class="row">
		<div class="col-lg-12">
			<div class="table-responsive">
				<div class="input-group"> <span class="input-group-addon">Filter</span>
				    <input id="filter" type="text" class="form-control" placeholder="Escriba la palabra a buscar...">
				</div>			
				<table class="table table-striped table-bordered table-hover"
					id="dataTable">
					<thead>
						<tr>
							<th>Producto</th>
							<th>Precio</th>
							<th>Fecha</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="dataTableContent" class="searchable">										
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Datatable -->			
	<button type="button" class="btn btn-primary" id="pagar">PAGAR</button>   
		   
<!-- 		  	  <h4 class="title">Shopping cart is empty</h4> -->
<!-- 		  	  <p class="cart">You have no items in your shopping cart.<br>Click<a href="index.jsp"> here</a> to continue shopping</p> -->
		   </div>
	     </div>
	    </div>
		<div class="container">
		  <div class="brands">
			 <ul class="brand_icons">
				<li><img src='static/images/icon1.jpg' class="img-responsive" alt=""/></li>
				<li><img src='static/images/icon2.jpg' class="img-responsive" alt=""/></li>
				<li><img src='static/images/icon3.jpg' class="img-responsive" alt=""/></li>
				<li><img src='static/images/icon4.jpg' class="img-responsive" alt=""/></li>
				<li><img src='static/images/icon5.jpg' class="img-responsive" alt=""/></li>
				<li><img src='static/images/icon6.jpg' class="img-responsive" alt=""/></li>
				<li class="last"><img src='static/images/icon7.jpg' class="img-responsive" alt=""/></li>
			 </ul>
		   </div>
	    </div>
	    <div class="container">
	      <div class="instagram_top">
	      	<div class="instagram text-center">
				<h3><i class="insta_icon"> </i> Instagram feed:&nbsp;<span class="small">#Surfhouse</span></h3>
			</div>
	        <ul class="instagram_grid">
			  <li><a class="popup-with-zoom-anim" href="#small-dialog1"><img src="static/images/i1.jpg" class="img-responsive"alt=""/></a></li>
			  <li><a class="popup-with-zoom-anim" href="#small-dialog1"><img src="static/images/i2.jpg" class="img-responsive" alt=""/></a></li>
			  <li><a class="popup-with-zoom-anim" href="#small-dialog1"><img src="static/images/i3.jpg" class="img-responsive" alt=""/></a></li>
			  <li><a class="popup-with-zoom-anim" href="#small-dialog1"><img src="static/images/i4.jpg" class="img-responsive" alt=""/></a></li>
			  <li><a class="popup-with-zoom-anim" href="#small-dialog1"><img src="static/images/i5.jpg" class="img-responsive" alt=""/></a></li>
			  <li class="last_instagram"><a class="popup-with-zoom-anim" href="#small-dialog1"><img src="static/images/i6.jpg" class="img-responsive" alt=""/></a></li>
			  <div class="clearfix"></div>
			  <div id="small-dialog1" class="mfp-hide">
				<div class="pop_up">
					<h4>A Sample Photo Stream</h4>
					<img src="static/images/i_zoom.jpg" class="img-responsive" alt=""/>
				</div>
			  </div>
			</ul>
		  </div>
	      <ul class="footer_social">
			<li><a href="#"> <i class="fb"> </i> </a></li>
			<li><a href="#"><i class="tw"> </i> </a></li>
			<li><a href="#"><i class="pin"> </i> </a></li>
			<div class="clearfix"></div>
		   </ul>
	    </div>
        <div class="footer">
			<div class="container">
				<div class="footer-grid">
					<h3>Category</h3>
					<ul class="list1">
					  <li><a href="#">Home</a></li>
					  <li><a href="#">About us</a></li>
					  <li><a href="#">Eshop</a></li>
					  <li><a href="#">Features</a></li>
					  <li><a href="#">New Collections</a></li>
					  <li><a href="#">Blog</a></li>
					  <li><a href="#">Contact</a></li>
				    </ul>
				</div>
				<div class="footer-grid">
					<h3>Our Account</h3>
				    <ul class="list1">
					  <li><a href="#">Your Account</a></li>
					  <li><a href="#">Personal information</a></li>
					  <li><a href="#">Addresses</a></li>
					  <li><a href="#">Discount</a></li>
					  <li><a href="#">Orders history</a></li>
					  <li><a href="#">Addresses</a></li>
					  <li><a href="#">Search Terms</a></li>
				    </ul>
				</div>
				<div class="footer-grid">
					<h3>Our Support</h3>
					<ul class="list1">
					  <li><a href="#">Site Map</a></li>
					  <li><a href="#">Search Terms</a></li>
					  <li><a href="#">Advanced Search</a></li>
					  <li><a href="#">Mobile</a></li>
					  <li><a href="#">Contact Us</a></li>
					  <li><a href="#">Mobile</a></li>
					  <li><a href="#">Addresses</a></li>
				    </ul>
				  </div>
				  <div class="footer-grid">
					<h3>Newsletter</h3>
					<p class="footer_desc">Nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat</p>
					<div class="search_footer">
			          <input type="text" class="text" value="Insert Email" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Insert Email';}">
			          <input type="submit" value="Submit">
			        </div>
			        <img src="static/images/payment.png" class="img-responsive" alt=""/>
				 </div>
				 <div class="footer-grid footer-grid_last">
					<h3>About Us</h3>
					<p class="footer_desc">Diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam,.</p>
                    <p class="f_text">Phone:  &nbsp;&nbsp;&nbsp;00-250-2131</p>
                    <p class="email">Email: &nbsp;&nbsp;&nbsp;<span>info(at)Surfhouse.com</span></p>	
                 </div>
				 <div class="clearfix"> </div>
			</div>
		</div>
        <div class="footer_bottom">
        	<div class="container">
        		<div class="copy">
				   <p>&copy; 2014 Template by <a href="http://w3layouts.com" target="_blank"> w3layouts</a></p>
			    </div>
        	</div>
        </div>
</body>
</html>