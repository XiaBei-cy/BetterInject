package com.android.support;
import android.content.Context;import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ApkExtract {

    public static void extractLibDirToAppData(Context context) {
        try {
            // Get the path to the APK of your app
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            String apkPath = appInfo.sourceDir;

            // Define the output directory within the app's data directory
            File appDataLibDir = new File(context.getFilesDir(), "lib");
            if (!appDataLibDir.exists()) {
                appDataLibDir.mkdirs(); // Create the directory if not already present
            }

            // Extract the lib directory from the APK and copy it to the app's data directory
            extractLibFromApk(apkPath, appDataLibDir);

            System.out.println("Lib directory copied to " + appDataLibDir.getAbsolutePath());
        } catch (PackageManager.NameNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }



    // Method to extract the lib directory from the APK
    private static void extractLibFromApk(String apkPath, File outputDir) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(apkPath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().startsWith("lib/")) {
                    // Create the file structure for the lib files
                    File file = new File(outputDir, entry.getName().replaceFirst("lib/", ""));
                    if (entry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        File parent = file.getParentFile();
                        if (parent != null && !parent.exists()) {
                            parent.mkdirs();
                        }

                        // Write the file
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = zipInputStream.read(buffer)) > 0) {
                                fos.write(buffer, 0, length);
                            }
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }

    public static void extractLibDirToTmp(MainActivity mainActivity) {
    }
}
