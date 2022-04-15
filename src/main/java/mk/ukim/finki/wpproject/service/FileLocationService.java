package mk.ukim.finki.wpproject.service;

import mk.ukim.finki.wpproject.model.Ad;
import mk.ukim.finki.wpproject.model.adImage;
import mk.ukim.finki.wpproject.repository.FileSystemRepository;
import mk.ukim.finki.wpproject.repository.ImageDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileLocationService {

    @Autowired
    FileSystemRepository fileSystemRepository;
    @Autowired
    ImageDbRepository imageDbRepository;

    public adImage save(byte[] bytes, String imageName) throws Exception {
        return fileSystemRepository.save(bytes, imageName);
    }

    public FileSystemResource find(Long imageId) {
        adImage image = imageDbRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return fileSystemRepository.findInFileSystem(image.getLocation());
    }

    public String findPath(Long imageId) {
        adImage image = imageDbRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return image.getLocation();
    }

    public List<String> findAllPaths (Ad ad) {
        return ad.getImages().stream().map(i -> this.findPath(i.getId())).collect(Collectors.toList());
    }
}
