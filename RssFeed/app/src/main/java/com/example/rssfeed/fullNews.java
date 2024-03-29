package com.example.rssfeed;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fullNews extends AppCompatActivity {

    TextView txt4;
    TextView txt5;
    TextView txt6;
    TextView txt7;
    TextView txt8;
    TextView txt9;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1) {
                    char c = (char) data;
                    result += c;
                    data = reader.read();
                }

             //   Log.i("ResultGot",result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("Hai", "hello");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Hai2", "hello2");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            String htmlText = " %s ";


            try {
                JSONArray arr = new JSONArray(result);
                JSONObject jsonObject = arr.getJSONObject(0);
                if(jsonObject.length()>0) {
                    if (jsonObject.getString("title") != null) {

                        txt4.setText(jsonObject.getString("title"));
                    }
                    if (jsonObject.getString("published_date") != null) {
                        txt5.setText(jsonObject.getString("published_date"));
                    }
                    if (jsonObject.getString("source") != null) {
                        txt6.setText(jsonObject.getString("source"));
                    }
                    if (jsonObject.getString("link") != null) {
                        txt7.setText(jsonObject.getString("link"));
                    }
                    if (jsonObject.getString("summary") != null) {
                        txt8.setText(jsonObject.getString("summary"));
                    }
                    if (jsonObject.getString("content") != null) {
                        txt9.setText(jsonObject.getString("content"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);
        setTitle("Today's News");
        Intent intent=getIntent();
        String data = intent.getExtras().getString("id");
        String url ="http://192.168.33.197:8000/news/api/list-news/?q="+data+"&format=json";

        Log.i("url", url);

        txt4 = findViewById(R.id.textView4);
        txt5 = findViewById(R.id.textView5);
        txt6 = findViewById(R.id.textView6);
        txt7 = findViewById(R.id.textView7);
        txt8 = findViewById(R.id.textView8);
        txt9 = findViewById(R.id.textView9);

        DownloadTask task = new DownloadTask();
        task.execute(url);
    }
}
