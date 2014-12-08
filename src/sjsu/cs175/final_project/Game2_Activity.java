package sjsu.cs175.final_project;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Game2_Activity extends Activity implements OnClickListener {
	private int lives;
	private int score;
	private int highScore;
	private String Title;
	private int speed;
	private ImageButton button1;
	private ImageButton button2;
	private ImageButton button3;
	private ImageButton button4;

	private SQLiteDatabase db;
	private TextView textLives;
	private TextView textScore;
	private TextView textHigh;
	private TextView textOrientation;
	private boolean hitting;
	Scores savedscores;
	private boolean clickDone;

	private Handler handler = new Handler();

	/**
	 * To start play a game
	 */
	private Runnable gamePlay = new Runnable() {

		@Override
		public void run() {
			if (lives > 0) {
				// reset
				clickDone = false;
				hitting = false;
				Title = getRandomOrientation();
				showMessage();
				// wait for time out
				handler.postDelayed(gameTimeout, speed);
			} else {
				gameOver();
			}
		}

	};

	/**
	 * Handle the game time out
	 */
	private Runnable gameTimeout = new Runnable() {

		@Override
		public void run() {
			if (!clickDone) {
				// the user doesn't click the button
				clickDone = true; // assume player click since time out
				lives--;
				showMessage();
				gameStart();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kids_activity);
		init();
		gameStart();
	}

	/**
	 * Initialize
	 */
	private void init() {
		textScore = (TextView) findViewById(R.id.game1TextView1);
		textLives = (TextView) findViewById(R.id.game1TextView3);
		textOrientation = (TextView) findViewById(R.id.game3TextView3);
		button1 = (ImageButton) findViewById(R.id.imageButton1);
		button2 = (ImageButton) findViewById(R.id.imageButton2);
		button3 = (ImageButton) findViewById(R.id.imageButton3);
		button4 = (ImageButton) findViewById(R.id.imageButton4);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);

		lives = getResources().getInteger(R.integer.max_lives);
		score = 0;
		highScore = 0;
		// orientation=0;
		speed = getResources().getInteger(R.integer.default_game_speed);
		hitting = false;
		// load highScore, speed from database
		savedscores = new Scores(getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE), getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE).edit());

		speed = savedscores.getGameSpeed();
		savedscores.setGameName("Animal");

	}

	/**
	 * Start the game
	 */
	private void gameStart() {
		// wait for 0.5 second to start a new game
		handler.postDelayed(gamePlay, 500);
	}

	/**
	 * The game over save data load game_over activity
	 */
	private void gameOver() {
		// save highScore, score to database

		savedscores.setCurrentScore(score);

		Intent i = new Intent(this, GameOver.class);
		startActivity(i);
		finish();
	}

	private String getRandomOrientation() {
		final String[] arr = { "Cat", "Dog", "Elephant", "Squirrel" };
		Random random = new Random();
		final int select = random.nextInt(arr.length);
		String result = arr[select];
		return result;

	}

	/**
	 * Show the message and orientation on screen
	 */
	private void showMessage() {
		textScore.setText(getString(R.string.text_score) + " " + score);
		textLives.setText(getString(R.string.text_lives) + " " + lives);
		if (hitting) {
			// don't show textOrientation
			textOrientation.setText("");
		} else {
			if (Title.equals("Cat")) {
				textOrientation.setText(R.string.Cat);
			} else if (Title.equals("Dog")) {
				textOrientation.setText(R.string.Dog);
			} else if (Title.equals("Elephant")) {
				textOrientation.setText(R.string.Elephant);
			} else if (Title.equals("Squirrel")) {
				textOrientation.setText(R.string.Squirrel);
			}

		}

		if (!clickDone)
			textOrientation.setTextColor(Color.BLACK); // new game
		else
			textOrientation.setTextColor(Color.RED); // miss, warm the
														// player
	}

	public void onClick(View v) {
		switch (v.getId()) {
		/*
		 * handle the case coming from thing on our Activity with id button
		 */
		case R.id.imageButton1:
			// only allow once
			if (!clickDone) {
				clickDone = true;
				// stop game time out timer
				handler.removeCallbacks(gameTimeout);
				// check if the player hit the right orientation
				int current = getResources().getConfiguration().orientation;
				// Log.d("hw2", ""+current+" or:"+orientation );
				if ((current == Configuration.ORIENTATION_PORTRAIT || current == Configuration.ORIENTATION_LANDSCAPE)
						&& Title.equals("Dog")) {
					hitting = true;
				}

				if (hitting) {
					score++;
					if (score > highScore) {
						highScore = score;
					}
				} else {
					lives--;
				}
				showMessage();
				// restart the game
				gameStart();
			}
			break;

		case R.id.imageButton2:
			// only allow once
			if (!clickDone) {
				clickDone = true;
				// stop game time out timer
				handler.removeCallbacks(gameTimeout);
				// check if the player hit the right orientation
				int current = getResources().getConfiguration().orientation;
				// Log.d("hw2", ""+current+" or:"+orientation );
				if ((current == Configuration.ORIENTATION_PORTRAIT || current == Configuration.ORIENTATION_LANDSCAPE)
						&& Title.equals("Cat")) {
					hitting = true;
				}

				if (hitting) {
					score++;
					if (score > highScore) {
						highScore = score;
					}
				} else {
					lives--;
				}
				showMessage();
				// restart the game
				gameStart();
			}
			break;
		case R.id.imageButton3:
			// only allow once
			if (!clickDone) {
				clickDone = true;
				// stop game time out timer
				handler.removeCallbacks(gameTimeout);
				// check if the player hit the right orientation
				int current = getResources().getConfiguration().orientation;
				// Log.d("hw2", ""+current+" or:"+orientation );
				if ((current == Configuration.ORIENTATION_PORTRAIT || current == Configuration.ORIENTATION_LANDSCAPE)
						&& Title.equals("Elephant")) {
					hitting = true;
				}

				if (hitting) {
					score++;
					if (score > highScore) {
						highScore = score;
					}
				} else {
					lives--;
				}
				showMessage();
				// restart the game
				gameStart();
			}
			break;
		case R.id.imageButton4:
			// only allow once
			if (!clickDone) {
				clickDone = true;
				// stop game time out timer
				handler.removeCallbacks(gameTimeout);
				// check if the player hit the right orientation
				int current = getResources().getConfiguration().orientation;
				// Log.d("hw2", ""+current+" or:"+orientation );
				if ((current == Configuration.ORIENTATION_PORTRAIT || current == Configuration.ORIENTATION_LANDSCAPE)
						&& Title.equals("Squirrel")) {
					hitting = true;
				}

				if (hitting) {
					score++;
					if (score > highScore) {
						highScore = score;
					}
				} else {
					lives--;
				}
				showMessage();
				// restart the game
				gameStart();
			}
			break;

		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Log the change of orientation
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Log.i("user", "ORIENTATION_PORTRAIT");
		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.i("user", "ORIENTATION_LANDSCAPE");
		} else {
			Log.i("user", "ORIENTATION_OTHERS");
		}

	}

}
