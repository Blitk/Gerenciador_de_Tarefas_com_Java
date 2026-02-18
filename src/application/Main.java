package application;

import repository.CSVTarefaRepository;
import repository.TarefaRepository;
import service.TarefaService;

public class Main {
	
	public static void main(String[] args) {
		TarefaRepository repository = new CSVTarefaRepository();
		TarefaService service = new TarefaService(repository);
	
	}
	
}
