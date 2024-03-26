package com.example.gamezoneproject.storage;

import com.example.gamezoneproject.domain.exceptions.InvalidFileExtensionException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This class is responsible for saving images from forms to project folders.
 */
@Service
public class FileStorageService {
    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final String fileStorageLocation;
    private final String imageStorageLocation;

    /**
     * Constructor for FileStorageService.
     *
     * @param storageLocation Base storage location for files and images
     */

    public FileStorageService(@Value("${app.storage.location}") String storageLocation) {
        this.fileStorageLocation = storageLocation + "/pliki/";
        this.imageStorageLocation = storageLocation + "/galeria/gry/";
        Path fileStoragePath = Path.of(this.fileStorageLocation);
        Path imageStoragePath = Path.of(this.imageStorageLocation);
        prepareStorageDirectories(fileStoragePath, imageStoragePath);
    }
    /**
     * Creates necessary storage directories if they do not exist.
     *
     * @param fileStoragePath  Path to file storage directory
     * @param imageStoragePath Path to image storage directory
     */
    private void prepareStorageDirectories(Path fileStoragePath, Path imageStoragePath) {
        try {
            if (Files.notExists(fileStoragePath)) {
                Files.createDirectories(fileStoragePath);
                logger.info("File storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }
            if (Files.notExists(imageStoragePath)) {
                Files.createDirectories(imageStoragePath);
                logger.info("Image storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Creation of storage directories failed", e);
        }
    }

    /**
     * Saves the provided image file.
     *
     * @param file     Image file to be saved
     * @param fileName Title of the game used to rename the file
     * @return Name of the saved file
     * @throws InvalidFileExtensionException if the file extension is not allowed
     */
    public String saveImage(MultipartFile file,String fileName) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension != null) {
            if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                return saveFile(file, imageStorageLocation,fileName);
            }
        }
        throw new InvalidFileExtensionException("Invalid file extension: " + extension);

    }
    /**
     * Saves the provided file.
     *
     * @param file     File to be saved
     * @param fileName Title of the game used to rename the file
     * @return Name of the saved file
     */
    public String saveFile(MultipartFile file,String fileName) {
        return saveFile(file, fileStorageLocation,fileName);
    }
    /**
     * Saves the file to the specified storage location.
     *
     * @param file              File to be saved
     * @param fileStorageLocation Storage location for the file
     * @param fileName          Title of the game used to rename the file
     * @return Name of the saved file
     */
    private String saveFile(MultipartFile file, String fileStorageLocation,String fileName) {
        Path filePath = createFilePath(file, fileStorageLocation,fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.getFileName().toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    /**
     * Creates a unique file path for the file to be saved. If file exists its rename file name by adding next number
     *
     * @param file              File to be saved
     * @param storageLocation   Storage location for the file
     * @param fileName          Title of the game used to rename the file
     * @return Unique file path
     */
    private Path createFilePath(MultipartFile file, String storageLocation,String fileName) {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        String completeFilename;
        Path filePath;
        int fileIndex = 0;
        do {
            completeFilename = fileName.trim() +"_" +fileIndex + "." + fileExtension;
            filePath = Paths.get(storageLocation, completeFilename);
            fileIndex++;
        } while (Files.exists(filePath));
        return filePath;
    }
}
