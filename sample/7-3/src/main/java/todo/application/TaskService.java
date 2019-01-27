package todo.application;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import todo.domain.task.Task;
import todo.domain.task.TaskRepository;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepository;

	public void create(Task task) {
		if(StringUtils.isNotBlank(task.getTitle())) {
			task.setStatus(1);
			task.setCreated(new Date());
			task.setUpdated(new Date());
			task.setReminder(null);
			taskRepository.save(task);
		}
	}

	public void update(Task task) {
		if(StringUtils.isNotBlank(task.getTitle())) {
			task.setUpdated(new Date());
			task.setReminder(null);
			taskRepository.save(task);
		}
	}

	public void delete(Integer id) {
		taskRepository.deleteById(id);
	}

	/**
	 * 登録済みのタスクの一覧を返す
	 * @return
	 */
	public List<Task> getTaskList() {
		return taskRepository.findAll();
	}

	/**
	 * ステータスごとの一覧を返す
	 * @return
	 */
	public List<Task> getTaskListByStatus(int status) {
		return taskRepository.findByStatusOrderByUpdated(status);
	}
}
