package com.example.mystepscounter.RecipeListeners;

import com.example.mystepscounter.RecipesModels.InstructionResponse;

import java.util.List;


public interface InstructionsListener {
    void didFetch(List<InstructionResponse> response, String message);
    void didError(String message);
}
