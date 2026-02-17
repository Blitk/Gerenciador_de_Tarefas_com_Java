package repository;

import java.util.List;
import java.util.Optional;

public interface Repository <T, ID> {
	
	List <T> findAll();
	Optional<T> findByID(ID id);
	void save(T entity);
	void update(T entity);
	void delete(ID id);
	
}
