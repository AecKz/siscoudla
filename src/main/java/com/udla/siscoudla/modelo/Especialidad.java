package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the especialidad database table.
 * 
 */
@Entity
@NamedQuery(name="Especialidad.findAll", query="SELECT e FROM Especialidad e")
public class Especialidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEspecialidad;

	private String descripcion;

	private String estado;

	private String nombre;

	//bi-directional many-to-one association to Tratamiento
	@OneToMany(mappedBy="especialidad")
	private List<Tratamiento> tratamientos;

	//bi-directional many-to-one association to Cubiculo
	@OneToMany(mappedBy="especialidad")
	private List<Cubiculo> cubiculos;

	public Especialidad() {
	}

	public int getIdEspecialidad() {
		return this.idEspecialidad;
	}

	public void setIdEspecialidad(int idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Tratamiento> getTratamientos() {
		return this.tratamientos;
	}

	public void setTratamientos(List<Tratamiento> tratamientos) {
		this.tratamientos = tratamientos;
	}

	public Tratamiento addTratamiento(Tratamiento tratamiento) {
		getTratamientos().add(tratamiento);
		tratamiento.setEspecialidad(this);

		return tratamiento;
	}

	public Tratamiento removeTratamiento(Tratamiento tratamiento) {
		getTratamientos().remove(tratamiento);
		tratamiento.setEspecialidad(null);

		return tratamiento;
	}

	public List<Cubiculo> getCubiculos() {
		return this.cubiculos;
	}

	public void setCubiculos(List<Cubiculo> cubiculos) {
		this.cubiculos = cubiculos;
	}

	public Cubiculo addCubiculo(Cubiculo cubiculo) {
		getCubiculos().add(cubiculo);
		cubiculo.setEspecialidad(this);

		return cubiculo;
	}

	public Cubiculo removeCubiculo(Cubiculo cubiculo) {
		getCubiculos().remove(cubiculo);
		cubiculo.setEspecialidad(null);

		return cubiculo;
	}

}