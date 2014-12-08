package sjsu.cs175.final_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ScorePage extends Activity implements AsyncResponse {
	Scores socket, ascoket, socket2;
	Scores userName;
	TextView showStatistics;

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
		showStatistics.setText(Game + " " + user + ":" + gamesocre + "\n" + Game2 + " " + user2 + ":" + gamesocre2);

		SocketConnector.handler = this;
		String gameStatistics2 = socket.getStatistics(user2);

		Log.i("Details of ", "game " + gameStatistics2);

		
	}

	@Override
	public void processFinish(String output) {
		// TODO Auto-generated method stub
		if (output != null && output.length() > 0) {
			showStatistics.setText(output);
		}
	}
}