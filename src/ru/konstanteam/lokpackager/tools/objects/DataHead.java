package ru.konstanteam.lokpackager.tools.objects;

import ru.konstanteam.lokpackager.tools.Tools;

import java.io.*;
import java.nio.ByteBuffer;

public class DataHead {
    public final String path;
    public final long size;

    public DataHead(String path, long size) {
        this.path = path;
        this.size = size;
    }

    public void putInStream(OutputStream stream) throws IOException {
        int dataLength = Tools.stringSizeInBuffer(path) + Long.BYTES;
        ByteBuffer byteBuffer = ByteBuffer.allocate(dataLength);

        Tools.putString(byteBuffer, path);
        byteBuffer.putLong(size);

        stream.write(Tools.longToBytes(dataLength));
        stream.write(byteBuffer.array());
    }

    public DataHead(InputStream stream) throws IOException {
        byte[] headSizeArray = new byte[Long.BYTES];
        if (stream.read(headSizeArray) != Long.BYTES){
            path = null;
            size = 0;
            return;
        }

        int headSize = (int)Tools.bytesToLong(headSizeArray);
        if (headSize == 0){
            path = null;
            size = 0;
            return;
        }

        byte[] head = new byte[headSize];
        if (stream.read(head) != headSize){
            path = null;
            size = 0;
            return;
        }

        ByteBuffer headBuffer = ByteBuffer.wrap(head);
        this.path = Tools.getString(headBuffer);
        this.size = headBuffer.getLong();
    }
}
