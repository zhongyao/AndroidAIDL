package com.hongri.androidaidl;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}