package application;

import repository.CSVTarefaRepository;
import repository.TarefaRepository;
import service.TarefaService;

public class Main {
	public TarefaRepository repository = new CSVTarefaRepository();
	public TarefaService service = new TarefaService(repository);
	
}
