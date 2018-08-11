package com.eliteai.blink.blinkApi;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bailingcloud.bailingvideo.BlinkEngine;
import com.bailingcloud.bailingvideo.BlinkEngineEventHandler;
import com.bailingcloud.bailingvideo.engine.binstack.http.BlinkHttpClient;
import com.bailingcloud.bailingvideo.engine.binstack.util.BlinkSessionManager;
import com.bailingcloud.bailingvideo.engine.context.BlinkContext;
import com.bailingcloud.bailingvideo.engine.view.BlinkVideoView;
import com.eliteai.blink.C;
import com.eliteai.blink.MyApplication;
import com.eliteai.blink.utils.LogUtlis;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author MR.ZHANG
 * @create 2018-08-10 14:38
 */
public class BlinkServer {
    private static final String TAG = "Blink";

    public void logonToServer() {
        new Thread(() -> {
            String deviceId = "";
            String iUserName = "";
            String channelID = "";
            deviceId = getDeviceId();
            iUserName = getUserName();
            channelID = getChannelID();
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = BlinkSessionManager.getInstance().getString(BlinkContext.BLINK_UUID);
            }
            final String token = BlinkHttpClient.getInstance().doPost(C.Net.BlinkCmpToken, "uid=" + deviceId + "&appid=" + C.APPID);
            LogUtlis.e(TAG, "token : " + token);
            if (TextUtils.isEmpty(token) || TextUtils.isEmpty(deviceId)) {
                LogUtlis.e(TAG, "获取token/deviceID失败，请重新尝试！");
            }
            Map<String, String> mCollect = Stream.of(token.split("&")).map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
            Optional.of(mCollect).ifPresent(x -> {
                MyApplication.dataCache.setBlinkUid(x.get("uid"));
                MyApplication.dataCache.setBlinkToken(x.get("sign"));
            });
        }
        ).start();
    }
    public void initHandler() {
        BlinkEngine.getInstance().setBlinkEngineEventHandler(new BlinkEngineEventHandler() {
            @Override
            public void onConnectionStateChanged(int mI) {
                LogUtlis.e(TAG,"onConnectionStateChanged : " + mI);
            }

            @Override
            public void onLeaveComplete(boolean success) {
                LogUtlis.e(TAG,"onLeaveComplete : " + success);
            }

            @Override
            public void onUserJoined(String mS, String mS1, BlinkEngine.UserType mUserType, long mL, int mI) {
                LogUtlis.e(TAG,"onUserJoined : mS : " + mS + "---mS1 : " + mS1 + "");
            }

            @Override
            public void onNotifyUserVideoCreated(String mS, String mS1, BlinkEngine.UserType mUserType, long mL, int mI) {
                LogUtlis.e(TAG,"onNotifyUserVideoCreated : mS : " + mS + "---mS1 : " + mS1 + "---mUserType : " + mUserType + "---mL : " + mL + "----mI : " + mI);
            }

            @Override
            public void onUserLeft(String mS) {
                LogUtlis.e(TAG,"onUserLeft : " + mS);
            }

            @Override
            public void OnNotifyUserVideoDestroyed(String mS) {
                Log.e(TAG, "OnNotifyUserVideoDestroyed: mS : " + mS );
            }

            @Override
            public void onWhiteBoardURL(String mS) {
                LogUtlis.e(TAG,"onWhiteBoardURL : " + mS);
            }

            @Override
            public void onNetworkSentLost(int mI) {
                LogUtlis.e(TAG,"onNetworkSentLost : " + mI);
            }

            @Override
            public void onNetworkReceiveLost(int mI) {
                LogUtlis.e(TAG,"onNetworkReceiveLost : " + mI);
            }

            @Override
            public int onTextureFrameCaptured(int mI, int mI1, int mI2) {
                LogUtlis.e(TAG,"onTextureFrameCaptured : mI : " + mI + "----mI1 : " + mI1 + "----mI2 : " + mI2);
                return 0;
            }

            @Override
            public void onControlAudioVideoDevice(int mI) {
                LogUtlis.e(TAG,"onControlAudioVideoDevice : " + mI);
            }

            @Override
            public void onNotifyControlAudioVideoDevice(String mS, BlinkEngine.BlinkDeviceType mBlinkDeviceType, boolean mB) {
                LogUtlis.e(TAG,"onNotifyControlAudioVideoDevice : mS---->" + mS +"----mB---->" + mB + "----mBlinkDeviceType : " + mBlinkDeviceType);
            }

            @Override
            public void onNotifyCreateWhiteBoard(String mS) {
                LogUtlis.e(TAG,"onNotifyCreateWhiteBoard : " + mS);
            }

            @Override
            public void onNotifySharingScreen(String mS, boolean mB) {
                LogUtlis.e(TAG,"onNotifySharingScreen : mS ---> " + mS + "----mB---->" + mB);
            }
        });
    }

    public void joinChanner() {
        BlinkEngine.getInstance().joinChannel(getDeviceId(), getUserName(),MyApplication.dataCache.getBlinkToken(),MyApplication.dataCache.getBlinkUid());
    }

    public BlinkVideoView setLocalView(Context context) {
        //使用BlinkEngine提供的方法创建BlinkVideoView
        BlinkVideoView localSurface = BlinkEngine.createVideoView(context);
        //创建好的view传递到BlinkEngine中。BlinkEngine会把相应的视频加载到此视图中
        BlinkEngine.getInstance().setLocalVideoView(localSurface);
        return localSurface;
    }

    private String getChannelID() {
        return "channelID";
    }

    private String getUserName() {
        return "userName";
    }

    private String getDeviceId() {
        return "543534534";
    }
}
