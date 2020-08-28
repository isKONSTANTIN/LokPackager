package ru.konstanteam.lokpackager.files;

import ru.konstanteam.lokpackager.base.UnPackager;
import ru.konstanteam.lokpackager.tools.OutputGenerator;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class FilesUnPackager extends UnPackager {
    protected FilesOutputGenerator generator;

    public FilesUnPackager(File packageFile, File outputDirectory) throws IOException {
        this(new BufferedInputStream(new GZIPInputStream(new FileInputStream(packageFile))), outputDirectory.getPath());
    }

    public FilesUnPackager(BufferedInputStream packageStream, String outputDirectory) throws IOException {
        super(packageStream, new FilesOutputGenerator(outputDirectory));

        generator = (FilesOutputGenerator)outputGenerator;
    }

    public void close() throws IOException {
        packageStream.close();
    }
}
