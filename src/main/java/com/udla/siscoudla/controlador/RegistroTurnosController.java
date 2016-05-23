package com.udla.siscoudla.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.udla.siscoudla.dao.EspecialidadDAO;
import com.udla.siscoudla.dao.EstudianteDAO;
import com.udla.siscoudla.dao.HorarioCubiculoEstadoDAO;
import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.HorarioEstudianteDAO;
import com.udla.siscoudla.dao.PacienteDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.dao.RolDAO;
import com.udla.siscoudla.dao.TratamientoDAO;
import com.udla.siscoudla.dao.TurnoDAO;
import com.udla.siscoudla.modelo.Especialidad;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Horariocubiculoestado;
import com.udla.siscoudla.modelo.Horarioestudiante;
import com.udla.siscoudla.modelo.Paciente;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.modelo.Rol;
import com.udla.siscoudla.modelo.Tratamiento;
import com.udla.siscoudla.modelo.Turno;
import com.udla.siscoudla.util.Utilitarios;

/**
 * Servlet implementation class RegistroTurnosController
 */
@WebServlet("/RegistroTurnosController")
public class RegistroTurnosController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistroTurnosController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		String valorUsuario = session.getAttribute("login").toString();
		JSONObject especialidadJSONObject = new JSONObject();
		JSONArray especialidadJSONArray = new JSONArray();
		JSONObject horarioJSONObject = new JSONObject();
		JSONArray horarioJSONArray = new JSONArray();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			String idRadio = request.getParameter("idRadio") == null ? ""
					: request.getParameter("idRadio");
			String fechaSeleccionada = request.getParameter("fechaSeleccionada") == null ? ""
					: request.getParameter("fechaSeleccionada");
			String historiaClinica = request.getParameter("historiaClinica") == null ? ""
					: request.getParameter("historiaClinica");
			String nombresPaciente = request.getParameter("nombresPaciente") == null ? ""
					: request.getParameter("nombresPaciente");
			String apellidosPaciente = request.getParameter("apellidosPaciente") == null ? ""
					: request.getParameter("apellidosPaciente");
			String emailPaciente = request.getParameter("emailPaciente") == null ? ""
					: request.getParameter("emailPaciente");
			String generoPaciente = request.getParameter("generoPaciente") == null ? ""
					: request.getParameter("generoPaciente");
			String fechaNacimientoPaciente = request.getParameter("fechaNacimientoPaciente") == null ? ""
					: request.getParameter("fechaNacimientoPaciente");
			String cubiculoAsignadoTurno = request.getParameter("cubiculoAsignadoTurno") == null ? ""
					: request.getParameter("cubiculoAsignadoTurno");
			String tratamientoSeleccionado = request.getParameter("tratamientoSeleccionado") == null ? ""
					: request.getParameter("tratamientoSeleccionado");
			//Para cargar los datos del menu
			if (tipoConsulta.equals("cargarDatosMenus")){
				if(Utilitarios.verificarRolEstudiante(valorUsuario)){
					PersonaDAO personaDAO = new PersonaDAO();
					Persona persona = new Persona();
					persona = personaDAO.buscarPorEmail(valorUsuario);
					String nombreCompleto = persona.getNombres() +" "+ persona.getApellidos();
					result.put("nombreCompleto", nombreCompleto);
				}
			}
			//Para cargar las especialidades y sus tratamientos
			if (tipoConsulta.equals("cargarTratamientos")) {
				//Cargar datos de Especialidad y Tratamientos
				EspecialidadDAO especialidadDAO = new EspecialidadDAO();
				TratamientoDAO tratamientoDAO = new TratamientoDAO();
				List<Especialidad> listaEspecialidades = especialidadDAO.buscarActivos();
				for(Especialidad especialidad:listaEspecialidades){
					System.out.println("Especialidad: " +especialidad.getNombre());
					especialidadJSONObject.put("idEspecialidad", especialidad.getIdEspecialidad());
					especialidadJSONObject.put("nombreEspecialidad", especialidad.getNombre());
										
					List<Tratamiento> listaTratamientos = tratamientoDAO.buscarTratamientosEspecialidad(especialidad.getIdEspecialidad());
					for(int i = 0; i<listaTratamientos.size();i++){
						Tratamiento tratamiento = new Tratamiento();
						tratamiento = listaTratamientos.get(i);
						System.out.println("Tratamiento: " +tratamiento.getNombre());
						especialidadJSONObject.put("contadorTratamientos", listaTratamientos.size());
						especialidadJSONObject.put("idTratamiento" + i, tratamiento.getIdTratamiento());
						especialidadJSONObject.put("nombreTratamiento" + i, tratamiento.getNombre());						
					}
					especialidadJSONArray.add(especialidadJSONObject);
				}
				result.put("numRegistros", (especialidadJSONArray.size()));
				result.put("listadoEspecialidades", especialidadJSONArray);
			}
			//Para cargar los horarios del estudiante
			if(tipoConsulta.equals("cargarHorarios")){
				//Comprueba si el rol del usuario es Estudiante
				if(Utilitarios.verificarRolEstudiante(valorUsuario)){
					//Si se verifica el rol, traemos los horarios que le pertenecen
					HorarioDAO horarioDAO = new HorarioDAO();
					Estudiante estudiante = Utilitarios.buscarEstudianteUsuario(valorUsuario); 		
					List<Horario> horarios = horarioDAO.buscarHorariosEstudiante(estudiante);
					if (!horarios.isEmpty()) {			
						for (Horario horario : horarios) {
							System.out.println("Resultado: " +horario.getDia() +"-"+ horario.getHoraInicio() +"-"+ horario.getHoraFinal());
							horarioJSONObject.put("dia", horario.getDia());
							horarioJSONObject.put("horaInicio", horario.getHoraInicio());
							horarioJSONObject.put("horaFin", horario.getHoraFinal());					
							horarioJSONArray.add(horarioJSONObject);
						}
					} else {
						System.out.println("No trae resultados");			
					}
					result.put("numRegistros", horarioJSONArray.size());
					result.put("listadoHorarios", horarioJSONArray);
				}
			}
			//Consultamos si el tratamiento seleccionado tiene cubiculos libres
			if(tipoConsulta.equals("consultarCubiculos")){
					EspecialidadDAO especialidadDAO = new EspecialidadDAO();	
					HorarioEstudianteDAO horarioEstudianteDAO = new HorarioEstudianteDAO();
					HorarioCubiculoEstadoDAO hceDAO = new HorarioCubiculoEstadoDAO();
					TratamientoDAO tratamientoDAO = new TratamientoDAO();
					HorarioDAO horarioDAO = new HorarioDAO();
					Horario horario = new Horario();
					Especialidad especialidad = new Especialidad();
					String tipoCubiculo = "NORMAL";
					String cubiculoAsignado = "0";
					//Obtenemos el verdadero id del cubiculo seleccionado
					String[] auxIdTratamiento = idRadio.split("tratamiento");
					int idTratamiento = Integer.parseInt(auxIdTratamiento[1]);	
					String nombreTratamiento = tratamientoDAO.buscarPorId(idTratamiento).getNombre();
					//Obtenemos el idEspecialidad del tratamiento seleccionado en el radio
					especialidad = especialidadDAO.buscarEspecialidadTratamiento(idTratamiento);
					int idEspecialidad = especialidad.getIdEspecialidad();
					String nombreEspecialidad = especialidad.getNombre();
					Date fecha = new Date();
					DateFormat fechaTexto = new SimpleDateFormat("dd-MM-yyyy");					
					String fechaTurno = fechaTexto.format(fecha);
					//Casteamos la fecha seleccionada de string a date
					if(!fechaSeleccionada.isEmpty()){
						fechaTurno = Utilitarios.fechaDatePickertoDate(fechaSeleccionada);
						fecha = Utilitarios.stringToDate(fechaTurno);
					}					 
					//De la fecha extraemos el dia y verificamos el horario del estudiante para ese dia
					int diaFecha = fecha.getDay();
					String diaNombre = Utilitarios.buscarDia(diaFecha);
					Estudiante estudiante = Utilitarios.buscarEstudianteUsuario(valorUsuario);
					int idHorario = horarioEstudianteDAO.buscarPorDiaEstudiante(diaNombre, estudiante.getIdEstudiante());
					horario = horarioDAO.buscarPorId(idHorario);
					//Consultamos los cubiculos libres para los datos ingresados
					List<String> cubiculos = hceDAO.buscarCubiculosLibres(fecha, idEspecialidad, idHorario, tipoCubiculo);					
					String cubiculo = "";
					if (!cubiculos.isEmpty()) {
						System.out.println("Existen disponibles los siguientes cubiculos: ");
						for (String he : cubiculos) {
							System.out.println("Cubiculo: " +he);
						}
						//Se asigna un cubiculo al estudiante
						cubiculo = cubiculos.get(0);
						System.out.println("Se ha asignado el cubiculo: "+ cubiculo);
						//Se comprueba una vez mas si el cubiculo asignado esta Libre
						//Ojo: Es posible que por concurrencia se asigne un cubiculo, y luego otro usuario lo registre.			
						Boolean flag = hceDAO.verificarEstado(Integer.parseInt(cubiculo), "LIB");						
						if(flag){
							System.out.println("El cubiculo esta libre");
							cubiculoAsignado = cubiculo;
						}else{
							System.out.println("El cubiculo NO esta libre");						
						}						
					} else {
						System.out.println("No trae resultados");						
					}
					result.put("cubiculoAsignado", cubiculoAsignado);
					result.put("nombreEspecialidad", nombreEspecialidad);
					result.put("nombreTratamiento", nombreTratamiento);
					result.put("fechaTurno",fechaTurno);
					result.put("horaInicio", horario.getHoraInicio());
					result.put("horaFinal", horario.getHoraFinal());
			}//Fin consultar cubiculos
			//Reserva de turnos
			if (tipoConsulta.equals("reservarTurno")) {
				System.out.println("Ingreso a reservarTurno");
				String resultado ="";
				//Verificar si el cubiculo asignado continua libre
				System.out.println("Verifica Estado cubiculo");
				HorarioCubiculoEstadoDAO hceDAO = new HorarioCubiculoEstadoDAO();
				Horariocubiculoestado hce = new Horariocubiculoestado();
				hce = hceDAO.buscarPorNumero(cubiculoAsignadoTurno);
				Boolean flag = hceDAO.verificarEstado(hce.getIdhorarioCubiculoEstado(), "LIB");				
				if(flag){
					System.out.println("El cubiculo esta libre");
					//Como el cubiculo esta libre, continuamos con el registro
					//Datos del paciente
					PacienteDAO pacienteDAO = new PacienteDAO();
					Paciente paciente = new Paciente();		
					PersonaDAO personaDAO = new PersonaDAO();
					Persona persona = new Persona();
					TurnoDAO turnoDAO = new TurnoDAO();
					Turno turno = new Turno();
					TratamientoDAO tratamientoDAO = new TratamientoDAO();
					Tratamiento tratamiento = new Tratamiento();
					HorarioEstudianteDAO horarioEstudianteDAO = new HorarioEstudianteDAO();
					Horarioestudiante horarioEstudiante = new Horarioestudiante();
					if (historiaClinica.equals("")){
						paciente = pacienteDAO.buscarPorNumeroHistoria(historiaClinica);
						//Si no existe el paciente se crea
						if (paciente.getIdPaciente()==0){
							paciente.setNumeroHistoria(historiaClinica);
							//Verificamos si existe la persona
							persona = personaDAO.buscarPorEmail(emailPaciente);
							// Si no existe la persona la creamos
							if(persona.getIdPersona()==0){
								//Datos de la persona					
								if (!nombresPaciente.equals("")){
									persona.setNombres(nombresPaciente);
								}
								if (!apellidosPaciente.equals("")){
									persona.setApellidos(apellidosPaciente);
								}
								if (!emailPaciente.equals("")){
									persona.setEmail(emailPaciente);
								}
								if (!generoPaciente.equals("")){
									persona.setGenero(generoPaciente);
								}
								if (!fechaNacimientoPaciente.equals("")){
									String fecha = Utilitarios.fechaDatePickertoDate(fechaNacimientoPaciente);
									Date fechaNacimiento = Utilitarios.stringToDate(fecha);									
									persona.setFechaNacimiento(fechaNacimiento);
								}
								persona.setEstado("ACT");
								personaDAO.crear(persona);
								//Una vez creada la persona creamos el paciente
								paciente.setPersona(persona);
								paciente.setEstado("ACT");
								pacienteDAO.crear(paciente);								
							}else{
							  //Si existe la persona pero no el paciente se crea	
							  paciente.setPersona(persona);
							  paciente.setEstado("ACT");
							  pacienteDAO.crear(paciente);
							}
						}//fin paciente					
					}else{//Ingreso Historia Clinica
						//TODO: Validar que exista el paciente en el front end sino guardaria un paciente vacio
						paciente = pacienteDAO.buscarPorNumeroHistoria(historiaClinica);
					}//fin historia clinica
					
					//Creamos el objeto Turno
					Date fecha = new Date();
					DateFormat fechaTexto = new SimpleDateFormat("dd-MM-yyyy");					
					String fechaTurno = fechaTexto.format(fecha);
					//Casteamos la fecha seleccionada de string a date
					if(!fechaSeleccionada.isEmpty()){
						fechaTurno = Utilitarios.fechaDatePickertoDate(fechaSeleccionada);
						fecha = Utilitarios.stringToDate(fechaTurno);
					}
					//De la fecha extraemos el dia y verificamos el horario del estudiante para ese dia
					int diaFecha = fecha.getDay();
					String diaNombre = Utilitarios.buscarDia(diaFecha);
					Estudiante estudiante = Utilitarios.buscarEstudianteUsuario(valorUsuario);
					int idHorario = horarioEstudianteDAO.buscarPorDiaEstudiante(diaNombre, estudiante.getIdEstudiante());
					horarioEstudiante = horarioEstudianteDAO.buscarPorIdHorarioYEstudiante(idHorario,estudiante);
					turno.setFecha(fecha);
					turno.setPaciente(paciente);
					turno.setTipo("NOR");
					turno.setEstado("RES");
					turno.setHorariocubiculoestado(hce);
					turno.setHorarioestudiante(horarioEstudiante);
					tratamiento = tratamientoDAO.buscarPorNombre(tratamientoSeleccionado);
					turno.setTratamiento(tratamiento);
					turno = turnoDAO.crear(turno);
					if(turno.getIdTurno()>0){
						resultado = "ok";
						hce.setEstado("RES");
						hceDAO.editar(hce);
					}else{
						resultado = "error";
					}
				}else{
					System.out.println("El cubiculo NO esta libre");
					resultado = "cubiculo ocupado";
				}	
				result.put("resultado", resultado);				
			}			
			result.put("success", Boolean.TRUE);
			response.setContentType("application/json; charset=UTF-8");
			result.write(response.getWriter());
		} catch (Exception e) {
			result.put("success", Boolean.FALSE);
			result.put("error", e.getLocalizedMessage());
			response.setContentType("application/json; charset=UTF-8");
			result.write(response.getWriter());
			e.printStackTrace();
		}
	}	
}
