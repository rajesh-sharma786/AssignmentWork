package com.example.rajesh.jakewhartonrepositorylist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class JakeActivity extends AppCompatActivity implements DownloadResultReceiver.Receiver {

    @BindView(R.id.main_root)
    ViewGroup root;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    AlertDialog dialog;

    String TAG="BookActivity";


    DownloadResultReceiver mReceiver;
    Realm realm;

    ArrayList<JakeWhartonDataEntity> _dataEntities=new ArrayList<>();

    JakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Realm.init(getApplicationContext());
        RealmManager.initializeRealmConfig();
        super.onCreate(savedInstanceState);
        //get realm instance
        realm = RealmManager.getRealm();

        /* Starting Download Service */
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        /* Send optional extras to Download IntentService */
        intent.putExtra("url", Constants.BASE_URL+"?page=1&per_page=15");
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);


        //get presenter instance

        setContentView(R.layout.activity_jake);

        ButterKnife.bind(this);

        //set toolbar
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        //setup recycler
        recycler.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);


        // changes will be reflected automatically
        adapter=new JakeAdapter(realm.where(JakeWhartonDataEntity.class).findAllAsync(),JakeActivity.this);
        recycler.setAdapter(adapter);


    }

    @Override
    protected void onDestroy() {
        if(dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public Object getSystemService(String name) {
        return super.getSystemService(name);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData)
    {
        switch (resultCode)
        {
            case DownloadService.STATUS_RUNNING:
                Log.e(TAG,"dialog moving");
                break;

            case DownloadService.STATUS_FINISHED:

                _dataEntities=new ArrayList<>();

                _dataEntities=resultData.getParcelableArrayList("result");


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for(int i=0;i<_dataEntities.size();i++)
                        {
                            realm.insertOrUpdate(_dataEntities.get(i));
                        }

                    }
                });

                adapter.notifyDataSetChanged();

                Log.e(TAG,_dataEntities.size()+"");

                //check number of entries

                break;

            case DownloadService.STATUS_ERROR:

                break;
        }
    }


    public void startIntentMethod(int page,int per_page)
    {
        String _urlPart="?page="+page+"&per_page="+per_page;
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

       /* Send optional extras to Download IntentService */
        intent.putExtra("url", Constants.BASE_URL+_urlPart);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);
    }
}
