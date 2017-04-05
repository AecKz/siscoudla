/**
 * Controlador del Dashboard del Administrador
 * Autor: Pablo Jácome A
 * Fecha: 23-05-2016
 */
function signOut() {
   var auth2 = gapi.auth2.getAuthInstance();
   if(auth2 != null){
	   auth2.signOut();   
   }
   cerrarSesion();
 }
function cerrarSesion(){
	   $.ajax({
			url : '../IndexController',
			data : {
				"tipoConsulta" : "cerrarSesion"
			},
			type : 'POST',
			datatype : 'json',
			success : function(data) {
				alert("Sesion cerrada correctamente");
					}
				});
}
//jquery ready
$(document).ready(function() {	
					$('#btnGoogle').hide();
					$('#btnEditarPerfil').click(function(){
						window.location.href = "datosAdministrador.jsp";
					});
					// Datos Iniciales
					// Cargar Datos del Menu
					$.ajax({
						url : '../DashboardAdministradorController',
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
						url : '../DashboardAdministradorController',
						data : {
							"tipoConsulta" : "cargarDatosProfile"
						},
						type : 'POST',
						datatype : 'json',
						success : function(data) {
							var usuario = data.usuario;
							var telefono = data.telefono;							
							$('#txtUsuario').text(" "+ usuario);							
							$('#txtTelefono').text(" "+ telefono);														
								}
							});
					// Cargar Datos de Turnos Reservados
					$.ajax({
						url : '../DashboardAdministradorController',
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
											" <td relation='estudiante'>"+ listadoReservados[index].estudiante +"</td>" +
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
						url : '../DashboardAdministradorController',
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
											" <td relation='estudiante'>"+ listadoOcupados[index].estudiante +"</td>" +
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
						url : '../DashboardAdministradorController',
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
											" <td relation='estudiante'>"+ listadoCancelados[index].estudiante +"</td>" +
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