package com.example.vibodha.demo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.couchbase.lite.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener{
    private Manager manager = null;
    private Database database = null;
    final String TAG = "items";
    private CouchbaseConnection couchbaseConnection;
    private String DB_NAME = "items";
    private String url = "http://172.16.1.127:4987/items/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        couchbaseConnection = new CouchbaseConnection(this);

        try {
            manager = couchbaseConnection.getManagerInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            database = couchbaseConnection.getDatabaseInstance(DB_NAME);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        couchbaseConnection.startSync(database, url);

        Query query = database.createAllDocumentsQuery();
        QueryEnumerator result = null;
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        ListView listView = (ListView) findViewById(R.id.historyList);
        List<Item> items = new ArrayList<>();

        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
            Document document = row.getDocument();

            Item item = new Item(document.getProperty("_id").toString() ,document.getProperty("image").toString() , document.getProperty("name").toString(),"Date: " + document.getProperty("date").toString(), document.getProperty("status").toString());
            items.add(item);
            final ItemAdapter adapter = new ItemAdapter(this, R.layout.item_layout, items.toArray(new Item[items.size()]));
            listView.setAdapter(adapter);
        }

    }


    @Override
    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.item:
                try {
                    Document document = database.getDocument(v.getTag().toString());
                    document.delete();
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}