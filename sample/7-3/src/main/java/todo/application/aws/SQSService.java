package todo.application.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SQSService {
	private static final String RESION_AMAZON_US_EAST = "us-east-1";

	@Value("${aws.api.key}")
	private String awsKey;
	@Value("${aws.api.secret}")
	private String awsSecret;

		/*
	get message list
	 */
	public List<Message> getMessages(String queueUrl, int count) throws InterruptedException {
		AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
		AmazonSQS sqs = AmazonSQSClient.builder()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(RESION_AMAZON_US_EAST)
				.build();

		ReceiveMessageResult result = sqs.receiveMessage(
				new ReceiveMessageRequest()
						.withQueueUrl(queueUrl)
						.withWaitTimeSeconds(20)
						.withMaxNumberOfMessages(count));

		return result.getMessages();
	}

	/*
	delete message
	 */
	public void deleteMessage(String queueUrl, String receiptHandle) {
		AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
		AmazonSQS sqs = AmazonSQSClient.builder()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(RESION_AMAZON_US_EAST)
				.build();

		sqs.deleteMessage(
				new DeleteMessageRequest()
						.withQueueUrl(queueUrl)
						.withReceiptHandle(receiptHandle)
		);
	}
}
