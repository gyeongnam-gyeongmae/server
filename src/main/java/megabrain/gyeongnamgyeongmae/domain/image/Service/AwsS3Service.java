package megabrain.gyeongnamgyeongmae.domain.image.Service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;

import megabrain.gyeongnamgyeongmae.domain.image.exception.ImageUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AwsS3Service {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public AwsS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void upload(MultipartFile file, String filename) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(new PutObjectRequest(bucket, filename, file.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (SdkClientException e) {
            e.printStackTrace();
            throw new ImageUploadException("업로드 실패: " + e.getMessage(), e);
        }

        amazonS3.getUrl(bucket, filename);
    }
}

