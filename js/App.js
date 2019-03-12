import React, {Component} from 'react';
import {View, Text, StyleSheet, TouchableHighlight} from 'react-native';

//挂载组件 rn中的，组件调用 nativemodules中的 android中的类跟方法
//组件中的代码详解 https://blog.csdn.net/sinat_17775997/article/details/70198637
var { NativeModules } = require('react-native');//最后在这里调用
export default class navigation extends Component {

    //当组件挂载之后,去获取Activity传输过来的数据...componentDidMount作用就是从activity返回来数据给js界面
    //

    componentDidMount(){
        //进行从Activity中获取数据传输到JS
        NativeModules.MyIntentModule.dataToJS((msg) => {
                console.log(msg);
                // ToastAndroid.show('JS界面:从Activity中传输过来的数据为:'+msg,ToastAndroid.SHORT);
                NativeModules.ToastCustomAndroid.show('JS界面:从Activity中传输过来的数据为:'+msg,NativeModules.ToastCustomAndroid.SHORT);
            },
            (result) => {
                // ToastAndroid.show('JS界面:错误信息为:'+result,ToastAndroid.SHORT);
                NativeModules.ToastCustomAndroid.show('JS界面:错误信息为:'+result,NativeModules.ToastCustomAndroid.SHORT);
            })

    }


    render() {
        return (
            <View>
                <Text style={styles.welcome}>
                    自定义弹出Toast消息
                </Text>

                <CustomButton
                    // text="点击弹出Toast消息"
                    // onPress={()=>NativeModules.ToastCustomAndroid.show("我是ToastCustomAndroid弹出消息",NativeModules.ToastCustomAndroid.SHORT)}
                    text="点击跳转原生界面"
                    onPress={()=>NativeModules.MyIntentModule.startActivityFromJS("com.wangkeke.reactnativedemo.SecondActivity","原生界面，你好,我来自RN-JS页面")}
                />
                <CustomButton
                     text="点击弹出Toast消息"
                     onPress={()=>NativeModules.ToastCustomAndroid.show("我是ToastCustomAndroid弹出消息",NativeModules.ToastCustomAndroid.SHORT)}
                     />

                <CustomButton
                    text="从原生扫码页面获取数据"
                    onPress={()=>NativeModules.MyIntentModule.startActivityFromJSGetResult("com.wangkeke.reactnativedemo.ZbarActivity",200,(msg) => {
                            NativeModules.ToastCustomAndroid.show('JS界面:从Activity中传输过来的数据为:'+msg,NativeModules.ToastCustomAndroid.SHORT);
                        },
                        (result) => {
                            NativeModules.ToastCustomAndroid.show('JS界面:错误信息为:'+result,NativeModules.ToastCustomAndroid.SHORT);
                        })}
                />
            </View>
        );
    }
}



class CustomButton extends React.Component {
    render() {
        return (
            <TouchableHighlight
                style={styles.button}
                underlayColor="#a5a5a5"
                onPress={this.props.onPress}>
                <Text style={styles.buttonText}>{this.props.text}</Text>
            </TouchableHighlight>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#ffffff'
    },
    text: {
        fontSize: 20,
        color: '#333333'
    },
    welcome: {
        fontSize: 30,
        textAlign: 'center',
        backgroundColor: 'white',
        margin: 10,
    },
    button: {
        margin:5,
        backgroundColor: 'white',
        padding: 15,
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderBottomColor: '#cdcdcd',
    },
})

