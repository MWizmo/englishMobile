package com.example.englishapp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JSONApi {
    @GET("modules")
    Call<List<Module>> getModules();

    @GET("modules/{module_id}")
    Call<Module> getModuleInfo(@Path("module_id") int module_id);

    @GET("sections/{module_id}")
    Call<List<Section>> getSections(@Path("module_id") int module_id);

    @GET("section/{section_id}")
    Call<Section> getSectionInfo(@Path("section_id") int section_id);

    @GET("word/{id}")
    Call<Word> getWordById(@Path("id") int id);

    @GET("words/{section_id}")
    Call<List<Word>> getSectionWords(@Path("section_id") int section_id);

    @GET("user_words/{user_id}")
    Call<List<Word>> getWordsOfUser(@Path("user_id") int user_id);

    @GET("tasks/{section_id}")
    Call<List<Task>> getTasksFromSection(@Path("section_id") int section_id);

    @GET("task/{task_id}")
    Call<Task> getTaskInfo(@Path("task_id") int task_id);

    @POST("add_to_dictionary")
    Call<ServerResponse> addToDictionary(@Body ServerRequest request);

}
