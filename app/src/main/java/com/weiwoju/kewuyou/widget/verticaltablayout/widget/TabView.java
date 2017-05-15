package com.weiwoju.kewuyou.widget.verticaltablayout.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import q.rorbin.badgeview.Badge;

import static android.R.attr.strokeWidth;

/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public abstract class TabView extends FrameLayout implements Checkable {

    public TabView(Context context) {
        super(context);
    }

    public abstract TabView setBadge(TabBadge badge);

    public abstract TabView setIcon(TabIcon icon);

    public abstract TabView setTitle(TabTitle title);

    public abstract TabView setBackground(int resid);

    public abstract TabBadge getBadge();

    public abstract TabIcon getIcon();

    public abstract TabTitle getTitle();

    public abstract ImageView getIconView();

    public abstract TextView getTitleView();

    public abstract Badge getBadgeView();

    public static class TabIcon {

        private Builder mBuilder;

        public TabIcon(Builder mBuilder) {
            this.mBuilder = mBuilder;
        }

        public int getSelectedIcon() {
            return mBuilder.mSelectedIcon;
        }

        public int getNormalIcon() {
            return mBuilder.mNormalIcon;
        }

        public int getIconGravity() {
            return mBuilder.mIconGravity;
        }

        public int getIconWidth() {
            return mBuilder.mIconWidth;
        }

        public int getIconHeight() {
            return mBuilder.mIconHeight;
        }

        public int getMargin() {
            return mBuilder.mMargin;
        }

        public static class Builder {
            private int mSelectedIcon;
            private int mNormalIcon;
            private int mIconGravity;
            private int mIconWidth;
            private int mIconHeight;
            private int mMargin;

            public Builder() {
                mSelectedIcon = 0;
                mNormalIcon = 0;
                mIconWidth = LayoutParams.WRAP_CONTENT;
                mIconHeight = LayoutParams.WRAP_CONTENT;
                mIconGravity = Gravity.START;
                mMargin = 0;
            }

            public Builder setIcon(int selectIconResId, int normalIconResId) {
                mSelectedIcon = selectIconResId;
                mNormalIcon = normalIconResId;
                return this;
            }

            public Builder setIconSize(int width, int height) {
                mIconWidth = width;
                mIconHeight = height;
                return this;
            }

            public Builder setIconGravity(int gravity) {
                if (gravity != Gravity.START && gravity != Gravity.END
                        & gravity != Gravity.TOP & gravity != Gravity.BOTTOM) {
                    throw new IllegalStateException("iconGravity only support Gravity.START " +
                            "or Gravity.END or Gravity.TOP or Gravity.BOTTOM");
                }
                mIconGravity = gravity;
                return this;
            }

            public Builder setIconMargin(int margin) {
                mMargin = margin;
                return this;
            }

            public TabIcon build() {
                return new TabIcon(this);
            }
        }
    }

    public static class TabTitle {
        private Builder mBuilder;

        public TabTitle(Builder mBuilder) {
            this.mBuilder = mBuilder;
        }

        public int getColorSelected() {
            return mBuilder.mColorSelected;
        }

        public int getColorNormal() {
            return mBuilder.mColorNormal;
        }

        public int getTitleTextSize() {
            return mBuilder.mTitleTextSize;
        }

        public String getContent() {
            return mBuilder.mContent;
        }

        public static class Builder {
            private int mColorSelected;
            private int mColorNormal;
            private int mTitleTextSize;
            private String mContent;

            public Builder() {
                this.mColorSelected = 0xFFFF4081;
                this.mColorNormal = 0xFF757575;
                this.mTitleTextSize = 16;
                this.mContent = "title";
            }

            public Builder setTextColor(int colorSelected, int colorNormal) {
                mColorSelected = colorSelected;
                mColorNormal = colorNormal;
                return this;
            }

            public Builder setTextSize(int sizeSp) {
                mTitleTextSize = sizeSp;
                return this;
            }

            public Builder setContent(String content) {
                mContent = content;
                return this;
            }

            public TabTitle build() {
                return new TabTitle(this);
            }
        }
    }

    public static class TabBadge {
        private Builder mBuilder;

        private TabBadge(Builder mBuilder) {
            this.mBuilder = mBuilder;
        }

        public int getBackgroundColor() {
            return mBuilder.colorBackground;
        }

        public int getBadgeTextColor() {
            return mBuilder.colorBadgeText;
        }

        public float getBadgeTextSize() {
            return mBuilder.badgeTextSize;
        }

        public float getBadgePadding() {
            return mBuilder.badgePadding;
        }

        public int getBadgeNumber() {
            return mBuilder.badgeNumber;
        }

        public String getBadgeText() {
            return mBuilder.badgeText;
        }

        public int getBadgeGravity() {
            return mBuilder.badgeGravity;
        }

        public int getGravityOffsetX() {
            return mBuilder.gravityOffsetX;
        }

        public int getGravityOffsetY() {
            return mBuilder.gravityOffsetY;
        }

        public boolean isExactMode() {
            return mBuilder.exactMode;
        }

        public boolean isShowShadow() {
            return mBuilder.showShadow;
        }

        public Drawable getDrawableBackground() {
            return mBuilder.drawableBackground;
        }

        public int getStrokeColor() {
            return mBuilder.colorStroke;
        }

        public boolean isDrawableBackgroundClip() {
            return mBuilder.drawableBackgroundClip;
        }

        public float getStrokeWidth() {
            return mBuilder.strokeWidth;
        }

        public Badge.OnDragStateChangedListener getOnDragStateChangedListener() {
            return mBuilder.dragStateChangedListener;
        }

        public static class Builder {
            private int colorBackground;
            private int colorBadgeText;
            private int colorStroke;
            private Drawable drawableBackground;
            private boolean drawableBackgroundClip;
            private float strokeWidth;
            private float badgeTextSize;
            private float badgePadding;
            private int badgeNumber;
            private String badgeText;
            private int badgeGravity;
            private int gravityOffsetX;
            private int gravityOffsetY;
            private boolean exactMode;
            private boolean showShadow;
            private Badge.OnDragStateChangedListener dragStateChangedListener;

            public Builder() {
                colorBackground = 0xFFE84E40;
                colorBadgeText = 0xFFFFFFFF;
                colorStroke = Color.TRANSPARENT;
                drawableBackground = null;
                drawableBackgroundClip = false;
                strokeWidth = 0;
                badgeTextSize = 11;
                badgePadding = 5;
                badgeNumber = 0;
                badgeText = null;
                badgeGravity = Gravity.END | Gravity.TOP;
                gravityOffsetX = 5;
                gravityOffsetY = 5;
                exactMode = false;
                showShadow = true;
            }

            public TabBadge build() {
                return new TabBadge(this);
            }

            public Builder stroke(int color, int strokeWidth) {
                this.colorStroke = color;
                this.strokeWidth = strokeWidth;
                return this;
            }

            public Builder setDrawableBackground(Drawable drawableBackground, boolean clip) {
                this.drawableBackground = drawableBackground;
                this.drawableBackgroundClip = clip;
                return this;
            }

            public Builder setShowShadow(boolean showShadow) {
                this.showShadow = showShadow;
                return this;
            }

            public Builder setOnDragStateChangedListener(Badge.OnDragStateChangedListener dragStateChangedListener) {
                this.dragStateChangedListener = dragStateChangedListener;
                return this;
            }

            public Builder setExactMode(boolean exactMode) {
                this.exactMode = exactMode;
                return this;
            }

            public Builder setBackgroundColor(int colorBackground) {
                this.colorBackground = colorBackground;
                return this;
            }

            public Builder setBadgePadding(float dpValue) {
                this.badgePadding = dpValue;
                return this;
            }

            public Builder setBadgeNumber(int badgeNumber) {
                this.badgeNumber = badgeNumber;
                this.badgeText = null;
                return this;
            }

            public Builder setBadgeGravity(int badgeGravity) {
                this.badgeGravity = badgeGravity;
                return this;
            }

            public Builder setBadgeTextColor(int colorBadgeText) {
                this.colorBadgeText = colorBadgeText;
                return this;
            }

            public Builder setBadgeTextSize(float badgeTextSize) {
                this.badgeTextSize = badgeTextSize;
                return this;
            }

            public Builder setBadgeText(String badgeText) {
                this.badgeText = badgeText;
                this.badgeNumber = 0;
                return this;
            }

            public Builder setGravityOffset(int offsetX, int offsetY) {
                this.gravityOffsetX = offsetX;
                this.gravityOffsetY = offsetY;
                return this;
            }
        }
    }
}
