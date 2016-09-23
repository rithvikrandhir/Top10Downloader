package com.example.rithvik.top10downloader;

import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button parseButton;
    private ListView xmlList;
    private String mFileContents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseButton = (Button) findViewById(R.id.parseButton);
        parseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add parse code
                ParseSong ps = new ParseSong(mFileContents);
                ps.process();
                ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(MainActivity.this, R.layout.list_item, ps.getSongs());
                xmlList.setAdapter(adapter);


            }
        });
        xmlList = (ListView) findViewById(R.id.xmlList);


        DownloadData dd = new DownloadData();
        dd.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml");
    }

    //<Download location URL, progress bar, Data or result>
    private class DownloadData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            mFileContents = downloadXMLFile(strings[0]);
            if (mFileContents == null) {
               // Log.d("DownloadData", "Error Downloading");
                //Log.debug
            }
            return mFileContents;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Log.d("DownloadData", "Result : " + s);


        }

        private String downloadXMLFile(String urlPath) {

            StringBuilder buffer = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                int response = connection.getResponseCode();
               // Log.d("DownloadData", "Response code : " + response);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charRead = isr.read(inputBuffer);
                    if (charRead <= 0) {
                        break;
                    }
                    buffer.append(String.copyValueOf(inputBuffer, 0, charRead));

                }
                return buffer.toString();


            } catch (IOException e) {

                Log.d("DownloadData", "IO Exception reading data " + e.getMessage());

            } catch (SecurityException e){

                Log.d("DownloadData", "Security Exception : " + e.getMessage());


            }
            return null;
        }
    }
}
