package todo.domain.task;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import todo.domain.file.File;
import todo.domain.reminder.Reminder;

@SuppressWarnings("serial")
@Entity
@Table(name = "tasks")
public class Task {

	private Integer id;
	private String title;
	private Integer status;
    private File attachment;
	private Date created;
	private Date updated;
	private Reminder reminder;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TITLE", nullable = false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "STATUS", nullable = false, columnDefinition = "int default 1")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

    @OneToOne(mappedBy = "task")
    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
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

	@OneToOne(mappedBy = "task")
	public Reminder getReminder() {
		return reminder;
	}
	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}
}
