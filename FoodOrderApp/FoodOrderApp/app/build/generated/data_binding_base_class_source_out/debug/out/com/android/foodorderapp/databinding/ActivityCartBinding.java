// Generated by view binder compiler. Do not edit!
package com.android.foodorderapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.android.foodorderapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCartBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btPurchase;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final RecyclerView recyCart;

  @NonNull
  public final TextView textView13;

  @NonNull
  public final TextView textView8;

  @NonNull
  public final TextView txtTotalMoneyInCart;

  private ActivityCartBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton btPurchase, @NonNull LinearLayout linearLayout2,
      @NonNull RecyclerView recyCart, @NonNull TextView textView13, @NonNull TextView textView8,
      @NonNull TextView txtTotalMoneyInCart) {
    this.rootView = rootView;
    this.btPurchase = btPurchase;
    this.linearLayout2 = linearLayout2;
    this.recyCart = recyCart;
    this.textView13 = textView13;
    this.textView8 = textView8;
    this.txtTotalMoneyInCart = txtTotalMoneyInCart;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_cart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btPurchase;
      AppCompatButton btPurchase = rootView.findViewById(id);
      if (btPurchase == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = rootView.findViewById(id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.recyCart;
      RecyclerView recyCart = rootView.findViewById(id);
      if (recyCart == null) {
        break missingId;
      }

      id = R.id.textView13;
      TextView textView13 = rootView.findViewById(id);
      if (textView13 == null) {
        break missingId;
      }

      id = R.id.textView8;
      TextView textView8 = rootView.findViewById(id);
      if (textView8 == null) {
        break missingId;
      }

      id = R.id.txtTotalMoneyInCart;
      TextView txtTotalMoneyInCart = rootView.findViewById(id);
      if (txtTotalMoneyInCart == null) {
        break missingId;
      }

      return new ActivityCartBinding((ConstraintLayout) rootView, btPurchase, linearLayout2,
          recyCart, textView13, textView8, txtTotalMoneyInCart);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
