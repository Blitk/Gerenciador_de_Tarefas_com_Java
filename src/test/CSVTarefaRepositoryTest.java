package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import model.Prioridade;
import model.Status;
import model.Tarefa;
import repository.CSVTarefaRepository;

public class CSVTarefaRepositoryTest{

	    private CSVTarefaRepository repository;
	    private final String PATH_ARQUIVO = "data/data.csv";

	    @BeforeEach
	    public void setup() {
	        // Limpa o arquivo de teste para começar do zero
	        File arquivo = new File(PATH_ARQUIVO);
	        if (arquivo.exists()) {
	            arquivo.delete();
	        }
	        repository = new CSVTarefaRepository();
	    }

	    @Test
	    @DisplayName("Deve salvar uma tarefa com sucesso no CSV")
	    public void testSalvarTarefa() {
	        Tarefa t = new Tarefa(1L, "Comprar Leite", "Ir ao mercado", 
	                   Prioridade.BAIXA, Status.NAOINICIADA, LocalDate.now(), LocalDate.now());
	        
	        repository.save(t);
	        
	        assertEquals(1, repository.findAll().size(), "O repositório deve ter 1 tarefa");
	        assertEquals("Comprar Leite", repository.findAll().get(0).getTitulo());
	    }

	    @Test
	    @DisplayName("Deve buscar uma tarefa pelo ID")
	    public void testBuscarPorId() {
	        Tarefa t = new Tarefa(5L, "Estudar", "Java", 
	                   Prioridade.ALTA, Status.EMANDAMENTO, LocalDate.now(), LocalDate.now());
	        repository.save(t);

	        Optional<Tarefa> buscada = repository.findByID(5L);
	        
	        assertTrue(buscada.isPresent());
	        assertEquals("Estudar", buscada.get().getTitulo());
	    }

	    @Test
	    @DisplayName("Deve remover uma tarefa")
	    public void testRemoverTarefa() {
	        Tarefa t = new Tarefa(1L, "Deletar", "Teste", 
	                   Prioridade.MEDIA, Status.NAOINICIADA, LocalDate.now(), LocalDate.now());
	        repository.save(t);
	        
	        repository.delete(1L);
	        
	        assertTrue(repository.findAll().isEmpty());
	    }

	    @Test
	    @DisplayName("Deve filtrar por título aproximado")
	    public void testBuscarTituloAproximado() {
	        repository.save(new Tarefa(1L, "Aula de Java", "Desc", Prioridade.ALTA, Status.NAOINICIADA, LocalDate.now(), LocalDate.now()));
	        repository.save(new Tarefa(2L, "Aula de Python", "Desc", Prioridade.ALTA, Status.NAOINICIADA, LocalDate.now(), LocalDate.now()));

	        List<Tarefa> resultado = repository.findByTituloAproximado("Java");
	        
	        assertEquals(1, resultado.size());
	        assertEquals("Aula de Java", resultado.get(0).getTitulo());
	    }

	    @Test
	    @DisplayName("Deve validar persistência ao recarregar o repositório")
	    public void testPersistenciaCSV() {
	        Tarefa t = new Tarefa(10L, "Persistência", "Verificar arquivo", 
	                   Prioridade.MEDIA, Status.NAOINICIADA, LocalDate.now(), LocalDate.now());
	        repository.save(t);
	        
	        CSVTarefaRepository novoRepo = new CSVTarefaRepository();
	        
	        assertEquals(1, novoRepo.findAll().size());
	        assertEquals(10L, novoRepo.findAll().get(0).getId());
	    }
}
