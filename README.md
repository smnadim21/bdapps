# bdapps

![Jitpack - Version](https://img.shields.io/jitpack/v/github/smnadim21/bdapps?color=green)
![PyPI - Status](https://img.shields.io/pypi/status/django)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

android support library for BDapps.

### How to 



Step 1. Add the JitPack repository and google gsm service to your build file

Add it in your root build.gradle at the end of dependencies:

     buildscript  {
     dependencies {
      ..
          //kindly use gradle version: 4.0.1. e.g: "com.android.tools.build:gradle:4.0.1"
        classpath "com.android.tools.build:gradle:4.0.1"
        classpath 'com.google.gms:google-services:4.3.3'//add this classpath
          }

    }
Add it in your root build.gradle at the end of repositories:

     allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
    
    
Step 2. Add compile in your module build.gradle at the end of android:

    android {
      ....
        compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
      }

    }

Add the dependency    

    dependencies {
                implementation 'com.github.smnadim21:bdapps:0.0.5'
      }
    
Step 3.Implement SubscriptionStatusListener.
 
        //In activity or fragment will have to implements SubscriptionStatusListener
        
         public class MainActivity extends AppCompatActivity implements SubscriptionStatusListener {
         
        //flag is to check Subscribtion status
        boolean flag=false;
    
        //Overriding methods of SubscriptionStatusListener
        @Override
            public void onSuccess(boolean isSubscribed) {
                if (!isSubscribed) {
                    flag = false;
                }
                else {
                    flag = true;
                }
            }
            @Override
            public void onFailed(String message) {

              }
            }
Step 4. Register Your App with server.

      Write below lines inside onCreate()// of any activity to sync and register your app with BDApps server! 

        Constants.MSG_TEXT = "start whatever!";
        Constants.APP_ID = "APP_123456";
        Constants.USSD = "2345";
        Constants.APP_PASSWORD = "Write Your App Password!";
        
        
Step 5. USAGE TIME!

        BdApps.registerAPP();// use this method to register

        BdApps.checkSubscriptionStatus(this);  // use this method to check if user is subscribed or not!
     
        //lock any content anywhere!

         if(!flag)// this line checks if the content is locked or not
                // your content is locked here
        {
            BdApps.showDialog(this, MainActivity.this); // BdApps shows dialogue for charging! via SMS [  pass Activity.this/ getActivity() / (Activity) context inside  as first parameter and Activity.this/Fragement.this as Second parameter in BdApps.showDialog() method! ]
            
            or 
            
            BdApps.showDialogUSSD(this, MainActivity.this); // BdApps shows dialogue for charging! via USSD DIAL [  pass Activity.this/ getActivity() / (Activity) context inside  as first parameter and Activity.this/Fragement.this as Second parameter in BdApps.showDialog() method! ]
           
           } else {
                //Your content is unlocked here!
            }

                
Step 5. ONE MORE THING!
      inside AndroidManifest.xml add below lines on desired section

          <?xml version="1.0" encoding="utf-8"?>
          <manifest ...
                    ...>
               <uses-permission android:name="android.permission.INTERNET" />

              <application
                    ...
                    android:usesCleartextTraffic="true">
                    <activity>
                    ....
                    </activity>
              </application>

          </manifest>
          
##Example:
          
        package com.shakiba.testproject;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

        import com.smnadim21.api.BdApps;
        import com.smnadim21.api.Constants;
        import com.smnadim21.api.listener.SubscriptionStatusListener;

        public class MainActivity extends AppCompatActivity implements SubscriptionStatusListener {

            boolean flag=false;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                Constants.MSG_TEXT = "start 123sa";
                Constants.APP_ID = "APP_016475";
                Constants.APP_PASSWORD = "f36f24ba800203e608718261e2d7d725";

                BdApps.registerAPP();// use this method to register

                BdApps.checkSubscriptionStatus(this);
            }

            @Override
            public void onSuccess(boolean isSubscribed) {
                if (!isSubscribed) {
                    flag = false;
                }
                else {
                    flag = true;
                }

            }

            @Override
            public void onFailed(String message) {

            }

            public void CheckPayment(View view) {
                if(!flag)// this line checks if the content is locked or not
                // your content is locked here
                {
                    BdApps.showDialog(this, MainActivity.this);// BdApps shows dialogue for charging!  [  pass Activity.this/ getActivity() / (Activity) context inside  as first parameter and Activity.this/Fragement.this as Second parameter in BdApps.showDialog() method! ]
                } else {
                    //Your content is unlocked here!
                    startActivity(new Intent(this,SecondActivity.class));
                }
            }
        }


To use CAAS charging on your app.

STEP.1 Implement PurchaseStatusListener on youe desired Activity




    public class MainActivity extends AppCompatActivity implements SubscriptionStatusListener, PurchaseStatusListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.MSG_TEXT = "start abcd";
        Constants.APP_ID = "your app_id";
        Constants.USSD = "2346"; // USSD dialing if any
        Constants.APP_PASSWORD = "your_pass"; 
        BdApps.registerAPP();
        BdApps.checkSubscriptionStatus(this);


    }
    

         @Override
         public void onPaymentSuccess(boolean paymentStats, String message, String item_code) {

         }

         @Override
         public void onPaymentFailed(String message) {

         }
     }
     
STEP.2 PUT ypur content identifier here for which you want to get charge on main class globally, Try declaring them as final String variable


    public class MainActivity extends AppCompatActivity implements SubscriptionStatusListener, PurchaseStatusListener {
    
    private final String ITEM_1 = "APP_000000-0001";
    private final String ITEM_2 = "APP_000000-0000002"; //you must remember this codes 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.MSG_TEXT = "start abcd";
        Constants.APP_ID = "your app_id";
        Constants.USSD = "2346"; // USSD dialing if any
        Constants.APP_PASSWORD = "your_pass"; 
        BdApps.registerAPP();
        BdApps.checkSubscriptionStatus(this);


    }
    

         @Override
         public void onPaymentSuccess(boolean paymentStats, String message, String item_code) {

         }

         @Override
         public void onPaymentFailed(String message) {

         }
     }
     
     
 STEP.3 REQUEST for charging from your content  with charging amount


    public class MainActivity extends AppCompatActivity implements SubscriptionStatusListener, PurchaseStatusListener {
    
    private final String ITEM_1 = "APP_000000-0001";
    private final String ITEM_2 = "APP_000000-0000002"; //you must remember this codes 
    private final CHARGE = "5"; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.MSG_TEXT = "start abcd";
        Constants.APP_ID = "your app_id";
        Constants.USSD = "2346"; // USSD dialing if any
        Constants.APP_PASSWORD = "your_pass"; 
        BdApps.registerAPP();
        BdApps.checkSubscriptionStatus(this);
        
        
        // example : ID of your view is caas
           findViewById(R.id.caas1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    
                    // requesting CAAS, CHARGE = charge amount, ITEM_1 = your item code
                        BdApps.requestCAAS(CHARGE, ITEM_1, MainActivity.this); //  pass activity here
                    }
                });


    }
    

         @Override
         public void onPaymentSuccess(boolean paymentStats, String message, String item_code) {

         }

         @Override
         public void onPaymentFailed(String message) {

         }
     }
     
     
  STEP.4 CATCH for payment information  for your content  inside onPaymentSuccess


    public class MainActivity extends AppCompatActivity implements SubscriptionStatusListener, PurchaseStatusListener {
    
    private final String ITEM_1 = "APP_000000-0001";
    private final String ITEM_2 = "APP_000000-0000002"; //you must remember this codes 
    private final CHARGE = "5"; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Constants.MSG_TEXT = "start abcd";
        Constants.APP_ID = "your app_id";
        Constants.USSD = "2346"; // USSD dialing if any
        Constants.APP_PASSWORD = "your_pass"; 
        BdApps.registerAPP();
        BdApps.checkSubscriptionStatus(this);
        
        
        // example : ID of your view is caas
           findViewById(R.id.caas1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    
                    // requesting CAAS, CHARGE = charge amount, ITEM_1 = your item code
                        BdApps.requestCAAS(CHARGE, ITEM_1, MainActivity.this); //  pass activity here
                    }
                });


    }
    

         @Override
         public void onPaymentSuccess(boolean paymentStats, String message, String item_code) {
         
          switch (item_code)
           {
               case ITEM_1:
               {
                     //todo  item_1 stuffs here
                     Toast.makeText(MainActivity.this, paymentStats + " for ITEM_1 " + item_code + " " + message, Toast.LENGTH_LONG).show();
                     break;
               }
               case ITEM_2:
               {
                     //todo  item_2 stuffs here
                     Toast.makeText(MainActivity.this, paymentStats + " for ITEM_2 " + item_code + " " + message, Toast.LENGTH_LONG).show();
                     break;
               }
               default:
               {
                     // unanted items here
               }
           }

         }


THATS ALL!!
         @Override
         public void onPaymentFailed(String message) {

         }
     }
