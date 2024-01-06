package com.example.mystepscounter.RecipeListeners;

import com.example.mystepscounter.RecipesModels.RandomRecipeApiResponse;

public interface RandomRecipeResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);
    void didError(String message);
}
