package com.pdm.servicebatalhanaval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.pdm.activities.Game;

public class MyClientTask extends AsyncTask<Void, Void, Void> {
	private final String TAG = "FireActionTask";
	private final Context context;
	private String ip = null;
	private int port;
	String response = "";
	public static boolean connected = false;
	private String msg;

	public MyClientTask(Context context, String host, int port, String msg) {
		this.context = context;
		this.msg = msg;
		this.ip = host;
		this.port = port;
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		try {
			Socket socket = new Socket(ip, port);
			//
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(this.msg);
			printWriter.flush();
			Log.i("Degug resquest", this.msg);

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String response = bufferedReader.readLine();
			Log.i("Degug resquest", this.msg);
			Log.i("Debug response", response);
			//
			Game game = (Game) context;
			game.setResultFromServer(response);
			//
			socket.close();
		} catch (UnknownHostException e) {
			Log.e(TAG, "erro ao executar o tiro:", e);
			response = "UnknownHostException: " + e.toString();
		} catch (IOException e) {
			Log.e(TAG, "erro ao executar o tiro:", e);
			response = "IOException: " + e.toString();
		}
		return null;
	}

}
