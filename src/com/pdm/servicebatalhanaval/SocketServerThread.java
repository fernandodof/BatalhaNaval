package com.pdm.servicebatalhanaval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class SocketServerThread extends AsyncTask<Void, Void, Void> {

	// private TextView info;
	// private TextView infoip;
	private String msg;
	private TextView response;
	private String message = "";
	private static ServerSocket serverSocket = null;
	private Context context;
	private static boolean gameStarted = false;
	private GameManager gameManager;
	private static final int PORT = 8090;
	private static Socket socket;

	public SocketServerThread(Context context) {
		this.context = context;
		this.gameManager = new GameManager();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			serverSocket = new ServerSocket(PORT);

			while (true) {
				socket = serverSocket.accept();
				InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

				String msgReceived = bufferedReader.readLine();
				Log.i("Msg received on server", msgReceived);

				String response = "ok from server";

				MainActivity mainActivity = (MainActivity) context;
				mainActivity.updateMsg(response);

				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println(response);
				printWriter.flush();

				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
