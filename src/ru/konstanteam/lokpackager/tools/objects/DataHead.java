package ru.konstanteam.lokpackager.tools.objects;

import ru.konstanteam.lokpackager.tools.Tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class DataHead {
    public final String path;

    public DataHead(String path) {
        this.path = path;
    }

    public void putInStream(BufferedOutputStream stream) throws IOException {
        int dataLength = Tools.stringSizeInBuffer(path);
        ByteBuffer byteBuffer = ByteBuffer.allocate(dataLength);

        Tools.putString(byteBuffer, path);

        stream.write(Tools.longToBytes(dataLength));
        stream.write(byteBuffer.array());
    }

    public DataHead(BufferedInputStream stream) throws IOException {
        byte[] headSizeArray = new byte[Long.BYTES];
        if (stream.read(headSizeArray) != Long.BYTES){
            path = null;
            return;
        }

        int headSize = (int)Tools.bytesToLong(headSizeArray);
        if (headSize == 0){
            path = null;
            return;
        }

        byte[] head = new byte[headSize];
        if (stream.read(head) != headSize){
            path = null;
            return;
        }

        ByteBuffer headBuffer = ByteBuffer.wrap(head);
        this.path = Tools.getString(headBuffer);
    }
}
