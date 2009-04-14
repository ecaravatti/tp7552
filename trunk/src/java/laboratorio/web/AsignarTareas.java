package laboratorio.web;

import general.web.PaginaBase;

import java.util.ArrayList;
import java.util.List;

import laboratorio.presenter.AsignarTareaPresenter;
import laboratorio.vo.AreaVO;
import laboratorio.vo.SupervisorVO;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class AsignarTareas extends PaginaBase implements AsignarTareaVista {

	@SpringBean(name = "asignarTareaPresenter")
	private AsignarTareaPresenter presenter;
	
	private List<SupervisorVO> supervisores = new ArrayList<SupervisorVO>();
	private List<AreaVO> areas = new ArrayList<AreaVO>();
	private SupervisorVO supervisorSeleccionado;
	private AreaVO areaSeleccionada;
	
	public AsignarTareas() {
		presenter.setVista(this);
		presenter.inicializar();
	}
	
	public void inicializar() {
		add(new FormAsignarTareas("formAsignarTareas"));
		add(new WebMarkupContainer("panelArea"));
		add(new FeedbackPanel("feedback"));
	}
	
	private class FormAsignarTareas extends Form {
		
		private static final long serialVersionUID = -3772813564643434418L;

		public FormAsignarTareas(String idForm){
			super(idForm);
			
			this.setOutputMarkupId(true);

			final DropDownChoice ddcAreas = crearAreasDropDownChoice();
			add(ddcAreas);

			DropDownChoice ddcSupervisores = crearSupervisoresDropDownChoice(ddcAreas);
			add(ddcSupervisores);
			
			Button btnMostrarTecnicos = crearButtonMostrarTecnicos(); 
			add(btnMostrarTecnicos);
		}

		private Button crearButtonMostrarTecnicos() {
			Button btnMostrarTecnicos = new Button("mostrarTecnicos") {
				
				private static final long serialVersionUID = 5736158426032420878L;

				@Override
				public void onSubmit() {
					final PanelArea panelArea = new PanelArea("panelArea", supervisorSeleccionado.getId(), areaSeleccionada) {
						
						private static final long serialVersionUID = 1L;

						@Override
						public boolean isVisible() {
							return areaSeleccionada != null;
						}
					};
					AsignarTareas.this.replace(panelArea);
				}
			};
			return btnMostrarTecnicos;
		}

		private DropDownChoice crearSupervisoresDropDownChoice(final DropDownChoice ddcAreas) {
			DropDownChoice ddcSupervisores = new DropDownChoice("supervisores", new PropertyModel(AsignarTareas.this, "supervisorSeleccionado"), supervisores, new IChoiceRenderer() {

				private static final long serialVersionUID = 1L;

				public Object getDisplayValue(Object arg0) {
					return ((SupervisorVO)arg0).getNombre();
				}

				public String getIdValue(Object arg0, int arg1) {
					return ((SupervisorVO)arg0).getId().toString();
				}
			});
			ddcSupervisores.setRequired(true);
			
			ddcSupervisores.add(new AjaxFormComponentUpdatingBehavior("onchange"){

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					presenter.actualizarAreas();
					ddcAreas.setChoices(areas);
					target.addComponent(ddcAreas);
				}
			});
			return ddcSupervisores;
		}

		private DropDownChoice crearAreasDropDownChoice() {
			final DropDownChoice ddcAreas = new DropDownChoice("areas", new PropertyModel(AsignarTareas.this, "areaSeleccionada"), areas, new IChoiceRenderer() {

				private static final long serialVersionUID = 1L;

				public Object getDisplayValue(Object arg0) {
					return ((AreaVO)arg0).getNombre();
				}

				public String getIdValue(Object arg0, int arg1) {
					return ((AreaVO)arg0).getId().toString();
				}
			});
			ddcAreas.setRequired(true);
			ddcAreas.setOutputMarkupId(true);
			return ddcAreas;
		}
	}
	
	public void setAreas(List<AreaVO> areas) {
		this.areas = areas;
	}

	public void setSupervisores(List<SupervisorVO> supervisores) {
		this.supervisores = supervisores;
	}
	
	public SupervisorVO getSupervisorSeleccionado() {
		return supervisorSeleccionado;
	}

	public void setSupervisorSeleccionado(SupervisorVO supervisorSeleccionado) {
		this.supervisorSeleccionado = supervisorSeleccionado;
	}
	
	public AreaVO getAreaSeleccionada() {
		return areaSeleccionada;
	}

	public void setAreaSeleccionada(AreaVO areaSeleccionada) {
		this.areaSeleccionada = areaSeleccionada;
	}

}
