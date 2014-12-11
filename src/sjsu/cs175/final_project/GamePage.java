package sjsu.cs175.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/**Game Selection Page where users can choose which game to play.
 * 
 * @authors Swathi Kotturu and Farjahan Hossain
 * @class CS 175 section 1
 * @date Wednesday, December 10, 2014
 */
public class GamePage extends Activity implements OnClickListener {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		View button1 = this.findViewById(R.id.game1);
		View button2 = this.findViewById(R.id.game2);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	/**
	 * Start either Game1 or Game2 depending on option clicked.
	 * 
	 * @param view that event is coming from
	 */
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		/*
		 * handle the case coming from thing on our Activity with id button
		 */
		case R.id.game1:
			// launch the Game Activity
			i = new Intent(this, TicTacToe.class);
			startActivity(i);
			break;
		case R.id.game2:
			// launch the Game Activity
			i = new Intent(this, Game2_Activity.class);
			startActivity(i);
			break;

		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
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
}