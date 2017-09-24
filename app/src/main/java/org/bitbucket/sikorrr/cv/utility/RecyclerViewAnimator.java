package org.bitbucket.sikorrr.cv.utility;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class RecyclerViewAnimator {
  private static final int INIT_DELAY = 350;
  private static final int INIT_TENSION = 150;
  private static final int INIT_FRICTION = 28;
  private static final int ITEM_DELAY = 500;

  private int recyclerWidth;
  private RecyclerView recyclerview;
  private SpringSystem springsystem;
  private boolean firstViewInit = true;
  private int startDelay;

  public RecyclerViewAnimator(RecyclerView recyclerView) {
    recyclerview = recyclerView;
    springsystem = SpringSystem.create();
    recyclerWidth = recyclerview.getResources().getDisplayMetrics().widthPixels;
    startDelay = INIT_DELAY;
  }

  public void onCreateViewHolder(View item) {

    if (firstViewInit) {
      slideIn(item, startDelay, INIT_TENSION, INIT_FRICTION);
      startDelay += ITEM_DELAY;
    }
  }

  private void slideIn(final View item, final int delay, final int tension, final int friction) {

    item.setTranslationX(recyclerWidth);

    Runnable startAnimation = new Runnable() {
      @Override public void run() {
        SpringConfig config = new SpringConfig(tension, friction);
        Spring spring = springsystem.createSpring();
        spring.setSpringConfig(config);
        spring.addListener(new SimpleSpringListener() {
          @Override public void onSpringUpdate(Spring spring) {
            float val = (float) (recyclerWidth - spring.getCurrentValue());
            item.setTranslationX(val);
          }

          @Override public void onSpringEndStateChange(Spring spring) {
            firstViewInit = false;
          }
        });
        spring.setEndValue(recyclerWidth);
      }
    };

    recyclerview.postDelayed(startAnimation, delay);
  }
}