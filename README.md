# BDapps-Charging

![Jitpack - Version](https://img.shields.io/jitpack/v/github/smnadim21/bdapps?color=green)
![PyPI - Status](https://img.shields.io/pypi/status/django)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

android support library for BDapps.

### How to 



Step 1. Add the JitPack repository to your build file 

Add it in your root build.gradle at the end of repositories:

     allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
    
    
Step 2. Add the dependency    

    dependencies {
              implementation 'com.github.smnadim21:bdapps:0.0.2'
      }
    
Step 3. Register Your App with server.

Write below lines inside onCreate() of any activity to sync and register your app with BDApps server! 

        Constants.MSG_TEXT = "start whatever!";
        Constants.APP_ID = "APP_123456";
        Constants.APP_PASSWORD = "Write Your App Password!";
        
Step 4. USAGE TIME!

        BdApps.checkSubStatus();  // use this method to check if user is subscribed or not!
        
        //lock any content anywhere!

        if (Subscription.getSubscriptionStatus()) {  // this line checks if the content is locked or not
                    // your content is locked here
                    BdApps.showDialog((Activity) context); // BdApps shows dialogue for charging!  [ pass Activity.this/ getActivity() / (Activity) context inside  BdApps.showDialog() method! ]
                } else {
                    //Your content is unlocked here!
                }

