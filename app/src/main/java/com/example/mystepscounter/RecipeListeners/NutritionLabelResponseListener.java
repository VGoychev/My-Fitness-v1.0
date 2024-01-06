package com.example.mystepscounter.RecipeListeners;

import com.example.mystepscounter.RecipesModels.NutritionLabelResponse;

public interface NutritionLabelResponseListener {
    void didFetch(NutritionLabelResponse response, String message);

    void didError(String message);
}
