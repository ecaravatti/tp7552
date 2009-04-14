package laboratorio.dominio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import laboratorio.dominio.estrategias.EstrategiaAsignacionTarea;
import laboratorio.dominio.excepciones.ExcepcionTecnicoNoSupervisado;
import laboratorio.dominio.tarea.Tarea;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "supervisor")
public class Supervisor {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "edad")
	private Integer edad;
	
	@Column(name = "salario")
	private Double salario;
	
	@OneToMany
	@Cascade(value = CascadeType.ALL)
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "supervisor_tecnico", joinColumns = @JoinColumn(name = "supervisor_id"), inverseJoinColumns = @JoinColumn(name = "tecnico_id"))
	private Set<Tecnico> tecnicos;
	
	@Transient
	private EstrategiaAsignacionTarea estrategiaDeAsignacion;
	
	protected Supervisor() {
		this.tecnicos = new HashSet<Tecnico>();
	}

	public Supervisor(String nombre, Integer edad, Double salario) {
		this.nombre = nombre;
		this.edad = edad;
		this.salario = salario;
		this.tecnicos = new HashSet<Tecnico>();
	}
	
	public void asignarTarea(Tecnico tecnico, Tarea tarea) {
		
		if (!supervisaTecnico(tecnico))
			throw new ExcepcionTecnicoNoSupervisado();
		
		tecnico.asignarTarea(tarea);
		
	}
	
	public void asignarTareas(List<Tecnico> tecnicos, List<Tarea> tareas) {
		estrategiaDeAsignacion.asignarTareas(tecnicos, tareas);
	}
	
	public void reasignarTarea(Tecnico tecnicoOrigen, Tecnico tecnicoDestino, Tarea tarea) {
		
		if (!supervisaTecnico(tecnicoOrigen))
			throw new ExcepcionTecnicoNoSupervisado();
		
		if (!supervisaTecnico(tecnicoDestino))
			throw new ExcepcionTecnicoNoSupervisado();
		
		tecnicoDestino.reasignarTarea(tecnicoOrigen, tarea);
		
	}
	
	public void desasignarTarea(Tecnico tecnico, Tarea tarea) {
		
		if (!supervisaTecnico(tecnico))
			throw new ExcepcionTecnicoNoSupervisado();
		
		tecnico.desasignarTarea(tarea);
		
	}
	
	private Boolean supervisaTecnico(Tecnico tecnico) {
		return tecnicos.contains(tecnico);
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

	public Set<Tecnico> getTecnicos() {
		return tecnicos;
	}
	
	public EstrategiaAsignacionTarea getEstrategiaDeAsignacion() {
		return estrategiaDeAsignacion;
	}

	public void setEstrategiaDeAsignacion(EstrategiaAsignacionTarea estrategiaDeAsignacion) {
		this.estrategiaDeAsignacion = estrategiaDeAsignacion;
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

	public void setTecnicos(Set<Tecnico> tecnicos) {
		this.tecnicos = tecnicos;
	}
}
