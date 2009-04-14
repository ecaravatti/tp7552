package laboratorio.dominio.tarea;

import general.dominio.Practica;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import laboratorio.dominio.Area;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "tarea")
public class Tarea {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "estado")
	@Enumerated(EnumType.STRING)
	private EstadoTarea estado;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "area_id", nullable = false)
	@ForeignKey(name = "tarea_area_fk")
	private Area area;
	
	@ManyToOne
	@JoinColumn(name = "practica_id", nullable = false)
	@ForeignKey(name = "tarea_practica_fk")
	private Practica practica;

	/**
	 * Constructor vacio para uso de Hibernate
	 */
	protected Tarea() {
	}
	
	public Tarea(Area area, Practica practica, String descripcion) {
		this.area = area;
		this.area.getTareasNoAsignadas().add(this);
		this.estado = EstadoTarea.NoAsignada;
		this.practica = practica;
		this.descripcion = descripcion;
	}
	
	public Boolean esAsignable() {
		return (estado.equals(EstadoTarea.NoAsignada) || estado.equals(EstadoTarea.Asignada));
	}
	
	public void registrarResultado(Map<String, String> resultados) {
		this.practica.modificarResultados(resultados);
		this.estado = EstadoTarea.Terminada;
	}
	
	public void modificarEstado(EstadoTarea estado) {
		
		if (this.estado.equals(EstadoTarea.NoAsignada) && !estado.equals(EstadoTarea.NoAsignada))
			this.area.getTareasNoAsignadas().remove(this);
		else if (!this.estado.equals(EstadoTarea.NoAsignada) && estado.equals(EstadoTarea.NoAsignada))
			this.area.getTareasNoAsignadas().add(this);
		
		if (estado.equals(EstadoTarea.EnProgreso))
			this.practica.setFechaRealizacion(new Date());
		
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public Area getArea() {
		return area;
	}
	
	public EstadoTarea getEstado() {
		return estado;
	}

	public Practica getPractica() {
		return practica;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

}
