package org.bitbucket.sikorrr.cv.presentation.base;

import android.support.v4.app.FragmentActivity;

public interface BaseContract {

  interface BasePresenter<VIEW extends BaseView> {

    void onViewAttached(VIEW view);

    void onViewDetached();
  }

  interface BaseView {

    void showToast(String message);

    void showToast(int id);

    FragmentActivity getFragmentActivity();
  }
}
