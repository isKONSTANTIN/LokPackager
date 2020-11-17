package ru.konstanteam.lokpackager.base;

import ru.konstanteam.lokpackager.tools.OutputGenerator;
import ru.konstanteam.lokpackager.tools.Tools;
import ru.konstanteam.lokpackager.tools.objects.DataHead;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class PackageInfo {
    protected final String packageSignature;
    protected final ArrayList<DataHead> heads = new ArrayList<>();

    public PackageInfo(BufferedInputStream packageStream) throws IOException {
        packageStream = new BufferedInputStream(new GZIPInputStream(packageStream));

        byte[] packageHeadArray = new byte[Packager.HEAD_SIZE];
        packageStream.read(packageHeadArray);
        ByteBuffer packageHead = ByteBuffer.wrap(packageHeadArray);

        packageSignature = Tools.getString(packageHead);

        while (packageStream.available() != 0) {
            DataHead head = new DataHead(packageStream);

            if (head.path == null || head.path.isEmpty())
                break;
            heads.add(head);

            long skipped = 0;
            while ((skipped += packageStream.skip(head.size - skipped)) < head.size);
        }
    }

    public String getPackageSignature() {
        return packageSignature;
    }

    public ArrayList<DataHead> getHeads() {
        return heads;
    }
}
