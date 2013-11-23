package com.angeliquehenry.tabsinlive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.angeliquehenry.tabsinlive.data.DataInitializer;
import com.angeliquehenry.tabsinlive.entity.Tab;
import com.angeliquehenry.tabsinlive.tools.AppLogger;

/**
 * Display tab in the screen.
 */
public class TabReaderActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab_reader_activity);

        DataInitializer data = new DataInitializer();
        loadTab(data.getSuperbus());

    }

    private void loadTab(Tab tab){
        LinearLayout tabsContentScroll = (LinearLayout)findViewById(R.id.tab_tabList);

        final ScrollView scrollView = (ScrollView)findViewById(R.id.tab_scrollView);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppLogger.debug("Click on view");
                scrollView.scrollBy(0, 100);
            }
        };

        for(int image: tab.getSheets()){
            ImageView firstPageView = new ImageView(this);
            firstPageView.setImageResource(image);
            firstPageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            firstPageView.setAdjustViewBounds(true);
            firstPageView.setPadding(1, 1, 1, 1);
            firstPageView.setBackgroundColor(Color.BLACK);
            firstPageView.setOnClickListener(clickListener);
            tabsContentScroll.addView(firstPageView);
        }
    }
}
