package cn.edu.ustc.studentinfomanagementsystem.utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
public class fileManager {
    // Create a directory
    public static void createDirectory(String dirPath) throws IOException {
        Path path = Paths.get(dirPath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("Directory created: " + dirPath);
        } else {
            System.out.println("Directory already exists");
        }
    }

    public static void renameFile(String oldFilePath, String newFilePath) throws IOException {
        Path sourcePath = Paths.get(oldFilePath);
        Path targetPath = Paths.get(newFilePath);
        if (Files.exists(sourcePath)) {
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File renamed from " + oldFilePath + " to " + newFilePath);
        } else {
            System.out.println("File not found: " + oldFilePath);
        }
    }
    // Method to copy a file
    public static void copyFile(String sourceFilePath, String targetFilePath) throws IOException {
        Path sourcePath = Paths.get(sourceFilePath);
        Path targetPath = Paths.get(targetFilePath);
        if (Files.exists(sourcePath)) {
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied from " + sourceFilePath + " to " + targetFilePath);
        } else {
            System.out.println("Source file does not exist: " + sourceFilePath);
        }
    }
}
