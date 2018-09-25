package com.tuling.web;

/**
 * Created by Yuanp on 2018/9/9.
 */
public class JvmTest {
    private static int i;
    public static void main(String[] args) {
        int j = 5;
        System.out.println(i);
        System.out.println(j);
        System.gc();
    }
}
