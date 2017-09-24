package org.bitbucket.sikorrr.cv.presentation.overview.view;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.presentation.overview.model.Section;
import org.bitbucket.sikorrr.cv.utility.ItemClickListener;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {

  private List<Section> sections = new ArrayList<>();
  private ItemClickListener itemClickListener;

  public void setItemClickListener(ItemClickListener itemClickListener) {
    this.itemClickListener = itemClickListener;
  }

  public void displaySections(List<Section> sections) {
    this.sections = sections;
    notifyDataSetChanged();
  }

  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView sectionTitle;
    private ImageView sectionImage;

    MyViewHolder(View view) {
      super(view);
      view.setOnClickListener(this);
      sectionTitle = view.findViewById(R.id.section_title);
      sectionImage = view.findViewById(R.id.section_image);
    }

    @Override public void onClick(View view) {
      itemClickListener.onClick(getAdapterPosition());
    }
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.section_layout, parent, false);
    return new MyViewHolder(view);
  }

  @Override public void onBindViewHolder(final MyViewHolder holder, int position) {
    final Section section = sections.get(position);
    holder.sectionTitle.setText(section.getTitle());

    if (section.getPhotoUrl() == null) {
      throw new UnsupportedOperationException("e");
    } else {
      Picasso.with(holder.sectionImage.getContext())
          .load(section.getPhotoUrl())
          .networkPolicy(NetworkPolicy.OFFLINE)
          .into(holder.sectionImage, new Callback() {
            @Override public void onSuccess() {
            }

            @Override public void onError() {
              Picasso.with(holder.sectionImage.getContext())
                  .load(section.getPhotoUrl())
                  .into(holder.sectionImage);
            }
          });
    }
    ViewCompat.setTransitionName(holder.sectionImage, OverviewFragment.TRANSITION_VALUE + position);
  }

  @Override public int getItemCount() {
    return sections.size();
  }
}


