package com.example.vibodha.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import com.google.gson.Gson;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        new AsyncCaller(this).execute();
    }



    public String getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);

        BufferedReader br=new BufferedReader(new InputStreamReader((InputStream) urlConnection.getContent()));

        char[] buffer = new char[1024];
        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return jsonString;

    }

    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        ListView listView = (ListView) findViewById(R.id.historyList);
        List<Item> items = new ArrayList<>();
        ProgressDialog dialog;
        private Context mContext;

        public AsyncCaller(Context activity) {
            dialog = new ProgressDialog(activity);
            mContext = activity;
        }

        protected void onPreExecute() {
            dialog.setMessage("Loading, please wait.");
            dialog.show();
        }



        @Override
        protected Void doInBackground(Void... params){

            String jsonObjectFromURL = null;
            try {
                jsonObjectFromURL = getJSONObjectFromURL("http://172.16.1.127:9669/businesses/silk/appointments/");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(jsonObjectFromURL.toString());
            Gson gson = new Gson();
            AppointmentResponse[] appointments = gson.fromJson(jsonObjectFromURL, AppointmentResponse[].class);

            for(AppointmentResponse appointment: appointments){

                URL url = null;
                try {
                    url = new URL(appointment.getTreatments().get(0).getImageUrl());
                    //url = new URL("https://img.grouponcdn.com/deal/hZjas5BUUbdLVbbLE13efV/471061181-2048x1229/v1/c700x420.jpg");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    Item item = new Item(bmp, appointment.getTreatments().get(0).getName(),"Date: " + appointment.getDate(), appointment.getAppointmentStatus());
                    items.add(item);
                    System.out.println(appointment.getAppointmentId());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            //this method will be running on UI thread
            int count = 0;
            final ItemAdapter adapter = new ItemAdapter(mContext, R.layout.item_layout, items.toArray(new Item[items.size()]), count);
            System.out.println(items.size());


            listView.setAdapter(adapter);

        }

    }

}