package todo.domain.reminder;

import todo.domain.task.Task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@Table(name = "reminder")
public class Reminder {

	private Integer id;
	private String email;
	private boolean sent;
	private Date created;
	private Date execDate;
	private Integer taskId;
	private Task task;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "EMAIL", nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "SENT", nullable = false, columnDefinition = "bit(1) default 0")
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}

	@Column(name = "CREATED", nullable = false)
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "EXEC_DATE")
	public Date getExecDate() {
		return execDate;
	}
	public void setExecDate(Date execDate) {
		this.execDate = execDate;
	}

	@Column(name = "TASK_ID", nullable = false)
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TASK_ID", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}


}
