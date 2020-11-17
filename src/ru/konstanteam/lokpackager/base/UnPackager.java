package ru.konstanteam.lokpackager.base;

import ru.konstanteam.lokpackager.tools.OutputGenerator;
import ru.konstanteam.lokpackager.tools.Tools;
import ru.konstanteam.lokpackager.tools.objects.DataHead;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class UnPackager {
    protected InputStream packageStream;
    protected OutputGenerator outputGenerator;

    public UnPackager(InputStream packageStream, OutputGenerator outputGenerator) throws IOException {
        this.packageStream = new BufferedInputStream(new GZIPInputStream(packageStream));
        this.outputGenerator = outputGenerator;
    }

    public int unpack() throws IOException {
        int heads = 0;

        packageStream.skip(Packager.HEAD_SIZE);

        while (packageStream.available() != 0) {
            DataHead head = new DataHead(packageStream);

            if (head.path == null || head.path.isEmpty())
                break;

            OutputStream outputStream = outputGenerator.getOutput(head.path);

            Tools.copyData(head.size, outputStream, packageStream);
            heads++;

            outputStream.close();
        }

        return heads;
    }
}