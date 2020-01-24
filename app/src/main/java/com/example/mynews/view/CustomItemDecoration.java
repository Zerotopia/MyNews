package com.example.mynews.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.R;

/**
 * This class is used to make a line separator between items in the RecyclerView
 */
public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    /**
     * In the constructor we instantiate the divider from the drawable divider.xml.
     * This drawable define a black rectangle of 1dp in height.
     */
    public CustomItemDecoration(Context context) {
        super();
        mDivider = context.getResources().getDrawable(R.drawable.divider);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            ViewGroup item = (ViewGroup) parent.getChildAt(i);
            RelativeLayout textLayout = item.findViewById(R.id.row_article_text_layout);
            TextView summary = textLayout.findViewById(R.id.row_article_summary);

            //leftMargin is calculate to ensure that
            // the divider is align with the beginning of the text.
            int leftMargin = textLayout.getLeft() + summary.getLeft();
            int top = item.getBottom();
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(leftMargin, top, parent.getWidth(), bottom);
            mDivider.draw(c);
        }
    }
}
