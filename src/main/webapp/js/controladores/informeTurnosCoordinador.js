/**
 * Controlador de Informe Turnos para el Coordinador
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
				url : '../TomarListaController',
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
				url : '../TomarListaController',
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
									" <td relation='clinica'>"+ listadoTurnos[index].clinica +"</td>" +
									" <td relation='estudiante'>"+ listadoTurnos[index].estudiante +"</td>" +
									" <td relation='especialidad'>"+ listadoTurnos[index].especialidad +"</td>" +
									" <td relation='tratamiento'>"+ listadoTurnos[index].tratamiento +"</td>" +
									" <td relation='cubiculo'>"+ listadoTurnos[index].cubiculo +"</td>" +
									" <td width='50px'> " +
									" <input type='checkbox' name='chkLista' value='"+ listadoTurnos[index].codigo +"'/>" +
									"</td>" +
									" <td width='175px'>" +
										" <input type='hidden' value='"+ listadoTurnos[index].codigo +"'/>" +
//										" <button type='button' class='btn btn-success btn-xs actualizar-btn'>" +
//		  									" <span class='glyphicon glyphicon glyphicon-edit'></span> Actualizar" +
//										" </button>" +
										" <button type='button' class='btn btn-danger btn-xs eliminar-btn'>" +
										  	"<span class='glyphicon glyphicon glyphicon-remove' id='delete-record'></span> Cancelar" +
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
						
						/* Inicio Controles Eliminar Registro */
						$(".eliminar-btn").bind({click: function() {
								var r = confirm("Seguro que desea cancelar el Turno: " + $(this).parent().parent().children().first().text());
								if (r == true){
									codigo = $(this).parent().children().first().val();
									nombre = ""; estudiante = ""; especialidad = ""; tratamiento ="";
									cubiculo = "";
									tipoConsulta = "eliminar";
									enviarDatos(codigo, clinica, estudiante, especialidad,tratamiento,cubiculo, tipoConsulta);
							    	$(this).parent().parent().remove();
								}
							}
						});	
						/* Fin Controles Eliminar Registro */
					}else{
						$("#dataTableContent").append("<tr><td colspan='2'>No existen Registros</td></tr>");
					}
				}
			});			
			
			/* Inicio Controles Grabar Registro*/
				$("#save-record").click(function() {
					var retorno=true;
					$(".required").css("border", "1px solid #ccc");
					$(".required").each(function(index) {
						var cadena = $(this).val();
						if (cadena.trim().length <= 0&&retorno) {
							$(this).css("border", "1px solid red");
							alert("Por favor ingrese el campo requerido");
							$(this).focus();
							retorno= false;
						}
					});		
					codigo = $("#codigo").val();
					clinica = $("#clinica").val();
					estudiante = $("#estudiante").val();
					especialidad = $("#selectEspecialidades").val();
					tratamiento = $("#tratamiento").val();
					cubiculo= $("#cubiculo").val();
					if (codigo == ""){
						tipoConsulta = "crear";
					}else{
						tipoConsulta = "actualizar";
					}
					if(retorno){
						enviarDatos(codigo, clinica, estudiante, especialidad,tratamiento,cubiculo, tipoConsulta);
					}
				});
			/* Fin Controles Grabar Resgistro*/
			
			function enviarDatos(codigo, clinica, estudiante, especialidad,tratamiento,cubiculo, tipoConsulta){
				$.ajax({
					url : '../TomarListaController',
					data : {
						"codigo" : codigo,
						"clinica" : clinica,
						"estudiante" : estudiante,
						"especialidad" : especialidad,
						"tratamiento" : tratamiento,
						"cubiculo" : cubiculo,
						"tipoConsulta": tipoConsulta
					},
					type : 'POST',
					datatype : 'json',
					success : function(data) {
						if(data.success){
							$("#msgPopup").show();
						}else{
							alert(data.error);
						}
					}
				});
			}
		});