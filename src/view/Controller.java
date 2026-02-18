package view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {
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
				//logica para captura dos dados e envio ao repository
				dialogController.fecharJanela();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
