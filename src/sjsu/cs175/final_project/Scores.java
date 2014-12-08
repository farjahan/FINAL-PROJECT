package sjsu.cs175.final_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class Scores {

	SharedPreferences sharedPref;
	SharedPreferences.Editor editor;
	String currentscore;
	String livescore;
	public static final int port = 7890;
	public static final String ip = "192.168.2.40"; // this ip
	String username;
	String gamename;
	String gamespeed;
	private Context con;

	public Scores(SharedPreferences sp, SharedPreferences.Editor e, Context context) {
		this.sharedPref = sp;
		this.editor = e;
		this.currentscore = "CurrentScore";
		this.livescore = "LiveScore";
		this.username = "Name";
		this.gamename = "GameName";
		this.gamespeed = "Speed";
		this.con = context;
	}

	public int getCurrentScore() {
		return sharedPref.getInt(currentscore, 0);
	}

	public void setCurrentScore(int score) {
		editor.putInt(currentscore, score);
		editor.commit();
		sendGameResult();
	}

	public int getLives() {
		return sharedPref.getInt(livescore, 3);
	}

	public void setLives(int live) {
		editor.putInt(livescore, live);
		editor.commit();
	}

	public String getUserName() {
		return sharedPref.getString(username, "");
	}

	public void setUserName(String name) {
		editor.putString(username, name);
		editor.commit();
		registerName();
		reset();

	}

	public String getGameName() {
		return sharedPref.getString(gamename, "none");
	}

	public void setGameName(String gname) {
		editor.putString(gamename, gname);
		reset();
		editor.commit();
	}

	public int getGameSpeed() {
		return sharedPref.getInt(gamespeed, 1000);
	}

	public void setGameSpeed(int speed) {
		editor.putInt(gamespeed, speed);
		reset();
		editor.commit();
	}

	public void savescores() {
		int current = getCurrentScore();
		setHighestScore(getCurrentScore());
	}

	public void incCurrentScore() {
		int current = getCurrentScore();
		current++;
		setCurrentScore(current);
	}

	public void decLives() {
		int live = getLives();
		live--;
		setLives(live);
	}

	public void reset() {
		setLives(3);
		editor.putInt(currentscore, 0);
		editor.commit();
	}

	public String registerName() {
		Log.i("calling socket data",
				"client calling socket data registerName(): " + getUserName());
		String ans = callSocket("register:" + getUserName());
		Log.i("calling socket data",
				"server returns calling socket data registerName(): " + ans);
		return ans;

	}

	public String sendGameResult() {
		Log.i("calling socket data",
				"client returns calling socket data sendGameResult(): "
						+ getUserName() + " gameName: " + getGameName());
		String ans = callSocket("result:" + getUserName() + "," + getGameName()
				+ "," + getCurrentScore());
		Log.i("calling socket data",
				"server returns calling socket data sendGameResult(): " + ans);
		return ans;
	}

	public String getStatistics(String playername) {
		Log.i("calling socket data",
				"client calling socket data getStatistics(): " + playername);
		String ans = callSocket("statistics:" + playername);
		Log.i("calling socket data",
				"server returns calling socket data getStatistics(): " + ans);
		return ans;
	}

	private String callSocket(String string) {
		// TODO Auto-generated method stub
		SocketConnector connector = new SocketConnector();

		AsyncTask<String, Integer, String> execute = connector.execute(string);
		return "";
	}

	@SuppressWarnings("resource")
	private String callSocket_old(String socketData) {
		Socket socket = null;
		BufferedWriter writer = null;
		BufferedReader reader = null;
		String output = "";

		try {
			socket = new Socket(ip, port);

			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));

			writer.write(socketData);
			writer.newLine();
			writer.flush();

			output = "socket trying";
			while (true) {

				if ((output = reader.readLine()) != null) {

					return output;
				}
			}
			// writer.close();
			// reader.close();
			// socket.close();
		} catch (Exception e) {
			output = "error";
			return e.getMessage();

		}

	}
	
	
	private void setHighestScore(int current) {
		initDB();
		int scoreInDb = getHighestScoreFromDB();

		Log.i("Existing DB Score is :", " " + scoreInDb);
		
		if(current > scoreInDb)
			scoreInDb = current;
			
		
		Log.i("Current score is the highest score :", " " + scoreInDb);
		MyDb hw5 = new MyDb(con);
		SQLiteDatabase db = hw5.getWritableDatabase();

		db.execSQL("update hw5 set HIGH_SCORE =" + scoreInDb
				+ " where name =  " + username);
		db.close();
		

	}
	
	private void initDB() {
		MyDb hw5 = new MyDb(con);
		SQLiteDatabase db = hw5.getWritableDatabase();
		// create table at first time
		String sql = "create table if not exists hw5 (name varchar(50), speed integer, HIGH_SCORE integer, score intger)";
		db.execSQL(sql);

		// db.execSQL("delete from hw5 where name = 'player 1'");
		Cursor rawQuery = db.rawQuery(
				"select * from hw5 where name = 'player 1'", null);

		if (rawQuery.getCount() == 0) {
			db.execSQL("insert into hw5 (name, HIGH_SCORE) values('player 1', 0)");
		}
		rawQuery.close();
		db.close();
	}

	private int getHighestScoreFromDB() {
		initDB();
		MyDb hw5 = new MyDb(con);
		SQLiteDatabase db = hw5.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from hw5 where name = " + username, null);
		cursor.moveToFirst();
		int count = cursor.getColumnCount();
		int scoreInDb = 0;
		if (count > 0) {
			scoreInDb = cursor.getInt(cursor.getColumnIndex("HIGH_SCORE"));
		}

		cursor.close();
		db.close();
		return scoreInDb;
	}

}