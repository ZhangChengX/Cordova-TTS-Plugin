# Cordova-TTS-Plugin

This instruction is for Cordova 3.0 and up.

### Description

Cordova TTS(Text To Speech) plugin for Android. (will support IOS later)

This plugin allows you to invoke native method form javascript code, converting text into speech, making your app speakable.

Before you installing this plugin, please go to [voicecloud](http://open.voicecloud.cn/) and sign up an APPID for TTS API.

### Installation

##### Automatically (CLI)
```
$ cordova plugin add https://github.com/etonn/Cordova-TTS-Plugin.git
```
##### Manually

1. Add following xml to res/xml/config.xml :
   ```
   <!-- for Android -->
   <feature name="Tts">
       <param name="android-package" value="com.etonn.plugins.Tts" />
   </feature>
   ```

2. Add permission to AndroidManifest.xml :
   ```
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
   <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
   ```

3. Reference tts.js in index.html
   ```
   <script type="text/javascript" charset="utf-8" src="plugins/tts.js"></script>
   ```

4. Copy files

   for Android platform: 
   
   ```
   Copy Tts.java to platforms/android/src/com/etonn/plugins/
   Copy tts.js to platforms/android/assets/www/plugins/com.etonn.plugins.tts/www/
   ```

5. Edit cordova_plugins.js
   ```
   module.exports = [
      {
         "file": "plugins/com.etonn.plugins.tts/www/tts.js",
         "id": "com.etonn.plugins.tts.Tts",
         "clobbers": [
            "window.plugins.tts"
         ]
       }
      ];
   module.exports.metadata = 
      {
         "com.etonn.plugins.tts": "0.0.1"
      }
   ```
6. Make sure you include a reference to cordova.js in your index.html

   ```
   <script type="text/javascript" src="cordova.js"></script>
   ```

### Usage
```
<a href="javascript:window.plugins.tts.start('text will be spoken');">start</a>
```
```
<a href="javascript:window.plugins.tts.stop();">stop</a>
```

### License
[Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.txt)

