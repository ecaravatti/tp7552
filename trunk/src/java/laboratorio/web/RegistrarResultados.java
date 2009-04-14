package laboratorio.web;

import general.web.PaginaBase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laboratorio.presenter.RegistrarResultadosPresenter;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import clinica.vo.PracticaVO;

public class RegistrarResultados extends PaginaBase implements RegistrarResultadosVista {
	
	@SpringBean(name = "registrarResultadosPresenter")
	private RegistrarResultadosPresenter presenter;
	
	private Integer cantidadPracticas;
	
	private Integer practicaSeleccionada;
	
	private List<PracticaVO> practicas;
	
	public RegistrarResultados() {
		this.presenter.setVista(this);
		this.presenter.inicializar();
	}
	
public void inicializar() {
		
		List<IColumn> columns = new ArrayList<IColumn>();
		columns.add(new PropertyColumn(new ResourceModel("ordenMedica"), "ordenMedicaCol", "ordenMedica"));
		columns.add(new PropertyColumn(new ResourceModel("paciente"), "pacienteCol", "paciente"));
		columns.add(new PropertyColumn(new ResourceModel("nombre"), "nombreCol", "nombre"));
		columns.add(new AbstractColumn(new ResourceModel("acciones")) {

			private static final long serialVersionUID = -3790714477771946452L;

			public void populateItem(Item item, String id, IModel model) {
				item.add(new ActionPanel(id, model));
			}
		});

		DefaultDataTable table = new DefaultDataTable("tablaPracticas", columns, new ProveedorDatosPractica(), PaginaBase.PAGINACION_TABLA);
		table.setOutputMarkupId(true);
		this.add(table);
	}
	
	class ActionPanel extends Panel {

		private static final long serialVersionUID = -1030969422197162162L;

		public ActionPanel(String id, IModel model) {
			super(id, model);
			add(new Link("verResultados", model) {

				private static final long serialVersionUID = -8212258754342165735L;

				public void onClick() {
					PracticaVO practica = (PracticaVO) ActionPanel.this.getModelObject();
					setResponsePage(new ResultadoPractica(practica.getId(), practica.getNombre()));
				}
			});
		}
	}
	
	class ProveedorDatosPractica extends SortableDataProvider {

		private static final long serialVersionUID = 3336774655408736100L;

		public ProveedorDatosPractica() {
			super();
			setSort("nombreCol", true);
		}

		/**
		 * Devuelve un iterador a la lista de PracticaVOs
		 */
		public Iterator<PracticaVO> iterator(int from, int to) {
			// El presenter actualiza la lista de practicas.
			presenter.buscar(getSort().getProperty(), from, to, getSort().isAscending());
			return practicas.iterator();
		}

		/**
		 * Devuelve el modelo de un AreaVO
		 */
		public IModel model(Object o) {
			return new Model((Serializable) o);
		}

		/**
		 * Devuelve la cantidad de areas.
		 */
		public int size() {
			// El presenter actualiza la cantidad de areas.
			presenter.actualizarCantidad();
			return cantidadPracticas;
		}
	}

	public Integer getCantidadPracticas() {
		return cantidadPracticas;
	}

	public Integer getPracticaSeleccionada() {
		return practicaSeleccionada;
	}

	public List<PracticaVO> getPracticas() {
		return practicas;
	}

	public void setCantidadPracticas(Integer cantidadPracticas) {
		this.cantidadPracticas = cantidadPracticas;
	}

	public void setPracticas(List<PracticaVO> practicas) {
		this.practicas = practicas;
	}

	public void setPresenter(RegistrarResultadosPresenter presenter) {
		this.presenter = presenter;
	}
}
