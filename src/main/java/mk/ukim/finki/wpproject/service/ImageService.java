package mk.ukim.finki.wpproject.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void addImagesToAd(Long adId, List<MultipartFile> images);

}
