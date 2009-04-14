package clinica.dominio.reglas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import clinica.dominio.Prestacion;
import clinica.dominio.excepciones.ExcepcionReglaCompuestaSinComportamiento;
import clinica.dominio.paciente.Paciente;

@Entity
@Table(name = "regla_compuesta")
public class ReglaCompuesta extends Regla {
	
	@OneToMany
	@Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	@LazyCollection(value = LazyCollectionOption.FALSE)
	@JoinTable(name = "regla_compuesta_regla", joinColumns = @JoinColumn(name = "regla_compuesta_id"), inverseJoinColumns = @JoinColumn(name = "regla_id"))
	private List<Regla> lReglas = new ArrayList<Regla>();
	
	@OneToOne
	@Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN})
	@JoinColumn(name = "comportamiento_id")
	@ForeignKey(name = "regla_compuesta_comportamiento_fk")
	private Comportamiento comportamiento;
	
	protected ReglaCompuesta(){
	}
	
	public ReglaCompuesta(Comportamiento comportamiento) {
		super();
		this.comportamiento = comportamiento; 
	}
	
	@Override
	public void agregarRegla(Regla regla) {
		if (regla != null) lReglas.add(regla);
	}
	
	@Override
	public Iterator<Regla> obtenerIteratorReglas() {
		return lReglas.iterator();
	}

	@Override
	public void evaluar(Paciente paciente, Prestacion prestacion) {
		if (comportamiento == null) throw new ExcepcionReglaCompuestaSinComportamiento();
		comportamiento.evaluar(paciente, prestacion, obtenerIteratorReglas());
	}
	
	public Comportamiento getComportamiento() {
		return comportamiento;
	}

	public void setComportamiento(Comportamiento comportamiento) {
		this.comportamiento = comportamiento;
	}

	public List<Regla> getLReglas() {
		return lReglas;
	}

	public void setLReglas(List<Regla> reglas) {
		lReglas = reglas;
	}

	@Override
	public String toString() {
		return "(" + comportamiento.toString(obtenerIteratorReglas()) + ")";
	}
	
}
