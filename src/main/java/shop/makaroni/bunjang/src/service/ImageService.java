package shop.makaroni.bunjang.src.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.makaroni.bunjang.config.BaseException;
import shop.makaroni.bunjang.src.dao.ItemDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static shop.makaroni.bunjang.config.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final AmazonS3Client amazonS3Client;
    private final ItemDao itemDao;
    private final ItemService itemService;

    private static final String FILE_EXTENSION_SEPARATOR = ".";
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;


    public HashMap<String, String> uploadFile(String itemIdx, List<MultipartFile> multipartFiles) throws BaseException {
        List<String> fileUrls = new ArrayList<>();
        if(itemDao.checkItemIdx(Long.valueOf(itemIdx)) == 0){
            throw new BaseException(ITEM_NO_EXIST);
        }

        for (MultipartFile multipartFile : multipartFiles) {
            if (fileUrls.size() > 12) {
                throw new BaseException(POST_IMAGE_COUNT);
            }
            validateFileExists(multipartFile);

            String fileName = buildFileName(itemIdx, multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                fileUrls.add(amazonS3Client.getUrl(bucketName, fileName).toString());
            } catch (IOException e) {
                throw new BaseException(POST_IMAGE_UPLOAD_FAIL);
            }
        }
        for(String url : fileUrls){
            itemDao.setImage(Long.valueOf(itemIdx),url);
        }
        HashMap<String, String> res = new HashMap<>();
        res.put("idx", itemIdx);
        return res;
    }

    private void validateFileExists(MultipartFile multipartFile) throws BaseException {
        if (multipartFile.isEmpty()) {
            throw new BaseException(POST_ITEM_EMPTY_IMAGE);
        }
    }

    public static String buildFileName(String itemIdx, String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        String IDX_PREFIX = "_";
        String TIME_SEPARATOR = "_";
        return itemIdx + IDX_PREFIX + fileName + TIME_SEPARATOR + now + fileExtension;
    }

    public HashMap<String, String> modifyFile(String itemIdx, List<MultipartFile> files) throws BaseException {
        itemService.deleteAllImages(Long.valueOf(itemIdx));
        return uploadFile(itemIdx, files);
    }
}