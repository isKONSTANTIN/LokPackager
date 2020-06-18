package ru.konstanteam.lokpackager;

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
        Packager packager = new Packager(args[0]);

        for (String arg : Arrays.copyOfRange(args, 1, args.length)) {
            File file = new File(arg);

            if (file.canRead())
                packager.addFile(file);
        }

        packager.build();
        packager.closePackageStream();
        System.out.println("Successful created " + args[0] + "!");
    }

    public static void handleUnpack(String[] args) throws IOException {
        UnPackager unPackager = new UnPackager(args[0], args[1]);
        unPackager.unpack();
        unPackager.closePackageStream();
    }

    public static void showTip() {
        System.out.println("LokPackager 0.1");
        System.out.println("-p <package_file> ... - pack files");
        System.out.println("-u <package_file> <path_to_unpack> - unpack files");
    }
}
