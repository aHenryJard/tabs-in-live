package com.angeliquehenry.tabsinlive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.angeliquehenry.tabsinlive.data.CacheManager;
import com.angeliquehenry.tabsinlive.entity.Concert;
import com.angeliquehenry.tabsinlive.tools.AppLogger;
import com.angeliquehenry.tabsinlive.tools.FileHelper;
import com.angeliquehenry.tabsinlive.tools.FileScanException;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Button importTabsBt = (Button)findViewById(R.id.home_import_bt);
        Button readTabsBt = (Button)findViewById(R.id.home_read_bt);

        importTabsBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FileHelper helper = new FileHelper();
                try {
                    helper.importTabsFromSDCard();
                    AppLogger.debug("Concerts:"+CacheManager.getInstance().getConcerts());

                } catch (FileScanException e) {
                    e.printStackTrace();
                }
            }
        });

        readTabsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent readTabsIntent = new Intent(getApplicationContext(),TabReaderActivity.class);
                startActivity(readTabsIntent);
            }
        });
    }

}
