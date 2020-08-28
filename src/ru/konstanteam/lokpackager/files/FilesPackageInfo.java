package ru.konstanteam.lokpackager.files;

import ru.konstanteam.lokpackager.base.PackageInfo;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class FilesPackageInfo extends PackageInfo {
    public FilesPackageInfo(File packageFile) throws IOException {
        super(new BufferedInputStream(new GZIPInputStream(new FileInputStream(packageFile))));
    }
}
