package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Tarefa {

	
	private Long id;
	private String titulo;
	private String descricao;
	private Prioridade prioridade;
	private Status status;
	private LocalDate dataCriacao;
	private LocalDate dataTermino;
	
	public Tarefa(Long id, String titulo, String descricao, Prioridade prioridade, Status status, LocalDate dataCriacao,
			LocalDate dataTermino) {

		this.setId(id);
		this.setTitulo(titulo);
		this.setDescricao(descricao);
		this.setPrioridade(prioridade);
		this.setStatus(status);
		this.setDataCriacao(dataCriacao);
		this.setDataTermino(dataTermino);
	}
	
	public Tarefa() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Prioridade getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public LocalDate getDataCriacao() {
		return dataCriacao;
	}
	
	public String getDataCriacaoFormatado() {
		return getDataCriacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public LocalDate getDataTermino() {
		return dataTermino;
	}
	
	public String getDataTerminoFormatado() {
		return getDataTermino().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}
	
	public String toCSV() {
		return getId()+","+getTitulo()+","+getDescricao()+","+getPrioridade()+","+getStatus()+","+getDataCriacaoFormatado()+","+getDataTerminoFormatado()+"\n";
		
	}


}
