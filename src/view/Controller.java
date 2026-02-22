package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.ListView;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Prioridade;
import model.Status;
import service.TarefaService;



public class Controller extends Application{

	private List<String> dadosFiltro = new ArrayList<>(); 
	
	private TarefaService service;
	
	@FXML
	private TextField campoBusca;
	
	@FXML
	private Button btnFiltro;
	
	@FXML
	private ListView listaTarefas;
	
	@FXML
	private Button btnNovo;
	
	@FXML
	private TextField campoTitulo;
	
	@FXML
	private TextField campoDescricao;
	
	@FXML
	private DatePicker campoDataTermino;
	
	@FXML
	private DatePicker campoDataCriacao;
	
	@FXML
	private ComboBox<String> campoPrioridade;
	
	@FXML
	private ComboBox<String> campoStatus;
	
	@FXML
	private void salvarTarefa() {
		
	}
	
	@FXML
	private void excluirTarefa() {
		
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
	
	public Controller(TarefaService service) {
		this.service = service;
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TarefaView.fxml"));
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root, 600,400);
		stage.setTitle("Controle de Tarefas");
		stage.setScene(scene);
		stage.show();
		
	}
}
