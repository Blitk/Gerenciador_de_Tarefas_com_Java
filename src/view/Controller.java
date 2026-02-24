package view;

import java.io.IOException;
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
import javafx.scene.control.Button;
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
	private void salvarTarefa() {
		
	}
	
	@FXML
	private void excluirTarefa() {
		
	}
	
	@FXML
	private void novaTarefa() {
		
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
				this.dadosFiltro.addAll(dialogController.retornaList());
				dialogController.fecharJanela();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		this.startService();	
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
	
	public static void main(String[] args) {
		launch();
	}
}
