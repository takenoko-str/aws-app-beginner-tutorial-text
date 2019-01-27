package todo.application.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.ListVerifiedEmailAddressesResult;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.VerifyEmailAddressRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SESService {
	private static final String RESION_AMAZON_US_EAST = "us-east-1";

	@Value("${aws.api.key}")
	private String awsKey;
	@Value("${aws.api.secret}")
	private String awsSecret;

	static private List<String> verifiedEmailAddresses
			= Collections.synchronizedList(new ArrayList<String>());

	public void send(String from, String to, String subject, String body)
			throws AmazonClientException {

		// 送信元が承認されているかを検証する
		verifyEmailAddress(from);

		// 宛先の設定
		Destination destination = new Destination().withToAddresses(to);

		// 件名の設定
		Content contentSubject = new Content().withData(subject);

		// 本文の設定
		Body contentBody = new Body();
		Content contentTextBox = new Content().withData(body);
		contentBody.setText(contentTextBox);

		// メールオブジェクトの作成
		Message message = new Message()
				.withSubject(contentSubject)
				.withBody(contentBody);

		// SESへのリクエストを作成
		SendEmailRequest request = new SendEmailRequest()
				.withSource(from)
				.withDestination(destination)
				.withMessage(message);

		// 送信処理
		try {
			AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
			AmazonSimpleEmailService service = AmazonSimpleEmailServiceClient.builder()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(RESION_AMAZON_US_EAST)
					.build();
			service.sendEmail(request);
		} catch (AmazonClientException ex) {
			request.setMessage(message);
			throw ex;
		}

	}

	private void verifyEmailAddress(String address) {
		if (verifiedEmailAddresses.contains(address)) return;

		AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
		AmazonSimpleEmailService service = AmazonSimpleEmailServiceClient.builder()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(RESION_AMAZON_US_EAST)
				.build();

		ListVerifiedEmailAddressesResult verifiedEmails
				= service.listVerifiedEmailAddresses();
		if (verifiedEmails.getVerifiedEmailAddresses().contains(address)) {
			verifiedEmailAddresses
					= Collections.synchronizedList(verifiedEmails.getVerifiedEmailAddresses());
			return;
		}

		service.verifyEmailAddress(
				new VerifyEmailAddressRequest().withEmailAddress(address)
		);
	}

}
