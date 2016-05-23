package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the horario database table.
 * 
 */
@Entity
@NamedQuery(name="Horario.findAll", query="SELECT h FROM Horario h")
public class Horario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idHorario;

	private String dia;

	private String estado;

	private String horaFinal;

	private String horaInicio;

	//bi-directional many-to-one association to Horariocubiculo
	@OneToMany(mappedBy="horario")
	private List<Horariocubiculo> horariocubiculos;

	//bi-directional many-to-one association to Horarioestudiante
	@OneToMany(mappedBy="horario")
	private List<Horarioestudiante> horarioestudiantes;

	public Horario() {
	}

	public int getIdHorario() {
		return this.idHorario;
	}

	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
	}

	public String getDia() {
		return this.dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getHoraFinal() {
		return this.horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public String getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public List<Horariocubiculo> getHorariocubiculos() {
		return this.horariocubiculos;
	}

	public void setHorariocubiculos(List<Horariocubiculo> horariocubiculos) {
		this.horariocubiculos = horariocubiculos;
	}

	public Horariocubiculo addHorariocubiculo(Horariocubiculo horariocubiculo) {
		getHorariocubiculos().add(horariocubiculo);
		horariocubiculo.setHorario(this);

		return horariocubiculo;
	}

	public Horariocubiculo removeHorariocubiculo(Horariocubiculo horariocubiculo) {
		getHorariocubiculos().remove(horariocubiculo);
		horariocubiculo.setHorario(null);

		return horariocubiculo;
	}

	public List<Horarioestudiante> getHorarioestudiantes() {
		return this.horarioestudiantes;
	}

	public void setHorarioestudiantes(List<Horarioestudiante> horarioestudiantes) {
		this.horarioestudiantes = horarioestudiantes;
	}

	public Horarioestudiante addHorarioestudiante(Horarioestudiante horarioestudiante) {
		getHorarioestudiantes().add(horarioestudiante);
		horarioestudiante.setHorario(this);

		return horarioestudiante;
	}

	public Horarioestudiante removeHorarioestudiante(Horarioestudiante horarioestudiante) {
		getHorarioestudiantes().remove(horarioestudiante);
		horarioestudiante.setHorario(null);

		return horarioestudiante;
	}

}