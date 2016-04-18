package com.udla.siscoudla.modelo;
import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


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
	@OneToMany(mappedBy="turno")
	private List<Paciente> pacientes;

	//bi-directional many-to-one association to Horario
	@ManyToOne
	@JoinColumn(name="idHorario")
	private Horario horario;

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

	public List<Paciente> getPacientes() {
		return this.pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public Paciente addPaciente(Paciente paciente) {
		getPacientes().add(paciente);
		paciente.setTurno(this);

		return paciente;
	}

	public Paciente removePaciente(Paciente paciente) {
		getPacientes().remove(paciente);
		paciente.setTurno(null);

		return paciente;
	}

	public Horario getHorario() {
		return this.horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

}