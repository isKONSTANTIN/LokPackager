package ru.konstanteam.lokpackager.tools;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class InputData {
    public final String path;
    public final InputStream stream;
    public final long size;

    public InputData(String path, InputStream stream, long size) {
        this.path = path;
        this.stream = stream;
        this.size = size;
    }
}
