package service;

import java.time.LocalDate;
import java.util.List;

import model.Prioridade;
import model.Status;
import model.Tarefa;
import repository.TarefaRepository;

public class TarefaService {
	
	private final TarefaRepository repository;
	
	public TarefaService(TarefaRepository repository) {
		this.repository = repository;
	}
	
	public void salvarTarefa(Tarefa tarefa) {
		repository.save(tarefa);
	}
	
	public List<Tarefa> listarTodos(){
		return repository.findAll();
	}
	
	public void  deletarTarefa(Long id) {
		repository.delete(id);
	}
	
	public void alterarTarefa(Tarefa tarefa) {
		repository.update(tarefa);
	}
	
	public List<Tarefa> procurarPorStatus(Status status) {
		return repository.findByStatus(status);
	}
	
	public List<Tarefa> procurarPorPrioridade(Prioridade prioridade){
		return repository.findByPrioridade(prioridade);
	}
	
	public List<Tarefa> procurarPorTituloAproximado(String tituloAprox){
		return repository.findByTituloAproximado(tituloAprox);
	}
	
	public List<Tarefa> procurarPorDataCriacao(LocalDate dataCriacao){
		return repository.findByDataCriacao(dataCriacao);
	}
	
	public List<Tarefa> procurarPorDataTermino(LocalDate dataTermino){
		return repository.findByDataTermino(dataTermino);
	}
	
}
