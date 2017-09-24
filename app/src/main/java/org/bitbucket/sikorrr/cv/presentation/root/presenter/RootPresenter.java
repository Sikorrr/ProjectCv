package org.bitbucket.sikorrr.cv.presentation.root.presenter;

import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.data.ModelChangedCallback;
import org.bitbucket.sikorrr.cv.data.RepositoryCv;
import org.bitbucket.sikorrr.cv.presentation.root.RootContract;

public class RootPresenter implements RootContract.Presenter, ModelChangedCallback {

  private RootContract.View view;
  private RepositoryCv repository = RepositoryCv.getRepository();

  @Override public void onViewAttached(RootContract.View view) {
    this.view = view;
    repository.addCallback(this);
    repository.initializeData();
  }

  @Override public void onViewDetached() {
    view = null;
    repository.removeCallback();
  }

  @Override public boolean onBottomBarClicked(int itemId) {
    switch (itemId) {
      case R.id.action_github:
        view.openWebPage(repository.getWebPage());
        break;
      case R.id.action_phone:
        view.dialPhoneNumber(repository.getPhoneNumber());
        break;
      case R.id.action_email:
        view.composeEmail(repository.getEmail());
        break;
      default:
        break;
    }
    return true;
  }

  @Override public void updateModel() {

  }
}
