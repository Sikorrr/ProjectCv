package org.bitbucket.sikorrr.cv.presentation.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import com.squareup.leakcanary.RefWatcher;

public abstract class BaseFragment<VIEW extends BaseContract.BaseView> extends Fragment
    implements BaseContract.BaseView {

  public abstract BaseContract.BasePresenter<VIEW> getPresenter();

  @Override public void showToast(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }

  @Override public void showToast(int id) {
    Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    RefWatcher refWatcher = CvApplication.getRefWatcher(getActivity());
    refWatcher.watch(this);
  }

  @Override public void onStart() {
    super.onStart();
    getPresenter().onViewAttached((VIEW) this);
  }

  @Override public void onStop() {
    getPresenter().onViewDetached();
    super.onStop();
  }

  @Override public FragmentActivity getFragmentActivity() {
    return getActivity();
  }
}
