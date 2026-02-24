package view;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiltroDialogController {
	
	@FXML
	private ComboBox<String> cbStatus;
	
	@FXML
	private ComboBox<String> cbPrioridade;
	
	@FXML
	private DatePicker filtroDataCriacao;
	
	@FXML
	private DatePicker filtroDataTermino;
	
	private String statusSelecionado;
	private String prioridadeSelecionada;
	private LocalDate dataCriacaoSelecionada;
	private LocalDate dataTerminoSelecionada;
	
	private boolean confirmado = false;
	
	@FXML
	private void inicializar() {
		
	}
	
	@FXML
	private void filtrarBusca() {
		statusSelecionado = cbStatus.getValue();
		prioridadeSelecionada = cbPrioridade.getValue();
		dataCriacaoSelecionada = filtroDataCriacao.getValue();
		dataTerminoSelecionada = filtroDataTermino.getValue();
		this.confirmado = true;
	}
	
	public void fecharJanela() {
		Stage stage = (Stage) cbStatus.getScene().getWindow();
		stage.close();
	}
	
	public boolean isConfirmado() {
		return this.confirmado;
	}
	
	public List<String> retornaList(){
		List<String> lista = new ArrayList<>();
		lista.add(this.getStatusSelecionado());
		lista.add(this.getPrioridadeSelecionada());
		lista.add(this.getDataCriacaoSelecionada().toString());
		lista.add(this.getDataTerminoSelecionada().toString());
		return lista;
	}

	public ComboBox<String> getCbStatus() {
		return cbStatus;
	}

	public ComboBox<String> getCbPrioridade() {
		return cbPrioridade;
	}

	public DatePicker getFiltroDataCriacao() {
		return filtroDataCriacao;
	}

	public DatePicker getFiltroDataTermino() {
		return filtroDataTermino;
	}

	public String getStatusSelecionado() {
		return statusSelecionado;
	}

	public String getPrioridadeSelecionada() {
		return prioridadeSelecionada;
	}

	public LocalDate getDataCriacaoSelecionada() {
		return dataCriacaoSelecionada;
	}

	public LocalDate getDataTerminoSelecionada() {
		return dataTerminoSelecionada;
	}
	
	
}
