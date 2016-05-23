/**
 * Controlador del Dashboard del Estudiante
 * Autor: Pablo Jácome A
 * Fecha: 22-05-2016
 */
//jquery ready
$(document).ready(function() {	
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
					// Cargar Datos Profile
					$.ajax({
						url : '../DashboardController',
						data : {
							"tipoConsulta" : "cargarDatosProfile"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							var usuario = data.usuario;
							var matricula = data.matricula;
							var clinica = data.clinica;
							$('#txtUsuario').text(" "+ usuario);							
							$('#txtMatricula').text(" "+ matricula);
							$('#txtClinica').text(" "+ clinica);							
								}
							});
					// Cargar Datos de Turnos Reservados
					$.ajax({
						url : '../DashboardController',
						data : {
							"tipoConsulta" : "cargarDatosReservados"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							if(data.numRegistros > 0){
								var listadoReservados = data.listadoReservados;
								$.each(listadoReservados, function(index){
									$("#contentReservados").append("	<tr class='odd gradeX'>" +
											" <td relation='fecha'>"+ listadoReservados[index].fecha +"</td>" +
											" <td relation='horario'>"+ listadoReservados[index].horario +"</td>" +
											" <td relation='tratamiento'>"+ listadoReservados[index].tratamiento +"</td>" +
											" <td relation='paciente'>"+ listadoReservados[index].paciente +"</td>" +
											" <td relation='cubiculo'>"+ listadoReservados[index].cubiculo +"</td>" +
										"</tr>");						
								});
							}else{
								$("#contentReservados").append("<tr><td colspan='4'>No existen Registros</td></tr>");
							}						
								}
							});
					// Cargar Datos de Turnos Ocupados
					$.ajax({
						url : '../DashboardController',
						data : {
							"tipoConsulta" : "cargarDatosOcupados"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							if(data.numRegistros > 0){
								var listadoOcupados = data.listadoOcupados;
								$.each(listadoOcupados, function(index){
									$("#contentOcupados").append("	<tr class='odd gradeX'>" +
											" <td relation='fecha'>"+ listadoOcupados[index].fecha +"</td>" +
											" <td relation='horario'>"+ listadoOcupados[index].horario +"</td>" +
											" <td relation='tratamiento'>"+ listadoOcupados[index].tratamiento +"</td>" +
											" <td relation='paciente'>"+ listadoOcupados[index].paciente +"</td>" +
											" <td relation='cubiculo'>"+ listadoOcupados[index].cubiculo +"</td>" +
										"</tr>");						
								});
							}else{
								$("#contentOcupados").append("<tr><td colspan='4'>No existen Registros</td></tr>");
							}						
								}
							});
					// Cargar Datos de Turnos Cancelados
					$.ajax({
						url : '../DashboardController',
						data : {
							"tipoConsulta" : "cargarDatosCancelados"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							if(data.numRegistros > 0){
								var listadoCancelados = data.listadoCancelados;
								$.each(listadoCancelados, function(index){
									$("#contentCancelados").append("	<tr class='odd gradeX'>" +
											" <td relation='fecha'>"+ listadoCancelados[index].fecha +"</td>" +
											" <td relation='horario'>"+ listadoCancelados[index].horario +"</td>" +
											" <td relation='tratamiento'>"+ listadoCancelados[index].tratamiento +"</td>" +
											" <td relation='paciente'>"+ listadoCancelados[index].paciente +"</td>" +
											" <td relation='cubiculo'>"+ listadoCancelados[index].cubiculo +"</td>" +
										"</tr>");						
								});
							}else{
								$("#contentCancelados").append("<tr><td colspan='4'>No existen Registros</td></tr>");
							}						
								}
							});
});//Fin jquery ready