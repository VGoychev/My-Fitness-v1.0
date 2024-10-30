package com.example.mystepscounter.recipe_listeners;

import com.example.mystepscounter.recipes_models.RandomRecipeApiResponse;


public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
