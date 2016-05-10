package siscoudla;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.udla.siscoudla.dao.EspecialidadDAO;
import com.udla.siscoudla.dao.HorarioCubiculoEstadoDAO;
import com.udla.siscoudla.dao.HorarioDAO;
import com.udla.siscoudla.dao.RolDAO;
import com.udla.siscoudla.dao.TratamientoDAO;
import com.udla.siscoudla.modelo.Especialidad;
import com.udla.siscoudla.modelo.Estudiante;
import com.udla.siscoudla.modelo.Horario;
import com.udla.siscoudla.modelo.Rol;
import com.udla.siscoudla.modelo.Tratamiento;
import com.udla.siscoudla.util.Utilitarios;

public class TestDAO {

	@Test
	public void test() {
		System.out.println("Iniciando Test DAO");
//		testBuscarHorariosEstudiante();
//		testBuscarRolUsuario();
//		testBuscarTratamientosEspecialidad();
		testBuscarEspecialidadTratamientos();
		testBuscarCubiculosLibres();
		testVerificarEstado();
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
		List<Especialidad> especialidades = especialidadDAO.buscarEspecialidadTratamiento(idTratamiento);
		if (!especialidades.isEmpty()) {			
			for (Especialidad he : especialidades) {
				System.out.println("Resultado: " +he.getNombre() +"-"+ he.getIdEspecialidad());
			}
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


}
