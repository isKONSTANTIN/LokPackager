package ru.konstanteam.lokpackager.files;

import ru.konstanteam.lokpackager.tools.OutputGenerator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class FilesOutputGenerator implements OutputGenerator {
    protected String outputDirectory;

    public FilesOutputGenerator(String outputDirectory){
        this.outputDirectory = outputDirectory;
    }

    @Override
    public BufferedOutputStream getOutput(String path) throws IOException {
        File output = new File((outputDirectory + "/" + path).replaceAll("\\\\", "/"));
        output.getParentFile().mkdirs();
        output.createNewFile();

        return new BufferedOutputStream(new FileOutputStream(output));
    }
}
