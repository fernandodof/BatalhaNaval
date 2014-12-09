package com.pdm.servicebatalhanaval;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.pdm.gameManager.GameManager;

public class StartBackgroundService extends Service {

	private static final String TAG = "StartBackgroundService";
	private GameManager gameManager;
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Background service started");
		gameManager = new GameManager();
		SocketServerTask socketServerThread = new SocketServerTask(getApplicationContext(), gameManager);
		socketServerThread.execute();
	}
	
	@Override
	public void onDestroy() {
		Log.i(TAG, "Service stopped.");
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	

}
