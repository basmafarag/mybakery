package com.example.android.mybakery.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.mybakery.Model.Ingredient;
import com.example.android.mybakery.Model.Recipe;
import com.example.android.mybakery.R;
import com.example.android.mybakery.RecipeDetails.RecipeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    private static final String RECIPE = "mRecipe";
    static List<Ingredient> ingredients = new ArrayList<>();
    private static String text;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setTextViewText(R.id.appwidget_text, text);

        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra("Recipe",RecipeDetailsActivity.mrecipe);
        views.setRemoteAdapter(R.id.lv_ingredients_widget, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.hasExtra(RECIPE)) {
            Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");
            text = recipe.getName();
            ingredients = recipe.getIngredientList();
        } else {
            Log.d("widgettttt   ", String.valueOf(intent.getStringExtra("recipe")));

            text = context.getString(R.string.no_recipe_selected);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), RecipeWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_ingredients_widget);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            onUpdate(context, appWidgetManager, appWidgetIds);
        }

        super.onReceive(context, intent);
    }
}

