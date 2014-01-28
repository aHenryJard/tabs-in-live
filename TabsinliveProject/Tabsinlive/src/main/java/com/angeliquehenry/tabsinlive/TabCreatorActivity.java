package com.angeliquehenry.tabsinlive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angeliquehenry.tabsinlive.data.SheetDao;
import com.angeliquehenry.tabsinlive.data.TabDao;
import com.angeliquehenry.tabsinlive.entity.Sheet;
import com.angeliquehenry.tabsinlive.entity.Tab;
import com.angeliquehenry.tabsinlive.tools.AppLogger;
import com.angeliquehenry.tabsinlive.tools.FileHelper;
import com.angeliquehenry.tabsinlive.tools.FileScanException;
import com.angeliquehenry.tabsinlive.tools.ImageHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Activity use to import image file to create tabs.
 */
public class TabCreatorActivity extends Activity{

    private Tab currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.tab_creator_activity);

        Button importButton = (Button)findViewById(R.id.tab_creator_import_bt);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileHelper helper = new FileHelper();
                try {
                    helper.importTabsFromSDCard();
                } catch (FileScanException e) {
                    e.printStackTrace();
                }
            }
        });

        //If it's a new tab, create temp data to allows the app to store in real time all data.
        //createEmptyTab();

        //updateViewWithTab(currentTab);
    }

    private void updateViewWithTab(Tab currentTab) {
        EditText singerText = (EditText)findViewById(R.id.tab_creator_singer_text);
        EditText tabNameText = (EditText)findViewById(R.id.tab_creator_tabname_text);

        if(currentTab.title==null){
            tabNameText.setText("");
        }else{
            tabNameText.setText(currentTab.title);
        }

        if(currentTab.singer==null){
            singerText.setText("");
        }else{
            singerText.setText(currentTab.singer);
        }

        /*for(Sheet sheet:currentTab.sheets){
            addPageView(sheet);
        }*/
    }

    /**
     * Create temp tab, with all minimum data to allows the app to store all data in real time.
     */
    private void createEmptyTab() {

        String tempTabName = "tempTabName"+System.currentTimeMillis();

        currentTab = new Tab();
        currentTab.title =tempTabName;
        Sheet firstSheet = new Sheet();
        firstSheet.pageNumber=1;

        Collection<Sheet> sheets = new ArrayList<Sheet>();
        sheets.add(firstSheet);

        TabDao tabDao = new TabDao(this);
        int tabId = tabDao.create(currentTab);

        firstSheet.tab = currentTab;
        SheetDao sheetDao = new SheetDao(this);
        sheetDao.create(firstSheet);

    }

    /**
     * Add layout that represent one page and all action button on that page.
     */
    private void addPageView(Sheet sheet){
        LinearLayout scrollViewContent = (LinearLayout)findViewById(R.id.tab_creator_scrollview_content);

        //Create layout for current page view.
        FrameLayout plateRow =(FrameLayout)getLayoutInflater().inflate(R.layout.tab_creator_page_view,null);

        if(sheet.image != null){
            ImageView tabImage = (ImageView)plateRow.findViewById(R.id.tab_creator_view_image);

            Bitmap bitmap = ImageHelper.getBitmapFromBytes(sheet.image);
            tabImage.setImageBitmap(bitmap);
        }
        else{
            //default image
            ImageView tabImage = (ImageView)plateRow.findViewById(R.id.tab_creator_view_image);
            tabImage.setImageResource(R.drawable.default_tab);
        }

        TextView pageText = (TextView)plateRow.findViewById(R.id.tab_creator_view_page_text);
        pageText.setText("Page "+sheet.pageNumber);

        //Set all listener
        //TODO

        scrollViewContent.addView(plateRow);

    }


    /**
     * Get image for tab from image picker intent.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageHelper.SELECT_PICTURE_INTENT) {
                android.net.Uri selectedImageUri = data.getData();
                AppLogger.debug("Image path:" + selectedImageUri);

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


}
