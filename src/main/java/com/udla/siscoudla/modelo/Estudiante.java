package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the estudiante database table.
 * 
 */
@Entity
@NamedQuery(name="Estudiante.findAll", query="SELECT e FROM Estudiante e")
public class Estudiante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEstudiante;

	private String estado;

	private String matricula;

	//bi-directional many-to-one association to Clinica
	@ManyToOne
	@JoinColumn(name="idClinica")
	private Clinica clinica;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="idPersona")
	private Persona persona;

	//bi-directional many-to-one association to Horarioestudiante
	@OneToMany(mappedBy="estudiante")
	private List<Horarioestudiante> horarioestudiantes;

	public Estudiante() {
	}

	public int getIdEstudiante() {
		return this.idEstudiante;
	}

	public void setIdEstudiante(int idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMatricula() {
		return this.matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Clinica getClinica() {
		return this.clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Horarioestudiante> getHorarioestudiantes() {
		return this.horarioestudiantes;
	}

	public void setHorarioestudiantes(List<Horarioestudiante> horarioestudiantes) {
		this.horarioestudiantes = horarioestudiantes;
	}

	public Horarioestudiante addHorarioestudiante(Horarioestudiante horarioestudiante) {
		getHorarioestudiantes().add(horarioestudiante);
		horarioestudiante.setEstudiante(this);

		return horarioestudiante;
	}

	public Horarioestudiante removeHorarioestudiante(Horarioestudiante horarioestudiante) {
		getHorarioestudiantes().remove(horarioestudiante);
		horarioestudiante.setEstudiante(null);

		return horarioestudiante;
	}

}