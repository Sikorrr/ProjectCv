package org.bitbucket.sikorrr.cv.presentation.root.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;
import org.bitbucket.sikorrr.cv.presentation.base.BaseFragment;
import org.bitbucket.sikorrr.cv.presentation.overview.view.OverviewFragment;
import org.bitbucket.sikorrr.cv.presentation.root.RootContract;
import org.bitbucket.sikorrr.cv.presentation.root.presenter.RootPresenter;
import org.bitbucket.sikorrr.cv.utility.Utility;

public class RootFragment extends BaseFragment<RootContract.View> implements RootContract.View {

  private RootContract.Presenter presenter = new RootPresenter();

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_root, container, false);
    if (savedInstanceState == null) {
      createFragmentTransaction();
    }
    initializeBottomNavigationView(view);
    makeToolbarTranslucent();
    return view;
  }

  private void createFragmentTransaction() {
    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
    ft.disallowAddToBackStack();
    ft.replace(R.id.child_fragment_top, new OverviewFragment());
    ft.commit();
  }

  private void makeToolbarTranslucent() {
    SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
    tintManager.setStatusBarTintEnabled(true);
    tintManager.setNavigationBarTintEnabled(true);
    tintManager.setTintColor(Color.TRANSPARENT);
  }

  private void initializeBottomNavigationView(View view) {
    BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation_bar);

    bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return presenter.onBottomBarClicked(item.getItemId());
          }
        });
  }

  @Override public BaseContract.BasePresenter getPresenter() {
    return presenter;
  }

  @Override public void showToast(String message) {
    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
  }

  @Override public void showToast(int id) {
    Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
  }

  @Override public void openWebPage(String webPage) {
    Utility.openWebPage(getContext(), webPage);
  }

  @Override public void dialPhoneNumber(String phoneNumber) {
    Utility.dialPhoneNumber(getContext(), phoneNumber);
  }

  @Override public void composeEmail(String email) {
    Utility.composeEmail(getContext(), email);
  }
}