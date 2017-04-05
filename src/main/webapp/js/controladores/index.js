/**
 * Controlador del index del sistema
 * Autor: Pablo Jácome A
 * Fecha: 22-05-2016
 */

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