package com.pdm.servicebatalhanaval;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.servicebatalhanaval.R;

public class MainActivity extends Activity {

	private static TextView textResponse;
	private EditText editTextAddress;
	private EditText editTextPort;
	Button buttonConnect, buttonClear;
	private boolean turn = true;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editTextAddress = (EditText) findViewById(R.id.address);
		editTextPort = (EditText) findViewById(R.id.port);
		buttonConnect = (Button) findViewById(R.id.connect);
		textResponse = (TextView) findViewById(R.id.response);
		buttonConnect.setOnClickListener(buttonConnectOnClickListener);
		this.context = MainActivity.this;

//		startService(new Intent(MainActivity.this, StartBackgroundService.class));
		
		SocketServerThread serverThread = new SocketServerThread(MainActivity.this);
		serverThread.execute();
	
		
		Log.i("IP LOCAL: ", getIpAddress());
		textResponse.setText(getIpAddress());
	}

	OnClickListener buttonConnectOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Log.i("Debug button",
					"connect button clicked " + editTextAddress.getText() + ":"
							+ editTextPort.getText());
			String msg = null;
			if (!MyClientTask.connected) {
				msg = "-c@" + editTextAddress.getText() + "@"
						+ editTextPort.getText();
			}

			MyClientTask myClientTask = new MyClientTask(MainActivity.this, msg);
			myClientTask.execute();
		}
	};

	public void updateMsg(final String msg) {
		MainActivity.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				textResponse.setText(msg);
				if (msg.equalsIgnoreCase("ok")) {
					Intent gameIntent = new Intent(MainActivity.this, Game.class);
					startActivity(gameIntent);
				}
			}

		});

	}

	private String getIpAddress() {
		String ip = "";
		try {
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements()) {
					InetAddress inetAddress = enumInetAddress.nextElement();

					if (inetAddress.isSiteLocalAddress()) {
						ip += "Endereco local: " + inetAddress.getHostAddress()
								+ "\n";
					}

				}

			}

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Ocorreu um erro! " + e.toString();
		}

		return ip;
	}
}