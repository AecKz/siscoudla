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

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="idPersona")
	private Persona persona;

	//bi-directional many-to-one association to Horario
	@OneToMany(mappedBy="estudiante1")
	private List<Horario> horarios1;

	//bi-directional many-to-one association to Clinica
	@ManyToOne
	@JoinColumn(name="idClinica")
	private Clinica clinica;

	//bi-directional many-to-one association to Horario
	@OneToMany(mappedBy="estudiante2")
	private List<Horario> horarios2;

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

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Horario> getHorarios1() {
		return this.horarios1;
	}

	public void setHorarios1(List<Horario> horarios1) {
		this.horarios1 = horarios1;
	}

	public Horario addHorarios1(Horario horarios1) {
		getHorarios1().add(horarios1);
		horarios1.setEstudiante1(this);

		return horarios1;
	}

	public Horario removeHorarios1(Horario horarios1) {
		getHorarios1().remove(horarios1);
		horarios1.setEstudiante1(null);

		return horarios1;
	}

	public Clinica getClinica() {
		return this.clinica;
	}

	public void setClinica(Clinica clinica) {
		this.clinica = clinica;
	}

	public List<Horario> getHorarios2() {
		return this.horarios2;
	}

	public void setHorarios2(List<Horario> horarios2) {
		this.horarios2 = horarios2;
	}

	public Horario addHorarios2(Horario horarios2) {
		getHorarios2().add(horarios2);
		horarios2.setEstudiante2(this);

		return horarios2;
	}

	public Horario removeHorarios2(Horario horarios2) {
		getHorarios2().remove(horarios2);
		horarios2.setEstudiante2(null);

		return horarios2;
	}

}