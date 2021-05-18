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

    @GET("collocation/{id}&{uid}")
    Call<Collocation> getCollocationById(@Path("id") int collocation_id, @Path("uid") int user_id);

    @GET("words/{section_id}")
    Call<List<Word>> getSectionWords(@Path("section_id") int section_id);

    @GET("collocations/{section_id}")
    Call<List<Collocation>> getSectionCollocations(@Path("section_id") int section_id);

    @GET("user_words/{user_id}")
    Call<List<Word>> getWordsOfUser(@Path("user_id") int user_id);

    @GET("user_collocations/{user_id}")
    Call<List<Collocation>> getCollocationsOfUser(@Path("user_id") int user_id);

    @GET("tasks/{section_id}&{user_id}")
    Call<List<Task>> getTasksFromSection(@Path("section_id") int section_id, @Path("user_id") int user_id);

    @GET("task/{task_id}")
    Call<Task> getTaskInfo(@Path("task_id") int task_id);

    @POST("add_to_dictionary")
    Call<ServerResponse> addToDictionary(@Body WordServerRequest request);

    @POST("remove_from_dictionary")
    Call<ServerResponse> remove_from_dictionary(@Body WordServerRequest request);

    @GET("translation_task/{section_id}&{current_word_id}")
    Call<List<TranslationTask>> getTranslationTask(@Path("section_id") int section_id, @Path("current_word_id") int current_word_id);

    @GET("audio_task/{section_id}&{current_word_id}")
    Call<List<TranslationTask>> getAudioTask(@Path("section_id") int section_id, @Path("current_word_id") int current_word_id);

    @GET("make_word_task/{section_id}&{current_word_id}")
    Call<TranslationTask> getMakeWordTask(@Path("section_id") int section_id, @Path("current_word_id") int current_word_id);

    @GET("translation_collocation_task/{section_id}&{current_word_id}")
    Call<List<TranslationTask>> getTranslationCollocationTask(@Path("section_id") int section_id, @Path("current_word_id") int current_word_id);

    @GET("dictionary_word_task/{user_id}&{current_word_id}")
    Call<List<TranslationTask>> getDictionaryWordTask(@Path("user_id") int user_id, @Path("current_word_id") int current_word_id);

    @GET("dictionary_collocation_task/{user_id}&{current_word_id}")
    Call<List<TranslationTask>> getDictionaryCollocationTask(@Path("user_id") int user_id, @Path("current_word_id") int current_word_id);

    @GET("matching_collocation_task/{section_id}&{current_word_id}")
    Call<List<TranslationTask>> getMatchingCollocationTask(@Path("section_id") int section_id, @Path("current_word_id") int current_word_id);

    @POST("fix_stat")
    Call<ServerResponse> fix_stat(@Body UserStatServerRequest request);

    @POST("fix_collocation_stat")
    Call<ServerResponse> fix_collocation_stat(@Body UserStatServerRequest request);

    @POST("login")
    Call<ServerResponse> login(@Body LoginRequest request);

    @GET("user_stat/{user_id}")
    Call<List<WordStat>> getUserStats(@Path("user_id") int user_id);

    @GET("sentense_task/{section_id}&{current_word_id}")
    Call<SentenceTask> getSentenceTask(@Path("section_id") int section_id, @Path("current_word_id") int current_word_id);

}
