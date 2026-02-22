package application;

import javafx.application.Application;
import repository.CSVTarefaRepository;
import repository.TarefaRepository;
import service.TarefaService;
import view.Controller;

public class Main {
	
	public static void main(String[] args) {
		TarefaRepository repository = new CSVTarefaRepository();
		TarefaService service = new TarefaService(repository);
		Controller controller = new Controller(service);
		
	}
	
}
