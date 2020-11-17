package ru.konstanteam.lokpackager.files;

import ru.konstanteam.lokpackager.base.Packager;
import ru.konstanteam.lokpackager.tools.InputGenerator;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class FilesPackager extends Packager {
    protected FilesInputGenerator generator;

    public FilesPackager(File packageFile) throws IOException {
        this(new FileOutputStream(packageFile));
    }

    public FilesPackager(OutputStream packageStream) throws IOException {
        super(packageStream, new FilesInputGenerator());

        this.generator = (FilesInputGenerator) this.inputGenerator;
    }

    public void addFileOrDirectory(File file) {
        generator.addFileOrDirectory(file);
    }

    public void close() throws IOException {
        packageStream.close();
    }
}
