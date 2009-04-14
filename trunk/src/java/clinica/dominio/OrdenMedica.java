package clinica.dominio;


import general.dominio.Practica;
import general.dominio.excepciones.ExcepcionNegocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import clinica.dominio.excepciones.ExcepcionEvaluacionCondicion;
import clinica.dominio.excepciones.ExcepcionValidacionReglas;
import clinica.dominio.paciente.Paciente;

@Entity
@Table(name = "orden_medica")
public class OrdenMedica {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "medico_emisor_id")
	@ForeignKey(name = "orden_medica_medico_fk")
	private Medico medicoEmisor;
	
	@ManyToOne
	@JoinColumn(name = "paciente_id")
	@ForeignKey(name = "orden_medica_paciente_fk")
	private Paciente paciente;
	
	@Column(name = "fecha_emision")
	private Date fechaEmision;
	
	@OneToMany
	//@Cascade(value = {CascadeType.PERSIST, CascadeType.SAVE_UPDATE})
	@Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "orden_medica_practica", joinColumns = @JoinColumn(name = "orden_medica_id"),	inverseJoinColumns = @JoinColumn(name = "practica_id"))
	private List<Practica> practicas;
	
	@Column(name = "aprobada")
	private Boolean aprobada;
	
	public void setAprobada(Boolean aprobada) {
		this.aprobada = aprobada;
	}

	public OrdenMedica() {
		this.practicas = new ArrayList<Practica>();
	    this.aprobada=false;
	}
	
	public void setMedicoEmisor(Medico medicoEmisor) {
		this.medicoEmisor = medicoEmisor;
	}

	public Medico getMedicoEmisor() {
		return medicoEmisor;
	}
	
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public Boolean estaAprobada() {
		return aprobada;
	}

	public void agregarPractica(Practica practica) {
		this.practicas.add(practica);
		if (this.paciente.getHistClinica() != null)
			this.paciente.getHistClinica().agregarPractica(practica);	
	}

	public void borrarPractica(Integer idPractica) {		
		Practica practicaAEliminar = new Practica();
		for (Practica practica: this.practicas) {
			if (practica.getId().equals(idPractica)) {				
				practicaAEliminar = practica;
			}
		}		
		this.getPaciente().getHistClinica().borrarPractica(idPractica);
		this.practicas.remove(practicaAEliminar);
	}

	public void autorizar()	{
		Plan planPaciente = this.paciente.getPlanMedico();
		ExcepcionValidacionReglas excepciones = new ExcepcionValidacionReglas();
		this.aprobada = false;
		for (Practica practica : practicas) {
			try	{
				planPaciente.ejecutarReglas(practica.getPrestacion(), paciente);
				practica.setAprobada(true);
			} catch(ExcepcionEvaluacionCondicion ex) {
				practica.setAprobada(false);
				excepciones.agregarExcepcion(ex);
				
			} catch (ExcepcionNegocio ex) {
				excepciones.agregarExcepcion(ex);
			}
		}
		if (excepciones.getCantidadExcepciones() > 0)
			throw excepciones;
		this.aprobada = true;
	}
	
	/**
	 * Busca en la lista de practicas, una correspondiente a la prestacion
	 * especificada.
	 * @param prestacion
	 * @return La practica o null de no haber una asociada a la prestacion.
	 */
	public Practica buscarPractica(Integer idPractica) {
		for (Practica practica: this.practicas) {
			if (practica.getId().equals(idPractica)) {
				return practica;
			}
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Practica> getPracticas() {
		return practicas;
	}

	public void setPracticas(List<Practica> practicas) {
		this.practicas = practicas;
	}

	public Boolean getAprobada() {
		return aprobada;
	}
}
