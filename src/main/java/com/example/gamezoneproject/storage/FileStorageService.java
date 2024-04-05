package com.example.gamezoneproject.storage;

import com.example.gamezoneproject.domain.exceptions.InvalidFileExtensionException;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

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
    private final String gameImageStorageLocation;
    private final String companyImageStorageLocation;

    /**
     * Constructor for FileStorageService.
     *
     * @param storageLocation Base storage location for files and images
     */

    public FileStorageService(@Value("${app.storage.location}") String storageLocation) {
        this.fileStorageLocation = storageLocation + "/pliki/";
        this.gameImageStorageLocation = storageLocation + "/galeria/gry/";
        this.companyImageStorageLocation = storageLocation + "/galeria/firmy/";
        Path fileStoragePath = Path.of(this.fileStorageLocation);
        Path gameImageStorageLocationPath = Path.of(this.gameImageStorageLocation);
        Path companyImageStoragePath = Path.of(this.companyImageStorageLocation);
        prepareStorageDirectories(fileStoragePath, gameImageStorageLocationPath, companyImageStoragePath);
    }

    /**
     * Creates necessary storage directories if they do not exist.
     *
     * @param fileStoragePath      Path to file storage directory
     * @param gameImageStoragePath Path to image storage directory
     */
    private void prepareStorageDirectories(Path fileStoragePath, Path gameImageStoragePath, Path companyImageStoragePath) {
        try {
            if (Files.notExists(fileStoragePath)) {
                Files.createDirectories(fileStoragePath);
                logger.info("File storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }
            if (Files.notExists(gameImageStoragePath)) {
                Files.createDirectories(gameImageStoragePath);
                logger.info("Image storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }
            if (Files.notExists(companyImageStoragePath)) {
                Files.createDirectories(companyImageStoragePath);
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
     * @param file      Image file to be saved
     * @param fileName  Title of the game used to rename the file
     * @param isGame    Boolean check if file to save is game.
     * @param isCompany Boolean check if file to save is company.
     * @return Name of the saved file
     * @throws InvalidFileExtensionException if the file extension is not allowed
     */

    public String saveImage(MultipartFile file, String fileName, boolean isGame, boolean isCompany) {
        String fileStorageLocation;

        if (isCompany) {
            fileStorageLocation = companyImageStorageLocation;
        } else {
            fileStorageLocation = gameImageStorageLocation;
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension != null) {
            if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                return saveFile(file, fileStorageLocation, fileName, isGame, isCompany);
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
    public String saveFile(MultipartFile file, String fileName) {
        return saveFile(file, fileStorageLocation, fileName, false, false);
    }

    /**
     * Saves the file to the specified storage location.
     *
     * @param file                File to be saved
     * @param fileStorageLocation Storage location for the file
     * @param fileName            Title of the game used to rename the file
     * @param isGame boolean info is saving image is for game.
     * @param isCompany boolean info is saving image is for company.
     * @return Name of the saved file
     */
    private String saveFile(MultipartFile file, String fileStorageLocation, String fileName, boolean isGame, boolean isCompany) {
        Path filePath = createFilePath(file, fileStorageLocation, fileName);
        try {
            int targetWidth = 0;
            int targetHeight = 0;

            if (isGame) {
                targetWidth = 400;
                targetHeight = 600;
            }
            if (isCompany) {
                targetWidth = 160;
                targetHeight = 160;
            }
            InputStream is = changeFileToInputStream(file, targetWidth, targetHeight);

            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.getFileName().toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * This method is for transform MultiPartFile to BufferedImage and return InputSteam.
     *
     * @param file         MultipartFile to transform.
     * @param targetWidth Target width for image in px.
     * @param targetHeight Target height for image in px.
     * @return Image saved as InputStream.
     * @throws IOException when ImageIO read() can't read file.
     */
    private static InputStream changeFileToInputStream(MultipartFile file, int targetWidth, int targetHeight) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        ResampleOp resampleOp = new ResampleOp(targetWidth,targetHeight);
        resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
        BufferedImage sc = resampleOp.filter(bufferedImage,null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(sc, "jpeg", baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * Creates a unique file path for the file to be saved. If file exists its rename file name by adding next number
     *
     * @param file            File to be saved
     * @param storageLocation Storage location for the file
     * @param fileName        Title of the game used to rename the file
     * @return Unique file path
     */
    private Path createFilePath(MultipartFile file, String storageLocation, String fileName) {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        String completeFilename;
        Path filePath;
        int fileIndex = 0;
        String preparedFileName = prepareFileName(fileName);
        do {
            completeFilename = preparedFileName + "_" + fileIndex + "." + fileExtension;
            filePath = Paths.get(storageLocation, completeFilename);
            fileIndex++;
        } while (Files.exists(filePath));
        return filePath;
    }

    private String prepareFileName(String fileName) {
        return fileName.replaceAll(" ", "")
                .trim()
                .replaceAll("[^a-zA-Z0-9]+", "");

    }
}
