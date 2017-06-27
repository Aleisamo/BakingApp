package aleisamo.github.com.bakingapp.BakingWidget;

import java.util.List;

import aleisamo.github.com.bakingapp.Ingredient;

public enum WidgetIngredients {

    INSTANCE;

    private List<Ingredient> ingredients;
    private String recipeName;

    public static List<Ingredient> getIngredients() {
        return INSTANCE.ingredients;
    }

    public static String getRecipeName() {
        return INSTANCE.recipeName;
    }

    public static void setIngredients(List<Ingredient> ingredients) {
        INSTANCE.ingredients = ingredients;
    }

    public static void setRecipeName(String recipeName) {
        INSTANCE.recipeName = recipeName;
    }
}
