package mk.ukim.finki.wpproject.repository;

import mk.ukim.finki.wpproject.model.adImage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Repository
public class FileSystemRepository {

    private final ImageDbRepository imageDbRepository;

    String RESOURCES_DIR = FileSystemRepository.class.getResource("").getPath();

    public FileSystemRepository(ImageDbRepository imageDbRepository) {
        this.imageDbRepository = imageDbRepository;
    }

    public adImage save(byte[] content, String imageName) throws Exception {

        Path currentPath = Paths.get(".").toAbsolutePath();
        String location = currentPath + "/src/main/resources/static/images/";

        String imgPathName = (new Date().getTime()) + imageName;
        adImage adImage = new adImage(imgPathName, location);
        imageDbRepository.save(adImage);

        //creating images with unique "keys"(names)
        //adds current date in milliseconds format to the image name
        Path newFile = Paths.get(location + imgPathName);

        Files.createDirectories(newFile.getParent());
        Files.write(newFile, content);

        return adImage;
    }

    public FileSystemResource findInFileSystem(String location) {
        try {
            return new FileSystemResource(Paths.get(location));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
