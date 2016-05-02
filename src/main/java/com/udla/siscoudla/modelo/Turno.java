package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the turno database table.
 * 
 */
@Entity
@NamedQuery(name="Turno.findAll", query="SELECT t FROM Turno t")
public class Turno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTurno;

	private String estado;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private String observacion;

	private String tipo;

	//bi-directional many-to-one association to Cubiculo
	@ManyToOne
	@JoinColumn(name="idCubiculo")
	private Cubiculo cubiculo;

	//bi-directional many-to-one association to Paciente
	@ManyToOne
	@JoinColumn(name="idPaciente")
	private Paciente paciente;

	//bi-directional many-to-one association to Horarioestudiante
	@ManyToOne
	@JoinColumn(name="idHorarioEstudiante")
	private Horarioestudiante horarioestudiante;

	public Turno() {
	}

	public int getIdTurno() {
		return this.idTurno;
	}

	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Cubiculo getCubiculo() {
		return this.cubiculo;
	}

	public void setCubiculo(Cubiculo cubiculo) {
		this.cubiculo = cubiculo;
	}

	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Horarioestudiante getHorarioestudiante() {
		return this.horarioestudiante;
	}

	public void setHorarioestudiante(Horarioestudiante horarioestudiante) {
		this.horarioestudiante = horarioestudiante;
	}

}