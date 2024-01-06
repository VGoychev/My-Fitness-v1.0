package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystepscounter.RecipeAdapters.IngredientsAdapter;
import com.example.mystepscounter.RecipeAdapters.InstructionsAdapter;
//import com.example.mystepscounter.RecipeAdapters.NutritionLabelAdapter;
import com.example.mystepscounter.RecipeAdapters.SimilarRecipeAdapter;
import com.example.mystepscounter.RecipeListeners.InstructionsListener;
import com.example.mystepscounter.RecipeListeners.NutritionLabelResponseListener;
import com.example.mystepscounter.RecipeListeners.RecipeClickListener;
import com.example.mystepscounter.RecipeListeners.RecipeDetailsListener;
import com.example.mystepscounter.RecipeListeners.SimilarRecipeListener;
import com.example.mystepscounter.RecipesModels.InstructionResponse;
import com.example.mystepscounter.RecipesModels.NutritionLabelResponse;
import com.example.mystepscounter.RecipesModels.RecipeDetailsResponse;
import com.example.mystepscounter.RecipesModels.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    String imageUrl;
    Button button_nutrition;
    TextView textView_meal_name, textView_meal_source, textView_meal_summary;
    ImageView imageView_meal_image;
    RecyclerView recycler_meal_ingredients, recycler_meal_similar, recycler_meal_instructions;
    RequestManager_Recipes manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_recipe_details);

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager_Recipes(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipeListener, id);
        manager.getInstructions(instructionsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();
        button_nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                manager.getNutritionLabelImage(nutritionLabelResponseListener, id);
            }
        });
    }
    private void findViews() {
        textView_meal_name = findViewById(R.id.textView_meal_name);
        textView_meal_source = findViewById(R.id.textView_meal_source);
        textView_meal_summary = findViewById(R.id.textView_meal_summary);
        imageView_meal_image = findViewById(R.id.imageView_meal_image);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);
        button_nutrition = findViewById(R.id.button_nutrition);
    }
    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            textView_meal_name.setText(response.title);
            textView_meal_source.setText(response.sourceName);
            textView_meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(imageView_meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private final SimilarRecipeListener similarRecipeListener = new SimilarRecipeListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };

   private final InstructionsListener instructionsListener = new InstructionsListener() {
       @Override
        public void didFetch(List<InstructionResponse> response, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this,LinearLayoutManager.VERTICAL, false));
           instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, response);
           recycler_meal_instructions.setAdapter(instructionsAdapter);
       }

        @Override
       public void didError(String message) {

        }
   };
    private final NutritionLabelResponseListener nutritionLabelResponseListener = new NutritionLabelResponseListener() {
        @Override
        public void didFetch(NutritionLabelResponse response, String message) {
            showImageInDialog(response);

        }

        @Override
        public void didError(String errorMessage) {
            Toast.makeText(RecipeDetailsActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        }
    };
    private void showImageInDialog(NutritionLabelResponse response) {
        Dialog dialog = new Dialog(RecipeDetailsActivity.this);
        dialog.setContentView(R.layout.dialog_layout); // Create a dialog layout with an ImageView
        ImageView nutritionImageView = dialog.findViewById(R.id.nutritionImageView);

        // Load image into the ImageView using Picasso
        Picasso.get().load(response.getImageUrl()).into(nutritionImageView);

        dialog.show();
    }
}