package com.tuwq.javapasstoc;

public class JNI {
    static{
        System.loadLibrary("passparam");
    }
    public native int add(int x, int y);
    public native String sayHelloInC(String s);
    public native int[] arrElementsIncrease(int[] intArray);
}
