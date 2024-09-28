package com.android.support;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class GameInfo {

    String pkgName = "";
    String tmp = "/data/local/tmp";
    String abi = "";
    String gameAbi = "";

    public  GameInfo(String pkgName) {
        this.pkgName = pkgName;

        try {
            if (!isRooted()) {
                System.out.println("App is not running as root.");
                return;
            }
            gameAbi = getCommandOutputAsRoot("dumpsys package " + pkgName + " | grep \"primaryCpuAbi\" | awk -F '=' '{print $2}'");
            abi = getCommandOutputAsRoot("getprop ro.product.cpu.abi");
            abi = adjustAbi(abi, gameAbi);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String adjustAbi(String abi, String gameAbi) {
        if (abi == null || gameAbi == null) {
            return null;  // Null check to avoid exceptions
        }

        switch (abi) {
            case "arm64-v8a":
            case "armeabi-v7a":
                return gameAbi;
            case "x86_64":
                if (gameAbi.equals("armeabi-v7a")) {
                    return "x86";
                }
                return abi;
            case "x86":
                return abi;
            default:
                // If the device ABI is not supported
                return null;
        }
    }

    private static String getCommandOutputAsRoot(String command) throws IOException {
        // Run the command as root by using 'su -c'
        ProcessBuilder processBuilder = new ProcessBuilder("su", "-c", command);
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        return output.toString().trim();
    }


    private static boolean isRooted() {
        // Check if the app is running as root using 'su -c id'
        try {
            String output = getCommandOutputAsRoot("id");
            return output != null && output.contains("uid=0");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPkgName() {
        return pkgName;
    }

    public String getAbi() {
        return abi;
    }

    public String getTmp() {
        return tmp;
    }

    public String getGameAbi() {
        return gameAbi;
    }
}
