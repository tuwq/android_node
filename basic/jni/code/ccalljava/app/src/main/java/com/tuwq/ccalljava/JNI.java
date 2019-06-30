package com.tuwq.ccalljava;

public class JNI {
    static{
        System.loadLibrary("ccalljava");
    }
    // c调用空参
    public void helloFromJava(){
        System.out.println("hello from java");
    }
    // c调用int
    public int add(int x,int y) {
        return x+y;
    }
    // c调用string
    public void printString(String s){
        System.out.println(s);
    }

    public native void callbackvoid();
    public native void callbackInt();
    public native void callbackString();
    public native void callbackShowToast();
}
