package com.pdm.servicebatalhanaval;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.servicebatalhanaval.R;

public class Game extends ActionBarActivity {
	private static boolean turn = true;
	private Button atirarButton;
	private EditText editX;
	private EditText editY;
	private TextView gameObjects;
	private TextView response;
	private String msg;
	public static GameManager gameManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		this.atirarButton = (Button) findViewById(R.id.atirar);
		this.atirarButton.setOnClickListener(atrirarButtononClickListener);
		this.editX = (EditText) findViewById(R.id.editTextX);
		this.editY = (EditText) findViewById(R.id.editTextY);
		this.gameObjects = (TextView) findViewById(R.id.gameObjects);
		this.response = (TextView) findViewById(R.id.response);
		SocketServerThread.setContext(Game.this);
		Game.gameManager = new GameManager();
		printBattefield();
		setTurn(MainActivity.turn);
		enableDisabeButton();
	}

	OnClickListener atrirarButtononClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			msg = "-g@" + editX.getText() + "@" + editY.getText();
			Log.i("Game Debug", msg);
			setTurn(false);
			enableDisabeButton();
			MyClientTask myClientTask = new MyClientTask(Game.this, msg);
			myClientTask.execute();
		}
	};

	public String atirar(int x, int y) {
		printBattefield();
		setTurn(true);
		enableDisabeButton();
		return Game.gameManager.atirar(x, y);
	}

	public void printBattefield() {
		Game.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				gameObjects.setText("");
				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 9; j++) {
						String part = Character.toString(gameManager.getMar()[i][j]);
						gameObjects.setText(gameObjects.getText() + part + " ");
					}
					gameObjects.setText(gameObjects.getText() + "\n");
				}
			}
		});
	}

	public void setResultFromServer(final String text) {
		Game.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				TextView points = (TextView) findViewById(R.id.points);
				int p = 0;
				String[] parts;
				if (text.startsWith("-p")) {
					parts = text.split("@");

					if (parts[1].matches("-?\\d+")) {
						int responsePoints = Integer.parseInt(parts[1]);

						if (points.getText().equals("")) {
							p = responsePoints;
						} else {
							p = Integer.parseInt((String) points.getText())
									+ responsePoints;
						}
						points.setText(String.valueOf(p));
					}

					if (p > 0) {
						response.setText("\\o/");
						if (parts.length > 2) {
							response.setText("\\o/ Fim do Jogo você Venceu");
							removeControls();
						}
					} else {
						response.setText(":(");
						if (parts[1].matches("-?\\d+")) {
							response.setText(":( ");
						}

					}
				}
			}
		});
	}

	public void setResponse(final String text) {
		Game.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				response.setText(text);
			}
		});
	}

	public void removeControls() {
		Game.this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				editX.setVisibility(View.GONE);
				// this.editX.setWidth(30);
				// this.editY.setEnabled(false);
				editY.setVisibility(View.GONE);
				atirarButton.setText("Restart");
				atirarButton.setEnabled(true);
				atirarButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = getIntent();
						overridePendingTransition(0, 0);
						intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						finish();

						overridePendingTransition(0, 0);
						startActivity(intent);
					}
				});
			}
		});

	}

	public boolean checkEnd() {
		return Game.gameManager.checkEnd();
	}

	public void enableDisabeButton() {
		Game.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if (Game.isTurn()) {
					atirarButton.setEnabled(true);
				} else {
					atirarButton.setEnabled(false);
				}	
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static boolean isTurn() {
		return turn;
	}

	public static void setTurn(boolean turn) {
		Game.turn = turn;
	}

}
