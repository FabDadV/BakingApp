package ex.com.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ex.com.bakingapp.R;
import ex.com.bakingapp.ui.MainActivity;

/** Implementation of App Widget functionality. */
public class BackingWidget extends AppWidgetProvider {
    // Set updateAppWidget to handle clicks and launch MainActivity
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, MainActivity.class);
        /* PendingIntent - обёртка для intent? которая позволяет стороннему приложению (в которое его передали)
         * запустить хранящийся внутри него Intent, от имени приложения (и теми же с полномочиями ) передавшего
         * этот PendingIntent. RemoteViews нужно связать с PendingIntent, чтобы запускать MainActivity после щелчка.
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.backing_widget);
        //views.setTextViewText(R.id.wtv_name, widgetText);
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget, pendingIntent);
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
}

