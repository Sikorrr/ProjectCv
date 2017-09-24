package org.bitbucket.sikorrr.cv.presentation.root;

import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;

public interface RootContract {

  interface View extends BaseContract.BaseView {
    void openWebPage(String webPage);

    void dialPhoneNumber(String phoneNumber);

    void composeEmail(String email);
  }

  interface Presenter extends BaseContract.BasePresenter<RootContract.View> {

    boolean onBottomBarClicked(int itemId);
  }
}
