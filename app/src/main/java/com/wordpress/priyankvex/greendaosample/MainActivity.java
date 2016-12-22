package com.wordpress.priyankvex.greendaosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addUser();
        getUser(0);
    }

    private void addUser(){

        String name = "Adam Young";
        String username = "owlcity";
        String email = "adam@owlcity.com";
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.insert(user);

        Log.d("test", "User inserted id: " + user.getId());
    }

    private User getUser(int index){

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        User user = userDao.queryBuilder().limit(1).list().get(0);
        Log.d("test", "Username : " + user.getName());

        return user;
    }
}
