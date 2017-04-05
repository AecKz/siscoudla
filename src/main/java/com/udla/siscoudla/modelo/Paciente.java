package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the paciente database table.
 * 
 */
@Entity
@NamedQuery(name="Paciente.findAll", query="SELECT p FROM Paciente p")
public class Paciente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPaciente;

	private String estado;

	private String numeroHistoria;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="idPersona")
	private Persona persona;

	//bi-directional many-to-one association to Turno
	@OneToMany(mappedBy="paciente")
	private List<Turno> turnos;

	public Paciente() {
	}

	public int getIdPaciente() {
		return this.idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumeroHistoria() {
		return this.numeroHistoria;
	}

	public void setNumeroHistoria(String numeroHistoria) {
		this.numeroHistoria = numeroHistoria;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Turno> getTurnos() {
		return this.turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}

	public Turno addTurno(Turno turno) {
		getTurnos().add(turno);
		turno.setPaciente(this);

		return turno;
	}

	public Turno removeTurno(Turno turno) {
		getTurnos().remove(turno);
		turno.setPaciente(null);

		return turno;
	}

}