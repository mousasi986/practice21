package com.example.myapplication;
import androidx.appcompat.app.AlertDialog;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface MessageApi {


    @GET("messages1.json")
    Call<List<MessageList>> messages();


}
