package com.android.support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.topjohnwu.superuser.Shell;

public class MainActivity extends Activity {

    //Only if you have changed MainActivity to yours and you wanna call game's activity.
    public String GameActivity = "com.unity3d.player.UnityPlayerActivity";
    public boolean hasLaunched = false;

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        // Your code here
    }


    //To call onCreate, please refer to README.md
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApkExtract.extractLibDirToAppData(this);
        /*Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable e) {
                        Log.e("AppCrash", "Error just lunched ");
                    }
                });*/


        //To launch game activity
        if (!hasLaunched) {
            try {
                //Start service
                hasLaunched = true;
                //Launch mod menu.
                MainActivity.this.startActivity(new Intent(MainActivity.this, Class.forName(MainActivity.this.GameActivity)));
                Main.Start(this);
                return;
            } catch (ClassNotFoundException e) {
                Log.e("Mod_menu", "Error. Game's main activity does not exist");
                //Uncomment this if you are following METHOD 2 to launch menu
                //Toast.makeText(MainActivity.this, "Error. Game's main activity does not exist", Toast.LENGTH_LONG).show();
            }
        }

        //Launch mod menu.
       // Main.StartWithoutPermission(this);
        Main.Start(this);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
