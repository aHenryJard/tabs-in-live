package com.angeliquehenry.tabsinlive.tools;

import android.os.Environment;

import com.angeliquehenry.tabsinlive.entity.Concert;

import java.io.File;
import java.io.FileFilter;

/**
 * Utilities for reading images on filesystem.
 * Created by a129119 on 28/01/14.
 */
public class FileHelper {

    private static final String ROOT_FOLDER = "tabsinlive";

    /**
     * Seach for a folder names tabsinlive in sdcard.
     * In this folder, the hierarchy must be concert > singer > tabs images.
     * @throws FileScanException
     */
    public void importTabsFromSDCard() throws FileScanException {

        //Get sd card path
        File folder = Environment.getExternalStorageDirectory();
        AppLogger.debug("SD card :"+folder.getAbsolutePath());

        File[] rootFile = folder.listFiles(tabinliveRootFilter);

        AppLogger.debug("Root :"+rootFile.length);

        if(rootFile.length<1){
            throw new FileScanException("Root folder not found in sd card "+ROOT_FOLDER);
        }

        //Get all concerts in the folder
        File[] concertsFile = rootFile[0].listFiles();
        Concert[] concerts = new Concert[concertsFile.length];

        for(File currentConcertFile:concertsFile){

            AppLogger.debug("Current concert is "+currentConcertFile.getName());

            //if it's no a directory, it's not processed.
            if(currentConcertFile.isDirectory()){
                Concert concert = new Concert();
                concert.setLabel(currentConcertFile.getName());

                File[] tabsFile = currentConcertFile.listFiles();
                for(File currentTabFile:tabsFile){

                    AppLogger.debug("Current tab is "+currentTabFile.getName());

                    //if it's not a dir, it's not processed.
                    if(currentTabFile.isDirectory()){
                        File[] sheetsFile = currentTabFile.listFiles();
                        for(File currentSheetFile:sheetsFile){
                            AppLogger.debug("Current sheet is "+currentSheetFile.getName());
                        }

                    }
                }

            }

        }

    }

    FileFilter tabinliveRootFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {

            AppLogger.debug("Filter, file name:"+file.getName());
            return (file.isDirectory()) && (ROOT_FOLDER.equals(file.getName()));
        }
    };

}
