package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tratamiento database table.
 * 
 */
@Entity
@NamedQuery(name="Tratamiento.findAll", query="SELECT t FROM Tratamiento t")
public class Tratamiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTratamiento;

	private String descripcion;

	private String estado;

	private String nombre;

	//bi-directional many-to-one association to Especialidad
	@ManyToOne
	@JoinColumn(name="idEspecialidad")
	private Especialidad especialidad;

	//bi-directional many-to-one association to Turno
	@OneToMany(mappedBy="tratamiento")
	private List<Turno> turnos;

	public Tratamiento() {
	}

	public int getIdTratamiento() {
		return this.idTratamiento;
	}

	public void setIdTratamiento(int idTratamiento) {
		this.idTratamiento = idTratamiento;
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

	public Especialidad getEspecialidad() {
		return this.especialidad;
	}

	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}

	public List<Turno> getTurnos() {
		return this.turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}

	public Turno addTurno(Turno turno) {
		getTurnos().add(turno);
		turno.setTratamiento(this);

		return turno;
	}

	public Turno removeTurno(Turno turno) {
		getTurnos().remove(turno);
		turno.setTratamiento(null);

		return turno;
	}

}