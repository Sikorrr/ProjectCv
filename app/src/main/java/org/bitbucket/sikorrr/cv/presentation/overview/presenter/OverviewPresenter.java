package org.bitbucket.sikorrr.cv.presentation.overview.presenter;

import android.support.annotation.NonNull;
import android.view.MenuItem;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.data.ModelChangedCallback;
import org.bitbucket.sikorrr.cv.data.RepositoryCv;
import org.bitbucket.sikorrr.cv.presentation.overview.OverviewContract;
import org.bitbucket.sikorrr.cv.presentation.overview.model.Section;

public class OverviewPresenter implements OverviewContract.Presenter, ModelChangedCallback {

  private OverviewContract.View view;
  private RepositoryCv repository = RepositoryCv.getRepository();

  @Override public void onItemSelected(int position) {
    Section sectionById = repository.getSectionById(position);
    if (sectionById != null) view.renderDetailFragment(position, sectionById);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.logout:
        logOut();
        break;
      default:
        break;
    }
    return true;
  }

  private void logOut() {
    AuthUI.getInstance()
        .signOut(view.getFragmentActivity())
        .addOnCompleteListener(new OnCompleteListener<Void>() {
          public void onComplete(@NonNull Task<Void> task) {
            view.renderLoginFragment();
          }
        });
  }

  @Override public String getCircularImage() {
    return repository.getCircularImageUrl();
  }

  @Override public String getBackgroundImage() {
    return repository.getBackgroundImage();
  }

  @Override public String getName() {
    return repository.getName();
  }

  @Override public void onViewAttached(OverviewContract.View view) {
    this.view = view;
    repository.addCallback(this);
    view.renderSections(repository.getSections());
  }

  @Override public void onViewDetached() {
    view = null;
    repository.removeCallback();
  }

  @Override public void updateModel() {
    if (view != null) view.modelHasChanged();
  }
}
