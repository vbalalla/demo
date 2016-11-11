package com.example.vibodha.demo;

import android.content.Context;
import android.content.Intent;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by vibodha on 10/10/16.
 */

public class CouchbaseConnection {
    Context context;
    CouchbaseConnection(Context context){
        this.context = context;
    }

    private Manager manager = null;
    private Database database = null;
    private static final String TAG = "undefined";

    public Database getDatabaseInstance(String DB_NAME) throws CouchbaseLiteException {


        if ((this.database == null) & (this.manager != null)) {
            this.database = manager.getDatabase(DB_NAME);
            Database.ChangeListener changeListener = new Database.ChangeListener() {
                @Override
                public void changed(Database.ChangeEvent event) {
                    Intent i = new Intent(context, MainActivity.class);  //your class
                    context.startActivity(i);
                }
            };
            database.addChangeListener(changeListener);
        }
        return database;
    }

    protected void startSync(Database database, String url) {

        URL syncUrl;
        try {
            syncUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        // server - client
        Replication pullReplication = database.createPullReplication(syncUrl);
        pullReplication.setContinuous(true);

        // client - server
        Replication pushReplication = database.createPushReplication(syncUrl);
        pushReplication.setContinuous(true);

        // replication listeners
        //pullReplication.addChangeListener();

//        pushReplication.addChangeListener(pushReplicationListener);

        // start both replications
        pullReplication.start();
        pushReplication.start();

    }

    public Manager getManagerInstance() throws IOException {
        if (manager == null) {
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
        }
        return manager;
    }

//    public String createDocument(Database database, Category category ) {
//        // Create a new document and add data
//        Document document = database.createDocument();
//        String documentId = document.getId();
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("id", category.getCategoryId());
//        map.put("name", category.getName());
//        map.put("img", category.getImageUrl());
//        try {
//            // Save the properties to the document
//            document.putProperties(map);
//        } catch (CouchbaseLiteException e) {
//            Log.e(TAG, "Error putting", e);
//        }
//        return documentId;
//    }

}
