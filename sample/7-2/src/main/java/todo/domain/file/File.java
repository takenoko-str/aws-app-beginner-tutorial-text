package todo.domain.file;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import todo.domain.task.Task;

@SuppressWarnings("serial")
@Entity
@Table(name = "files")
public class File {

	private Integer id;
	private Integer taskId;
	private String name;
	private String s3ObjectKey;
	private Task task;
	private Date created;
	private Date updated;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TASK_ID")
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "NAME", nullable = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "S3_OBJECT", nullable = false)
	public String getS3ObjectKey() {
		return s3ObjectKey;
	}
	public void setS3ObjectKey(String s3ObjectKey) {
		this.s3ObjectKey = s3ObjectKey;
	}

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID", referencedColumnName = "id", nullable = true, insertable = false, updatable = false)
    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

	@Column(name = "CREATED", nullable = false)
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

	@Column(name = "UPDATED")
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}
