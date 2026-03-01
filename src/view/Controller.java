package view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Tarefa;
import javafx.scene.control.ListView; 
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Prioridade;
import model.Status;
import repository.CSVTarefaRepository;
import repository.TarefaRepository;
import service.TarefaService;



public class Controller extends Application{
	
	private TarefaService service;

	private List<String> dadosFiltro = new ArrayList<>(); 
	
	
	public void startService() {
		TarefaRepository repository = new CSVTarefaRepository();
		TarefaService service = new TarefaService(repository);
		this.service = service;
	}
	
	private Long iDEmFoco;
	
	@FXML
	private TextField campoBusca;
	
	@FXML
	private Button btnFiltro;
	
	@FXML
	private CheckBox filtroCheck;
	
	@FXML
	private ListView<String> listaTarefas;
	
	@FXML
	private Button btnNovo;
	
	@FXML
	private TextField campoTitulo;
	
	@FXML
	private TextArea campoDescricao;
	
	@FXML
	private DatePicker campoDataTermino;
	
	@FXML
	private DatePicker campoDataCriacao;
	
	@FXML
	private ComboBox<Prioridade> campoPrioridade;
	
	@FXML
	private ComboBox<Status> campoStatus;
	
	@FXML
	private void atualizarTarefa() {
		Tarefa tf = new Tarefa();
		tf.setId(iDEmFoco);
		tf.setTitulo(campoTitulo.getText());
		tf.setDescricao(campoDescricao.getText());
		tf.setPrioridade(campoPrioridade.getValue());
		tf.setStatus(campoStatus.getValue());
		tf.setDataCriacao(campoDataCriacao.getValue());
		tf.setDataTermino(campoDataTermino.getValue());
		
		this.service.alterarTarefa(tf);
		this.avisos("Sucesso", "Sucesso", "Tarefa atualizada com sucesso");
		this.atualizarAposAcao();
		this.limparCampos();
	}
	
	@FXML
	private void salvarTarefa() {
		Tarefa tf = new Tarefa();
		tf.setId(iDEmFoco);
		tf.setTitulo(campoTitulo.getText());
		tf.setDescricao(campoDescricao.getText());
		tf.setPrioridade(campoPrioridade.getValue());
		tf.setStatus(campoStatus.getValue());
		tf.setDataCriacao(campoDataCriacao.getValue());
		tf.setDataTermino(campoDataTermino.getValue());
		
		this.service.salvarTarefa(tf);
		this.avisos("Sucesso", "Sucesso", "Tarefa salva com sucesso");
		this.atualizarAposAcao();
		this.limparCampos();
		
	}
	
	@FXML
	private void excluirTarefa() {
		if(campoTitulo.getText() == "") {
			 this.avisos("Erro", "Erro", "Nenhuma Tarefa selecionada!");
		}
		if(service.listarTodos().getLast().getId() < this.iDEmFoco) {
			this.avisos("Erro", "Erro", "Não é possivel deletar a tarefa que não está salva!");
		}else {
			this.service.deletarTarefa(this.iDEmFoco);
			this.avisos("Sucesso", "Sucesso", "Tarefa Deletada com Sucesso");
			this.atualizarAposAcao();
			this.limparCampos();
		}
	}
	
	@FXML
	private void novaTarefa() {
		this.iDEmFoco = this.getLastIdNumber()+1;
		this.limparCampos();
	
	}
	
	@FXML
	private void filtrarTarefas() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("FiltroDialog.fxml")
			);
			Parent root = loader.load();
			
			Stage stage = new Stage();
			stage.setTitle("Filtros");
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			
			stage.showAndWait();
			FiltroDialogController dialogController = loader.getController();
			if(dialogController.isConfirmado()) {
				this.dadosFiltro.clear();
			    this.dadosFiltro.addAll(dialogController.retornaList());
			    this.filtroCheck.setSelected(true);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void aplicarFiltro() {
		switch(this.dadosFiltro.get(0)) {
			case "Status":
				this.atualizarAposAcao(this.service.procurarPorStatus(Status.valueOf(dadosFiltro.get(1))));
				break;
			case "Prioridade":
				this.atualizarAposAcao(this.service.procurarPorPrioridade(Prioridade.valueOf(dadosFiltro.get(2))));
				break;
			case "Data criação":
				this.atualizarAposAcao(this.service.procurarPorDataCriacao(LocalDate.parse(dadosFiltro.get(3))));
				break;
			case "Data término":
				this.atualizarAposAcao(this.service.procurarPorDataTermino(LocalDate.parse(dadosFiltro.get(4))));
				break;
			default:
				break;
		}
	}
	
	@FXML
	public void initialize() {
		this.startService();	
		this.atualizarAposAcao();
		this.limparCampos();
		
		this.campoBusca.textProperty().addListener((observable, oldValue, newValue) -> {
			if(this.campoBusca.getText().isBlank()) {
				this.atualizarAposAcao();
			}else {
				this.atualizarAposAcao(service.procurarPorTituloAproximado(newValue));
			}
		});
		
		this.filtroCheck.selectedProperty().addListener((observable, oldValue, newValue) -> {
			if(this.filtroCheck.isSelected()) {
				this.aplicarFiltro();
			}else {
				this.dadosFiltro.clear();
				this.filtroCheck.setSelected(false);
				this.atualizarAposAcao();
			}
		});
		
	}
	
	public Controller() {
		
//		launch();
	}
	
	

	@Override
	public void start(Stage stage) throws Exception { 
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TarefaView.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root, 1000,600);
		stage.setTitle("Controle de Tarefas");
		stage.setScene(scene);
		stage.show();
		
	}
	
	private Long getLastIdNumber() {
		if(this.service.listarTodos().isEmpty()) {
			return 0L;
		}else {
			return this.service.listarTodos().getLast().getId();
		}
	}
		
	
	private void avisos(String titulo, String header, String texto) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(texto);
        alert.showAndWait();
	}
	
	private void limparCampos() {
		this.campoTitulo.clear();
		this.campoDescricao.clear();
		this.campoPrioridade.setItems(FXCollections.observableArrayList(Prioridade.values()));
        this.campoStatus.setItems(FXCollections.observableArrayList(Status.values()));
		this.campoDataCriacao.setValue(LocalDate.now());
		this.campoDataTermino.setValue(LocalDate.now());
	}
	
	private void atualizarAposAcao() {
		if(service.listarTodos().isEmpty()) {
			
		}else{
			ObservableList<String> nomeTarefas = FXCollections.observableArrayList(
					service.listarTodos()
					.stream()
					.map(t-> t.getTitulo())
					.toList()
					);
			listaTarefas.setItems(nomeTarefas);
			listaTarefas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			       if (newValue != null) {
			            Tarefa tf = service.listarTodos()
			            		.stream()
			            		.filter(t-> t.getTitulo() == newValue)
			            		.findFirst()
			            		.orElseThrow();
			            this.iDEmFoco = tf.getId();
			            this.campoTitulo.setText(tf.getTitulo());
			            this.campoDescricao.setText(tf.getDescricao());
			            campoPrioridade.setItems(FXCollections.observableArrayList(Prioridade.values()));
			            this.campoPrioridade.setValue(tf.getPrioridade());
			            campoStatus.setItems(FXCollections.observableArrayList(Status.values()));
			            this.campoStatus.setValue(tf.getStatus());
			            this.campoDataCriacao.setValue(tf.getDataCriacao());
			            this.campoDataTermino.setValue(tf.getDataTermino());
			        }
			    });
		}
	}
	
	private void atualizarAposAcao(List<Tarefa> lista) {
		ObservableList<String> nomeTarefas = FXCollections.observableArrayList(
				lista.stream()
				.map(t-> t.getTitulo())
				.toList()
				);
		
		listaTarefas.setItems(nomeTarefas);
		listaTarefas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		       if (newValue != null) {
		            Tarefa tf = service.listarTodos()
		            		.stream()
		            		.filter(t-> t.getTitulo() == newValue)
		            		.findFirst()
		            		.orElseThrow();
		            this.iDEmFoco = tf.getId();
		            this.campoTitulo.setText(tf.getTitulo());
		            this.campoDescricao.setText(tf.getDescricao());
		            campoPrioridade.setItems(FXCollections.observableArrayList(Prioridade.values()));
		            this.campoPrioridade.setValue(tf.getPrioridade());
		            campoStatus.setItems(FXCollections.observableArrayList(Status.values()));
		            this.campoStatus.setValue(tf.getStatus());
		            this.campoDataCriacao.setValue(tf.getDataCriacao());
		            this.campoDataTermino.setValue(tf.getDataTermino());
		        }
		    });
		
	}
	
	public static void main(String[] args) {
		launch();
	}
}
