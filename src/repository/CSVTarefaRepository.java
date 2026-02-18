package repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import model.Prioridade;
import model.Status;
import model.Tarefa;

public class CSVTarefaRepository implements TarefaRepository{
	
	private String path = "data/data.csv";

	private List<Tarefa> tarefas = new ArrayList<>();
	
	public CSVTarefaRepository() {
		carregarCSV();
	}
	
	private void carregarCSV() {
		File arquivo = new File(this.path);
		if(arquivo.exists()) {
			try(BufferedReader br = new BufferedReader(new FileReader(this.path))){
				String linha;
				br.readLine(); //pula o cabeçalho
				this.tarefas.clear();
				while((linha = br.readLine()) != null) {
					String[] dados = linha.split(",");
					Tarefa tarefa = new Tarefa(
							Long.parseLong(dados[0]),
							dados[1],
							dados[2],
							Prioridade.valueOf(dados[3]),
							Status.valueOf(dados[4]),
							LocalDate.parse(dados[4]),
							LocalDate.parse(dados[5]));
					tarefas.add(tarefa);
				}
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		}else {
			this.criarCSV();
		}
	}
	
	private void criarCSV() {
		try(FileWriter writer = new FileWriter(this.path)){
			writer.append("ID, Titulo, Descricao, Prioridade, Status, DataCriacao, DataTermino\n");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void escreverCSV() {
		try(FileWriter writer = new FileWriter(this.path)){
			writer.append("ID, Titulo, Descricao, Prioridade, Status, DataCriacao, DataTermino\n");
			for(Tarefa tarefa : this.tarefas) {
				writer.append(tarefa.toCSV());
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Tarefa> findAll() {
		
		return new ArrayList<>(tarefas);
	}

	@Override
	public Optional<Tarefa> findByID(Long id) {
		
		return this.tarefas.stream()
				.filter(t -> t.getId().equals(id))
				.findFirst();
	}

	@Override
	public void save(Tarefa entity) {
		this.tarefas.add(entity);
		escreverCSV();
		
	}

	@Override
	public void update(Tarefa entity) {
		delete(entity.getId());
		tarefas.add(entity);
		escreverCSV();
		
	}

	@Override
	public void delete(Long id) {
		tarefas.removeIf(t -> t.getId().equals(id));
		escreverCSV();
		
	}

	@Override
	public List<Tarefa> findByStatus(Status status) {
		return tarefas.stream()
				.filter(t -> t.getStatus() == status)
				.toList();
	}

	@Override
	public List<Tarefa> findByPrioridade(Prioridade prioridade) {
		
		return tarefas.stream()
				.filter(t -> t.getPrioridade() == prioridade)
				.toList();
	}

	@Override
	public List<Tarefa> findByDataCriacao(LocalDate dataCriacao) {
		
		return tarefas.stream()
				.filter(t -> t.getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) == dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.toList();
	}

	@Override
	public List<Tarefa> findByDataTermino(LocalDate dataTermino) {
		
		return tarefas.stream()
				.filter(t -> t.getDataTermino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) == dataTermino.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.toList();
	}

	@Override
	public List<Tarefa> findByTituloAproximado(String tituloAprox) {
		
		return tarefas.stream()
				.filter(t -> t.getTitulo().contains(tituloAprox))
				.toList();
	}

}
