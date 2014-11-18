package com.pdm.servicebatalhanaval;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Server extends Service {
	private ServerSocket serverSocket;
	private Socket socket;

	private static Server instance = null;
	private static int port = 8090;

	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	private Server() {
		try {

			this.serverSocket = new ServerSocket(port);
			System.out.println("Server is up and running");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			try {
				this.socket = serverSocket.accept();
				Log.i("Debug", "Passou");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Thread thread = new Thread(new ThreadClass(this.socket));
			thread.start();
		}
	}

	public class ThreadClass implements Runnable {
		private Socket socket;

		public ThreadClass(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			while (true) {
				
			}
		}
	}

	@Override
	public void onDestroy() {
		Log.i("Debug", "Service stopped.");
		super.onDestroy();
	}

	public static void startServer(){
		new Server();
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
