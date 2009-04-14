package clinica.dominio.reglas.comportamientos;

import java.util.Iterator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import clinica.dominio.Prestacion;
import clinica.dominio.paciente.Paciente;
import clinica.dominio.reglas.Comportamiento;
import clinica.dominio.reglas.Regla;

@Entity
@DiscriminatorValue("AND")
public class ComportamientoAND extends Comportamiento {

	public void evaluar(Paciente paciente, Prestacion prestacion, Iterator<Regla> reglasIterator) {
		while (reglasIterator.hasNext()) {
			reglasIterator.next().evaluar(paciente, prestacion); //Si alguna arroja excepción, entonces ya el AND no se cumple.
		}
	}

	@Override
	public String getConector() {
		return "AND";
	}

}
