package com.example.mystepscounter.RecipeListeners;

import com.example.mystepscounter.RecipesModels.Recipe;
import com.example.mystepscounter.RecipesModels.RecipeDetailsResponse;


public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
