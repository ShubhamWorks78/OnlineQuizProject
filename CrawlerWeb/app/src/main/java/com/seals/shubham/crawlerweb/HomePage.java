package com.seals.shubham.crawlerweb;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.Image;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements View.OnClickListener {
    Context mContext = this;
    private String LOG_TAG = "Activity";
    ArrayList<String> LinksList;
    ArrayAdapter<String> Ladapter;
    ArrayList<String> ImagesList;
    ArrayAdapter<String> Iadapter;

    ListView links,images;
    Button goCrawl;
    EditText url;

    Messenger mService = null;
    boolean mIsBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Log.i(LOG_TAG,"OnCreate");
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG,"On Resume");
        bindService(new Intent(this,Search.class),mConnection,Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    private void initialize(){
        Log.i(LOG_TAG,"On Initialize");
        LinksList = new ArrayList<String>();
        Ladapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,LinksList);
        links = (ListView)findViewById(R.id.ivHyperLinks);
        links.setAdapter(Ladapter);

        ImagesList = new ArrayList<String>();
        Iadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ImagesList);
        images = (ListView)findViewById(R.id.ivImageLinks);
        images.setAdapter(Iadapter);

        goCrawl = (Button)findViewById(R.id.bCrawl);
        goCrawl.setOnClickListener(this);

        url = (EditText)findViewById(R.id.url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.images_links,menu);
        return true;
    }

    class IncomingHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Search.MSG_DATA:
                    Log.i(LOG_TAG,"Message Received");
                    LinksList.clear();
                    LinksList.addAll(msg.getData().getStringArrayList("links"));
                    Ladapter.notifyDataSetChanged();
                    ImagesList.clear();
                    ImagesList.addAll(msg.getData().getStringArrayList("images"));
                    Iadapter.notifyDataSetChanged();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(mContext,"Connected",Toast.LENGTH_SHORT).show();
            mService = new Messenger(binder);
            try{
                Message msg = Message.obtain(null,Search.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);
                msg = Message.obtain(null,Search.MSG_SET_VALUE,this.hashCode(),0);
                mService.send(msg);
                Log.i(LOG_TAG,"Message Sent");
            }
            catch (RemoteException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            Toast.makeText(mContext,"Disconnected",Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    public void onClick(View v) {
        try{
            Message msg = Message.obtain(null,Search.MSG_SET_URL,this.hashCode(),0);
            Bundle b = new Bundle();
            b.putString("url",url.getText().toString());
            msg.setData(b);
            mService.send(msg);
            msg = Message.obtain(null,Search.MSG_SET_VALUE,this.hashCode(),0);
            mService.send(msg);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        Log.i(LOG_TAG,"Message Sent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        doUnbindService();
        unbindService(mConnection);
        mIsBound = false;
    }
    void doUnbindService(){
        if(mIsBound){
            if(mService!=null){
                try{
                    Message msg = Message.obtain(null,Search.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                }
                catch (RemoteException e){

                }
            }
        }
    }
}
