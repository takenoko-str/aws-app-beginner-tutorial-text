package todo.domain.reminder;

import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends org.springframework.data.repository.Repository<Reminder, Integer> {
	Reminder findById(Integer id);
	Reminder findByTaskId(Integer taskId);
	<S extends Reminder> S save(S var1);
	void deleteById(Integer id);
}
