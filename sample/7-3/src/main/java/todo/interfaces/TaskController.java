package todo.interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import todo.application.FileService;
import todo.application.ReminderService;
import todo.application.TaskService;
import todo.domain.reminder.Reminder;
import todo.domain.task.Task;

@Controller
@RequestMapping("/")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    FileService fileService;
    @Autowired
	ReminderService reminderService;

	@InitBinder
	public void dateBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		dateFormat.setLenient(false);
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@GetMapping
	public ModelAndView index(Task task) {
		List<Task> todoList = taskService.getTaskListByStatus(1);
		List<Task> doingList = taskService.getTaskListByStatus(2);
		List<Task> doneList = taskService.getTaskListByStatus(3);
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("todoList", todoList);
		mav.addObject("doingList", doingList);
		mav.addObject("doneList", doneList);
		return mav;
	}

    /**
     * [作成]ボタンをクリックしたときの処理。
     * タスクを新規登録する。
     * @param task
     * @return
     */
    @PostMapping("/task")
    public ModelAndView create(Task task, MultipartFile attachmentFile) {
    	Reminder reminder = task.getReminder();
        taskService.create(task);

        if(attachmentFile != null && !attachmentFile.isEmpty()) {
			fileService.uploadFile(attachmentFile, task.getId());
		}

		reminderService.createReminder(task.getId(), reminder.getEmail(), reminder.getExecDate(), task.getTitle());

        return new ModelAndView("redirect:/");
    }

    @PutMapping("/task/{id}")
    public ModelAndView update(@PathVariable("id") int id, Task task,
							   @RequestParam(value = "currentstatus", required = false) Integer currentStatus,
							   MultipartFile attachmentFile) {
		Reminder reminder = task.getReminder();
		reminder.setTaskId(task.getId());

        taskService.update(task);

        if(currentStatus == null) {
			reminderService.updateReminder(reminder, task.getTitle());
		} else if(currentStatus == 3) {
        	reminderService.completeReminder(reminder.getId());
		}

        return new ModelAndView("redirect:/");
    }

    @DeleteMapping("/task/{id}")
    public ModelAndView delete(@PathVariable("id") int id) {
    	reminderService.deleteReminder(id);
        taskService.delete(id);
        return new ModelAndView("redirect:/");
    }
}
