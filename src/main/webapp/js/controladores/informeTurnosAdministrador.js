/**
 * Controlador de Informe Turnos para el Administrador
 * Autor: Pablo Jácome A
 * Fecha: 24-05-2016
 */
	var codigo = "";
	var clinica = "";
	var estudiante = "";
	var especialidad = "";
	var tratamiento = "";
	var cubiculo = "";
	var tipoConsulta = "";

	$(document).ready(function() {
			$("#msgPopup").remove();
			// Datos Iniciales
			// Cargar Datos del Menu
			$.ajax({
				url : '../InformesController',
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
	
			//Cargar Turnos
			$.ajax({
				url : '../InformesController',
				data : {
					"tipoConsulta" : "encontrarTodos"
				},
				type : 'POST',
				datatype : 'json',
				success : function(data) {
					$("#loading").remove();
					if(data.numRegistros > 0){
						var listadoTurnos = data.listadoTurnos;
						$.each(listadoTurnos, function(index){
							$("#dataTableContent").append("	<tr class='odd gradeX'>" +
									" <td relation='fecha'>"+ listadoTurnos[index].fecha +"</td>" +
									" <td relation='clinica'>"+ listadoTurnos[index].clinica +"</td>" +
									" <td relation='estudiante'>"+ listadoTurnos[index].estudiante +"</td>" +
									" <td relation='especialidad'>"+ listadoTurnos[index].especialidad +"</td>" +
									" <td relation='tratamiento'>"+ listadoTurnos[index].tratamiento +"</td>" +
									" <td relation='cubiculo'>"+ listadoTurnos[index].cubiculo +"</td>" +
									" <td relation='estado'>"+ listadoTurnos[index].estado +"</td>" +
								"</tr>");					
						});						

					}else{
						$("#dataTableContent").append("<tr><td colspan='2'>No existen Registros</td></tr>");
					}
				}
			});
			//Imprimir
			$('#btnImprimirLista').click(function(){	
					$.print("#informeTurnos");
			});
		});