package ru.konstanteam.lokpackager;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class UnPackager {
    protected BufferedInputStream packageStream;
    protected String outputPath = "./";
    protected ArrayList<File> files = new ArrayList<>();

    public UnPackager(String packageFilePath, String outputPath) throws FileNotFoundException {
        this(new File(packageFilePath), outputPath);
    }

    public UnPackager(File packageFile, String outputPath) throws FileNotFoundException {
        this(new BufferedInputStream(new FileInputStream(packageFile)), outputPath);
    }

    public UnPackager(BufferedInputStream packageStream, String outputPath) {
        this.packageStream = packageStream;
        this.outputPath = outputPath;
    }

    public void unpack() throws IOException {
        packageStream.skip(Packager.HEAD_SIZE);

        while (packageStream.available() != -1) {
            byte[] nameFileBufferArray = new byte[Packager.FILES_NAMES_SIZE];
            ByteBuffer nameFileBuffer = ByteBuffer.allocate(Packager.FILES_NAMES_SIZE);

            if (packageStream.read(nameFileBufferArray) == 0)
                break;

            nameFileBuffer.put(nameFileBufferArray);
            nameFileBuffer.flip();
            String newFileName = Tools.getString(nameFileBuffer);

            if (newFileName.isEmpty())
                break;

            File newFile = new File(outputPath + newFileName);

            if (!newFile.createNewFile())
                throw new IOException("Cannot create new file!");

            files.add(newFile);

            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(newFile));

            Tools.readFileFromStream(outputStream, packageStream);

            outputStream.close();
        }
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void closePackageStream() throws IOException {
        packageStream.close();
    }
}