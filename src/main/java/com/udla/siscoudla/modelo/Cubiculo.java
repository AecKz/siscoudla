package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cubiculo database table.
 * 
 */
@Entity
@NamedQuery(name="Cubiculo.findAll", query="SELECT c FROM Cubiculo c")
public class Cubiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCubiculo;

	private String estado;

	private String numero;

	private String tipo;

	private String ubicacion;

	//bi-directional many-to-one association to Especialidad
	@ManyToOne
	@JoinColumn(name="idEspecialidad")
	private Especialidad especialidad;

	//bi-directional many-to-one association to Horariocubiculo
	@OneToMany(mappedBy="cubiculo")
	private List<Horariocubiculo> horariocubiculos;

	public Cubiculo() {
	}

	public int getIdCubiculo() {
		return this.idCubiculo;
	}

	public void setIdCubiculo(int idCubiculo) {
		this.idCubiculo = idCubiculo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUbicacion() {
		return this.ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Especialidad getEspecialidad() {
		return this.especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public List<Horariocubiculo> getHorariocubiculos() {
		return this.horariocubiculos;
	}

	public void setHorariocubiculos(List<Horariocubiculo> horariocubiculos) {
		this.horariocubiculos = horariocubiculos;
	}

	public Horariocubiculo addHorariocubiculo(Horariocubiculo horariocubiculo) {
		getHorariocubiculos().add(horariocubiculo);
		horariocubiculo.setCubiculo(this);

		return horariocubiculo;
	}

	public Horariocubiculo removeHorariocubiculo(Horariocubiculo horariocubiculo) {
		getHorariocubiculos().remove(horariocubiculo);
		horariocubiculo.setCubiculo(null);

		return horariocubiculo;
	}

}