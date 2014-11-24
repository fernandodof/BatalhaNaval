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

public class MyClientTask extends AsyncTask<Void, Void, Void> {
	private final Context context;
	private static String ip =null;
	private static int port;
	String response = "";
	public static boolean connected = false;
	private String[] msgArray;
	private String msg;
	public static Socket socket = null;
	
	public MyClientTask(Context context, String msg) {
		this.context = context;
		this.msg = msg;
		this.msgArray = msg.split("@");
		if (msgArray[0].equalsIgnoreCase("-c")) {
			MyClientTask.ip = msgArray[1];
			MyClientTask.port = Integer.parseInt(msgArray[2]);
		}
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		try {
			MyClientTask.socket = new Socket(ip, port);
			
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(this.msg);
			printWriter.flush();
            Log.i("Degug resquest", this.msg);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(MyClientTask.socket.getInputStream()));
            String response = bufferedReader.readLine();
            Log.i("Debug response", response);

            if(!context.getClass().getSimpleName().equalsIgnoreCase("Game")){
            	MainActivity mainActivity = (MainActivity) context;
				mainActivity.updateMsg(response);
            }else{
            	Game game = (Game) context;
            	game.setResultFromServer(response);
            }
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = "UnknownHostException: " + e.toString();
			MainActivity mainActivity = (MainActivity) context;
			mainActivity.updateMsg(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = "IOException: " + e.toString();
			MainActivity mainActivity = (MainActivity) context;
			mainActivity.updateMsg(response);
		}
		return null;
	}
	
}
