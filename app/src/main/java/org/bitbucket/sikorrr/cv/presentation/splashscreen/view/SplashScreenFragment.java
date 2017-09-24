package org.bitbucket.sikorrr.cv.presentation.splashscreen.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;
import org.bitbucket.sikorrr.cv.presentation.base.BaseFragment;
import org.bitbucket.sikorrr.cv.presentation.login.view.LoginFragment;
import org.bitbucket.sikorrr.cv.presentation.root.view.RootFragment;
import org.bitbucket.sikorrr.cv.presentation.splashscreen.SplashContract;
import org.bitbucket.sikorrr.cv.presentation.splashscreen.presenter.SplashScreenPresenter;

public class SplashScreenFragment extends BaseFragment<SplashContract.View>
    implements SplashContract.View {

  private TextView textView;
  private FrameLayout frameLayout;
  private ImageView iv;
  private Context context = getActivity();
  private SplashContract.Presenter presenter = new SplashScreenPresenter();

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getActivity();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.splashscreen_fragment, container, false);

    textView = view.findViewById(R.id.splash_text);
    textView.setTypeface(ResourcesCompat.getFont(context, R.font.android_insomnia_regular));
    frameLayout = view.findViewById(R.id.splashscreen_layout);
    iv = view.findViewById(R.id.splashscreen_image);

    return view;
  }

  @Override public void onResume() {
    super.onResume();
    presenter.checkIfUserIsLoggedIn();
  }

  public void startAnimation() {
    Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
    anim.reset();

    frameLayout.clearAnimation();
    frameLayout.startAnimation(anim);

    anim = AnimationUtils.loadAnimation(context, R.anim.translate);
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        ft.disallowAddToBackStack();
        ft.replace(R.id.root_fragment_container, new LoginFragment());
        ft.commit();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    anim.reset();

    iv.clearAnimation();
    iv.startAnimation(anim);
  }

  public void createFragmentTransaction() {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
    ft.disallowAddToBackStack();
    ft.replace(R.id.root_fragment_container, new RootFragment());
    ft.commit();
  }

  @Override public BaseContract.BasePresenter<SplashContract.View> getPresenter() {
    return presenter;
  }
}
