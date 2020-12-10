package ru.konstanteam.lokpackager.tools;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface OutputGenerator {
    OutputStream getOutput(String path) throws IOException;
}
