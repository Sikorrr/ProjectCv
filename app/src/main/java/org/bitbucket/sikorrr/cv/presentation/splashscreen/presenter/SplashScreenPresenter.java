package org.bitbucket.sikorrr.cv.presentation.splashscreen.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.bitbucket.sikorrr.cv.presentation.splashscreen.SplashContract;

public class SplashScreenPresenter implements SplashContract.Presenter {

  private SplashContract.View view;

  @Override public void onViewAttached(SplashContract.View view) {
    this.view = view;
  }

  @Override public void onViewDetached() {
    view = null;
  }

  @Override public void checkIfUserIsLoggedIn() {
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    if (firebaseUser != null) {
      view.createFragmentTransaction();
    } else {
      view.startAnimation();
    }
  }
}
