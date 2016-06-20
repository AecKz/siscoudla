/**
 * Controlador de Datos Estudiante
 * Autor: Pablo Jácome A
 * Fecha: 19-06-2016
 */
//signOut Google
function signOut() {
   var auth2 = gapi.auth2.getAuthInstance();
   auth2.signOut().then(function () {
     console.log('User signed out.');
   });
 }

//jquery ready
$(document).ready(function() {
					//Ocultar boton					
					$('#btnGoogle').hide();
					$("#btnCargarHorarios").hide();
					// Datos Iniciales
					// Cargar Datos del Menu
					$.ajax({
						url : '../DashboardController',
						data : {
							"tipoConsulta" : "cargarDatosMenus"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							var nombreCompleto = data.nombreCompleto;
							$('#txtUsuarioMenu').text(nombreCompleto);							
							$('#txtUsuarioContent').text(nombreCompleto);
							$('#txtUsuarioCabecera').text(nombreCompleto);							
								}
							});
					//Cargar Horarios de los Estudiantes
					$.ajax({
								url : '../RegistroTurnosController',
								data : {
									"tipoConsulta" : "cargarHorarios"
								},
								type : 'POST',
								datatype : 'json',
								success : function(data) {
									if (data.numRegistros > 0) {
										var listadoHorarios = data.listadoHorarios;
										$.each(listadoHorarios, function(index) {
											$("#dataTableContent").append("	<tr>"
												+ " <td relation='dia'>"
												+ listadoHorarios[index].dia
												+ "</td>"
												+ " <td relation='horaInicio'>"
												+ listadoHorarios[index].horaInicio
												+ "</td>"
												+ " <td relation='horaFin'>"
												+ listadoHorarios[index].horaFin
												+ "</td>"
												+ "</tr>");
														});
									} else {
										$("#dataTableContent")
												.append(
														"<tr><td colspan='4'>No existen horarios cargados</td></tr>");
										$("#btnCargarHorarios").show();
									}
								}
							});
					// Cargar Datos Profile
					$.ajax({
						url : '../DatosEstudianteController',
						data : {
							"tipoConsulta" : "cargarDatosProfile"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							var nombres = data.nombres;
							var apellidos = data.apellidos;
							var email = data.email;
							var matricula = data.matricula;
							var clinica = data.clinica;
							var fechaNacimiento = data.fechaNacimiento;
							var genero = data.genero;
							var telefono = data.telefono;
							
							$('#txtNombres').val(nombres);
							$('#txtApellidos').val(apellidos);
							$('#txtEmail').val(email);
							$('#txtMatricula').val(matricula);
							$('#txtClinica').val(clinica);
							$('#txtTelefono').val(telefono);
							if(fechaNacimiento != null){								
								$('#fechaNacimiento').datepicker('setDate', fechaNacimiento);
							}
							if(genero != null){
								$("input[name=genero][value=" + genero + "]").attr('checked', 'checked');																
							}							
						}
						});
					//Guardar Datos
					$('#btnGuardar').click(function(){
						var nombres = $('#txtNombres').val();
						var apellidos = $('#txtApellidos').val();
						var email = $('#txtEmail').val();
						var matricula = $('#txtMatricula').val();
						var clinica = $('#txtClinica').val();
						var telefono = $('#txtTelefono').val();
						var fechaNacimiento =  $('#fechaNacimiento').datepicker('getDate');
						var genero = $('#genero input:radio:checked').val();
						$.ajax({
							url : '../DatosEstudianteController',
							data : {
								"tipoConsulta" : "guardarDatosProfile",
								"nombres": nombres,
								"apellidos":apellidos,
								"email":email,
								"matricula":matricula,
								"clinica":clinica,
								"telefono":telefono,
								"fechaNacimiento":fechaNacimiento,
								"genero":genero
							},
							type : 'POST',
							datatype : 'json',
							success : function(data) {
								alert("Datos Guardados Correctamente");								
							}
						});
						 
					});
					
					$('#fechaNacimiento').datepicker({
					    format: "dd/mm/yyyy",
					    language: "es"
					});
					
});//Fin jquery ready