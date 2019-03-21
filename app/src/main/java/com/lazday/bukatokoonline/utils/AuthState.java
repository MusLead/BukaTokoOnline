package com.lazday.bukatokoonline.utils;

import android.view.Menu;

import com.lazday.bukatokoonline.R;


public class AuthState {

    public static void isLoggedIn(Menu menu){
        menu.findItem(R.id.nav_notif).setVisible(true);
        menu.findItem(R.id.nav_profile).setVisible(true);
        menu.findItem(R.id.nav_trans).setVisible(true);
        menu.findItem(R.id.nav_logout).setVisible(true);

        menu.findItem(R.id.nav_login).setVisible(false);
    }

    public static void isLoggedOut(Menu menu){
        menu.findItem(R.id.nav_notif).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);
        menu.findItem(R.id.nav_trans).setVisible(false);
        menu.findItem(R.id.nav_logout).setVisible(false);

        menu.findItem(R.id.nav_login).setVisible(true);
    }
}
