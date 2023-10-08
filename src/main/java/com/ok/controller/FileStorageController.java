package com.ok.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.ok.service.StorageService;

@RestController
@RequestMapping("/api/file")
@CrossOrigin(origins = "*")
public class FileStorageController {

	@Autowired
	private StorageService storageService;
	
	@PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(name = "files") MultipartFile[] file) {
        try {
            String result = storageService.uploadFile(file);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error uploading the file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
		byte[] data = storageService.downloadFile(fileName);
		ByteArrayResource byteArrayResource = new ByteArrayResource(data);
		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type", "application/octet-stream")
				.header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
				.body(byteArrayResource);
				
	}
	
	@DeleteMapping("/delete/{fileName}")
	public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
		return new ResponseEntity<String>(storageService.deleteFile(fileName), HttpStatus.OK);
	}
	
	@GetMapping("/getAllFiles")
	public ResponseEntity<List<String>> getAllFiles() {
		List<S3ObjectSummary> listSummary = storageService.getAllFiles();

		if (listSummary.size() != 0) {
			
			return new ResponseEntity<List<String>>(listSummary.stream().map(i -> i.getKey()).toList(), HttpStatus.OK);
		} 
		
		return null;
	}
	
	@GetMapping("/getFileInfo/{fileName}")
	public ResponseEntity<Map<String, Object>> getSingleFileInfo(@PathVariable String fileName) {
		return new ResponseEntity<Map<String,Object>>(storageService.getSingleFileFromS3(fileName), HttpStatus.OK);
	}
	
	@GetMapping("/getTotalSize")
	public ResponseEntity<Long> getTotalSize() {
		List<S3ObjectSummary> listSummary = storageService.getAllFiles();
	if (listSummary.size() != 0) {
			
			return new ResponseEntity<Long>(listSummary.stream().mapToLong(item -> item.getSize()).sum(), HttpStatus.OK);
		} 
	
	return null;
	}
}










