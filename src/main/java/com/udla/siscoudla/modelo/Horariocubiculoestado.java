package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the horariocubiculoestado database table.
 * 
 */
@Entity
@NamedQuery(name="Horariocubiculoestado.findAll", query="SELECT h FROM Horariocubiculoestado h")
public class Horariocubiculoestado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idhorarioCubiculoEstado;

	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	//bi-directional many-to-one association to Horariocubiculo
	@ManyToOne
	@JoinColumn(name="idHorarioCubiculo")
	private Horariocubiculo horariocubiculo;

	//bi-directional many-to-one association to Turno
	@OneToMany(mappedBy="horariocubiculoestado")
	private List<Turno> turnos;

	public Horariocubiculoestado() {
	}

	public int getIdhorarioCubiculoEstado() {
		return this.idhorarioCubiculoEstado;
	}

	public void setIdhorarioCubiculoEstado(int idhorarioCubiculoEstado) {
		this.idhorarioCubiculoEstado = idhorarioCubiculoEstado;
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

	public Horariocubiculo getHorariocubiculo() {
		return this.horariocubiculo;
	}

	public void setHorariocubiculo(Horariocubiculo horariocubiculo) {
		this.horariocubiculo = horariocubiculo;
	}

	public List<Turno> getTurnos() {
		return this.turnos;
	}

	public void setTurnos(List<Turno> turnos) {
		this.turnos = turnos;
	}

	public Turno addTurno(Turno turno) {
		getTurnos().add(turno);
		turno.setHorariocubiculoestado(this);

		return turno;
	}

	public Turno removeTurno(Turno turno) {
		getTurnos().remove(turno);
		turno.setHorariocubiculoestado(null);

		return turno;
	}

}