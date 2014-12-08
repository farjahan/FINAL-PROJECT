package sjsu.cs175.final_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ScorePage extends Activity implements AsyncResponse {
	Scores socket, ascoket;
	Scores userName;
	TextView showStatistics;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.score_layout);

		showStatistics = (TextView) findViewById(R.id.textView1);
		socket = new Scores(getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE), getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE).edit());
		String user = socket.getUserName();

		String Game = socket.getGameName();
		int score = socket.getCurrentScore();
		String gamesocre = Integer.toString(score);
		/********* Display user name ********/
		showStatistics.setText(Game + " " + user + ":" + gamesocre);

		SocketConnector.handler = this;
		String gameStatistics = socket.getStatistics(user);

		Log.i("Details of ", "game " + gameStatistics);
	}

	@Override
	public void processFinish(String output) {
		// TODO Auto-generated method stub
		if (output != null && output.length() > 0) {
			showStatistics.setText(output);
		}
	}
}