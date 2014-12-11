package sjsu.cs175.final_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

/**Game over class which ends the game and displays game result.
 * 
 * @authors Swathi Kotturu and Farjahan Hossain
 * @class CS 175 section 1
 * @date Wednesday, December 10, 2014
 */
public class GameOver extends Activity {
	private int waiting_time;
	private TextView textScore;
	Scores score;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		textScore = (TextView) findViewById(R.id.textView2);
		waiting_time = getResources().getInteger(R.integer.game_over_waiting);

		score = new Scores(getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE), getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE).edit(), this);

		int gameScore = score.getCurrentScore();
		textScore.setText(getString(R.string.text_score) + " " + gameScore);

		gameEnd();
	}

	/**
	 * Waits for 10 second to return start page.
	 */
	private void gameEnd() {
		// init timer
		CountDownTimer timer = new CountDownTimer(waiting_time, 1000) {

			@Override
			public void onFinish() {
				//
				finish();
			}

			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub

			}

		};
		timer.start();
	}

}