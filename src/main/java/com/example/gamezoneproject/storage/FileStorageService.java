package com.example.gamezoneproject.storage;

import com.example.gamezoneproject.domain.exceptions.InvalidFileExtensionException;
import com.example.gamezoneproject.storage.storageStrategy.*;
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

    private final BigPoster bigPoster; // TO WYWAL I DODAJ W INTEREFJSE PRZEKAZYWANIE STORAGELOCATION
    private final SmallPoster smallPoster;
    private final GamePoster gamePoster;
    private final CompanyPoster companyPoster;
    private final OtherFiles otherFiles;
    private final String baseLocation;

    /**
     * Constructor for FileStorageService.
     *
     * @param storageLocation Base storage location for files and images
     */

    public FileStorageService(@Value("${app.storage.location}") String storageLocation, BigPoster bigPoster,
                              SmallPoster smallPoster, GamePoster gamePoster, CompanyPoster companyPoster, OtherFiles otherFiles) {
        this.baseLocation = storageLocation;
        this.bigPoster = bigPoster;
        this.smallPoster = smallPoster;
        this.gamePoster = gamePoster;
        this.companyPoster = companyPoster;
        this.otherFiles = otherFiles;
        Path fileStoragePath = Path.of(otherFiles.setImageLocation(storageLocation));
        Path gameImageStorageLocationPath = Path.of(gamePoster.setImageLocation(storageLocation));
        Path companyImageStoragePath = Path.of(companyPoster.setImageLocation(storageLocation));
        Path bigAddImageStorageLocationPath = Path.of(bigPoster.setImageLocation(storageLocation));
        Path smallAddImageStorageLocationPath = Path.of(smallPoster.setImageLocation(storageLocation));

        prepareStorageDirectories(fileStoragePath, gameImageStorageLocationPath,
                companyImageStoragePath, bigAddImageStorageLocationPath, smallAddImageStorageLocationPath);
    }

    /**
     * Creates necessary storage directories if they do not exist.
     *
     * @param fileStoragePath                  Path to file storage directory
     * @param gameImageStoragePath             Path to image storage directory
     * @param companyImageStoragePath          Path to company image storage directory
     * @param bigAddImageStorageLocationPath   Path to big add image storage directory
     * @param smallAddImageStorageLocationPath Path to small add image storage directory
     */
    private void prepareStorageDirectories(Path fileStoragePath, Path gameImageStoragePath,
                                           Path companyImageStoragePath, Path bigAddImageStorageLocationPath,
                                           Path smallAddImageStorageLocationPath) {
        try {
            if (Files.notExists(fileStoragePath)) {
                Files.createDirectories(fileStoragePath);
                logger.info("File storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }
            if (Files.notExists(gameImageStoragePath)) {
                Files.createDirectories(gameImageStoragePath);
                logger.info("Game image storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }

            if (Files.notExists(companyImageStoragePath)) {
                Files.createDirectories(companyImageStoragePath);
                logger.info("Company image storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }

            if (Files.notExists(bigAddImageStorageLocationPath)) {
                Files.createDirectories(bigAddImageStorageLocationPath);
                logger.info("Big adds image storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }
            if (Files.notExists(smallAddImageStorageLocationPath)) {
                Files.createDirectories(smallAddImageStorageLocationPath);
                logger.info("Small ads image storage directory created %s".formatted(fileStoragePath.toAbsolutePath()
                        .toString()));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Creation of storage directories failed", e);
        }
    }

    /**
     * Saves the provided image file.
     *
     * @param file     Image file to be saved.
     * @param fileName Title of the game used to rename the file.
     * @return String name of the saved file.
     * @throws InvalidFileExtensionException if the file extension is not allowed.
     */

    public String saveImage(MultipartFile file, String fileName, ImageStrategy imageStorageFile) {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension != null) {
            if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                return saveFile(file, fileName, imageStorageFile);
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
        return saveFile(file, fileName, otherFiles);
    }

    /**
     * Saves the file to the specified storage location.
     *
     * @param file             File to be saved
     * @param fileName         Title of the game used to rename the file
     * @param imageStorageFile image type
     * @return Name of the saved file
     */
    private String saveFile(MultipartFile file, String fileName, ImageStrategy imageStorageFile) {
        Path filePath = createFilePath(file, fileName, imageStorageFile);
        System.err.println(filePath);
        try {
            int targetWidth = imageStorageFile.getImageTargetWidth();
            int targetHeight = imageStorageFile.getImageTargetHeight();
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
     * @param targetWidth  Target width for image in px.
     * @param targetHeight Target height for image in px.
     * @return Image saved as InputStream.
     * @throws IOException when ImageIO read() can't read file.
     */
    private static InputStream changeFileToInputStream(MultipartFile file, int targetWidth, int targetHeight) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        ResampleOp resampleOp = new ResampleOp(targetWidth, targetHeight);
        resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
        BufferedImage sc = resampleOp.filter(bufferedImage, null);

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(sc, extension, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * Creates a unique file path for the file to be saved. If file exists its rename file name by adding next number
     *
     * @param file             File to be saved.
     * @param fileName         Title of the game used to rename the file.
     * @param imageStorageFile image type.
     * @return Unique file path
     */
    private Path createFilePath(MultipartFile file, String fileName, ImageStrategy imageStorageFile) {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        String completeFilename;
        Path filePath;
        int fileIndex = 0;
        String preparedFileName = prepareFileName(fileName);
        preparedFileName += imageStorageFile.getImagePreparedName();

        do {
            completeFilename = preparedFileName + "_" + fileIndex + "." + fileExtension;
            filePath = Paths.get(imageStorageFile.setImageLocation(baseLocation), completeFilename);
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
