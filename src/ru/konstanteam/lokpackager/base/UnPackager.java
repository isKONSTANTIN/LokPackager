package ru.konstanteam.lokpackager.base;

import ru.konstanteam.lokpackager.tools.OutputGenerator;
import ru.konstanteam.lokpackager.tools.Tools;
import ru.konstanteam.lokpackager.tools.objects.DataHead;

import java.io.*;
import java.util.ArrayList;

public class UnPackager {
    protected BufferedInputStream packageStream;
    protected OutputGenerator outputGenerator;
    protected ArrayList<BufferedOutputStream> streams = new ArrayList<>();

    public UnPackager(BufferedInputStream packageStream, OutputGenerator outputGenerator) {
        this.packageStream = packageStream;
        this.outputGenerator = outputGenerator;
    }

    public int unpack() throws IOException {
        int heads = 0;

        packageStream.skip(Packager.HEAD_SIZE);

        while (packageStream.available() != 0) {
            DataHead head = new DataHead(packageStream);

            if (head.path == null || head.path.isEmpty())
                break;

            BufferedOutputStream outputStream = outputGenerator.getOutput(head.path);
            streams.add(outputStream);

            Tools.copyData(head.size, outputStream, packageStream);
            heads++;

            outputStream.close();
        }

        return heads;
    }

    public ArrayList<BufferedOutputStream> getStreams() {
        return streams;
    }
}