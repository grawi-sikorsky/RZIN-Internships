package com.pgs.praktyki.rzeintern1021.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AwsS3SenderTest {

    @Value("${cloud.s3.endpoint.bucketname}")
    private String avatarBucketName;

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private AwsS3sender awsS3Sender;

    @BeforeAll
    void setupDate() {
    }

    @Test
    public void uploadToS3_should_invoke_putObject() throws IOException {
        String originalFileName = "original FileName.jpg";
        URL expectedURL = new URL("http://expected.com/originalFileName.jpg");
        Integer randomChars = 16;
        MultipartFile multipartFile = new MockMultipartFile("param1", originalFileName, "param3", "param4".getBytes());
        File convertedTestFile = awsS3Sender.convertMultipartToFile(multipartFile);
        when(amazonS3.getUrl(eq(avatarBucketName), anyString())).thenReturn(expectedURL);

        awsS3Sender.uploadToS3(multipartFile, randomChars);

        verify(amazonS3, times(1)).putObject(eq(avatarBucketName), anyString(), eq(convertedTestFile));
    }

    @Test
    public void uploadToS3_should_return_url() throws MalformedURLException {
        String originalFileName = "originalFileName.jpg";
        URL expectedURL = new URL("http://expected.com/originalFileName.jpg");
        Integer randomChars = 16;
        MultipartFile multipartFile = new MockMultipartFile("param1", originalFileName, "param3", "param4".getBytes());

        when(amazonS3.getUrl(eq(avatarBucketName), anyString())).thenReturn(expectedURL);
        String returnURL = awsS3Sender.uploadToS3(multipartFile, randomChars);

        assertEquals(returnURL, expectedURL.toString());
    }

    @Test
    public void uploadToS3_should_throw_IOexception() throws IOException {
        String originalFileName = "originalFileName.jpg";
        MultipartFile multipartFile = new MockMultipartFile("param1", originalFileName, "param3", "param4".getBytes());

        AwsS3sender awsS3senderMock = mock(AwsS3sender.class);

        when(awsS3senderMock.convertMultipartToFile(multipartFile)).thenThrow(new IOException());

        assertThrows(IOException.class, () -> {
            awsS3senderMock.convertMultipartToFile(multipartFile);
        });
    }

    @Test
    public void generateUniqueName_should_return_correct_name() {
        String originalFileName = "original FileName.jpg";
        Integer randomChars = 16;
        MockMultipartFile multipartFile = new MockMultipartFile("param1", originalFileName, "param3", "param4".getBytes());

        String resultUniqueName = awsS3Sender.generateUniqueName(multipartFile, randomChars);

        assertEquals(resultUniqueName.length(), randomChars + 1 + originalFileName.length());
    }

    @Test
    public void convertMultipartToFile_should_return_converted_file() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("param1", "originalFileName", "param3", "param4".getBytes());

        File convertedFile = awsS3Sender.convertMultipartToFile(multipartFile);

        assertEquals(multipartFile.getOriginalFilename(), convertedFile.getName());
        assertEquals(multipartFile.getBytes().length, convertedFile.length());
        convertedFile.delete();
    }

    @Test
    public void getFileURL_should_invoke_geturl() throws MalformedURLException {
        URL expectedURL = new URL("http://expected.com/originalFileName.jpg");
        String filename = "filename";
        when(amazonS3.getUrl(eq(avatarBucketName), anyString())).thenReturn(expectedURL);

        awsS3Sender.getFileURL(filename);

        verify(amazonS3, times(1)).getUrl(avatarBucketName, filename);
    }

    @Test
    public void deleteFileByFilename_should_remove_file() {
        String filename = "filename";
        awsS3Sender.deleteFileByFilename(filename);
        verify(amazonS3, times(1)).deleteObject(avatarBucketName, filename);
    }
}