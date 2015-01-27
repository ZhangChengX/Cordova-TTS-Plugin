package com.etonn.plugins;

import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by ZhangCheng on 1/24/15.
 */
public class Tts extends CordovaPlugin {

    // tts
    SpeechSynthesizer mTts;
    // toast
    android.widget.Toast mToast;
    // buffering progress
    private int mPercentForBuffering = 0;
    // playing progress
    private int mPercentForPlaying = 0;
    // log tag
    private static String TAG = "TtsLog";

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // set up APPID for TTS API, sign up from http://open.voicecloud.cn/
        SpeechUtility.createUtility(webView.getContext(), SpeechConstant.APPID + "=your-APPID=");
        // init toast
        mToast = android.widget.Toast.makeText(webView.getContext(), "", android.widget.Toast.LENGTH_SHORT);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        //return super.execute(action, args, callbackContext);

        if (action.equals("start")) {
            Log.i(TAG, "start to TTS");
            showTip("准备播放...");
            String text = args.getString(0);
            // setting
            mTts= SpeechSynthesizer.createSynthesizer(webView.getContext(), null);
            setParam();
            // start to speaking
            mTts.startSpeaking(text, mTtsListener);
            // return
            callbackContext.success();
            return true;
        }

        if (action.equals("stop")) {
            Log.i(TAG, "stop speaking");
            mTts.stopSpeaking();
        }
        
        if (action.equals("pause")) {
            mTts.pauseSpeaking();
        }

        if (action.equals("resume")) {
            mTts.resumeSpeaking();
        }

        return false;
    }

    /**
     * 合成回调监听
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            mPercentForBuffering = percent;
            showTip(String.format("正在缓冲...%d%%", mPercentForBuffering));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            mPercentForPlaying = percent;
            showTip("正在播放...");
            //showTip(String.format("正在播放...%d%%", mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error) {
            if(error == null)
            {
                showTip("播放完成");
            }
            else if(error != null)
            {
                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };

    /**
     * init listener
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码："+code);
            }
        }
    };

    private void setParam() {
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "80");//0~100
    }

    private void showTip(String msg)
    {
        mToast.setText(msg);
        mToast.show();
    }

}
