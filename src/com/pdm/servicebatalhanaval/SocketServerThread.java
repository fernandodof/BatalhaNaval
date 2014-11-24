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
	public static ServerSocket serverSocket = null;
	public static Context context;
	public static boolean gameStarted = false;

	private static final int PORT = 8090;
	private static Socket socket;

	public SocketServerThread(Context context) {
		SocketServerThread.context = context;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		try {
			serverSocket = new ServerSocket(PORT);

			while (true) {
				socket = serverSocket.accept();
				InputStreamReader inputStreamReader = new InputStreamReader(
						socket.getInputStream());
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String msgReceived = bufferedReader.readLine();

				Log.i("Msg received on server", msgReceived);

				String response = "ok";
				if (!gameStarted) {
					gameStarted = true;
					if(Game.isTurn()){
						response = "okT";
					}
					
				} else {
					String[] parts = msgReceived.split("@");
					Game game = (Game) context;
					if (parts[0].equalsIgnoreCase("-g")) {
						response = game.atirar(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
						response = "-p@" + response;
					}
					
					if(response.split("@").length>2){
						game.removeControls();
						game.setResponse(":-( Você perdeu");
					}
				}

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

	public static void startServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			Log.i("StartServer", "StartServer");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static void setContext(Context context) {
		SocketServerThread.context = context;
	}

}
