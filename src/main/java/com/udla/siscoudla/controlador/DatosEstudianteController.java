package com.udla.siscoudla.controlador;

import java.io.IOException;
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

import com.udla.siscoudla.dao.ClinicaDAO;
import com.udla.siscoudla.dao.EstudianteDAO;
import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.HorarioEstudianteDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.dao.UsuarioDAO;
import com.udla.siscoudla.modelo.Clinica;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Horarioestudiante;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.modelo.Rol;
import com.udla.siscoudla.modelo.Usuario;
import com.udla.siscoudla.util.Utilitarios;

/**
 * Servlet implementation class RegistroTurnosController
 */
@WebServlet("/DatosEstudianteController")
public class DatosEstudianteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DatosEstudianteController() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		String valorUsuario = session.getAttribute("login").toString();
		String nombres = request.getParameter("nombres") == null ? ""
				: request.getParameter("nombres");
		String apellidos = request.getParameter("apellidos") == null ? ""
				: request.getParameter("apellidos");
		String email = request.getParameter("email") == null ? ""
				: request.getParameter("email");
		String matriculaEstudiante = request.getParameter("matricula") == null ? ""
				: request.getParameter("matricula");
		String clinicaEstudiante = request.getParameter("clinica") == null ? ""
				: request.getParameter("clinica");
		String generoEstudiante = request.getParameter("genero") == null ? ""
				: request.getParameter("genero");
		String fechaNacimientoEstudiante = request.getParameter("fechaNacimiento") == null ? ""
				: request.getParameter("fechaNacimiento");
		String telefonoEstudiante = request.getParameter("telefono") == null ? ""
				: request.getParameter("telefono");
		String semestre = request.getParameter("semestre") == null ? ""
				: request.getParameter("semestre");
		String horarios = request.getParameter("horarios") == null ? ""
				: request.getParameter("horarios");

		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");	
			PersonaDAO personaDAO = new PersonaDAO();
			Persona personaVO = new Persona();
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuario = new Usuario();
			EstudianteDAO estudianteDAO = new EstudianteDAO();
			Estudiante estudianteVO = new Estudiante();
			ClinicaDAO clinicaDAO = new ClinicaDAO();
			Clinica clinicaVO = new Clinica();
			HorarioDAO horarioDAO = new HorarioDAO();
			Horario horarioVO = new Horario();
			//Para cargar los datos del menu
			if (tipoConsulta.equals("cargarDatosMenus")){
				if(Utilitarios.verificarRolEstudiante(valorUsuario)){					
					personaVO = personaDAO.buscarPorEmail(valorUsuario);
					String nombreCompleto = personaVO.getNombres() +" "+ personaVO.getApellidos();
					result.put("nombreCompleto", nombreCompleto);
				}
			}
			if(tipoConsulta.equals("cargarClinicas")){
				// Se consultan todos las Clinicas
				JSONObject clinicaJSONObject = new JSONObject();
				JSONArray clinicaJSONArray = new JSONArray();
				List <Clinica> listaClinicas =  clinicaDAO.buscarTodos();
				for (int i=0; i<listaClinicas.size(); i++){
					clinicaVO = listaClinicas.get(i);
					clinicaJSONObject.put("codigo", clinicaVO.getIdClinica());
					clinicaJSONObject.put("nombre", clinicaVO.getNombre());
					clinicaJSONArray.add(clinicaJSONObject);
				}
				result.put("listadoClinicas", clinicaJSONArray);
			}
			if(tipoConsulta.equals("cargarHorariosCombos")){
				// Se consultan todos los Horarios
				JSONObject horarioJSONObject = new JSONObject();
				JSONArray horarioJSONArray = new JSONArray();
				List <Horario> listaHorarios =  horarioDAO.buscarTodosActivos();
				for (int i=0; i<listaHorarios.size(); i++){
					horarioVO = listaHorarios.get(i);
					horarioJSONObject.put("codigo", horarioVO.getIdHorario());
					horarioJSONObject.put("horario", horarioVO.getHoraInicio() + "-"+ horarioVO.getHoraFinal());
					horarioJSONObject.put("dia", horarioVO.getDia());
					horarioJSONArray.add(horarioJSONObject);
				}
				result.put("listadoHorariosCombos", horarioJSONArray);
			}
			if(tipoConsulta.equals("guardarHorarios")){
				if (Utilitarios.verificarRolEstudiante(valorUsuario)) {
					Horarioestudiante horarioEstudianteVO = new Horarioestudiante();
					HorarioEstudianteDAO horarioEstudianteDAO = new HorarioEstudianteDAO();
					String [] auxHorarios = horarios.split("-");
					for(int i =0; i<auxHorarios.length;i++){
						String IdHorario = auxHorarios[i];
						horarioEstudianteVO.setHorario(horarioVO);
						horarioVO = horarioDAO.buscarPorId(Integer.parseInt(IdHorario));						
						personaVO = personaDAO.buscarPorEmail(valorUsuario);
						estudianteVO = estudianteDAO.buscarPorPersona(personaVO);						
						horarioEstudianteVO.setEstudiante(estudianteVO);
						horarioEstudianteVO.setEstado("ACT");
						horarioEstudianteVO.setSemestre(semestre);
						horarioEstudianteDAO.crear(horarioEstudianteVO);	
						System.out.print("Horario Creado: " + horarioEstudianteVO.getHorario().getDia() +"-"
						+ horarioEstudianteVO.getHorario().getHoraInicio() + "-" + horarioEstudianteVO.getHorario().getHoraFinal()+"\n");
					}
					
				}
				
			}
			//Cargar datos del estudiante
			if (tipoConsulta.equals("cargarDatosProfile")) {
				if (Utilitarios.verificarRolEstudiante(valorUsuario)) {
					personaVO = personaDAO.buscarPorEmail(valorUsuario);
					estudianteVO = estudianteDAO.buscarPorPersona(personaVO);
					String matricula = "";
					int clinica = 0;
					String telefono ="";
					String fechaNacimiento ="";
					String genero = "";
					result.put("nombres", personaVO.getNombres());
					result.put("apellidos", personaVO.getApellidos());
					result.put("email", personaVO.getEmail());
					if(personaVO.getFechaNacimiento()!=null){
						fechaNacimiento = Utilitarios.dateToString(personaVO.getFechaNacimiento()); 
					}
					if(personaVO.getTelefono()!=null){
						telefono = personaVO.getTelefono();
					}
					if(personaVO.getGenero()!=null){
						genero = personaVO.getGenero();						
					}
					if(estudianteVO.getMatricula()!=null && estudianteVO.getClinica()!=null){
						matricula = estudianteVO.getMatricula();
						clinica = estudianteVO.getClinica().getIdClinica();
					}
					result.put("matricula", matricula);
					result.put("clinica", clinica);
					result.put("fechaNacimiento", fechaNacimiento);
					result.put("telefono", telefono);
					result.put("genero", genero);
				}
			}
			//Guardar datos del estudiante
			if (tipoConsulta.equals("guardarDatosProfile")) {
				if (Utilitarios.verificarRolEstudiante(valorUsuario)) {
					personaVO = personaDAO.buscarPorEmail(valorUsuario);
					estudianteVO = estudianteDAO.buscarPorPersona(personaVO);
					personaVO.setNombres(nombres);
					personaVO.setApellidos(apellidos);
					personaVO.setEmail(email);
					personaVO.setEstado("ACT");
					String fecha = Utilitarios.fechaDatePickertoDate(fechaNacimientoEstudiante);
					Date fechaNacimientoPersona = Utilitarios.stringToDate(fecha);									
					personaVO.setFechaNacimiento(fechaNacimientoPersona);
					personaVO.setGenero(generoEstudiante);
					personaVO.setTelefono(telefonoEstudiante);
					personaDAO.editar(personaVO);
					estudianteVO.setMatricula(matriculaEstudiante);
					clinicaVO = clinicaDAO.buscarPorId(Integer.parseInt(clinicaEstudiante));
					estudianteVO.setClinica(clinicaVO);
					estudianteVO.setEstado("ACT");
					estudianteDAO.editar(estudianteVO);
				}
			}
			
			//Asigna el rol de Estudiante al nuevo usuario
			if (tipoConsulta.equals("registroEstudiante")){							
				Rol rol = new Rol();
				personaVO = personaDAO.buscarPorEmail(valorUsuario);
				int idPersonaLogin = personaVO.getIdPersona();
				usuario = usuarioDAO.buscarPorIdPersona(idPersonaLogin);
				rol.setIdRol(1);
				usuario.setRol(rol);
				usuario.setEstado("ACT");
				usuarioDAO.editar(usuario);
				estudianteVO.setPersona(personaVO);
				estudianteVO.setEstado("ACT");
				estudianteDAO.crear(estudianteVO);
				session.setAttribute("rol", "Estudiante");
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