package org.bitbucket.sikorrr.cv.presentation.detail.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;
import org.bitbucket.sikorrr.cv.presentation.base.BaseFragment;
import org.bitbucket.sikorrr.cv.presentation.detail.DetailContract;
import org.bitbucket.sikorrr.cv.presentation.detail.model.Detail;
import org.bitbucket.sikorrr.cv.presentation.detail.presenter.DetailPresenter;
import org.bitbucket.sikorrr.cv.presentation.overview.view.OverviewFragment;
import org.bitbucket.sikorrr.cv.utility.Utility;

public class DetailFragment extends BaseFragment<DetailContract.View>
    implements DetailContract.View {

  private Context context;
  private DetailContract.Presenter presenter = new DetailPresenter();
  private DetailAdapter adapter;
  private int itemId;
  private KonfettiView viewKonfetti;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getActivity();
    itemId = getArguments().getInt(OverviewFragment.ITEM_ID);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initializeImageView(view);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    viewKonfetti = view.findViewById(R.id.konfetti_view_details);
    String title = getArguments().getString(OverviewFragment.SECTION_TITLE);
    initializeRecyclerView(view);
    initializeToolbar(view);
    initializeCollapsingToolbar(view, title);
    return view;
  }

  @Override public void onStart() {
    super.onStart();
    presenter.onIdReceived(itemId);
  }

  private void initializeImageView(View view) {
    String transitionName = getArguments().getString(OverviewFragment.TRANSITION_KEY);
    ImageView imageView = view.findViewById(R.id.image_details);
    imageView.setTransitionName(transitionName);
    Picasso.with(imageView.getContext())
        .load(getArguments().getString(OverviewFragment.IMAGE_ID))
        .into(imageView);
  }

  private void initializeRecyclerView(View view) {
    RecyclerView recyclerView = view.findViewById(R.id.recyclerview_details);
    recyclerView.setLayoutManager(new LinearLayoutManager(context));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    adapter = new DetailAdapter(recyclerView);
    recyclerView.setAdapter(adapter);
    recyclerView.setHasFixedSize(true);
    int orientation = this.getResources().getConfiguration().orientation;
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
      recyclerView.setPadding(0, 0, 0, Utility.dpToPx(
          (getResources().getDimension(R.dimen.bottom_bar_height)
              / getResources().getDisplayMetrics().density), context));
    }
  }

  private void initializeToolbar(View view) {
    Toolbar toolbar = view.findViewById(R.id.toolbar_details);
    toolbar.setTitle("");
    toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
    toolbar.setPadding(0, Utility.getStatusBarHeight(context), 0, 0);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });
  }

  private void initializeCollapsingToolbar(View view, String title) {
    CollapsingToolbarLayout collapsingToolbarLayout =
        view.findViewById(R.id.collapsing_toolbar_details);
    collapsingToolbarLayout.setTitle(title);
    collapsingToolbarLayout.setCollapsedTitleTypeface(
        ResourcesCompat.getFont(context, R.font.amita_bold));
    collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL);
    collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.START);
    view.findViewById(R.id.collapsing_toolbar_details).bringToFront();
  }

  private void createKonfetti() {
    viewKonfetti.build()
        .addColors(Color.YELLOW, Color.CYAN, Color.MAGENTA)
        .setDirection(500.0, 1000.0)
        .setSpeed(1f, 5f)
        .setFadeOutEnabled(true)
        .setTimeToLive(2000L)
        .addShapes(Shape.RECT, Shape.CIRCLE)
        .addSizes(new Size(12, 12))
        .setPosition(1800, viewKonfetti.getWidth() + 0f, -50f, 150f)
        .stream(150, 2000L);
  }

  @Override public BaseContract.BasePresenter<DetailContract.View> getPresenter() {
    return presenter;
  }

  @Override public void renderDetails(Detail detailsById) {
    if (detailsById != null) {
      adapter.displayDetails(detailsById);
      if (detailsById.isConfetti()) createKonfetti();
    }
  }

  @Override public void modelHasChanged() {
    adapter.notifyDataSetChanged();
  }
}

