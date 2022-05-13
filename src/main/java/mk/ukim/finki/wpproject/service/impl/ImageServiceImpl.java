package mk.ukim.finki.wpproject.service.impl;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.adImage;
import mk.ukim.finki.wpproject.model.exceptions.AdNotFoundException;
import mk.ukim.finki.wpproject.service.AdService;
import mk.ukim.finki.wpproject.service.FileLocationService;
import mk.ukim.finki.wpproject.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final AdService adService;
    private final FileLocationService fileLocationService;

    public ImageServiceImpl(AdService adService, FileLocationService fileLocationService) {
        this.adService = adService;
        this.fileLocationService = fileLocationService;
    }

    @Override
    public void addImagesToAd(Long adId, List<MultipartFile> images) {
        Ad ad = adService.findById(adId).orElseThrow(() -> new AdNotFoundException(adId));

        images.stream().forEach(image -> {
            try {
                if (image.getOriginalFilename() != null && !image.getOriginalFilename().isEmpty()) {
                    adImage adImage = fileLocationService.save(image.getBytes(), image.getOriginalFilename());

                    ad.getImages().add(adImage);
                    adService.save(ad);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
