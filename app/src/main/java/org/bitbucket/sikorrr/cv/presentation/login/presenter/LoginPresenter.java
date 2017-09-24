package org.bitbucket.sikorrr.cv.presentation.login.presenter;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.presentation.login.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {

  private LoginContract.View view;
  private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

  @Override public void onViewAttached(LoginContract.View view) {
    this.view = view;
  }

  @Override public void onViewDetached() {
    view = null;
  }

  @Override public void logInWithEmail(String email, String password) {

    if (firebaseAuth != null) {
      firebaseAuth.signInWithEmailAndPassword(email, password)
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override public void onComplete(@NonNull Task<AuthResult> task) {
              if (!task.isSuccessful()) {
                view.showErrorMessage();
              } else {
                view.createFragmentTransaction();
              }
            }
          });
    }
  }

  @Override public void loginAnonymously() {
    if (firebaseAuth != null) {
      firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override public void onComplete(@NonNull Task<AuthResult> task) {
          if (task.isSuccessful()) {
            if (view != null) view.createFragmentTransaction();
          } else {
            view.showToast(R.string.authentication_failed);
          }
        }
      });
    }
  }
}
