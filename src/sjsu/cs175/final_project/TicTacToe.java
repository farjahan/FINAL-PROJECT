package sjsu.cs175.final_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TicTacToe extends Activity {
	private Game game1;
	Scores savedscores;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		game1 = new Game(this);
		setContentView(game1);
		// load highScore, speed from database
		savedscores = new Scores(getSharedPreferences("MyPREFERENCES2",
				Context.MODE_PRIVATE), getSharedPreferences("MyPREFERENCES2",
				Context.MODE_PRIVATE).edit(), this);

		int speed = savedscores.getGameSpeed();
		savedscores.setGameName("TicTacToe");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
