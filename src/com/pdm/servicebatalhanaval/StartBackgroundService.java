package com.pdm.servicebatalhanaval;

import java.io.IOException;
import java.net.ServerSocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class StartBackgroundService extends Service {

	private static final String TAG = "BackgroundService";

	// private NotificationManager notificationMgr;
	//
	// private void displayNotificationMessage(String message) {
	// PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new
	// Intent(this, MainActivity.class), 0);
	// Notification notification = new NotificationCompat
	// .Builder(getApplicationContext())
	// .setContentTitle("Notification")
	// .setContentText(message)
	// .setSmallIcon(R.drawable.note)
	// .setContentIntent(contentIntent)
	// .build();
	// notification.flags |= Notification.FLAG_AUTO_CANCEL;
	// notificationMgr = (NotificationManager)
	// getSystemService(NOTIFICATION_SERVICE);
	// notificationMgr.notify(R.id., notification);
	// }

	@Override
	public void onCreate() {
		super.onCreate();
		Thread thr = new Thread(null, new ServiceWorker(),
				"StartBackgroundService");
		thr.start();
	}

	private class ServiceWorker implements Runnable {
		public void run() {
			try {
				SocketServerThread.serverSocket = new ServerSocket(8090);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.i("ServiceWorker", "ServiceWorker");
			Log.i(TAG, "ServiceWorker started.");
		}
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
