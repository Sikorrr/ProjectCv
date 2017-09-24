package org.bitbucket.sikorrr.cv.presentation.detail.presenter;

import org.bitbucket.sikorrr.cv.data.ModelChangedCallback;
import org.bitbucket.sikorrr.cv.data.RepositoryCv;
import org.bitbucket.sikorrr.cv.presentation.detail.DetailContract;
import org.bitbucket.sikorrr.cv.presentation.detail.model.Detail;

public class DetailPresenter implements DetailContract.Presenter, ModelChangedCallback {

  private DetailContract.View view;

  private RepositoryCv repository = RepositoryCv.getRepository();

  @Override public void onViewAttached(DetailContract.View view) {
    this.view = view;
    repository.addCallback(this);
  }

  @Override public void onIdReceived(int itemId) {
    Detail detailsById = repository.getDetailsById(itemId);
    view.renderDetails(detailsById);
  }

  @Override public void onViewDetached() {
    view = null;
    repository.removeCallback();
  }

  @Override public void updateModel() {
    view.modelHasChanged();
  }
}
