package ex.com.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.widget.RemoteViews;

import ex.com.bakingapp.R;
import ex.com.bakingapp.ui.MainActivity;

/** Implementation of App Widget functionality. */
public class BackingWidget extends AppWidgetProvider {
    // Set updateAppWidget to handle clicks and launch MainActivity
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                String name, String list, int appWidgetId) {

        // Set the click handler to open the DetailActivity for plant ID,
        // or the MainActivity if plant ID is invalid
//        Intent intent;
        Intent intent = new Intent(context, MainActivity.class);
/*
        if (itemId == PlantContract.INVALID_ITEM_ID) {
            intent = new Intent(context, MainActivity.class);
        } else { // Set on click to open the corresponding detail activity
            Log.d(PlantWidgetProvider.class.getSimpleName(), "plantId=" + plantId);
            intent = new Intent(context, PlantDetailActivity.class);
            intent.putExtra(PlantDetailActivity.EXTRA_PLANT_ID, plantId);
        }
*/

        /* PendingIntent - обёртка для intent? которая позволяет стороннему приложению (в которое его передали)
         * запустить хранящийся внутри него Intent, от имени приложения (и теми же с полномочиями ) передавшего
         * этот PendingIntent. RemoteViews нужно связать с PendingIntent, чтобы запускать MainActivity после щелчка.
endingIntent - wrapper for intent? which allows the third-party application (in which it was transmitted)
          * run the Intent stored inside it, on behalf of the application (and the same with the credentials) that transmitted
          * this PendingIntent. RemoteViews should be associated with PendingIntent to run MainActivity after clicking.
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.backing_widget);
        views.setTextViewText(R.id.wtv_name, name);
        views.setTextViewText(R.id.wtv_ingredients, list);
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager,
                                     String name, String list, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, name, list, appWidgetId);
            Log.d("TAG", "updateW " + String.valueOf(appWidgetId));
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Resources resources = context.getResources();
        String name = resources.getString(R.string.recipe);
        String list = resources.getString(R.string.list);
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, name, list, appWidgetId);
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
}

