package com.wordpress.priyankvex.greendaosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*addUser();
        getUser(0);*/
        JsonClient.UsersClient usersClient = ServiceGenerator.createService(JsonClient.UsersClient.class);

        Call<List<User>> call =
                usersClient.users();

            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    Log.d("test", response.message());
                    if (response.isSuccessful()){
                        List<User> users = response.body();
                        Log.d("test", "Users : " + users.get(0).getName());
                        Log.d("test", "Users : " + users.get(0).getAddress().getCity());
                        // Save the users in database
                        for (User user : users){
                            addUser(user);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Log.d("test", t.getMessage());
                    // load the users from local storage
                    Log.d("test", "Loading users from local storage");
                    List<User> user = getUsersFromStorage();
                    Log.d("test", "Saved users count : " + user.size());
                }
            });

    }

    private void addUser(User sourceUser){
        DaoSession daoSession = ((App) getApplication()).getDaoSession();

        AddressDao addressDao = daoSession.getAddressDao();
        Address address = sourceUser.getAddress();
        addressDao.insert(address);

        User user = sourceUser;
        user.setAddress(address);
        user.setId(null);
        UserDao userDao = daoSession.getUserDao();
        userDao.insert(user);

        Log.d("test", "User inserted id & name : " + user.getId() + " " + user.getName());
    }

    private User getUser(int index){

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        User user = userDao.queryBuilder().limit(1).list().get(0);
        Log.d("test", "Username : " + user.getName());
        Address address = user.getAddress();
        Log.d("test", "User address : " + address.getCity());

        Gson gson = new Gson();

        Log.d("test", gson.toJson(user));

        return user;
    }

    private List<User> getUsersFromStorage(){
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        UserDao userDao = daoSession.getUserDao();
        List<User> users = userDao.queryBuilder().list();
        return users;
    }

}
