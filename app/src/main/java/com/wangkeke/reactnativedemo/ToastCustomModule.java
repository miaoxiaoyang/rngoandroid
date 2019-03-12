package com.wangkeke.reactnativedemo;

import android.widget.Toast;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求网址具体方法：
 *
 * ReactContextBaseJavaModule作用 ： 原生类继承 ReactContextBaseJavaModule，以便实现javascript功能
 *   ReactContextBaseJavaModule要求返回一个 getName()方法，这个函数中返回一个字符串，用于在javascript中调用的模块的名字，
 * 例如这里取名为 ToastCustomAndroid，在javascript中就可用 NativeModules.ToastCustomAndroid.show()调用
 *
 */

public class ToastCustomModule extends ReactContextBaseJavaModule {

    private static final String DURATION_SHORT = "SHORT";
    private static final String DURATION_LONG = "LONG";

    public ToastCustomModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "ToastCustomAndroid";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(DURATION_SHORT, Toast.LENGTH_SHORT);
        constants.put(DURATION_LONG, Toast.LENGTH_LONG);
        return constants;
    }

    /**
     * 该方法用于给JavaScript进行调用
     * @ReactMethod 中 java提供给JavaScript调用，就必须加注解
     * @param message
     * @param duration
     */
    @ReactMethod
    public void show(String message, int duration) {
        Toast.makeText(getReactApplicationContext(), message, duration).show();
    }

}
