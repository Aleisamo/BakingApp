package aleisamo.github.com.bakingapp;

import java.util.List;

public class Recipe {

    private String name;
    private String servings;

    private List<Ingredient> ingredients ;

    private List<Step>steps;

    public Recipe() {
    }
    public List<Ingredient> getIngredients() {
        return ingredients;}

    public List<Step> getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    public String getServings() {
        return servings;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
