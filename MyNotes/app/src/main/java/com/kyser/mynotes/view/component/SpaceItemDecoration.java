package com.kyser.mynotes.view.component;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int spaceWidth;
    private final int spaceHeight;
    private final boolean avoidLR;

    public SpaceItemDecoration(int spaceWidth, int spaceHeight, boolean avoidLR) {
        this.spaceWidth = spaceWidth;
        this.spaceHeight = spaceHeight;
        this.avoidLR = avoidLR;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = outRect.bottom =  spaceHeight;
        outRect.left =  outRect.right =spaceWidth;
    }
}
