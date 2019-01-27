var _SELECTED_TASK_ID;

$(function(){
	console.log('init');

	$('.create-button').click(function () {
		_SELECTED_TASK_ID = '';
		showTaskForm();
	});

	$('.task-container').click(function () {
		let title = $(this).find('.task-title').text();
		let status = $(this).data('task-status');
		let reminderId = $(this).data('reminder-id');
		let reminderSchedule = $(this).data('reminder-schedule');
		let reminderTo = $(this).data('reminder-to');
		_SELECTED_TASK_ID = $(this).data('task-id');
		showTaskForm(title, status, reminderId, reminderSchedule, reminderTo);
	});

	$('.task-container .task-attachment a').click(function (e) {
		e.stopPropagation();
	});

	$('.task-container .delete-button').click(function (e) {
		e.stopPropagation();
		console.log('delete');
		let element = e.currentTarget.parentNode;
		_SELECTED_TASK_ID = $(element).data('task-id');
		let url = '/task/' + _SELECTED_TASK_ID;
		let method = 'DELETE';
		formSubmit(url, method);
	});

	$('.task-container .status-move').click(function (e) {
		e.stopPropagation();
		console.log('move');
		let element = e.currentTarget.parentNode;
		_SELECTED_TASK_ID = $(element).data('task-id');
		let title = $(element).find('.task-title').text();
		let status = $(element).data('task-status');
		let nextStatusCode = status == 1 ? 2
				: status == 2 ? 3
				: 1;
		$("#task-form .form-item input[name=title]").val(title);
		$('#task-form .status').val(nextStatusCode);
		let url = '/task/' + _SELECTED_TASK_ID + "?currentstatus=" + nextStatusCode;
		let method = 'PUT';
		formSubmit(url, method);
	});

	$('#task-form .submit-button').click(function () {
		console.log('update');
		if(_SELECTED_TASK_ID){
			var url = '/task/' + _SELECTED_TASK_ID;
			var method = 'PUT';
		} else {
			var url = '/task';
			var method = 'POST';
		}
		formSubmit(url, method);
	});

	$('.modal-close-button').click(function () {
		cancel();
	});

	$.datetimepicker.setLocale('ja');
	$('#datetimepicker').datetimepicker({step: 1});
});

function showTaskForm(title, status, reminderId, reminderSchedule, reminderTo) {
	console.log("task-form");
	console.log(_SELECTED_TASK_ID);
	$(".form-item input[name=title]").val(title);
	$("#datetimepicker").val(reminderSchedule);
	$("#reminderTo").val(reminderTo);

	let form = $('#task-form');
	if(title){
		$('#task-form-modal').find('.task-form-header').text('タスクの編集');
		form.find('.submit-button').text('更新');
		$('#task-form .status').val(status);
		$("#reminderId").val(reminderId);
	} else{
		$('#task-form-modal').find('.task-form-header').text('タスクの作成');
		form.find('.submit-button').text('作成');
		$('#task-form .status').val(1);
	}

	$("#modal").removeClass('hide');
	$(".modal-box").removeClass('hide');
}

function cancel() {
	console.log("cancel");
	$("#modal").addClass('hide');
	$(".modal-box").addClass('hide');
}

function formSubmit(url, method) {
	console.log("submit");
	let form = $('#task-form');
	form.find('#form-method').val(method);
	form.attr('action', url);
	form.submit();
}
