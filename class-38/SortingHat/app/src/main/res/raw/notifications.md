# Notifications Guide

1. initialize notifications on aws
    - amplify add notifications
    - Choose FCM for firebase cloud messaging
    - give it a name
    - asks for api key
        - this is the server api key from a firebase project
1. Create a Google Firebase Project
    - Click new Project, give it a name
    - It can host multiple apps so the name could be something really generic
    - click settings icon, click project settins, tab into `cloud messaging`
    - Paste the Server Key into the amplify cli
1. Add dependenencies for Firebase and Amplify Notifications
    - ```
     implementation 'com.google.android.gms:play-services-auth:15.0.1'

    implementation 'com.google.firebase:firebase-core:16.0.1'
    implementation 'com.google.firebase:firebase-messaging:17.3.0'

    implementation 'com.amazonaws:aws-android-sdk-pinpoint:2.11.+'
    implementation ('com.amazonaws:aws-android-sdk-mobile-client:2.11.+@aar'){ transitive = true}
    ```
1. Add plugin for google services
    - ```
    apply plugin: 'com.google.gms.google-services'
    ```
    - On any line of code not in {brackets} preferably near your other plugin

 1. In Project build.gradle, add this dependency
    - ```
    classpath "com.google.gms:google-services:4.0.1"
    ```
 1.  Update the manifest file with a Service
 1. Add to the manifest
    - ```
    <service
        android:name=".PushListenerService">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT" />
        </intent-filter>
    </service>
    ```
 1. Add the PinpointManager into the Main Activity (Reference the Demo Code)
 1. Create and fill in the PushListenerService class
 1. Connect our app to Firebase
    - on firebase go back to settings, the tab `general`
    - Add an app
    - Copy the google-services.json to the `app` direcory
    - add `google-services.json` to your gitignore
    - skip adding any more dependencies
 1. You can run the app now NOPE
 1. IF IT IS MISSING: In src/main/res/raw/awsconfiguration.json
    - Add ```
    "PinpointAnalytics": {
            "Default" : {
                "AppId": "__YOUR APP ON AWS PINPOINT__",
                "Region": "us-west-2"
            }
        }
        ```
    - The AppId can be found in the pinpoint console in settings/general settings
 1. The notification will PROBABLY only show up if the app is in the background, though it should still log
    - the log will look like Message: {}, that does not mean it is empty
 1. You can then send notifications from AWS pinpoint or Firebase Cloud Messaging