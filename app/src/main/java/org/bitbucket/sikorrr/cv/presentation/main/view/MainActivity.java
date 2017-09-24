package org.bitbucket.sikorrr.cv.presentation.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.data.RepositoryCv;
import org.bitbucket.sikorrr.cv.presentation.splashscreen.view.SplashScreenFragment;

public class MainActivity extends AppCompatActivity {

  private RepositoryCv repositoryCv = RepositoryCv.getRepository();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      ft.replace(R.id.root_fragment_container, new SplashScreenFragment());
      ft.commit();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    repositoryCv.removeListeners();
  }

  @Override public void onBackPressed() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    for (Fragment fragment : fragmentManager.getFragments()) {
      if (fragment.isVisible()) {
        FragmentManager childFragmentManager = fragment.getChildFragmentManager();
        if (childFragmentManager.getBackStackEntryCount() > 0) {
          childFragmentManager.popBackStack();
          return;
        }
      }
    }
    super.onBackPressed();
  }
}