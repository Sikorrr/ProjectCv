package org.bitbucket.sikorrr.cv.presentation.base;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class CvApplication extends Application {

  public static RefWatcher getRefWatcher(Context context) {
    CvApplication application = (CvApplication) context.getApplicationContext();
    return application.refWatcher;
  }

  private RefWatcher refWatcher;

  @Override public void onCreate() {
    super.onCreate();
    getPicassoInstance();

    if (LeakCanary.isInAnalyzerProcess(this)) {
      return;
    }
    refWatcher = LeakCanary.install(this);
  }

  private void getPicassoInstance() {
    Picasso.Builder builder = new Picasso.Builder(this);
    builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
    Picasso built = builder.build();
    Picasso.setSingletonInstance(built);
  }
}