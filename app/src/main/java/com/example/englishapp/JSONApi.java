package com.example.englishapp;

import com.example.englishapp.requests.LoginRequest;
import com.example.englishapp.requests.UserStatServerRequest;
import com.example.englishapp.requests.WordServerRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("word/{id}&{uid}")
    Call<Word> getWordById(@Path("id") int word_id, @Path("uid") int user_id);

    @GET("words/{section_id}")
    Call<List<Word>> getSectionWords(@Path("section_id") int section_id);

    @GET("user_words/{user_id}")
    Call<List<Word>> getWordsOfUser(@Path("user_id") int user_id);

    @GET("tasks/{section_id}")
    Call<List<Task>> getTasksFromSection(@Path("section_id") int section_id);

    @GET("task/{task_id}")
    Call<Task> getTaskInfo(@Path("task_id") int task_id);

    @POST("add_to_dictionary")
    Call<ServerResponse> addToDictionary(@Body WordServerRequest request);

    @POST("remove_from_dictionary")
    Call<ServerResponse> remove_from_dictionary(@Body WordServerRequest request);

    @GET("translation_task/{section_id}&{current_word_id}")
    Call<List<TranslationTask>> getTranslationTask(@Path("section_id") int section_id, @Path("current_word_id") int current_word_id);

    @POST("fix_stat")
    Call<ServerResponse> fix_stat(@Body UserStatServerRequest request);

    @POST("login")
    Call<ServerResponse> login(@Body LoginRequest request);

}
