package com.wordpress.priyankvex.greendaosample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by @priyankvex on 22/12/16.
 */

public class JsonClient {

    public interface UsersClient {
        @GET("/users")
        Call<List<User>> users();
    }
}
