package org.bitbucket.sikorrr.cv.presentation.overview;

import android.view.MenuItem;
import java.util.List;
import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;
import org.bitbucket.sikorrr.cv.presentation.overview.model.Section;

public interface OverviewContract {

  interface View extends BaseContract.BaseView {

    void renderDetailFragment(int position, Section section);

    void renderSections(List<Section> sections);

    void modelHasChanged();

    void renderLoginFragment();
  }

  interface Presenter extends BaseContract.BasePresenter<View> {

    void onItemSelected(int position);

    boolean onOptionsItemSelected(MenuItem item);

    String getCircularImage();

    String getBackgroundImage();

    String getName();
  }
}
