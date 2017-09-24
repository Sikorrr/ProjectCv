package org.bitbucket.sikorrr.cv.presentation.overview.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.List;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.presentation.base.BaseContract;
import org.bitbucket.sikorrr.cv.presentation.base.BaseFragment;
import org.bitbucket.sikorrr.cv.presentation.detail.view.DetailFragment;
import org.bitbucket.sikorrr.cv.presentation.login.view.LoginFragment;
import org.bitbucket.sikorrr.cv.presentation.overview.OverviewContract;
import org.bitbucket.sikorrr.cv.presentation.overview.model.Section;
import org.bitbucket.sikorrr.cv.presentation.overview.presenter.OverviewPresenter;
import org.bitbucket.sikorrr.cv.utility.ItemClickListener;
import org.bitbucket.sikorrr.cv.utility.Utility;

public class OverviewFragment extends BaseFragment<OverviewContract.View>
    implements ItemClickListener, OverviewContract.View {

  public static final String TRANSITION_VALUE = "transition";
  private OverviewContract.Presenter presenter = new OverviewPresenter();
  public static final String IMAGE_ID = "image_id";
  public static final String SECTION_TITLE = "section_title";
  public static final String ITEM_ID = "item_id";
  public static final String TRANSITION_KEY = "transition_name";
  private SectionAdapter sectionAdapter;
  private Context context;
  private RecyclerView.LayoutManager layoutManager;
  private CircularImageView circularImageView;
  private TextView nameTextView;
  private RecyclerView recyclerView;
  private int initialRecyclerviewHeight;
  private int statusBarHeight;
  private int bottombarHeight;
  private int requiredRecyclerviewHeight;
  private ImageView backgroundHeaderImageView;
  private ProgressDialog progressDialog;

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_main, menu);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = getActivity();
    sectionAdapter = new SectionAdapter();
    setHasOptionsMenu(true);

    if (savedInstanceState == null) {
      progressDialog = new ProgressDialog(getContext());
      progressDialog.setMessage(getString(R.string.progress_dialog));
      progressDialog.show();
    }
  }

  @Override public BaseContract.BasePresenter<OverviewContract.View> getPresenter() {
    return presenter;
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_overview, container, false);

    initializeRecyclerView(context, view);
    initializeToolbar(view);
    initializeCollapsingToolbar(view);
    circularImageView = view.findViewById(R.id.profile_image);
    backgroundHeaderImageView = view.findViewById(R.id.background_image);
    loadCircularImage(circularImageView);
    nameTextView = view.findViewById(R.id.name);
    loadName();
    setHeaderBackgroundImage();
    return view;
  }

  private void measureHeights() {
    final FrameLayout layout =
        getParentFragment().getView().findViewById(R.id.bottom_navigation_bar);
    ViewTreeObserver vto = layout.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override public void onGlobalLayout() {
        layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (initialRecyclerviewHeight == 0 && statusBarHeight == 0 && bottombarHeight == 0) {
          initialRecyclerviewHeight = getRecyclerViewHeight();
          statusBarHeight = Utility.getStatusBarHeight(getContext());
          bottombarHeight = layout.getHeight() + statusBarHeight;
        }
        requiredRecyclerviewHeight = initialRecyclerviewHeight - bottombarHeight;
      }
    });
  }

  private void loadName() {
    nameTextView.setText(presenter.getName());
  }

  private void setHeaderBackgroundImage() {
    final String backgroundHeaderUrl = presenter.getBackgroundImage();

    Picasso.with(context)
        .load(backgroundHeaderUrl)
        .networkPolicy(NetworkPolicy.OFFLINE)
        .into(backgroundHeaderImageView, new Callback() {
          @Override public void onSuccess() {

          }

          @Override public void onError() {
            Picasso.with(context).load(backgroundHeaderUrl).into(backgroundHeaderImageView);
          }
        });
  }

  private void loadCircularImage(final CircularImageView circularImageView) {
    final String circularImageUrl = presenter.getCircularImage();
    Picasso.with(circularImageView.getContext())
        .load(circularImageUrl)
        .networkPolicy(NetworkPolicy.OFFLINE)
        .into(circularImageView, new Callback() {
          @Override public void onSuccess() {

          }

          @Override public void onError() {
            Picasso.with(circularImageView.getContext())
                .load(circularImageUrl)
                .into(circularImageView);
          }
        });
  }

  private void initializeRecyclerView(Context context, View view) {
    recyclerView = view.findViewById(R.id.recyclerview_overview);
    layoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(sectionAdapter);
    sectionAdapter.setItemClickListener(this);
  }

  private int getRecyclerViewHeight() {
    return recyclerView.getHeight();
  }

  private void initializeToolbar(View view) {
    Toolbar toolbar = view.findViewById(R.id.toolbar_overview);
    toolbar.setTitle("");
    ((AppCompatActivity) context).setSupportActionBar(toolbar);
    toolbar.setPadding(0, Utility.getStatusBarHeight(context), 0, 0);
  }

  private void initializeCollapsingToolbar(View view) {
    CollapsingToolbarLayout collapsingToolbarLayout =
        view.findViewById(R.id.collapsing_toolbar_overview);
    collapsingToolbarLayout.setCollapsedTitleTypeface(
        ResourcesCompat.getFont(context, R.font.android_insomnia_regular));
    collapsingToolbarLayout.setTitle(context.getResources().getText(R.string.curriculum_vitae));
    collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL);
    collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
    collapsingToolbarLayout.setExpandedTitleColor(
        getResources().getColor(android.R.color.transparent));
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    return presenter.onOptionsItemSelected(item);
  }

  @Override public void onClick(int position) {
    presenter.onItemSelected(position);
  }

  @Override public void renderDetailFragment(int position, Section section) {
    Bundle args = putBundle(position, section);
    DetailFragment detailFragment = new DetailFragment();
    detailFragment.setArguments(args);
    makeSharedElementTransition(detailFragment);
    createFragmentTransaction(position, detailFragment);
  }

  @Override public void renderSections(List<Section> sections) {
    sectionAdapter.displaySections(sections);
    measureHeights();
    setRequiredRecyclerviewHeight();
  }

  private void setRequiredRecyclerviewHeight() {
        /*
         Prevents from cutting of recyclerview's last item
         after returning from detailfragment
         */

    ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
    if (requiredRecyclerviewHeight != 0) {
      params.height = requiredRecyclerviewHeight;
      recyclerView.setLayoutParams(params);
    }
  }

  @Override public void modelHasChanged() {
    sectionAdapter.notifyDataSetChanged();
    loadCircularImage(circularImageView);
    setHeaderBackgroundImage();
    loadName();
    if (progressDialog != null) progressDialog.dismiss();
  }

  @Override public void renderLoginFragment() {
    FragmentTransaction ft = getParentFragment().getFragmentManager().beginTransaction();
    ft.replace(R.id.root_fragment_container, new LoginFragment());
    ft.commit();
  }

  @NonNull private Bundle putBundle(int position, Section section) {
    Bundle args = new Bundle();
    args.putString(IMAGE_ID, section.getPhotoUrl());
    args.putString(SECTION_TITLE, section.getTitle());
    args.putInt(ITEM_ID, section.getItemId());
    args.putString(TRANSITION_KEY, TRANSITION_VALUE + position);
    return args;
  }

  private void makeSharedElementTransition(DetailFragment detailFragment) {
    Transition changeTransform = TransitionInflater.from(context).
        inflateTransition(R.transition.default_transition);
    Transition explodeTransform = TransitionInflater.from(context).
        inflateTransition(android.R.transition.fade);

    this.setSharedElementReturnTransition(changeTransform);
    this.setExitTransition(explodeTransform);

    detailFragment.setSharedElementEnterTransition(changeTransform);
    detailFragment.setEnterTransition(explodeTransform);
    circularImageView.setVisibility(View.INVISIBLE);
  }

  private void createFragmentTransaction(int position, DetailFragment detailFragment) {
    FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
    View sharedElement =
        layoutManager.findViewByPosition(position).findViewById(R.id.section_image);
    ft.addToBackStack(getResources().getString(R.string.fragment_detail));
    String transitionName = ViewCompat.getTransitionName(sharedElement);
    ft.addSharedElement(sharedElement, transitionName);
    ft.replace(R.id.child_fragment_top, detailFragment);
    ft.commit();
  }

  @Override public FragmentActivity getFragmentActivity() {
    return (FragmentActivity) getContext();
  }
}




