package todo.application;

import com.amazonaws.services.sqs.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import todo.application.aws.SQSService;

import java.util.List;

@Service
public class BounceMailService {

	@Autowired
	SQSService sqsService;

	@Value("${aws.sqs.settings.bounce-queue.url}")
	private String bounceQueueUrl;

	// 1日に1回実行する（0時実行）
	@Scheduled(cron = "0 0 0 * * ?")
	public void getBounceQueueData() {
		try {
			List<Message> messageList = sqsService.getMessages(bounceQueueUrl, 10);

			for(Message message : messageList) {
				/*
				バウンスの処理をここで行う
				送信しないようにブラックリスト化するなど
				 */

				// SQSで設定したメッセージの生存期間は、
				// 残り続けるため手動でキューからメッセージを削除する
				sqsService.deleteMessage(bounceQueueUrl, message.getReceiptHandle());
			}
		} catch(Exception e) {
			// 例外処理
		}
	}

}
