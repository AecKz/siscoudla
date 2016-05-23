package siscoudla;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.udla.siscoudla.dao.EspecialidadDAO;
import com.udla.siscoudla.dao.EstudianteDAO;
import com.udla.siscoudla.dao.HorarioCubiculoEstadoDAO;
import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.HorarioEstudianteDAO;
import com.udla.siscoudla.dao.PersonaDAO;
import com.udla.siscoudla.dao.RolDAO;
import com.udla.siscoudla.dao.TratamientoDAO;
import com.udla.siscoudla.dao.TurnoDAO;
import com.udla.siscoudla.modelo.Especialidad;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Horariocubiculoestado;
import com.udla.siscoudla.modelo.Persona;
import com.udla.siscoudla.modelo.Rol;
import com.udla.siscoudla.modelo.Tratamiento;
import com.udla.siscoudla.modelo.Turno;
import com.udla.siscoudla.util.Utilitarios;

public class TestDAO {

	@Test
	public void test() {
		System.out.println("Iniciando Test DAO");
//		testBuscarHorariosEstudiante();
//		testBuscarRolUsuario();
//		testBuscarTratamientosEspecialidad();
		//testBuscarEspecialidadTratamientos();
		//testBuscarCubiculosLibres();
		//testVerificarEstado();
		//testBuscarPorUsuario();
		//testBuscarPorPersona();
		//testBuscarEspecialidadTratamientos();
		//testBuscarPorDiaEstudiante();
		//testBuscarPorNumero();
		testBuscarTurnosEstudiante();
		System.out.println("Fin Test DAO");
	}

	public void testBuscarRolUsuario() {
		try{
		RolDAO rolDAO = new RolDAO();
		List<Rol> roles = rolDAO.buscarRolPorUsuario("paajacome@udlanet.ec");
		if (!roles.isEmpty()) {			
			for (Rol rol : roles) {
				System.out.println("Resultado: " +rol.getNombre());
			}
		} else {
			System.out.println("No trae resultados");			
		}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	
	public void testBuscarHorariosEstudiante() {
		try{
		HorarioDAO horarioDAO = new HorarioDAO();
		Estudiante estudiante = new Estudiante ();
		estudiante.setIdEstudiante(Integer.parseInt("1"));		
		List<Horario> horarios = horarioDAO.buscarHorariosEstudiante(estudiante);
		if (!horarios.isEmpty()) {			
			for (Horario he : horarios) {
				System.out.println("Resultado: " +he.getDia() +"-"+ he.getHoraInicio() +"-"+ he.getHoraFinal());
			}
		} else {
			System.out.println("No trae resultados");			
		}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarTratamientosEspecialidad() {
		try{
		TratamientoDAO tratamientoDAO = new TratamientoDAO();
		int idEspecialidad = 4;		
		List<Tratamiento> tratamientos = tratamientoDAO.buscarTratamientosEspecialidad(idEspecialidad);
		if (!tratamientos.isEmpty()) {			
			for (Tratamiento he : tratamientos) {
				System.out.println("Resultado: " +he.getNombre() +"-"+ he.getEspecialidad().getNombre());
			}
		} else {
			System.out.println("No trae resultados");			
		}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarEspecialidadTratamientos() {
		try{
		EspecialidadDAO especialidadDAO = new EspecialidadDAO();
		int idTratamiento = 5;		
		Especialidad especialidades = especialidadDAO.buscarEspecialidadTratamiento(idTratamiento);
		if (especialidades!=null) {
				System.out.println("Resultado: " +especialidades.getNombre());
			
		} else {
			System.out.println("No trae resultados");			
		}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarCubiculosLibres() {
		try{
		int idHorario = 1;
		int idEspecialidad  = 2;
		Date fecha = Utilitarios.stringToDate("2016-05-09");
		String tipoCubiculo = "NORMAL";
		HorarioCubiculoEstadoDAO hceDAO = new HorarioCubiculoEstadoDAO();
		List<String> cubiculos = hceDAO.buscarCubiculosLibres(fecha, idEspecialidad, idHorario, tipoCubiculo);		
		if (!cubiculos.isEmpty()) {			
			for (String he : cubiculos) {
				System.out.println("Resultado: " +he);
			}
		} else {
			System.out.println("No trae resultados");			
		}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testVerificarEstado(){
		try{
			HorarioCubiculoEstadoDAO hceDAO = new HorarioCubiculoEstadoDAO();
			Boolean flag = hceDAO.verificarEstado(1, "LIB");
			if(flag){
				System.out.println("El cubiculo esta libre");
			}else{
				System.out.println("El cubiculo NO esta libre");
			}		
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarPorUsuario(){
		try{
			PersonaDAO personaDAO = new PersonaDAO();
			Persona persona = new Persona();
			String usuario = "paajacome@udlanet.ec";
			persona = personaDAO.buscarPorUsuario(usuario);
			if(persona!=null){
				System.out.println("La persona es:" + persona.getNombres() +" "+persona.getApellidos());
			}else{
				System.out.println("No existen datos");
			}		
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarPorPersona(){
		try{
			EstudianteDAO estudianteDAO = new EstudianteDAO();
			Estudiante estudiante = new Estudiante();
			Persona persona = new Persona();
			persona.setIdPersona(Integer.parseInt("1"));
			estudiante = estudianteDAO.buscarPorPersona(persona);
			if(estudiante!=null){
				System.out.println("El estudiante es:" + estudiante.getMatricula());
			}else{
				System.out.println("No existen datos");
			}		
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarPorDiaEstudiante(){
		try{
			HorarioEstudianteDAO horarioEstudianteDAO = new HorarioEstudianteDAO();
			String diaNombre = "LUNES";
			int idEstudiante = 1;
			int idHorarioEstudiante = horarioEstudianteDAO.buscarPorDiaEstudiante(diaNombre, idEstudiante);
			if(idHorarioEstudiante!=0){
				System.out.println("El id es:" + idHorarioEstudiante);
			}else{
				System.out.println("No existen datos");
			}		
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarPorNumero(){
		try{
			HorarioCubiculoEstadoDAO hceDAO = new HorarioCubiculoEstadoDAO();
			Horariocubiculoestado hce = new Horariocubiculoestado();
			String numeroCubiculo = "5";
			hce = hceDAO.buscarPorNumero(numeroCubiculo);			
			if(hce!=null){
				System.out.println("El id es:" + hce.getIdhorarioCubiculoEstado());
			}else{
				System.out.println("No existen datos");
			}		
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
	public void testBuscarTurnosEstudiante(){
		try{
			TurnoDAO turnoDAO = new TurnoDAO();			
			String estado = "RES";
			int idEstudiante = 1;
			List<Turno> turnos = turnoDAO.buscarReservadosPorEstudiante(idEstudiante, estado);		
			if (!turnos.isEmpty()) {			
				for (Turno turno : turnos) {
					System.out.println("Turno: " +turno.getFecha()+ " - Horario:" 
							+ turno.getHorarioestudiante().getHorario().getHoraInicio()
							+ " - "
							+ turno.getHorarioestudiante().getHorario().getHoraFinal()
							+ " -Tratamiento: "
							+ turno.getTratamiento().getNombre()
							+ " -Paciente: "
							+ turno.getPaciente().getPersona().getNombres()
							+ " - "
							+ turno.getPaciente().getPersona().getApellidos()
							+ " - Cubiculo: "
							+ turno.getHorariocubiculoestado().getHorariocubiculo().getCubiculo().getNumero()
							);
				}
			} else {
				System.out.println("No trae resultados");			
			}
		}catch(Exception e){
			System.out.println("Error:" + e);
			fail("Fail: " + e);
		}
	}
}
