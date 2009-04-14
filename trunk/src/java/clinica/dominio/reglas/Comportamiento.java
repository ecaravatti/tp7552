package clinica.dominio.reglas;


import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
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
@Table(name = "comportamiento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name = "descripcion",
    discriminatorType = DiscriminatorType.STRING
)
public abstract class Comportamiento {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public abstract void evaluar(Paciente pPaciente, Prestacion pPrestacion, Iterator<Regla> reglasIterator);
	
	/**
	 * Igual para todos los comportamientos, excepto el conector a usar, que utiliza el método getConector() que implementan las subclases.
	 */
	public String toString(Iterator<Regla> reglasIterator) {
		StringBuffer buffer = new StringBuffer();
		while (reglasIterator.hasNext()) {
			buffer.append(reglasIterator.next());
			if (reglasIterator.hasNext()) buffer.append(" " + getConector() + " ");
		}
		return buffer.toString();
	}
	
	/**
	 * Son las subclases quienes saben como escribir el conector correspondiente
	 */
	public abstract String getConector();
	
}
