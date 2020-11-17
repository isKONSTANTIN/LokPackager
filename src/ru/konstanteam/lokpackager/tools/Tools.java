package ru.konstanteam.lokpackager.tools;

import java.io.*;
import java.nio.ByteBuffer;

public class Tools {
    public static int stringSizeInBuffer(String string){
        byte[] bytes = string.getBytes();
        return bytes.length + Integer.BYTES;
    }

    public static void putString(ByteBuffer byteBuffer, String string) {
        byte[] bytes = string.getBytes();
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
    }

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();

        return buffer.getLong();
    }

    public static String getString(ByteBuffer byteBuffer) {
        int length = byteBuffer.getInt();
        byte[] bytes = new byte[length];
        byteBuffer.get(bytes);

        return new String(bytes);
    }

    public static void copyData(long size, OutputStream outputStream, InputStream inputStream) throws IOException {
        for (long i = 0; i < size; i++) {
            byte inputByte = (byte) inputStream.read();
            outputStream.write(inputByte);
        }

        outputStream.flush();
    }

    public static long copyData(OutputStream outputStream, InputStream inputStream) throws IOException {
        long size = 0;
        int inputByte;

        while ((inputByte = inputStream.read()) != -1){
            outputStream.write(inputByte);
            size++;
        }

        outputStream.flush();

        return size;
    }

}
