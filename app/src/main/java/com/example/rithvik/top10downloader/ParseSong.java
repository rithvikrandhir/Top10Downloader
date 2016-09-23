package com.example.rithvik.top10downloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by rithvik on 18/07/16.
 */
public class ParseSong {
    private String xmldata;
    private ArrayList<Song> songs;

    public ParseSong(String xmldata) {
        this.xmldata = xmldata;
        songs = new ArrayList<Song>();
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public boolean process(){
        boolean status = true;
        Song currentSong;
        boolean inEntry = false;
        String textValue = "";
        currentSong = new Song();

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmldata));
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                       // Log.d("ParseSong","Start Tag for " + tagName);
                        if(tagName.equalsIgnoreCase("entry")){
                            inEntry = true;
                            currentSong = new Song();
                            break;
                        }


                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                       // Log.d("ParseSong","End Tag for " + tagName);
                        if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){
                                songs.add(currentSong);
                                inEntry = false;
                            }else if(tagName.equalsIgnoreCase("name")){
                                currentSong.setTitle(textValue);
                            }else if (tagName.equalsIgnoreCase("artist")){
                                currentSong.setArtist(textValue);
                            }else if (tagName.equalsIgnoreCase("image")){
                                currentSong.setUrl(textValue);
                            }
                        }
                        break;
                    default:


                }
                eventType = xpp.next();
            }

        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        for (Song song : songs){
            Log.d("ParseApplication", "*****************");
            Log.d("ParseApplication", "name : " + song.getTitle());
            Log.d("ParseApplication", "artist : " + song.getArtist());
            Log.d("ParseApplication", "url : " + song.getUrl());


        }
    return true;
    }
}
