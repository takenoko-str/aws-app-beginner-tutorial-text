package todo.application;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import todo.application.aws.S3Service;
import todo.domain.file.File;
import todo.domain.file.FileRepository;

@Service
public class FileService {

	@Autowired
	FileRepository fileRepository;

	@Autowired
	S3Service s3service;

	@Value("${aws.s3.bucket}")
	private String bucketName;


	public void uploadFile(MultipartFile attachmentFile, int taskId) {
		String key = attachmentFile.getOriginalFilename();

		File file = new File();
		file.setTaskId(taskId);
		file.setName(key);
		file.setS3ObjectKey(key);
		file.setUpdated(new Date());
		fileRepository.save(file);

		//S3へのアップロード
		try {
			s3service.uploadToPublicBucket(attachmentFile, bucketName, key);
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public String downloadFile(int fileId) {
		File file = fileRepository.findById(fileId);
		String key = file.getS3ObjectKey();
		String url = "";
		try {
			url = s3service.getPresignedUrl(bucketName, key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return url;
	}

}
