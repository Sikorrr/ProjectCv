package org.bitbucket.sikorrr.cv.presentation.login.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;
import org.bitbucket.sikorrr.cv.presentation.base.BaseFragment;
import org.bitbucket.sikorrr.cv.presentation.login.LoginContract;
import org.bitbucket.sikorrr.cv.presentation.login.presenter.LoginPresenter;
import org.bitbucket.sikorrr.cv.presentation.root.view.RootFragment;

public class LoginFragment extends BaseFragment<LoginContract.View> implements LoginContract.View {

  private Context context;
  private EditText emailInput;
  private EditText passwordInput;
  private TextView errorText;
  private TextView logoText;
  private ProgressBar progressBar;
  private Button loginButton;
  private LoginContract.Presenter presenter = new LoginPresenter();
  private Button anonymousButton;

  private boolean isValidEmail(String email) {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.login_fragment, container, false);

    errorText = view.findViewById(R.id.login_error);
    emailInput = view.findViewById(R.id.email);
    passwordInput = view.findViewById(R.id.password);
    logoText = view.findViewById(R.id.logo);
    logoText.setTypeface(ResourcesCompat.getFont(context, R.font.android_insomnia_regular));
    progressBar = view.findViewById(R.id.progressBar);
    loginButton = view.findViewById(R.id.login_button);
    anonymousButton = view.findViewById(R.id.anonymous_button);

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String enteredEmail = emailInput.getText().toString();
        String enteredPassword = passwordInput.getText().toString();

        if (TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)) {
          errorText.setVisibility(View.VISIBLE);
          errorText.setText(R.string.input_info);
          return;
        }

        if (!isValidEmail(enteredEmail)) {
          errorText.setVisibility(View.VISIBLE);
          errorText.setText(R.string.input_info2);
          return;
        }

        loginAUser(enteredEmail, enteredPassword);
      }
    });

    anonymousButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        presenter.loginAnonymously();
      }
    });

    return view;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getActivity();
  }

  public void loginAUser(String email, String password) {
    progressBar.setVisibility(View.VISIBLE);
    errorText.setVisibility(View.INVISIBLE);
    presenter.logInWithEmail(email, password);
  }

  @Override public void showErrorMessage() {
    progressBar.setVisibility(View.INVISIBLE);
    errorText.setVisibility(View.VISIBLE);
    errorText.setText(R.string.invalid_user);
  }

  @Override public void createFragmentTransaction() {
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.disallowAddToBackStack();
    ft.replace(R.id.root_fragment_container, new RootFragment());
    ft.commit();
  }

  @Override public BaseContract.BasePresenter<LoginContract.View> getPresenter() {
    return presenter;
  }

  @Override public void showToast(String message) {
    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
  }

  @Override public void showToast(int id) {
    Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
  }
}

