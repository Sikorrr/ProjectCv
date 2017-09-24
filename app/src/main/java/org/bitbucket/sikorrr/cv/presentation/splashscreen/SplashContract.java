package org.bitbucket.sikorrr.cv.presentation.splashscreen;

import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;

public interface SplashContract {

  interface View extends BaseContract.BaseView {
    void createFragmentTransaction();

    void startAnimation();
  }

  interface Presenter extends BaseContract.BasePresenter<SplashContract.View> {
    void checkIfUserIsLoggedIn();
  }
}
