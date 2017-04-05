package com.udla.siscoudla.controlador;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.util.Utilitarios;

/**
 * Servlet implementation class RegistroTurnosController
 */
@WebServlet("/DatosCoordinadorController")
public class DatosCoordinadorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DatosCoordinadorController() {
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
		String generoCoordinador = request.getParameter("genero") == null ? ""
				: request.getParameter("genero");
		String fechaNacimientoCoordinador = request.getParameter("fechaNacimiento") == null ? ""
				: request.getParameter("fechaNacimiento");
		String telefonoCoordinador = request.getParameter("telefono") == null ? ""
				: request.getParameter("telefono");

		try {
			String tipoConsulta = request.getParameter("tipoConsulta") == null ? ""
					: request.getParameter("tipoConsulta");	
			PersonaDAO personaDAO = new PersonaDAO();
			Persona personaVO = new Persona();		
			//Para cargar los datos del menu
			if (tipoConsulta.equals("cargarDatosMenus")){
				if(Utilitarios.verificarRolCoordinador(valorUsuario)){					
					personaVO = personaDAO.buscarPorEmail(valorUsuario);
					String nombreCompleto = personaVO.getNombres() +" "+ personaVO.getApellidos();
					result.put("nombreCompleto", nombreCompleto);
				}
			}
			//Cargar datos del estudiante
			if (tipoConsulta.equals("cargarDatosProfile")) {
				if (Utilitarios.verificarRolCoordinador(valorUsuario)) {
					personaVO = personaDAO.buscarPorEmail(valorUsuario);										
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
					result.put("fechaNacimiento", fechaNacimiento);
					result.put("telefono", telefono);
					result.put("genero", genero);
				}
			}
			//Guardar datos del estudiante
			if (tipoConsulta.equals("guardarDatosProfile")) {
				if (Utilitarios.verificarRolCoordinador(valorUsuario)) {
					personaVO = personaDAO.buscarPorEmail(valorUsuario);					
					personaVO.setNombres(nombres);
					personaVO.setApellidos(apellidos);
					personaVO.setEmail(email);
					personaVO.setEstado("ACT");
					String fecha = Utilitarios.fechaDatePickertoDate(fechaNacimientoCoordinador);
					Date fechaNacimientoPersona = Utilitarios.stringToDate(fecha);									
					personaVO.setFechaNacimiento(fechaNacimientoPersona);
					personaVO.setGenero(generoCoordinador);
					personaVO.setTelefono(telefonoCoordinador);
					personaDAO.editar(personaVO);					
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
}