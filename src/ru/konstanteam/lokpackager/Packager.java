package ru.konstanteam.lokpackager;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Packager {
    public static final String FILE_SIGNATURE = "LOK_PACKAGE_FILE_0_1";
    public static final int HEAD_SIZE = 512;
    public static final int FILES_NAMES_SIZE = 256;

    protected ArrayList<File> files = new ArrayList<>();
    protected BufferedOutputStream packageStream;

    public Packager(String packageFilePath) throws FileNotFoundException {
        this(new File(packageFilePath));
    }

    public Packager(File packageFile) throws FileNotFoundException {
        this(new BufferedOutputStream(new FileOutputStream(packageFile)));
    }

    public Packager(BufferedOutputStream packageStream) {
        this.packageStream = packageStream;
    }

    public void addFile(File file) throws IOException {
        if (!file.canRead())
            throw new IOException("File not readable");

        files.add(file);
    }

    public void build() throws IOException {
        ByteBuffer head = ByteBuffer.allocate(HEAD_SIZE);
        Tools.putString(head, FILE_SIGNATURE);
        packageStream.write(head.array());
        packageStream.flush();

        for (File file : files) {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            ByteBuffer nameFileBuffer = ByteBuffer.allocate(FILES_NAMES_SIZE);
            Tools.putString(nameFileBuffer, file.getName());
            packageStream.write(nameFileBuffer.array());

            Tools.writeFileToStream(file.length(), packageStream, inputStream);

            inputStream.close();
        }

        files.clear();
    }

    public void closePackageStream() throws IOException {
        packageStream.close();
    }
}
