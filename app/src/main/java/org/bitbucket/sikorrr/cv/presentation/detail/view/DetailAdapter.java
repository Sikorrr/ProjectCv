package org.bitbucket.sikorrr.cv.presentation.detail.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.bitbucket.sikorrr.cv.R;
import org.bitbucket.sikorrr.cv.presentation.detail.model.Detail;
import org.bitbucket.sikorrr.cv.utility.RecyclerViewAnimator;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ItemViewHolder> {
  private Detail details;
  private RecyclerViewAnimator animator;

  DetailAdapter(RecyclerView recyclerView) {
    animator = new RecyclerViewAnimator(recyclerView);
  }

  void displayDetails(Detail details) {
    this.details = details;
    notifyDataSetChanged();
  }

  static class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView itemHeader;
    LinearLayout subitem;

    ItemViewHolder(View view) {
      super(view);
      itemHeader = view.findViewById(R.id.header_item);
      subitem = view.findViewById(R.id.subitem_layout);
    }
  }

  @Override public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
    animator.onCreateViewHolder(view);
    return new ItemViewHolder(view);
  }

  @Override public void onBindViewHolder(ItemViewHolder holder, int position) {
    Detail.Item detailItem = details.getItems().get(position);
    LinearLayout subitemLayout = holder.subitem;
    holder.subitem.removeAllViews();

    if (detailItem != null) {
      for (int i = 0; i < detailItem.getSubitems().size(); i++) {

        View view = LayoutInflater.from(subitemLayout.getContext())
            .inflate(R.layout.subitem_layout, subitemLayout, false);

        TextView subsectionLeft = view.findViewById(R.id.subsectionLeft);
        TextView subsectionRight = view.findViewById(R.id.subsectionRight);
        String subsectionLeftText = detailItem.getSubitemLeft(i);
        subsectionLeft.setText(subsectionLeftText);
        String subsectionRightText = detailItem.getSubitemRight(i);
        subsectionRight.setText(subsectionRightText);
        subitemLayout.addView(view);

        if ("".equals(subsectionLeftText) || subsectionLeftText == null) {
          subsectionLeft.setVisibility(View.GONE);
        }

        if ("".equals(subsectionRightText) || subsectionRightText == null) {
          subsectionRight.setVisibility(View.GONE);
        }
      }

      if ("null".equals(detailItem.getHeader())) {
        holder.itemHeader.setVisibility(View.GONE);
      } else {
        holder.itemHeader.setText(detailItem.getHeader());
      }
    }
  }

  @Override public int getItemCount() {
    return details.getItems().size();
  }
}
