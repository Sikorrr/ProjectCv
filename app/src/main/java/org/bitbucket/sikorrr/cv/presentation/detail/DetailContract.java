package org.bitbucket.sikorrr.cv.presentation.detail;

import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;
import org.bitbucket.sikorrr.cv.presentation.detail.model.Detail;

public interface DetailContract {

  interface View extends BaseContract.BaseView {

    void renderDetails(Detail detailsById);

    void modelHasChanged();
  }

  interface Presenter extends BaseContract.BasePresenter<DetailContract.View> {

    void onIdReceived(int itemId);
  }
}
