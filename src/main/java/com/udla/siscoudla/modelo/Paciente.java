package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;


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

	private int idPersona;

	private String numeroHistoria;

	//bi-directional many-to-one association to Turno
	@ManyToOne
	@JoinColumn(name="idPaciente", referencedColumnName="idPaciente")
	private Turno turno;

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

	public int getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getNumeroHistoria() {
		return this.numeroHistoria;
	}

	public void setNumeroHistoria(String numeroHistoria) {
		this.numeroHistoria = numeroHistoria;
	}

	public Turno getTurno() {
		return this.turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

}