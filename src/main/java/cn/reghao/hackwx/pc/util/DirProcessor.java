package cn.reghao.hackwx.pc.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author reghao
 * @date 2021-01-15 00:43:41
 */
public class DirProcessor {
    private final Path path;
    private final FileHandler fileHandler;

    public DirProcessor(String dirPath, FileHandler fileHandler) {
        this.path = Paths.get(dirPath);
        this.fileHandler = fileHandler;
    }

    public void walkAndProcess() throws IOException {
        if (path.toString().equals("")) {
            throw new IOException("CAN NOT specify a BLANK path");
        }

        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String filePath = file.toString();
                try {
                    fileHandler.handleFile(filePath);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
