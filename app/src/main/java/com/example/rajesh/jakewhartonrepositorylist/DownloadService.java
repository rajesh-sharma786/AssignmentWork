package com.example.rajesh.jakewhartonrepositorylist;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class DownloadService extends IntentService {
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";

    public ArrayList<JakeWhartonDataEntity> _dataArray=new ArrayList<>();

    public DownloadService() {
        super(DownloadService.class.getName());
        _dataArray=new ArrayList<>();
    }






    @Override
    protected void onHandleIntent(Intent intent) {

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("url");

        Bundle bundle = new Bundle();

        if (!TextUtils.isEmpty(url)) {
            /* Update UI: Download Service is Running */
            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                RequestFuture<JSONArray> future = RequestFuture.newFuture();
                JsonArrayRequest request = new JsonArrayRequest(url, future, future);
                RequestQueue requestQueue = Volley.newRequestQueue(getSystemService(this));
                requestQueue.add(request);

                try {
                    JSONArray response = future.get(); // this will block
                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject _obj=response.getJSONObject(i);

                        long id=_obj.getLong("id");
                        String name=_obj.optString("name","");
                        String htmlurl=_obj.optString("html_url","");
                        String description=_obj.optString("description","");
                        String language=_obj.optString("language","");
                        int watchers_count=_obj.optInt("watchers_count",0);
                        int stargazers_count=_obj.optInt("stargazers_count",0);


                        JakeWhartonDataEntity _entity=new JakeWhartonDataEntity(id,name,htmlurl,description,language,watchers_count,stargazers_count);
                        _dataArray.add(_entity);

                    }

                    if (null != _dataArray && _dataArray.size() > 0) {
                        bundle.putParcelableArrayList("result",_dataArray);
                        receiver.send(STATUS_FINISHED, bundle);
                    }


                } catch (InterruptedException e) {
                    // exception handling
                } catch (ExecutionException e) {
                    // exception handling
                }

            } catch (Exception e) {

                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                receiver.send(STATUS_ERROR, bundle);
            }
        }
        Log.d(TAG, "Service Stopping!");
        this.stopSelf();

    }

    private Context getSystemService(DownloadService downloadService) {
        return DownloadService.this;
    }



}
