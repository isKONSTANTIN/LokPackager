package ru.konstanteam.lokpackager.base;

import ru.konstanteam.lokpackager.tools.InputData;
import ru.konstanteam.lokpackager.tools.InputGenerator;
import ru.konstanteam.lokpackager.tools.Tools;
import ru.konstanteam.lokpackager.tools.objects.DataHead;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Packager {
    public static final String PACKAGE_SIGNATURE = "LOK_PACKAGE_FILE_0_4";
    public static final int HEAD_SIZE = 64;

    protected ArrayList<BufferedInputStream> streams = new ArrayList<>();
    protected BufferedOutputStream packageStream;
    protected InputGenerator inputGenerator;

    public Packager(BufferedOutputStream packageStream, InputGenerator inputGenerator) {
        this.packageStream = packageStream;
        this.inputGenerator = inputGenerator;
    }

    public void build() throws IOException {
        ByteBuffer head = ByteBuffer.allocate(HEAD_SIZE);
        Tools.putString(head, PACKAGE_SIGNATURE);
        packageStream.write(head.array());
        packageStream.flush();

        while (inputGenerator.available()){
            InputData inputData = inputGenerator.get();
            streams.add(inputData.stream);

            new DataHead(inputData.path).putInStream(packageStream);

            Tools.writeDataToStream(inputData.size, packageStream, inputData.stream);

            inputData.stream.close();
        }
    }

    public ArrayList<BufferedInputStream> getStreams() {
        return streams;
    }
}
