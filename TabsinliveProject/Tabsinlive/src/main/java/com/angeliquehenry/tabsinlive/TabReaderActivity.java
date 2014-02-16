package com.angeliquehenry.tabsinlive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.angeliquehenry.tabsinlive.data.CacheManager;
import com.angeliquehenry.tabsinlive.data.SheetDao;
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

/**
 * Display tab in the screen.
 */
public class TabReaderActivity extends Activity implements AdapterView.OnItemSelectedListener {

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

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void testDb() {

        SheetDao sheetDao = new SheetDao(this);

        Sheet sheet = new Sheet();
        sheet.pageNumber = 3;

        sheetDao.create(sheet);

        List<Sheet> sheets = sheetDao.getAll();

        AppLogger.debug("Sheets:");
        for(Sheet s:sheets){
            AppLogger.debug(""+s);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void checkScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;
    }

    private void loadExistingTabs() {
        ArrayList<Concert> concerts = CacheManager.getInstance().getConcerts();
        Concert currentConcert = concerts.get(0);

        if(currentConcert.tabs.size()>0){
            loadTab(currentConcert.tabs.get(0));

            Spinner spinner = (Spinner) findViewById(R.id.tab_spinner);

            ArrayAdapter<Tab> adapter = new ArrayAdapter<Tab>(this, R.layout.tab_spinner_view,currentConcert.tabs);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
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

            Toast.makeText(this,"Bm height:"+bitmap.getHeight(),1).show();
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
                AppLogger.debug("Clic sur page");
                scrollView.smoothScrollBy(0,screenHeight/2);
            }
        };

        return clickListener;
    }


    // ----- SPINNER ------------

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        AppLogger.debug("Clic sur "+pos);
        Tab tab = (Tab)parent.getItemAtPosition(pos);
        loadTab(tab);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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
