package com.ok.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

@Service
public class StorageService {
	
	@Value("${application.bucket.name}")
	private String bucketName;
	
	@Autowired
	private AmazonS3 client;
	
	public String uploadFile(MultipartFile file ) {
		String uniqueFileName = UUID.randomUUID().toString() + file.getOriginalFilename();
		File newFile = convertToFile(file);
		client.putObject(new PutObjectRequest(bucketName, uniqueFileName, newFile));
		
		// Delete it before the next file upload
		newFile.delete();
		
		return "File successfully uploaded: " + uniqueFileName;
	}
	
	
	public byte[] downloadFile(String fileName) {
		 S3Object s3Object = client.getObject(bucketName, fileName);
		 
		 S3ObjectInputStream inputStream = s3Object.getObjectContent();
		 
		 try {
			byte[] inputContent =  IOUtils.toByteArray(inputStream);
			return inputContent;
			
		} catch (Exception e) {
			System.out.println("File could not be found");
		}
		 
		 return null;
	}
	
	
	public String deleteFile(String fileName) {
		client.deleteObject(bucketName, fileName);
		return fileName + " has been deleted";
	}
	
	private static File convertToFile(MultipartFile file) {
		File convertedObject = new File(file.getOriginalFilename());
		
		try(FileOutputStream fs = new FileOutputStream(convertedObject)) {
			fs.write(file.getBytes());
		} catch(IOException e) {
			System.out.println("Error converting file" + " " + e);
		}
		return convertedObject;
	}
}
