package com.udla.siscoudla.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the horariocubiculo database table.
 * 
 */
@Entity
@NamedQuery(name="Horariocubiculo.findAll", query="SELECT h FROM Horariocubiculo h")
public class Horariocubiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idhorarioCubiculo;

	private String estado;

	//bi-directional many-to-one association to Cubiculo
	@ManyToOne
	@JoinColumn(name="idCubiculo")
	private Cubiculo cubiculo;

	//bi-directional many-to-one association to Horario
	@ManyToOne
	@JoinColumn(name="idHorario")
	private Horario horario;

	//bi-directional many-to-one association to Horariocubiculoestado
	@OneToMany(mappedBy="horariocubiculo")
	private List<Horariocubiculoestado> horariocubiculoestados;

	public Horariocubiculo() {
	}

	public int getIdhorarioCubiculo() {
		return this.idhorarioCubiculo;
	}

	public void setIdhorarioCubiculo(int idhorarioCubiculo) {
		this.idhorarioCubiculo = idhorarioCubiculo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Cubiculo getCubiculo() {
		return this.cubiculo;
	}

	public void setCubiculo(Cubiculo cubiculo) {
		this.cubiculo = cubiculo;
	}

	public Horario getHorario() {
		return this.horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public List<Horariocubiculoestado> getHorariocubiculoestados() {
		return this.horariocubiculoestados;
	}

	public void setHorariocubiculoestados(List<Horariocubiculoestado> horariocubiculoestados) {
		this.horariocubiculoestados = horariocubiculoestados;
	}

	public Horariocubiculoestado addHorariocubiculoestado(Horariocubiculoestado horariocubiculoestado) {
		getHorariocubiculoestados().add(horariocubiculoestado);
		horariocubiculoestado.setHorariocubiculo(this);

		return horariocubiculoestado;
	}

	public Horariocubiculoestado removeHorariocubiculoestado(Horariocubiculoestado horariocubiculoestado) {
		getHorariocubiculoestados().remove(horariocubiculoestado);
		horariocubiculoestado.setHorariocubiculo(null);

		return horariocubiculoestado;
	}

}