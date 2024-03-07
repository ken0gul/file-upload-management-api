# Spring Boot API for File Management with AWS S3

This is a Spring Boot application that serves as an API for uploading, fetching, updating, and downloading files to/from AWS S3. It provides REST endpoints to interact with files stored in AWS S3 buckets.

## Features

- **File Upload**: Allows users to upload files to AWS S3 buckets.
- **File Fetching**: Retrieves files from AWS S3 buckets.
- **File Updating**: Updates existing files in AWS S3 buckets.
- **File Downloading**: Downloads files from AWS S3 buckets.
- **Integration with AWS S3**: Utilizes AWS SDK to interact with AWS S3 for file storage and retrieval.

## Prerequisites

Before running this project locally, ensure you have the following:

- Java Development Kit (JDK)
- Apache Maven
- AWS S3 account with credentials configured

## Configuration

1. Configure AWS credentials:
   
   You need to provide your AWS access key ID and secret access key in the `application.properties` file:

   ```properties
   aws.accessKey=YOUR_ACCESS_KEY_ID
   aws.secretKey=YOUR_SECRET_ACCESS_KEY
   aws.region=YOUR_AWS_REGION
   ```
## Usage

1. Build the project:
   ```bash
    mvn clean install
   ```

2. Run the application:
   ```bash
   java -jar target/spring-boot-api-s3.jar
   ```
   OR
   Use your favorite IDE

3. Once the application is running, you can access the API endpoints to upload, fetch, update, and download files from AWS S3.

## API END POINTS
- File Upload:
```bash
POST /api/file/upload
```
- File Download:
```bash
GET /api/file/download/{fileName}
```
- File Deletion:
 ``` bash
  DELETE /api/file/delete/{fileName}
```
- File Fetch(All files):
```bash
GET /api/file/getAllFiles
```
- Get File Info:
```bash
GET /api/file/getFileInfo/{fileName}
```

- Get Total Size:
```bash
GET /api/file/getTotalSize
```



