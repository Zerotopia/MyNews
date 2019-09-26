package com.example.mynews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mynews.R;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private Drawable mDivider;

    public CustomItemDecoration(Context context) {
        super();
        mContext = context;
        mDivider = mContext.getResources().getDrawable(R.drawable.divider);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            ViewGroup item = (ViewGroup) parent.getChildAt(i);
            RelativeLayout layout = (RelativeLayout) item.getChildAt(1);
            TextView resume = layout.findViewById(R.id.resume);


            int leftMargin = layout.getLeft() + resume.getLeft();
            int top = item.getBottom();
            int bottom = item.getBottom() + mDivider.getIntrinsicHeight();

            mDivider.setBounds(leftMargin, top, parent.getWidth(), bottom);
            mDivider.draw(c);
        }
    }
}
