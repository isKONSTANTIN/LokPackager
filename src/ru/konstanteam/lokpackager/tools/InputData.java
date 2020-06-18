package ru.konstanteam.lokpackager.tools;

import java.io.BufferedInputStream;

public class InputData {
    public final String path;
    public final BufferedInputStream stream;
    public final long size;

    public InputData(String path, BufferedInputStream stream, long size) {
        this.path = path;
        this.stream = stream;
        this.size = size;
    }
}
