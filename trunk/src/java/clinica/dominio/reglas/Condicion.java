package clinica.dominio.reglas;
import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "condicion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Condicion extends Regla {
	
	@Override
	public void agregarRegla(Regla regla) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Iterator<Regla> obtenerIteratorReglas() {
		return new ArrayList<Regla>().iterator(); //Devuelvo un iterator vacío
	}
	
}
