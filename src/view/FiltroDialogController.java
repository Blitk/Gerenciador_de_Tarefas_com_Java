package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiltroDialogController {
	
	@FXML
    private ToggleGroup filtros; 

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
	
	private String filtroSelecionado;
	
	@FXML
	private void initialize() {
		filtros.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (new_toggle != null) {
                    RadioButton selected = (RadioButton) new_toggle;
                    filtroSelecionado = selected.getText();
                }
            }
        });
		
	}
	
	@FXML
	private void filtrarBusca() {
		
	    statusSelecionado = cbStatus.getValue();
	    prioridadeSelecionada = cbPrioridade.getValue();
	    dataCriacaoSelecionada = filtroDataCriacao.getValue();
	    dataTerminoSelecionada = filtroDataTermino.getValue();

	    if (verificaCampoObrigatorio()) {
	        this.confirmado = true;
	        fecharJanela(); 
	    } else {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Campo Vazio");
	        alert.setHeaderText(null);
	        alert.setContentText("Por favor, preencha o campo obrigatório: " + (filtroSelecionado != null ? filtroSelecionado : "Selecione um filtro"));
	        alert.showAndWait();
	    }

		
	}
	
	private Boolean verificaCampoObrigatorio() {
	    if (filtroSelecionado == null) return false;

	    if (filtroSelecionado.equals("Status")) {
	        return statusSelecionado != null && !statusSelecionado.isBlank();
	    }
	    if (filtroSelecionado.equals("Prioridade")) {
	        return prioridadeSelecionada != null && !prioridadeSelecionada.isBlank();
	    }
	    if (filtroSelecionado.equals("Data criação")) {
	        return dataCriacaoSelecionada != null;
	    }
	    if (filtroSelecionado.equals("Data término")) {
	        return dataTerminoSelecionada != null;
	    }
	    
	    return false;
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
		lista.add(this.filtroSelecionado);
		lista.add(this.getStatusSelecionado());
		lista.add(this.getPrioridadeSelecionada());
		if (this.getDataCriacaoSelecionada() != null) {
			lista.add(this.getDataCriacaoSelecionada().toString());
		}else {
			lista.add(null);
		}
		if (this.dataTerminoSelecionada != null) {
			lista.add(this.getDataTerminoSelecionada().toString());
		}else {
			lista.add(null);
		}
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
