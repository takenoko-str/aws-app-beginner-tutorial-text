package todo.application.aws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

@Service
public class S3Service {
	private static final String RESION_AMAZON_US_EAST = "us-east-1";

	@Value("${aws.api.key}")
	private String awsKey;
	@Value("${aws.api.secret}")
	private String awsSecret;

	public void uploadToPublicBucket(InputStream is, String bucketName, String key, String contentType, int contentLength) throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(RESION_AMAZON_US_EAST)
				.build();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(contentType);
		metadata.setContentLength(contentLength);

		PutObjectRequest req = new PutObjectRequest(bucketName, key, is, metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		s3.putObject(req);
	}

	public void uploadToPublicBucket(MultipartFile multipartFile, String bucketName, String key) throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(RESION_AMAZON_US_EAST)
				.build();

		InputStream is = new ByteArrayInputStream(multipartFile.getBytes());
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(multipartFile.getContentType());
		metadata.setContentDisposition("inline; filename*=UTF-8''" + URLEncoder.encode(multipartFile.getOriginalFilename(), "UTF-8"));
		metadata.setContentLength(multipartFile.getBytes().length);

		PutObjectRequest req = new PutObjectRequest(bucketName, key, is, metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
		s3.putObject(req);
	}

	public void uploadToPrivateBucket(InputStream is, String bucketName, String key, String contentType, int contentLength) throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(RESION_AMAZON_US_EAST)
				.build();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(contentType);
		metadata.setContentLength(contentLength);

		PutObjectRequest req = new PutObjectRequest(bucketName, key, is, metadata)
				.withCannedAcl(CannedAccessControlList.Private);
		s3.putObject(req);
	}

	public void uploadToPrivateBucket(MultipartFile multipartFile, String bucketName, String key) throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(RESION_AMAZON_US_EAST)
				.build();

		InputStream is = new ByteArrayInputStream(multipartFile.getBytes());
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(multipartFile.getContentType());
		metadata.setContentDisposition("inline; filename*=UTF-8''" + URLEncoder.encode(multipartFile.getOriginalFilename(), "UTF-8"));
		metadata.setContentLength(multipartFile.getBytes().length);

		PutObjectRequest req = new PutObjectRequest(bucketName, key, is, metadata)
				.withCannedAcl(CannedAccessControlList.Private);
		s3.putObject(req);
	}

	public String getPresignedUrl(String bucketName, String key) throws IOException {
		AWSCredentials credentials = new BasicAWSCredentials(awsKey, awsSecret);
		AmazonS3 s3 = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(RESION_AMAZON_US_EAST)
				.build();

		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 1000 * 60 * 60;
		expiration.setTime(expTimeMillis);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key)
				.withMethod(HttpMethod.GET)
				.withExpiration(expiration);
		return s3.generatePresignedUrl(generatePresignedUrlRequest).toString();
	}
}
