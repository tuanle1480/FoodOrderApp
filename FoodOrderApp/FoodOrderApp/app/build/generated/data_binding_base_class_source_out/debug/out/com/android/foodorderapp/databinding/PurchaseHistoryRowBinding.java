// Generated by view binder compiler. Do not edit!
package com.android.foodorderapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.android.foodorderapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PurchaseHistoryRowBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ImageView imageView4;

  @NonNull
  public final ConstraintLayout purchaseHistoryLayout;

  @NonNull
  public final TextView textView14;

  @NonNull
  public final TextView txtBuyDate;

  private PurchaseHistoryRowBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView imageView3, @NonNull ImageView imageView4,
      @NonNull ConstraintLayout purchaseHistoryLayout, @NonNull TextView textView14,
      @NonNull TextView txtBuyDate) {
    this.rootView = rootView;
    this.imageView3 = imageView3;
    this.imageView4 = imageView4;
    this.purchaseHistoryLayout = purchaseHistoryLayout;
    this.textView14 = textView14;
    this.txtBuyDate = txtBuyDate;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PurchaseHistoryRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PurchaseHistoryRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.purchase_history_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PurchaseHistoryRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView3;
      ImageView imageView3 = rootView.findViewById(id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.imageView4;
      ImageView imageView4 = rootView.findViewById(id);
      if (imageView4 == null) {
        break missingId;
      }

      ConstraintLayout purchaseHistoryLayout = (ConstraintLayout) rootView;

      id = R.id.textView14;
      TextView textView14 = rootView.findViewById(id);
      if (textView14 == null) {
        break missingId;
      }

      id = R.id.txtBuyDate;
      TextView txtBuyDate = rootView.findViewById(id);
      if (txtBuyDate == null) {
        break missingId;
      }

      return new PurchaseHistoryRowBinding((ConstraintLayout) rootView, imageView3, imageView4,
          purchaseHistoryLayout, textView14, txtBuyDate);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}