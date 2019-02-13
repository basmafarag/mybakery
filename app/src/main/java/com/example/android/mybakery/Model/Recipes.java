package com.example.android.mybakery.Model;

import java.io.Serializable;
import java.util.List;

public class Recipes implements Serializable{

    private int id;
    private String name;
    private List<Ingredients> ingredientsList;
    private List<Steps> stepsList;

    private int servings;
    private String image;


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredientsList +
                ", steps=" + stepsList +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    public int getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public List<Steps> getStepsList() {
        return stepsList;
    }


}

