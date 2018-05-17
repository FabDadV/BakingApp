package ex.com.bakingapp;

import android.app.Application;
import com.facebook.stetho.Stetho;

import ex.com.bakingapp.data.db.AppDB;
/**
 * Android Application class. Used for accessing singletons.
 */
public class BakingApp extends Application {
    private AppExecutors appExecutors;
    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = new AppExecutors();
        Stetho.initializeWithDefaults(this);
    }
    public AppDB getDatabase() { return AppDB.getInstance(this, appExecutors);}
    public DataRepository getRepository() { return DataRepository.getInstance(getDatabase());}
}
