package com.android.support;
import android.app.Application;
import java.io.IOException;
import com.topjohnwu.superuser.Shell;
public class AppMain extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Runtime.getRuntime().exec("su");
            Shell.su("setenforce 0").exec();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
}
