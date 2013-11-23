package com.angeliquehenry.tabsinlive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.angeliquehenry.tabsinlive.data.DataInitializer;
import com.angeliquehenry.tabsinlive.entity.Tab;
import com.angeliquehenry.tabsinlive.tools.AppLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Display tab in the screen.
 */
public class TabReaderActivity extends Activity {

    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.tab_reader_activity);

        DataInitializer data = new DataInitializer();

        if(data.getTabList().size()>0){
            loadTab(data.getTabList().get(0));

            Spinner spinner = (Spinner) findViewById(R.id.tab_spinner);

            ArrayAdapter<Tab> adapter = new ArrayAdapter<Tab>(this, R.layout.tab_spinner_view,data.getTabList());

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;

    }

    private void loadTab(Tab tab){
        LinearLayout tabsContentScroll = (LinearLayout)findViewById(R.id.tab_tabList);

        final ScrollView scrollView = (ScrollView)findViewById(R.id.tab_scrollView);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppLogger.debug("Click on view");
                scrollView.smoothScrollBy(0,screenHeight/3);
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
