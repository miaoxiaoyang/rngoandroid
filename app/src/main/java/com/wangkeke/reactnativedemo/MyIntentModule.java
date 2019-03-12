package com.wangkeke.reactnativedemo;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by wangkeke on 2017/5/26.
 * 连接转换
 * 原生类继承 ReactContextBaseJavaModule 以便实现jascript功能,返回一个方法getName(); 函数返回一个字符串即是这个 js代码中调用的名称
 *  组件.名字.方法
 * 创建原生模块
 * 1.继承 ReactContextBaseJavaModule
 * 2.返回两个，A.构造器 B.返回一个方法getName()
 * 3.方法返回来的一个字符串，就是调用的时候的一种名称
 */

public class MyIntentModule extends ReactContextBaseJavaModule {
    public MyIntentModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "MyIntentModule";
    }

    @Override
    public boolean canOverrideExistingModule() {
        return true;
    }

    /**
     *      * 从JS页面跳转到原生activity   同时也可以从JS传递相关数据到原生
     *      * @param name  需要打开的Activity的class
     *      * @param params
     *      ReactMethod 此时注意 如果是 java中的代码给js调用即就要加注解
     *      
     */
    @ReactMethod
    public void startActivityFromJS(String name, String params) {
        try {
            Activity currentActivity = getCurrentActivity();
            if (null != currentActivity) {
                Class toActivity = Class.forName(name);
                Intent intent = new Intent(currentActivity, toActivity);
                intent.putExtra("params", params);
                currentActivity.startActivity(intent);
            }
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "不能打开Activity : " + e.getMessage());
        }
    }


    /**
     * Activtiy跳转到JS页面，传输数据
     * @param successBack
     * @param errorBack
     */
    @ReactMethod
    public void dataToJS(Callback successBack, Callback errorBack){
        try{
            Activity currentActivity = getCurrentActivity();
            String result = currentActivity.getIntent().getStringExtra("data");
            if (TextUtils.isEmpty(result)){
                result = "没有数据";
            }
            successBack.invoke(result);
        }catch (Exception e){
            errorBack.invoke(e.getMessage());
        }
    }


    /**
     * 从JS页面跳转到Activity界面，并且等待从Activity返回的数据给JS
     * @param className
     * @param successBack
     * @param errorBack
     */
    @ReactMethod
    public void startActivityFromJSGetResult(String className, int requestCode, Callback successBack, Callback errorBack){
        try {
            Activity currentActivity=getCurrentActivity();
            if(currentActivity!=null) {
                Class toActivity = Class.forName(className);
                Intent intent = new Intent(currentActivity,toActivity);
                currentActivity.startActivityForResult(intent,requestCode);
               //进行回调数据
                successBack.invoke(MainActivity.mQueue.take());
            }
        } catch (Exception e) {
            errorBack.invoke(e.getMessage());
            e.printStackTrace();
        }

    }

}
