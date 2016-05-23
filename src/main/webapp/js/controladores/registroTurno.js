/**
 * Controlador del registro de turnos
 * Autor: Pablo Jácome A
 * Fecha: 22-05-2016
 */
function reservarTurno(){
		//Ocultamos el boton
		$('#btnAceptar').hide();
		//Enviamos datos del paciente
		var historiaClinica = $('txtHistoriaClinica').val();
		var nombresPaciente = $('#txtNombresPaciente').val();
		var apellidosPaciente = $('#txtApellidosPaciente').val();
		var emailPaciente = $('#txtEmailPaciente').val();
		var generoPaciente = $('#genero input:radio:checked').val();
		var fechaNacimientoPaciente =  $('#fechaNacimientoPaciente').datepicker('getDate');
		var fechaSeleccionada = $('#fechaSeleccionada').datepicker('getDate');		
		var cubiculoAsignadoTurno = $('#lblCubiculoAsignado').text();
		var tratamientoSeleccionado = $('#lblnTratamiento').text();
		$.ajax({
			url : '../RegistroTurnosController',
			data : {
				"tipoConsulta" : "reservarTurno",
				"historiaClinica":historiaClinica,
				"nombresPaciente":nombresPaciente,
				"apellidosPaciente":apellidosPaciente,
				"emailPaciente":emailPaciente,
				"generoPaciente":generoPaciente,
				"fechaNacimientoPaciente":fechaNacimientoPaciente,
				"fechaSeleccionada":fechaSeleccionada,
				"fechaNacimientoPaciente":fechaNacimientoPaciente,
				"tratamientoSeleccionado":tratamientoSeleccionado,
				"cubiculoAsignadoTurno" : cubiculoAsignadoTurno
			},
			type : 'POST',
			datatype : 'json',
			success : function(data) {
				var resultado = data.resultado;
				switch (resultado)
				{
				   case "ok":
					   alert('Turno Reservado!');
					   break;
				   case "error":
					   alert('Hubo un error');
					   break;
				   case "cubiculo ocupado": 
				       alert('El cubículo asignado ha sido ocupado, por favor seleccione otro Tratamiento');
				       break;

				   default: 
				       alert('Hubo un error');
				}
								
			}
		});
	}
	function consultarCubiculos(idRadio){
			var fechaSeleccionada = $('#fechaSeleccionada').datepicker('getDate');
			var nombre = $('#txtNombresPaciente').val();
			var apellido = $('#txtApellidosPaciente').val();
			$('#lblnNombrePaciente').text(nombre +" " + apellido);
			$.ajax({
				url : '../RegistroTurnosController',
				data : {
					"tipoConsulta" : "consultarCubiculos",
					"fechaSeleccionada":fechaSeleccionada,
					"idRadio" : idRadio
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					var cubiculo = data.cubiculoAsignado;
					var nTratamiento = data.nombreTratamiento;
					var nEspecialidad = data.nombreEspecialidad;
					var nHoraInicio = data.horaInicio;
					var nHoraFinal = data.horaFinal;
					var nFechaTurno = data.fechaTurno;
					$('#lblnTratamiento').text(nTratamiento);
					$('#lblnEspecialidad').text(nEspecialidad);
					$('#lblnFechaTurno').text(nFechaTurno);
					$('#lblnHorario').text(nHoraInicio + " - " + nHoraFinal);					
					if(cubiculo>0){						
						$('#lblCubiculoAsignado').text(cubiculo);
						$('#btnAceptar').show();
					}else{
						$('#lblCubiculoAsignado').text('No existen cubículos disponibles');
						$('#btnAceptar').hide();
					}
				}
			});
		}
		
		$(document).ready(function() {
					//Datos Iniciales
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
											}
										}
									});
							//Cargar Tratamientos y especialidades
							$.ajax({
										url : '../RegistroTurnosController',
										data : {
											"tipoConsulta" : "cargarTratamientos"
										},
										type : 'POST',
										datatype : 'json',
										success : function(data) {
											if (data.numRegistros > 0) {
												var listadoEspecialidades = data.listadoEspecialidades;												
													$.each(listadoEspecialidades, function(index) {
														var contadorTratamientos = listadoEspecialidades[index].contadorTratamientos;
														var tratamientos = "";
														for(var i = 0;i<contadorTratamientos;i++){
															var contNombreTratamiento = "nombreTratamiento" + i;
															var contIdTratamiento = "idTratamiento" + i;
															var tratamiento = listadoEspecialidades[index][contNombreTratamiento];
															var idTratamiento =  listadoEspecialidades[index][contIdTratamiento];
															tratamientos = tratamientos + " <input type='radio' name='tratamientos' id='tratamiento"+idTratamiento
																						+"' onclick='consultarCubiculos(id)'> " + tratamiento +"<br>";
														}
														$("#panelContent").append("	<a class='panel-heading' role='tab' id='heading"+[index]
															+ "' data-toggle='collapse' data-parent='#accordion' href='#collapse"+[index]
															+ "' aria-expanded='false' aria-controls='collapse"+[index]+"'>"
															+ " <h4 class='panel-title'> - " +listadoEspecialidades[index].nombreEspecialidad+"</h4>"
															+ " </a>"
															+ " <div id='collapse"+[index]+"' class='panel-collapse collapse in' role='tabpanel' aria-labelledby='heading"+[index]+"'>"
															+ " <div class='panel-body'>"
															+ " <div class='radio'>"
															+ " <label>"															 
															+ tratamientos 
															+ " </label>"
															+ " </div>"
															+ " </div>"
															+ " </div>");
															});

											} else {
												$("#panelContent").append("<p>No existen datos</p>");
											}
										}
									});

							// Smart Wizard
							$('#wizard').smartWizard();
							//Date picker 
							$('#fechaSeleccionada').datepicker({
							    format: "dd-mm-yyyy",							    
							    language: "es",
							    daysOfWeekDisabled: "0,6",
							    todayHighlight: true
							});
							$('#fechaNacimientoPaciente').datepicker({
							    format: "dd-MM-yyyy",
							    language: "es",
							    autoclose: true
							});
							
						});//Fin jquery ready