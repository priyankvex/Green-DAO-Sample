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
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        AddressDao addressDao = daoSession.getAddressDao();
        String street = "Victor Road";
        String city = "Vetican City";
        String zipCode = "123-456";
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setZipCode(zipCode);
        addressDao.insert(address);

        String name = "Adam Young";
        String username = "owlcity";
        String email = "adam@owlcity.com";
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setAddress(address);
        UserDao userDao = daoSession.getUserDao();
        userDao.insert(user);

        // insert one more user with same address
        String name2 = "Adam Young";
        String username2 = "owlcity";
        String email2 = "adam@owlcity.com";
        User user2 = new User();
        user2.setName(name2);
        user2.setUsername(username2);
        user2.setEmail(email2);
        user2.setAddress(address);
        userDao.insert(user2);


        Log.d("test", "User inserted id: " + user.getId());
    }

    private User getUser(int index){

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        User user = userDao.queryBuilder().limit(1).list().get(0);
        Log.d("test", "Username : " + user.getName());
        Address address = user.getAddress();
        Log.d("test", "User address : " + address.getCity());

        return user;
    }
}
