package com.cgty.denemeins;

import android.util.Log;

import com.cgty.denemeins.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class Test {
    public static void main(String[] args) {
        System.out.println( User.getUser("QRlN7XBzp4YQrMc047tYnr6Mo7A2").toString() );
    }
}
