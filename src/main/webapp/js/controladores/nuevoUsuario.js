/**
 * Controlador de Usuarios Nuevos
 * Autor: Pablo Jácome A
 * Fecha: 18-06-2016
 */
$(document).ready(function() {
	$('#infoCoordinador').hide();
$('#btnCoordinador').click(function(){
	$('#infoCoordinador').show();
});
$('#btnEstudiante').click(function(){
		tipoConsulta = "registroEstudiante";		
		$.ajax({
			url : '../DatosEstudianteController',
			data : {																								
				"tipoConsulta" : tipoConsulta
			},
			type : 'POST',
			datatype : 'json', 
			success : function (data) {											
				window.location = "datosEstudiante.jsp";
			}
		});	
});							
});//Fin jquery ready