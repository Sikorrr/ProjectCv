package org.bitbucket.sikorrr.cv.utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;
import org.bitbucket.sikorrr.cv.R;

public class Utility {

  public static int dpToPx(float dp, Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    return px;
  }

  public static int getStatusBarHeight(Context context) {
    int result = 0;
    int resourceId = context.getResources()
        .getIdentifier(context.getString(R.string.status_bar_height),
            context.getString(R.string.dimen), context.getString(R.string.android));
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  public static void openWebPage(Context context, String url) {
    Uri webpage = Uri.parse(url);
    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

    if (intent.resolveActivity(context.getPackageManager()) != null) {
      context.startActivity(intent);
    } else {
      Toast.makeText(context, R.string.no_intent, Toast.LENGTH_LONG).show();
    }
  }

  public static void dialPhoneNumber(Context context, String number) {
    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:" + number));
    if (intent.resolveActivity(context.getPackageManager()) != null) {
      context.startActivity(intent);
    } else {
      Toast.makeText(context, R.string.no_intent, Toast.LENGTH_LONG).show();
    }
  }

  public static void composeEmail(Context context, String address) {
    String[] addresses = new String[] { address };
    Intent intent = new Intent(Intent.ACTION_SENDTO);
    intent.setData(Uri.parse("mailto:"));
    intent.putExtra(Intent.EXTRA_EMAIL, addresses);
    if (intent.resolveActivity(context.getPackageManager()) != null) {
      context.startActivity(intent);
    } else {
      Toast.makeText(context, R.string.email_intent, Toast.LENGTH_LONG).show();
    }
  }
}
