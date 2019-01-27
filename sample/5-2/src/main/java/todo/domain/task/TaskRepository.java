package todo.domain.task;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends org.springframework.data.repository.Repository<Task, Integer> {
	Task findById(Integer id);
	List<Task> findAll();
	List<Task> findByStatusOrderByUpdated(Integer status);
	void deleteById(Integer id);
	<S extends Task> S save(S var1);
}
