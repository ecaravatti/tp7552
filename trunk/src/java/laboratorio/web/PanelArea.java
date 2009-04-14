package laboratorio.web;

import java.util.ArrayList;
import java.util.List;

import laboratorio.dominio.excepciones.ExcepcionAreaDeTrabajoInvalida;
import laboratorio.dominio.excepciones.ExcepcionTareaAAsignarInconsistente;
import laboratorio.dominio.excepciones.ExcepcionTareaADesasignarInconsistente;
import laboratorio.dominio.excepciones.ExcepcionTareaAReasignarInconsistente;
import laboratorio.dominio.excepciones.ExcepcionTareaNoAsignable;
import laboratorio.dominio.excepciones.ExcepcionTareaYaAsignada;
import laboratorio.dominio.excepciones.ExcepcionTecnicoNoSupervisado;
import laboratorio.presenter.PanelAreaPresenter;
import laboratorio.presenter.TareasTecnicoPresenter;
import laboratorio.vo.AreaVO;
import laboratorio.vo.TareaVO;
import laboratorio.vo.TecnicoVO;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.dojo.dojodnd.DojoDragContainer;
import org.wicketstuff.dojo.dojodnd.DojoDropContainer;

public class PanelArea extends Panel implements PanelAreaVista {

	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="panelAreaPresenter")
	PanelAreaPresenter presenter;
	
	private Integer areaId;
	private Integer supervisorId;
	private Integer tecnicoOrigenId;
	private Integer tareaId;
	private List<Integer> tecnicosDesplegados;
	private List<TecnicoVO> tecnicos;
	private List<TareaVO> tareasNoAsignadas;
	private List<String> estrategias;
	private String estrategiaSeleccionada;
	private MarkupContainer contenedorOrigen;
	private MarkupContainer seccionTareasNoAsignadas;
	private FeedbackPanel feedback;

	public PanelArea(String id, Integer supervisorId, AreaVO area){
		super(id);
		
		tecnicosDesplegados = new ArrayList<Integer>();
		tecnicos = new ArrayList<TecnicoVO>();
		tareasNoAsignadas = new ArrayList<TareaVO>();
		estrategias = new ArrayList<String>();
		this.supervisorId = supervisorId;
		areaId = area.getId();
		
		presenter.cargarTecnicosArea(PanelArea.this);
		presenter.cargarTareasNoAsignadas(PanelArea.this);
		presenter.cargarEstrategias(PanelArea.this);
		
		feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);
		feedback.setOutputMarkupPlaceholderTag(true);
		add(feedback);
		add(new Label("area", area.getNombre()));
		
		ListView listaTecnicos = crearTecnicosListView();
		add(listaTecnicos);
		
		ListView tareasNoAsignadasListView = crearTareasNoAsignadasListView();
		DojoDropContainer tareasNoAsignadasDrop = crearTareasNoAsignadasDropContainer(tareasNoAsignadasListView);
		crearTareasNoAsignadasContainer(tareasNoAsignadasDrop);
		
		Form form = new Form("formAsignarTareasAutomaticamente");
		add(form);
		
		DropDownChoice ddcEstrategias = crearEstrategiasDropDownChoice();
		form.add(ddcEstrategias);

		Button btnAplicarEstrategia = crearBtnAplicarEstrategia();
		form.add(btnAplicarEstrategia);
		
		this.setOutputMarkupId(true);
	}

	private Button crearBtnAplicarEstrategia() {
		Button btnAplicarEstrategia = new AjaxButton("aplicarEstrategia") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form form) {
				try {
					presenter.asignarTareasAutomaticamente(PanelArea.this);
				} catch (ExcepcionTecnicoNoSupervisado e) {
					info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
				} catch (ExcepcionTareaYaAsignada e) {
					info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
				} catch (ExcepcionAreaDeTrabajoInvalida e) {
					info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
				} catch (ExcepcionTareaNoAsignable e) {
					info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
				} catch (ExcepcionTareaAAsignarInconsistente e) {
					info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
				}
				target.addComponent(PanelArea.this);
			}
		};
		return btnAplicarEstrategia;
	}

	private DropDownChoice crearEstrategiasDropDownChoice() {
		DropDownChoice ddcEstrategias = new DropDownChoice("estrategias", new PropertyModel(PanelArea.this, "estrategiaSeleccionada"), estrategias);
		ddcEstrategias.setRequired(true);
		return ddcEstrategias;
	}

	private ListView crearTareasNoAsignadasListView() {
		ListView tareasNoAsignadasListView = new ListView("tareasNoAsignadas", tareasNoAsignadas) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem listItem) {
				final ListItem item = listItem;
				DojoDragContainer tareaDrag = new DojoDragContainer("tareaNoAsignadaDrag") {
					
					private static final long serialVersionUID = 1L;

					@Override
					public void onDrag(AjaxRequestTarget target) {
						PanelArea.this.tareaId = ((TareaVO)item.getModelObject()).getId();
						PanelArea.this.contenedorOrigen = PanelArea.this.seccionTareasNoAsignadas;
					}
				};
				tareaDrag.add(new Label("nombreTarea", ((TareaVO)listItem.getModelObject()).getDescripcion()));
				listItem.add(tareaDrag);
			}
		};
		return tareasNoAsignadasListView;
	}

	private DojoDropContainer crearTareasNoAsignadasDropContainer(ListView tareasNoAsignadasListView) {
		final DojoDropContainer tareasNoAsignadasDrop = new DojoDropContainer("tareasNoAsignadasDrop"){
			
			private static final long serialVersionUID = 1L;
	
			public void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position) {
				container.onDrag(target);
				try {
					presenter.agregarTareaNoAsignada(PanelArea.this);
				} catch (ExcepcionTecnicoNoSupervisado e) {
					info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
				} catch (ExcepcionTareaADesasignarInconsistente e) {
					info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
				}
				PanelArea.this.tecnicoOrigenId = null;
				PanelArea.this.tareaId = null;
				target.addComponent(seccionTareasNoAsignadas);
				target.addComponent(contenedorOrigen);
				target.addComponent(feedback);
			}
	
		};
		tareasNoAsignadasDrop.add(tareasNoAsignadasListView);
		return tareasNoAsignadasDrop;
	}
	
	private void crearTareasNoAsignadasContainer(WebMarkupContainer tareasNoAsignadasDrop) {
		
		this.seccionTareasNoAsignadas = new WebMarkupContainer("seccionTareasNoAsignadas") {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onBeforeRender() {
				PanelArea.this.presenter.cargarTareasNoAsignadas(PanelArea.this);
				super.onBeforeRender();
			}
		};
		this.seccionTareasNoAsignadas.setOutputMarkupId(true);
		this.seccionTareasNoAsignadas.add(tareasNoAsignadasDrop);
		add(this.seccionTareasNoAsignadas);
	}

	private ListView crearTecnicosListView() {
		ListView listaTecnicos = new ListView("tecnicos", tecnicos) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem listItem) {

				listItem.add(new TecnicoFragment("tecnicoFragment", listItem.getModel(), PanelArea.this));
			}
			
		};
		return listaTecnicos;
	}
	
	
	class TecnicoFragment extends Fragment implements TareasTecnicoVista {
		
		private static final long serialVersionUID = 1L;
		
		Integer tecnicoId;
		List<TareaVO> tareasEnProgreso;
		List<TareaVO> tareasPendientes;		
		List<TareaVO> tareasOtrasAreas;
		List<TipoTarea> tareasDesplegadas;
		
		@SpringBean(name="tareasTecnicoPresenter")
		TareasTecnicoPresenter presenter;
		
		public TecnicoFragment(String id, IModel model, final MarkupContainer container) {
			
			super(id, "fragment", container);
			
			this.tareasEnProgreso = new ArrayList<TareaVO>();
			this.tareasPendientes = new ArrayList<TareaVO>();
			this.tareasOtrasAreas = new ArrayList<TareaVO>();
			this.tareasDesplegadas = new ArrayList<TipoTarea>();
			
			TecnicoVO tecnico = ((TecnicoVO) model.getObject());
			this.tecnicoId = tecnico.getId();
			
			WebMarkupContainer seccionTareas = crearSeccionTareasContainer();
			add(seccionTareas);
			
			AjaxLink imgTecnicoLink = crearImagenTecnicoAjaxLink();
			add(imgTecnicoLink);
			
			AjaxLink nombreTecnico = crearNombreTecnicoAjaxLink(tecnico);
			add(nombreTecnico);
			
			ListView listaTareasEnProgreso = crearTareasEnProgresoListView();
			seccionTareas.add(listaTareasEnProgreso);

			AjaxLink enProgresoImg = crearImagenTareasEnProgresoAjaxLink();
			seccionTareas.add(enProgresoImg);
			
			AjaxLink pendientesImg = crearImagenTareasPendientesAjaxLink();
			seccionTareas.add(pendientesImg);
			
			AjaxLink otraAreasImg = crearImagenTareasOtrasAreasAjaxLink();
			seccionTareas.add(otraAreasImg);
			
			Label enProgresoLabel = crearEnProgresoLabel();
			AjaxLink tareasEnProgreso = crearTareasEnProgresoAjaxLink(enProgresoLabel);
			seccionTareas.add(tareasEnProgreso);
			
			ListView listaTareasPendientes = crearTareasPendientesListView();
			
			Label pendientesLabel = crearPendientesLabel();
			AjaxLink tareasPendientes = crearTareasPendientesAjaxLink(pendientesLabel);
			seccionTareas.add(tareasPendientes);
			
			DojoDropContainer tareasAsignadasDrop = crearTareasAsignadasDropContainer(listaTareasPendientes);
			seccionTareas.add(tareasAsignadasDrop);
			
			ListView listaTareasOtrasAreas = crearTareasOtrasAreasListView();
			seccionTareas.add(listaTareasOtrasAreas);
			
			Label otrasAreasLabel = crearOtrasAreasLabel();
			AjaxLink tareasOtrasAreas = crearTareasOtrasAreasAjaxLink(otrasAreasLabel);
			seccionTareas.add(tareasOtrasAreas);
			
			this.setOutputMarkupId(true);
			this.setOutputMarkupPlaceholderTag(true);
		}

		private AjaxLink crearTareasOtrasAreasAjaxLink(Label otrasAreasLabel) {
			AjaxLink tareasOtrasAreas = new AjaxLink("tareasOtrasAreas") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {	
					if(tareasDesplegadas.contains(TipoTarea.OtraArea))
						tareasDesplegadas.remove(TipoTarea.OtraArea);
					else
						tareasDesplegadas.add(TipoTarea.OtraArea);

					target.addComponent(TecnicoFragment.this);
				}
			};
			tareasOtrasAreas.add(otrasAreasLabel);
			return tareasOtrasAreas;
		}

		private Label crearOtrasAreasLabel() {
			Label otrasAreasLabel = new Label("otrasAreas", new IModel() {
				
				private static final long serialVersionUID = 1L;
				
				public Object getObject() {
					return new StringResourceModel("otrasAreas", PanelArea.this, null, new String[]{String.valueOf(TecnicoFragment.this.tareasOtrasAreas.size())}).getString();
				}
				
				public void setObject(Object object) {}
				public void detach() {}
				
			});
			return otrasAreasLabel;
		}

		private ListView crearTareasOtrasAreasListView() {
			final ListView listaTareasOtrasAreas = new ListView("listaTareasOtrasAreas", TecnicoFragment.this.tareasOtrasAreas) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem listItem) {

					listItem.add(new Label("nombreTarea", ((TareaVO)listItem.getModelObject()).getDescripcion()));
				}
				
				@Override
				public boolean isVisible() {
					return tareasDesplegadas.contains(TipoTarea.OtraArea);
				}
			};
			listaTareasOtrasAreas.setOutputMarkupId(true);
			listaTareasOtrasAreas.setOutputMarkupPlaceholderTag(true);
			return listaTareasOtrasAreas;
		}

		private DojoDropContainer crearTareasAsignadasDropContainer(final ListView listaTareasPendientes) {
			DojoDropContainer tareasAsignadasDrop = new DojoDropContainer("tareasAsignadasDrop"){
				
				private static final long serialVersionUID = 1L;
		
				public void onDrop(AjaxRequestTarget target, DojoDragContainer container, int position) {
					container.onDrag(target);
					try {
						presenter.asignarTarea(TecnicoFragment.this);
					} catch (ExcepcionTecnicoNoSupervisado e) {
						info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
					} catch (ExcepcionTareaYaAsignada e) {
						info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
					} catch (ExcepcionAreaDeTrabajoInvalida e) {
						info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
					} catch (ExcepcionTareaNoAsignable e) {
						info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
					} catch (ExcepcionTareaAAsignarInconsistente e) {
						info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
					} catch (ExcepcionTareaAReasignarInconsistente e) {
						info(new StringResourceModel(e.getClave(), PanelArea.this, null).getString());
					}
					PanelArea.this.tecnicoOrigenId = null;
					PanelArea.this.tareaId = null;
					target.addComponent(TecnicoFragment.this);
					target.addComponent(contenedorOrigen);
					target.addComponent(feedback);
				}
		
			};
			tareasAsignadasDrop.add(listaTareasPendientes);
			return tareasAsignadasDrop;
		}

		private AjaxLink crearTareasPendientesAjaxLink(Label pendientesLabel) {
			AjaxLink tareasPendientes = new AjaxLink("tareasPendientes") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {	
					if(tareasDesplegadas.contains(TipoTarea.Pendiente))
						tareasDesplegadas.remove(TipoTarea.Pendiente);
					else
						tareasDesplegadas.add(TipoTarea.Pendiente);

					target.addComponent(TecnicoFragment.this);
				}
			};
			tareasPendientes.add(pendientesLabel);
			return tareasPendientes;
		}

		private Label crearPendientesLabel() {
			Label pendientesLabel = new Label("pendientes", new IModel() {
				
				private static final long serialVersionUID = 1L;
				
				public Object getObject() {
					return new StringResourceModel("pendientes", PanelArea.this, null, new String[]{String.valueOf(TecnicoFragment.this.tareasPendientes.size())}).getString();
				}
				
				public void setObject(Object object) {}
				public void detach() {}
				
			});
			return pendientesLabel;
		}

		private ListView crearTareasPendientesListView() {
			final ListView listaTareasPendientes = new ListView("listaTareasPendientes", TecnicoFragment.this.tareasPendientes) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem listItem) {
					
					final ListItem item = listItem;
					
					DojoDragContainer tareaDrag = new DojoDragContainer("tareaPendienteDrag") {
						
						private static final long serialVersionUID = 1L;

						@Override
						public void onDrag(AjaxRequestTarget target) {
							PanelArea.this.tecnicoOrigenId = tecnicoId;
							PanelArea.this.tareaId = ((TareaVO)item.getModelObject()).getId();
							PanelArea.this.contenedorOrigen = TecnicoFragment.this;
						}
					};
					tareaDrag.add(new Label("nombreTarea", ((TareaVO)listItem.getModelObject()).getDescripcion()));
					listItem.add(tareaDrag);
				}
				
				@Override
				public boolean isVisible() {
					return tareasDesplegadas.contains(TipoTarea.Pendiente);
				}
			};
			listaTareasPendientes.setOutputMarkupId(true);
			listaTareasPendientes.setOutputMarkupPlaceholderTag(true);
			return listaTareasPendientes;
		}

		private AjaxLink crearTareasEnProgresoAjaxLink(Label enProgresoLabel) {
			AjaxLink tareasEnProgreso = new AjaxLink("tareasEnProgreso") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {	
					if(tareasDesplegadas.contains(TipoTarea.EnProgreso))
						tareasDesplegadas.remove(TipoTarea.EnProgreso);
					else
						tareasDesplegadas.add(TipoTarea.EnProgreso);

					target.addComponent(TecnicoFragment.this);
				}
			};
			
			tareasEnProgreso.add(enProgresoLabel);
			return tareasEnProgreso;
		}

		private Label crearEnProgresoLabel() {
			Label enProgresoLabel = new Label("enProgreso", new IModel() {
				
				private static final long serialVersionUID = 1L;
				
				public Object getObject() {
					return new StringResourceModel("enProgreso", PanelArea.this, null, new String[]{String.valueOf(TecnicoFragment.this.tareasEnProgreso.size())}).getString();
				}
				
				public void setObject(Object object) {}
				public void detach() {}
				
			});
			return enProgresoLabel;
		}

		private ListView crearTareasEnProgresoListView() {
			final ListView listaTareasEnProgreso = new ListView("listaTareasEnProgreso", TecnicoFragment.this.tareasEnProgreso) {
				private static final long serialVersionUID = 1L;

				@Override
				protected void populateItem(ListItem listItem) {

					listItem.add(new Label("nombreTarea", ((TareaVO)listItem.getModelObject()).getDescripcion()));
				}
				
				@Override
				public boolean isVisible() {
					return tareasDesplegadas.contains(TipoTarea.EnProgreso);
				}
			};
			listaTareasEnProgreso.setOutputMarkupId(true);
			listaTareasEnProgreso.setOutputMarkupPlaceholderTag(true);
			return listaTareasEnProgreso;
		}
		
		private AjaxLink crearImagenTecnicoAjaxLink() {
			AjaxLink imgLink = new AjaxLink("imgTecnicoLink") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {	
					if(tecnicosDesplegados.contains(tecnicoId))
						tecnicosDesplegados.remove(tecnicoId);
					else
						tecnicosDesplegados.add(tecnicoId);

					target.addComponent(TecnicoFragment.this);
				}
				
				@Override
				protected void onBeforeRender() {
					if(tecnicosDesplegados.contains(tecnicoId))
						add(new SimpleAttributeModifier("class", "plegable"));
					else
						add(new SimpleAttributeModifier("class", "desplegable"));
					super.onBeforeRender();
				}
			};
			
			return imgLink;
		}
		
		private AjaxLink crearNombreTecnicoAjaxLink(final TecnicoVO tecnico) {
			AjaxLink nombreTecnico = new AjaxLink("tecnico") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {	
					if(tecnicosDesplegados.contains(tecnicoId))
						tecnicosDesplegados.remove(tecnicoId);
					else
						tecnicosDesplegados.add(tecnicoId);

					target.addComponent(TecnicoFragment.this);
				}
			};
			nombreTecnico.add(new Label("nombreTecnico", tecnico.getNombre()));
			return nombreTecnico;
		}
		
		private AjaxLink crearImagenTareasEnProgresoAjaxLink() {
			
			AjaxLink imgTareasEnProgresoLink = new AjaxLink("imgTareasEnProgresoLink") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {	
					if(tareasDesplegadas.contains(TipoTarea.EnProgreso))
						tareasDesplegadas.remove(TipoTarea.EnProgreso);
					else
						tareasDesplegadas.add(TipoTarea.EnProgreso);

					target.addComponent(TecnicoFragment.this);
				}
				
				@Override
				protected void onBeforeRender() {
					if(tareasDesplegadas.contains(TipoTarea.EnProgreso))
						add(new SimpleAttributeModifier("class", "plegable"));
					else
						add(new SimpleAttributeModifier("class", "desplegable"));
					super.onBeforeRender();
				}
			};
			
			return imgTareasEnProgresoLink;
		}
		
		private AjaxLink crearImagenTareasPendientesAjaxLink() {
			
			AjaxLink imgTareasPendientesLink = new AjaxLink("imgTareasPendientesLink") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {	
					if(tareasDesplegadas.contains(TipoTarea.Pendiente))
						tareasDesplegadas.remove(TipoTarea.Pendiente);
					else
						tareasDesplegadas.add(TipoTarea.Pendiente);

					target.addComponent(TecnicoFragment.this);
				}
				
				@Override
				protected void onBeforeRender() {
					if(tareasDesplegadas.contains(TipoTarea.Pendiente))
						add(new SimpleAttributeModifier("class", "plegable"));
					else
						add(new SimpleAttributeModifier("class", "desplegable"));
					super.onBeforeRender();
				}
			};
			
			return imgTareasPendientesLink;
		}

		private AjaxLink crearImagenTareasOtrasAreasAjaxLink() {
			
			AjaxLink imgTareasOtrasAreasLink = new AjaxLink("imgTareasOtrasAreasLink") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {	
					if(tareasDesplegadas.contains(TipoTarea.OtraArea))
						tareasDesplegadas.remove(TipoTarea.OtraArea);
					else
						tareasDesplegadas.add(TipoTarea.OtraArea);

					target.addComponent(TecnicoFragment.this);
				}
				
				@Override
				protected void onBeforeRender() {
					if(tareasDesplegadas.contains(TipoTarea.OtraArea))
						add(new SimpleAttributeModifier("class", "plegable"));
					else
						add(new SimpleAttributeModifier("class", "desplegable"));
					super.onBeforeRender();
				}
			};
			
			return imgTareasOtrasAreasLink;
		}

		private WebMarkupContainer crearSeccionTareasContainer() {
			final WebMarkupContainer seccionTareas = new WebMarkupContainer("seccionTareas") {
				
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isVisible() {
					return tecnicosDesplegados.contains(tecnicoId);
				}
			};
			seccionTareas.setOutputMarkupId(true);
			seccionTareas.setOutputMarkupPlaceholderTag(true);
			return seccionTareas;
		}
		
		public Integer getAreaId() {
			return PanelArea.this.areaId;
		}
		
		public Integer getTecnicoId() {
			return tecnicoId;
		}

		public List<TareaVO> getTareasEnProgreso() {
			return tareasEnProgreso;
		}

		public List<TareaVO> getTareasPendientes() {
			return tareasPendientes;
		}

		public List<TareaVO> getTareasOtrasAreas() {
			return tareasOtrasAreas;
		}

		public Integer getSupervisorId() {
			return PanelArea.this.supervisorId;
		}

		public Integer getTareaId() {
			return PanelArea.this.tareaId;
		}

		public Integer getTecnicoOrigenId() {
			return PanelArea.this.tecnicoOrigenId;
		}
		
		@Override
		protected void onBeforeRender() {
			presenter.inicializar(TecnicoFragment.this);
			super.onBeforeRender();
		}

	}
	
	private enum TipoTarea {
		EnProgreso,
		Pendiente,
		OtraArea;
	}
	
	@Override
	protected void onBeforeRender() {
		presenter.cargarTareasNoAsignadas(PanelArea.this);
		super.onBeforeRender();
	}

	public Integer getAreaId() {
		return areaId;
	}
	
	public Integer getSupervisorId() {
		return supervisorId;
	}
	
	public Integer getTareaId() {
		return tareaId;
	}

	public Integer getTecnicoId() {
		return tecnicoOrigenId;
	}

	public List<TareaVO> getTareasNoAsignadas() {
		return tareasNoAsignadas;
	}

	public List<TecnicoVO> getTecnicos() {
		return tecnicos;
	}
	
	public String getEstrategiaSeleccionada() {
		return estrategiaSeleccionada;
	}

	public void setEstrategiaSeleccionada(String estrategiaSeleccionada) {
		this.estrategiaSeleccionada = estrategiaSeleccionada;
	}

	public List<String> getEstrategias() {
		return estrategias;
	}

}
