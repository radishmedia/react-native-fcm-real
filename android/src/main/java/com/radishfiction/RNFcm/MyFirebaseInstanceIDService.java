/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.radishfiction.RNFcm;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends ReactContextBaseJavaModule {

    private static final String TAG = "MyFirebaseIIDService";

    private ReactContext mReactContext;

    public MyFirebaseInstanceIDService(ReactApplicationContext reactContext) {
      super(reactContext);
      mReactContext = reactContext;
    }

    @Override
    public String getName() {
      return "RNFcm";
    }

    private void sendEvent(String eventName, Object params) {
        mReactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Send the token to ReactNative whenever the token is refreshed.
        sendTokenToReactNative(refreshedToken);
    }
    // [END refresh_token]

    private void sendTokenToReactNative(String token){
        if( token == null ) return;
        WritableMap params = Arguments.createMap();
        params.putString("deviceToken", token);
        sendEvent("RNFcmEventRefreshToken", params);
    }

    @ReactMethod
    public void requestPermissions() {
      String token = FirebaseInstanceId.getInstance().getToken();
      sendTokenToReactNative(token);
    }
}
