package laboratorio.dominio;

import java.util.HashSet;
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

import laboratorio.dominio.tarea.Tarea;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "area")
public class Area {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@OneToMany
	@Cascade(value = CascadeType.ALL)
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "area_tarea", joinColumns = @JoinColumn(name = "area_id"), inverseJoinColumns = @JoinColumn(name = "tarea_id"))
	private Set<Tarea> tareasNoAsignadas;
		
	protected Area() {
		this.tareasNoAsignadas = new HashSet<Tarea>();
	}
	
	public Area(String nombre) {
		this.nombre = nombre;
		this.tareasNoAsignadas = new HashSet<Tarea>();
	}
	
	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	
	public Set<Tarea> getTareasNoAsignadas() {
		return tareasNoAsignadas;
	}

}
