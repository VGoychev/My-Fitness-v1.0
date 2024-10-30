package com.example.mystepscounter.recipe_listeners;

import com.example.mystepscounter.recipes_models.RecipeDetailsResponse;


public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
