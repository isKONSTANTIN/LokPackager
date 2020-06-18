package ru.konstanteam.lokpackager.files;

import ru.konstanteam.lokpackager.tools.InputData;
import ru.konstanteam.lokpackager.tools.InputGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public class FilesInputGenerator implements InputGenerator {
    protected ArrayList<File> files = new ArrayList<>();
    protected int mark;

    @Override
    public InputData get() throws IOException {
        File file = files.get(mark);
        mark++;

        return new InputData(
                file.getPath(),
                new BufferedInputStream(new FileInputStream(file)),
                file.length()
        );
    }

    @Override
    public boolean available() {
        return mark < files.size();
    }

    public void addFileOrDirectory(File file) {
        if (!file.isDirectory()) {
            if (file.exists() && file.canRead())
                files.add(file);
            return;
        }

        File[] files = file.listFiles();

        if (files == null)
            return;

        for (File dirFile : files)
            addFileOrDirectory(dirFile);
    }
}
