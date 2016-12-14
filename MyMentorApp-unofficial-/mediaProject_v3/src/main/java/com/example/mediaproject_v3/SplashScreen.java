package com.example.mediaproject_v3;

import android.app.Activity;  
import android.content.Intent;  
import android.os.Bundle;  
import android.os.Handler;  
   
public class SplashScreen extends Activity {  
   
    // Splash screen timer  
    private static int SPLASH_TIME_OUT = 2000; //開啟畫面時間(3秒)  
   
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.splash);  
   
        new Handler().postDelayed(new Runnable() {  
   
            /* 
             * Showing splash screen with a timer. This will be useful when you 
             * want to show case your app logo / company 
             */  
   
            @Override  
            public void run() {  
                // This method will be executed once the timer is over  
                // Start your app main activity  
                Intent i = new Intent(SplashScreen.this, MainActivity.class); //MainActivity為主要檔案名稱  
                startActivity(i);  
   
                // close this activity  
                finish();  
            }  
        }, SPLASH_TIME_OUT);  
    }  
    
    
    
   
}  