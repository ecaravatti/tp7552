package clinica.dominio.reglas;

import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import clinica.dominio.Prestacion;
import clinica.dominio.paciente.Paciente;

@Entity
@Table(name = "regla")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Regla {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre")
	private String nombre;
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public abstract void evaluar(Paciente pPaciente, Prestacion pPrestacion);
	public abstract void agregarRegla(Regla regla);
	public abstract Iterator<Regla> obtenerIteratorReglas();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
