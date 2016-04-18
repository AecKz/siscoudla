package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the clinica database table.
 * 
 */
@Entity
@NamedQuery(name="Clinica.findAll", query="SELECT c FROM Clinica c")
public class Clinica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idClinica;

	private String codigoMateria;

	private String descripcion;

	private String nombre;

	private String nombreCorto;

	//bi-directional many-to-one association to Estudiante
	@OneToMany(mappedBy="clinica")
	private List<Estudiante> estudiantes;

	public Clinica() {
	}

	public int getIdClinica() {
		return this.idClinica;
	}

	public void setIdClinica(int idClinica) {
		this.idClinica = idClinica;
	}

	public String getCodigoMateria() {
		return this.codigoMateria;
	}

	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCorto() {
		return this.nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public List<Estudiante> getEstudiantes() {
		return this.estudiantes;
	}

	public void setEstudiantes(List<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Estudiante addEstudiante(Estudiante estudiante) {
		getEstudiantes().add(estudiante);
		estudiante.setClinica(this);

		return estudiante;
	}

	public Estudiante removeEstudiante(Estudiante estudiante) {
		getEstudiantes().remove(estudiante);
		estudiante.setClinica(null);

		return estudiante;
	}

}