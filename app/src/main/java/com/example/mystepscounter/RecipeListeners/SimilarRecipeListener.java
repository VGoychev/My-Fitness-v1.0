package com.example.mystepscounter.RecipeListeners;

import com.example.mystepscounter.RecipesModels.SimilarRecipeResponse;

import java.util.List;


public interface SimilarRecipeListener {
    void didFetch(List<SimilarRecipeResponse> response, String message);
    void didError(String message);
}
