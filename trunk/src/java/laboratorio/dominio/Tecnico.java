package laboratorio.dominio;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import laboratorio.dominio.excepciones.ExcepcionAreaDeTrabajoInvalida;
import laboratorio.dominio.excepciones.ExcepcionTareaAAsignarInconsistente;
import laboratorio.dominio.excepciones.ExcepcionTareaADesasignarInconsistente;
import laboratorio.dominio.excepciones.ExcepcionTareaAReasignarInconsistente;
import laboratorio.dominio.excepciones.ExcepcionTareaNoAsignable;
import laboratorio.dominio.excepciones.ExcepcionTareaYaAsignada;
import laboratorio.dominio.tarea.EstadoTarea;
import laboratorio.dominio.tarea.Tarea;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "tecnico")
public class Tecnico {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "edad")
	private Integer edad;
	
	@Column(name = "salario")
	private Double salario;
	
	@ManyToMany
	@Cascade(value = CascadeType.ALL)
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "tecnico_tarea", joinColumns = @JoinColumn(name = "tecnico_id"), inverseJoinColumns = @JoinColumn(name = "tarea_id"))
	private Set<Tarea> tareas;
	
	@ManyToMany
	@Cascade(value = CascadeType.ALL)
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "tecnico_area", joinColumns = @JoinColumn(name = "tecnico_id"), inverseJoinColumns = @JoinColumn(name = "area_id"))
	private Set<Area> areas;

	/**
	 * Constructor por default para uso de Hibernate.
	 */
	protected Tecnico() {
	}
	
	public Tecnico(String nombre, Integer edad, Double salario) {
		this.nombre = nombre;
		this.edad = edad;
		this.salario = salario;
		this.tareas = new HashSet<Tarea>();
		this.areas = new HashSet<Area>();
	}
	
	public void asignarTarea(Tarea tarea) {
		
		if (estaAsignada(tarea))
			throw new ExcepcionTareaYaAsignada();

		if (tarea.getEstado().equals(EstadoTarea.Asignada))
			throw new ExcepcionTareaAAsignarInconsistente();
		
		if (!trabajaEnArea(tarea.getArea()))
			throw new ExcepcionAreaDeTrabajoInvalida();
		
		if (!tarea.esAsignable())
			throw new ExcepcionTareaNoAsignable();
		
		tarea.modificarEstado(EstadoTarea.Asignada);
		tareas.add(tarea);
	}

	public void reasignarTarea(Tecnico tecnicoOrigen, Tarea tarea) {
		
		if (tarea.getEstado().equals(EstadoTarea.NoAsignada))
			throw new ExcepcionTareaAReasignarInconsistente();
		
		if (estaAsignada(tarea))
			throw new ExcepcionTareaYaAsignada();
		
		if (!trabajaEnArea(tarea.getArea()))
			throw new ExcepcionAreaDeTrabajoInvalida();
		if (!tarea.esAsignable())
			throw new ExcepcionTareaNoAsignable();
		
		tecnicoOrigen.getTareas().remove(tarea);
		tareas.add(tarea);
	}
	
	public void desasignarTarea(Tarea tarea) {
		if (tarea.getEstado().equals(EstadoTarea.NoAsignada))
			throw new ExcepcionTareaADesasignarInconsistente();
		
		tarea.modificarEstado(EstadoTarea.NoAsignada);
		tareas.remove(tarea);
	}

	private Boolean estaAsignada(Tarea tarea) {
		return tareas.contains(tarea);
	}
	
	private Boolean trabajaEnArea(Area area) {
		return areas.contains(area);
	}

	public void registrarResultados(Integer idTarea, Map<String, String> resultados) {
		Tarea tarea = buscarTarea(idTarea);
		tarea.registrarResultado(resultados);
	}
	
	/**
	 * Busca si el tecnico tiene asignada la tarea de id dado.
	 * Si es asi, devuelve la tarea; de lo contrario devuelve null.
	 * @param idTarea id de la tarea a buscar.
	 * @return la tarea de id dado.
	 */
	private Tarea buscarTarea(Integer idTarea) {
		for (Tarea tarea: this.tareas) {
			if (tarea.getId().equals(idTarea)) {
				return tarea;
			}
		}
		return null;
	}
	
	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Integer getEdad() {
		return edad;
	}

	public Double getSalario() {
		return salario;
	}
	
	public Set<Tarea> getTareas() {
		return tareas;
	}

	public Set<Area> getAreas() {
		return areas;
	}
	
	public int obtenerCantidadTareas() {
		return this.tareas.size();
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public void setTareas(Set<Tarea> tareas) {
		this.tareas = tareas;
	}

	public void setAreas(Set<Area> areas) {
		this.areas = areas;
	}
	
}
