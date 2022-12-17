package es.unex.dcadmin;

import android.app.Application;

public class DCAdmin extends Application {
    public AppContainer appContainer;

    @Override
    public void onCreate() {
        super.onCreate();
        appContainer = new AppContainer(this);
    }
}
