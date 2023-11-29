package com.example.mystepscounter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.mystepscounter.RecipeListeners.InstructionsListener;
import com.example.mystepscounter.RecipeListeners.NutritionLabelResponseListener;
import com.example.mystepscounter.RecipeListeners.RandomRecipeResponseListener;
import com.example.mystepscounter.RecipeListeners.RecipeDetailsListener;
import com.example.mystepscounter.RecipeListeners.SimilarRecipeListener;
import com.example.mystepscounter.RecipesModels.InstructionResponse;
import com.example.mystepscounter.RecipesModels.NutritionLabelResponse;
import com.example.mystepscounter.RecipesModels.RandomRecipeApiResponse;
import com.example.mystepscounter.RecipesModels.RecipeDetailsResponse;
import com.example.mystepscounter.RecipesModels.SimilarRecipeResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager_Recipes {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    OkHttpClient client = new OkHttpClient();

//    Gson gson = new GsonBuilder()
//            .setLenient()
//            .create();
//
//    OkHttpClient client = new OkHttpClient.Builder()
//            .retryOnConnectionFailure(true)
//            .build();
//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://api.spoonacular.com/")
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build();

    public RequestManager_Recipes(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipeResponseListener listener, List<String> tags){
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "12", tags);
        call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }
    public void getRecipeDetails(RecipeDetailsListener listener, int id){
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.callRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getSimilarRecipes(SimilarRecipeListener listener, int id){
        CallSimilarRecipes callSimilarRecipes = retrofit.create(CallSimilarRecipes.class);
        Call<List<SimilarRecipeResponse>> call = callSimilarRecipes.callSimilarRecipe(id, "4", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeResponse>> call, Response<List<SimilarRecipeResponse>> response) {
                if (!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipeResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getInstructions(InstructionsListener listener, int id){
        CallInstructions callInstructions = retrofit.create(CallInstructions.class);
        Call<List<InstructionResponse>> call = callInstructions.callInstructions(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionResponse>> call, Response<List<InstructionResponse>> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionResponse>> call, Throwable t) {

                listener.didError(t.getMessage());
            }
        });
    }
    public void getNutritionLabelImage(NutritionLabelResponseListener listener, int id) {
        NutritionLabelService nutritionLabelService = retrofit.create(NutritionLabelService.class);
        Call<NutritionLabelResponse> call = nutritionLabelService.getNutritionLabel(id, context.getString(R.string.api_key));

        call.enqueue(new Callback<NutritionLabelResponse>() {
            @Override
            public void onResponse(Call<NutritionLabelResponse> call, Response<NutritionLabelResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }


            @Override
            public void onFailure(Call<NutritionLabelResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }



    private interface CallRandomRecipes{
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }
    private interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> callRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallSimilarRecipes{
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipeResponse>> callSimilarRecipe(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }
    private interface CallInstructions{
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
    private interface NutritionLabelService{
        @GET("recipes/{id}/nutritionLabel.png")
        Call<NutritionLabelResponse> getNutritionLabel(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
