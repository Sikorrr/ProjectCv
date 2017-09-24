package org.bitbucket.sikorrr.cv.presentation.base;

import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity<VIEW extends BaseContract.BaseView> extends AppCompatActivity {

  public abstract BaseContract.BasePresenter<VIEW> getPresenter();

  @Override public void onStart() {
    super.onStart();
    getPresenter().onViewAttached((VIEW) this);
  }

  @Override public void onStop() {
    getPresenter().onViewDetached();
    super.onStop();
  }
}
