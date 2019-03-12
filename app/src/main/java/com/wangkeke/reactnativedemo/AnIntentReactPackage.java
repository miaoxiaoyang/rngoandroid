package com.wangkeke.reactnativedemo;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 注册原生模块
 * 1.需要继承ReactPackage接口，返回来3个方法
 * 2.具体方法如下解释
 *
 *
 */

public class AnIntentReactPackage implements ReactPackage {
    //添加原生模块
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new MyIntentModule(reactContext));
        return modules;
    }

    //暂时不清楚干嘛
    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
