package ru.konstanteam.lokpackager.tools;

import java.io.BufferedOutputStream;
import java.io.IOException;

public interface OutputGenerator {
    BufferedOutputStream getOutput(String path) throws IOException;
}
