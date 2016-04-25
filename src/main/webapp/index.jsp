<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <!-- Meta, title, CSS, favicons, etc. -->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>SISCOUDLA</title>

  <!-- Bootstrap core CSS -->

  <link href="css/bootstrap.min.css" rel="stylesheet">

  <link href="fonts/css/font-awesome.min.css" rel="stylesheet">
  <link href="css/animate.min.css" rel="stylesheet">
  <!-- Para Login con Google -->
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <meta name="google-signin-client_id" content="581128372441-0fhgqacdt6tc43pbrvkn2erm3hb0c7ac.apps.googleusercontent.com">

  <!-- Custom styling plus plugins -->
  <link href="css/custom.css" rel="stylesheet">
  <link href="css/icheck/flat/green.css" rel="stylesheet">
  <script src="js/jquery.min.js"></script>

		<script>
		var nombres= "";
		var apellidos = "";
		var email = "";
		var contrasena = "";
		var usuarioLogin = "";
		var contrasenaLogin = "";
		var tipoConsulta = "";
		//Login Google
		function onSignIn(googleUser) {
			  var profile = googleUser.getBasicProfile();
			 	tipoConsulta = "loginGoogle";
				nombres=profile.getGivenName()
				apellidos=profile.getFamilyName();
				email=profile.getEmail();
				contrasena=profile.getId();
				$.ajax({
					url : '../IndexController',
					data : {
						"nombres" : nombres,
						"apellidos" : apellidos,
						"email" : email,
						"contrasena" : contrasena,																				
						"tipoConsulta" : tipoConsulta
					},
					type : 'POST',
					datatype : 'json', 
					success : function (data) {
						window.location = "dashboard.jsp";
					}
				});
			}//Fin LoginGoogle
 		$(document).ready(function() {

				//Crear Cuenta Manual
				$("#btnCrearCuenta").click(function() {
					tipoConsulta = "crear";
					nombres=$("#txtNombres").val().trim();
					apellidos=$("#txtApellidos").val().trim();
					email=$("#txtEmail").val().trim();
					contrasena=$("#txtContrasena").val().trim();
					$.ajax({
						url : '../IndexController',
						data : {
							"nombres" : nombres,
							"apellidos" : apellidos,
							"email" : email,
							"contrasena" : contrasena,																				
							"tipoConsulta" : tipoConsulta
						},
						type : 'POST',
						datatype : 'json', 
						success : function (data) {
							alert("Cuenta creada satisfactoriamente");							
							window.location = "dashboard.jsp";
						}
					});
				});//Fin Crear Cuenta Manual
				//Login Manual
				$("#btnLogin").click(function() {
					tipoConsulta = "login";
					usuarioLogin=$("#txtUsuarioLogin").val().trim();
					contrasenaLogin=$("#txtContrasenaLogin").val().trim();
					$.ajax({
						url : '../IndexController',
						data : {
							"usuarioLogin" : usuarioLogin,
							"contrasenaLogin" : contrasenaLogin,																											
							"tipoConsulta" : tipoConsulta
						},
						type : 'POST',
						datatype : 'json', 
						success : function (data) {							
							if(data.success){
								window.location = "dashboard.jsp";
							}else{
								alert("Usuario o Contrase&ntilde;a Incorrecta")
							}															
						}
					});
				});//Fin Login Manual
				
 		});
		</script>


</head>

<body style="background:#F7F7F7;">

  <div class="">
    <a class="hiddenanchor" id="toregister"></a>
    <a class="hiddenanchor" id="tologin"></a>

    <div id="wrapper">
      <div id="login" class="animate form">
        <section class="login_content">
          <form>          
            <h1>Login</h1>
            <div class="g-signin2" data-onsuccess="onSignIn"></div>
            <br>            
            <div>
              <input type="text" class="form-control" id="txtUsuarioLogin" placeholder="Usuario" required/>
            </div>
            <div>
              <input type="password" class="form-control" id="txtContrasenaLogin" placeholder="Contrase&ntilde;a" required/>
            </div>
            <div>
		       <button type="button" class="btn btn-primary" id="btnLogin">Enviar</button>
               <a class="reset_pass" href="#">Recuperar Contrase&ntilde;a</a>
            </div>            
            <div class="clearfix"></div>
            <div class="separator">

              <p class="change_link"><a href="#toregister" class="to_register"> Crear cuenta</a>
              </p>
              <div class="clearfix"></div>
              <br />
              <div>
                <h1><i class="fa fa-institution" style="font-size: 26px;"></i> SISCOUDLA</h1>

                <p> ©2016 Todos los derechos reservados</p>
              </div>
            </div>
          </form>
          <!-- form -->
        </section>
        <!-- content -->
      </div>
      <div id="register" class="animate form">
        <section class="login_content">
          <form>
            <h1>Crear cuenta</h1>             
            <div>
              <input type="text" class="form-control" id="txtNombres" placeholder="Nombres" required/>
            </div>
            <div>
              <input type="text" class="form-control" id="txtApellidos" placeholder="Apellidos" required/>
            </div>
            <div>
              <input type="email" class="form-control" id="txtEmail" placeholder="Email" required/>
            </div>
			<div>
              <input type="password" class="form-control" id="txtContrasena" placeholder="Contrase&ntilde;a" required/>
            </div>
            <div>
              <button type="button" class="btn btn-primary" id="btnCrearCuenta">Enviar</button>  
            </div>
            <div class="clearfix"></div>
            <div class="separator">

              <p class="change_link">Ya est&aacute; registrado?
                <a href="#tologin" class="to_register"> Log in </a>
              </p>
              <div class="clearfix"></div>
              <br />
              <div>
                <h1><i class="fa fa-institution" style="font-size: 26px;"></i> SISCOUDLA</h1>

                <p> ©2016 Todos los derechos reservados</p>
              </div>
            </div>
          </form>
          <!-- form -->
        </section>
        <!-- content -->
      </div>
    </div>
  </div>

</body>
</html>