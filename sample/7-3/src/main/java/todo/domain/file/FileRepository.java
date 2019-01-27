package todo.domain.file;

import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends org.springframework.data.repository.Repository<File, Integer> {
	File findById(Integer id);
	File findByTaskId(Integer taskId);
	void deleteByTaskId(Integer taskId);
	<S extends File> S save(S var1);
}
