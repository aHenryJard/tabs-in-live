package com.angeliquehenry.tabsinlive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.angeliquehenry.tabsinlive.data.CacheManager;
import com.angeliquehenry.tabsinlive.entity.Concert;
import com.angeliquehenry.tabsinlive.entity.Sheet;
import com.angeliquehenry.tabsinlive.entity.Tab;
import com.angeliquehenry.tabsinlive.tools.AppLogger;
import com.angeliquehenry.tabsinlive.tools.ImageHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.TargetApi;

import android.os.CountDownTimer;

/**
 * Display tab in the screen.
 */
public class TabReaderActivity extends Activity {

    private int screenHeight;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.tab_reader_activity);
        loadExistingTabs();
        checkScreenSize();


    }

    @Override
    protected void onResume() {
        super.onResume();


    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void checkScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;

        if(screenHeight==0){
            screenHeight = 800;
        }
    }

    private void loadExistingTabs() {
        ArrayList<Concert> concerts = CacheManager.getInstance().getConcerts();

        if(concerts.size()>0){
            final Spinner concertSpinner = (Spinner) findViewById(R.id.concert_spinner);
            ArrayAdapter<Concert> concertAdapter = new ArrayAdapter<Concert>(this, R.layout.tab_spinner_view,concerts);
            concertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            concertSpinner.setAdapter(concertAdapter);
            concertSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    // An item was selected. You can retrieve the selected item using
                    AppLogger.debug("Clic concert sur "+pos);
                    Concert concert = (Concert)parent.getItemAtPosition(pos);
                    initTabsForConcert(concert);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });

        }
        else
        {
            Toast.makeText(this,"No tabs are loaded, you should maybe import some.",1).show();
        }

    }

    private void initTabsForConcert(final Concert concert){
        if(concert.tabs.size()>0){
            loadTab(concert.tabs.get(0));

            final Spinner spinner = (Spinner) findViewById(R.id.tab_spinner);

            ArrayAdapter<Tab> adapter = new ArrayAdapter<Tab>(this, R.layout.tab_spinner_view,concert.tabs);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    // An item was selected. You can retrieve the selected item using
                    AppLogger.debug("Clic tab sur "+pos);
                    Tab tab = (Tab)parent.getItemAtPosition(pos);
                    loadTab(tab);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });

            Button nextButton = (Button)findViewById(R.id.tab_reader_next_bt);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentSelection = spinner.getSelectedItemPosition();
                    int nextSelected = currentSelection+1;
                    if(currentSelection>concert.tabs.size()){
                        nextSelected=0;
                    }
                    spinner.setSelection(nextSelected);
                }
            });
        }
    }

    private void loadTab(Tab tab){
        LinearLayout tabsContentScroll = (LinearLayout)findViewById(R.id.tab_tabList);
        final ScrollView scrollView = (ScrollView)findViewById(R.id.tab_scrollView);

        scrollView.scrollTo(0,0);
        tabsContentScroll.removeAllViews();

        Collections.sort(tab.sheets);

        for(Sheet sheet: tab.sheets){

            Bitmap bitmap;
            if(sheet.image==null){
                //load image from path
                bitmap = ImageHelper.getImageFromSdCard(sheet.imagePath);
            }
            else{
                bitmap = ImageHelper.getBitmapFromBytes(sheet.image);
            }

            //Toast.makeText(this,"Bm height:"+bitmap.getHeight(),1).show();
           // LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, bitmap.getHeight());

            ImageView currentSheetView = new ImageView(this);
            //currentSheetView.setLayoutParams(layoutParams);
            currentSheetView.setImageBitmap(bitmap);
            currentSheetView.setScaleType(ImageView.ScaleType.FIT_START);
            currentSheetView.setAdjustViewBounds(true);

            currentSheetView.setPadding(1, 1, 1, 1);
            currentSheetView.setBackgroundColor(Color.BLACK);
            currentSheetView.setOnClickListener(getScrollClickListener());
            tabsContentScroll.addView(currentSheetView);

            tabsContentScroll.setOnClickListener(getScrollClickListener());
        }
    }

    private float getBitmapScalingFactor(Bitmap bm) {
        // Get display width from device
        int displayWidth = getWindowManager().getDefaultDisplay().getWidth();

        // Get margin to use it for calculating to max width of the ImageView
        int leftMargin = 0;
        int rightMargin = 0;

        // Calculate the max width of the imageView
        int imageViewWidth = displayWidth - (leftMargin + rightMargin);

        // Calculate scaling factor and return it
        return ( (float) imageViewWidth / (float) bm.getWidth() );
    }

    private View.OnClickListener getScrollClickListener() {
        final ScrollView scrollView = (ScrollView)findViewById(R.id.tab_scrollView);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollDownTab();
            }
        };

        return clickListener;
    }

    //ancienne version
    /*private void scrollDownTab(){
        AppLogger.debug("Scroll page, hauteur:"+screenHeight/2);
        final ScrollView scrollView = (ScrollView)findViewById(R.id.tab_scrollView);
        scrollView.smoothScrollBy(0,screenHeight/2);
    }*/

    private void scrollDownTab(){
        AppLogger.debug("Scroll page, hauteur:"+screenHeight/2);
        final ScrollView scrollView = (ScrollView)findViewById(R.id.tab_scrollView);
        CountDownTimer timer = new CountDownTimer(2000, 20) {

            public void onTick(long millisUntilFinished) {

                scrollView.scrollTo(0, (int) (2000 - millisUntilFinished));
            }

            public void onFinish() {
            }

        }.start();
    }

    // -----------------------------------------
    // -- Selection des images -----------------
    // -----------------------------------------

    // this is the action code we use in our intent,
    // this way we know we're looking at the response from our own action
    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                android.net.Uri selectedImageUri = data.getData();
                //selectedImagePath = getPath(selectedImageUri);
                AppLogger.debug("Image path:"+selectedImageUri);
                LinearLayout tabsContentScroll = (LinearLayout)findViewById(R.id.tab_tabList);

                try {
                    Bitmap bitmap = ImageHelper.getBitmapFromURI(selectedImageUri, this);

                    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
                    boolean isCompressSuccess = bitmap.compress(Bitmap.CompressFormat.PNG,100,byteOutputStream);

                    //TODO ici
                    byteOutputStream.toByteArray();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }


    private void callImagePickerIntent() {
        // in onCreate or any event where your want the user to
        // select a file
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    // --------------------------
    // -- Fin selection images --
    // --------------------------
}
