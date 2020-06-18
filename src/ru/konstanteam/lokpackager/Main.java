package ru.konstanteam.lokpackager;

import ru.konstanteam.lokpackager.base.Packager;
import ru.konstanteam.lokpackager.base.UnPackager;
import ru.konstanteam.lokpackager.files.FilesPackager;
import ru.konstanteam.lokpackager.files.FilesUnPackager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length >= 2 && args[0].equals("-p")) {
            handlePack(Arrays.copyOfRange(args, 1, args.length));
        } else if (args.length >= 2 && args[0].equals("-u")) {
            handleUnpack(Arrays.copyOfRange(args, 1, args.length));
        } else {
            showTip();
        }
    }

    public static void handlePack(String[] args) throws IOException {
        FilesPackager packager = new FilesPackager(new File(args[0]));

        for (String arg : Arrays.copyOfRange(args, 1, args.length)) {
            File file = new File(arg);

            if (file.canRead())
                packager.addFileOrDirectory(file);
        }

        packager.build();
        packager.close();
        System.out.println("Successful created " + args[0] + "!");
    }

    public static void handleUnpack(String[] args) throws IOException {
        FilesUnPackager unPackager = new FilesUnPackager(new File(args[0]), new File(args[1]));
        unPackager.unpack();
        unPackager.close();
    }

    public static void showTip() {
        System.out.println("LokPackager 0.4");
        System.out.println("-p <package_file> ... - pack files");
        System.out.println("-u <package_file> <path_to_unpack> - unpack files");
    }
}
