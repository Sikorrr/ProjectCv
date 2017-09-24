package org.bitbucket.sikorrr.cv.presentation.login;

import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;

public interface LoginContract {

  interface View extends BaseContract.BaseView {
    void showErrorMessage();

    void createFragmentTransaction();
  }

  interface Presenter extends BaseContract.BasePresenter<LoginContract.View> {

    void logInWithEmail(String email, String password);

    void loginAnonymously();
  }
}
