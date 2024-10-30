package com.example.mystepscounter.recipe_listeners;

import com.example.mystepscounter.recipes_models.SimilarRecipeResponse;

import java.util.List;


public interface SimilarRecipeListener {
    void didFetch(List<SimilarRecipeResponse> response, String message);
    void didError(String message);
}
