package ru.konstanteam.lokpackager.files;

import ru.konstanteam.lokpackager.base.PackageInfo;

import java.io.*;

public class FilesPackageInfo extends PackageInfo {
    public FilesPackageInfo(File packageFile) throws IOException {
        super(new BufferedInputStream(new FileInputStream(packageFile)));
    }
}
