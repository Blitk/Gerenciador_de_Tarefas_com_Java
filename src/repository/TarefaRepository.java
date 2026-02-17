package repository;

import java.util.List;
import java.time.LocalDate;

import model.Prioridade;
import model.Status;
import model.Tarefa;

public interface TarefaRepository extends Repository <Tarefa, Long> {
	
	List<Tarefa> findByStatus(Status status);
	List<Tarefa> findByPrioridade(Prioridade prioridade);
	List<Tarefa> findByDataCriacao(LocalDate dataCriacao);
	List<Tarefa> findByDataTermino(LocalDate dataTermino);
	List<Tarefa> findByTituloAproximado(String tituloAprox);
	
}
