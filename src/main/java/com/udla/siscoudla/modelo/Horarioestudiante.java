package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the horarioestudiante database table.
 * 
 */
@Entity
@NamedQuery(name="Horarioestudiante.findAll", query="SELECT h FROM Horarioestudiante h")
public class Horarioestudiante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idhorarioEstudiante;

	private String estado;

	private String semestre;

	//bi-directional many-to-one association to Estudiante
	@ManyToOne
	@JoinColumn(name="idEstudiante")
	private Estudiante estudiante;

	//bi-directional many-to-one association to Horario
	@ManyToOne
	@JoinColumn(name="idHorario")
	private Horario horario;

	//bi-directional many-to-one association to Turno
	@OneToMany(mappedBy="horarioestudiante")
	private List<Turno> turnos;

	public Horarioestudiante() {
	}

	public int getIdhorarioEstudiante() {
		return this.idhorarioEstudiante;
	}

	public void setIdhorarioEstudiante(int idhorarioEstudiante) {
		this.idhorarioEstudiante = idhorarioEstudiante;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getSemestre() {
		return this.semestre;
	}

	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}

	public Estudiante getEstudiante() {
		return this.estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Horario getHorario() {
		return this.horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public List<Turno> getTurnos() {
		return this.turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}

	public Turno addTurno(Turno turno) {
		getTurnos().add(turno);
		turno.setHorarioestudiante(this);

		return turno;
	}

	public Turno removeTurno(Turno turno) {
		getTurnos().remove(turno);
		turno.setHorarioestudiante(null);

		return turno;
	}

}