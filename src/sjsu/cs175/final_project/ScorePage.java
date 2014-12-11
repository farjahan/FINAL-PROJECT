package sjsu.cs175.final_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**A Score Page that displays statistics for a given user.
 * 
 * @authors Swathi Kotturu and Farjahan Hossain
 * @class CS 175 section 1
 * @date Wednesday, December 10, 2014
 */
public class ScorePage extends Activity implements AsyncResponse {
	Scores socket, ascoket, socket2;
	Scores userName;
	TextView showStatistics;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.score_layout);

		showStatistics = (TextView) findViewById(R.id.textView1);
		socket = new Scores(getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE), getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE).edit(), this);
		String user = socket.getUserName();

		String Game = socket.getGameName();
		int score = socket.getCurrentScore();
		String gamesocre = Integer.toString(score);

		SocketConnector.handler = this;
		String gameStatistics = socket.getStatistics(user);

		Log.i("Details of ", "game " + gameStatistics);
		
		socket2 = new Scores(getSharedPreferences("MyPREFERENCES2",
				Context.MODE_PRIVATE), getSharedPreferences("MyPREFERENCES2",
				Context.MODE_PRIVATE).edit(), this);
		String user2 = socket2.getUserName();

		String Game2 = socket2.getGameName();
		int score2 = socket2.getCurrentScore();
		String gamesocre2 = Integer.toString(score2);
		/********* Display user name ********/
		showStatistics.setText("Playername: "+ user + "\n" + 
								"Game: " + Game + " " + gamesocre + "\n" + 
								"Game: " + Game2 + " " +  gamesocre2);

		SocketConnector.handler = this;
		String gameStatistics2 = socket.getStatistics(user2);

		Log.i("Details of ", "game " + gameStatistics2);

		
	}

	/* (non-Javadoc)
	 * @see sjsu.cs175.final_project.AsyncResponse#processFinish(java.lang.String)
	 */
	@Override
	public void processFinish(String output) {
		if (output != null && output.length() > 0) {
			showStatistics.setText(output);
		}
	}
}