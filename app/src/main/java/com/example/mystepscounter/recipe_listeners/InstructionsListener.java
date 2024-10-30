package com.example.mystepscounter.recipe_listeners;

import com.example.mystepscounter.recipes_models.InstructionResponse;

import java.util.List;


public interface InstructionsListener {
    void didFetch(List<InstructionResponse> response, String message);
    void didError(String message);
}
