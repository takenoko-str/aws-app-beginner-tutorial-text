package todo.application.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import todo.application.ReminderService;
import todo.application.aws.SESService;

@Component
public class ReminderTask implements Runnable {

	private int id;
	private String to;
	private String subject;
	private String body;

	@Autowired
	SESService sesService;
	@Autowired
	ReminderService reminderService;

	@Value("${aws.ses.settings.from}")
	private String from;

	@Override
	public void run() {
		sesService.send(from, to, subject, body);
		reminderService.completeReminder(id);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}
