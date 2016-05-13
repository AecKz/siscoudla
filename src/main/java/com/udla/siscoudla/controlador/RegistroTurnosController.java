package com.udla.siscoudla.controlador;

import java.io.IOException;
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
import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.dao.RolDAO;
import com.udla.siscoudla.dao.TratamientoDAO;
import com.udla.siscoudla.modelo.Especialidad;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.modelo.Rol;
import com.udla.siscoudla.modelo.Tratamiento;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		String valorUsuario = session.getAttribute("login").toString();
		JSONObject especialidadJSONObject = new JSONObject();
		JSONArray especialidadJSONArray = new JSONArray();
		JSONObject tratamientoJSONObject = new JSONObject();
		JSONArray tratamientoJSONArray = new JSONArray();
		JSONObject horarioJSONObject = new JSONObject();
		JSONArray horarioJSONArray = new JSONArray();
		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");
			
			if (tipoConsulta.equals("cargarTratamientos")) {
				//Cargar datos de Especialidad y Tratamientos
				EspecialidadDAO especialidadDAO = new EspecialidadDAO();
				TratamientoDAO tratamientoDAO = new TratamientoDAO();
				List<Especialidad> listaEspecialidades = especialidadDAO.buscarActivos();
				for(Especialidad especialidad:listaEspecialidades){
					System.out.println("Especialidad: " +especialidad.getNombre());
					especialidadJSONObject.put("idEspecialidad", especialidad.getIdEspecialidad());
					especialidadJSONObject.put("nombreEspecialidad", especialidad.getNombre());
					especialidadJSONArray.add(especialidadJSONObject);					
					List<Tratamiento> listaTratamientos = tratamientoDAO.buscarTratamientosEspecialidad(especialidad.getIdEspecialidad());
					for(Tratamiento tratamiento:listaTratamientos){
						System.out.println("Tratamiento: " +tratamiento.getNombre());
						tratamientoJSONObject.put("idTratamiento", tratamiento.getIdTratamiento());
						tratamientoJSONObject.put("nombreTratamiento", tratamiento.getNombre());
						tratamientoJSONArray.add(tratamientoJSONObject);
					}
				}
				result.put("numRegistros", (especialidadJSONArray.size() + tratamientoJSONArray.size()));
				result.put("listadoEspecialidades", especialidadJSONArray);
				result.put("listadoTratamientos", tratamientoJSONArray);
			}
			
			if(tipoConsulta.equals("cargarHorarios")){
				//Comprueba si el rol del usuario es Estudiante
				if(verificarRolEstudiante(valorUsuario)){
					//Si se verifica el rol, traemos los horarios que le pertenecen
					HorarioDAO horarioDAO = new HorarioDAO();
					PersonaDAO personaDAO = new PersonaDAO();
					EstudianteDAO estudianteDAO = new EstudianteDAO();
					Persona persona = new Persona();
					Estudiante estudiante = new Estudiante ();			
					persona = personaDAO.buscarPorUsuario(valorUsuario);						
					estudiante = estudianteDAO.buscarPorPersona(persona); 		
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

	public Boolean verificarRolEstudiante(String valorUsuario) {		
		RolDAO rolDAO = new RolDAO();
		Boolean esEstudiante = false;
		List<Rol> lista = rolDAO.buscarRolPorUsuario(valorUsuario);
		for (Rol rol : lista) {
			if (rol.getNombre().equals("Estudiante")) {
				esEstudiante = true;
			}
		}
		return esEstudiante;
	}	
}
