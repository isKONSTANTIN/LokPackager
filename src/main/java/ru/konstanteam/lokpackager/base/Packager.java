package ru.konstanteam.lokpackager.base;

import ru.konstanteam.lokpackager.tools.InputData;
import ru.konstanteam.lokpackager.tools.InputGenerator;
import ru.konstanteam.lokpackager.tools.Tools;
import ru.konstanteam.lokpackager.tools.objects.DataHead;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Packager {
    public static final String PACKAGE_SIGNATURE = "LOK_PACKAGE_FILE_0_5";
    public static final int HEAD_SIZE = 64;

    protected OutputStream packageStream;
    protected InputGenerator inputGenerator;

    public Packager(OutputStream packageStream, InputGenerator inputGenerator) throws IOException {
        this.packageStream = new BufferedOutputStream(new GZIPOutputStream(packageStream));
        this.inputGenerator = inputGenerator;
    }

    public void build() throws IOException {
        ByteBuffer head = ByteBuffer.allocate(HEAD_SIZE);
        Tools.putString(head, PACKAGE_SIGNATURE);
        packageStream.write(head.array());
        packageStream.flush();

        while (inputGenerator.available()){
            InputData inputData = inputGenerator.get();

            new DataHead(inputData.path, inputData.size).putInStream(packageStream);

            Tools.copyData(inputData.size, packageStream, inputData.stream);

            inputData.stream.close();
        }
    }
}
