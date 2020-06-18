package ru.konstanteam.lokpackager.tools;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface InputGenerator {
    InputData get() throws IOException;
    boolean available();
}
