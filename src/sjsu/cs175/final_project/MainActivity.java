package sjsu.cs175.final_project;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

/**Main Class that contains the tab sets of the Setup Page, Game Page, and Statistics Page.
 * 
 * @authors Swathi Kotturu and Farjahan Hossain
 * @class CS 175 section 1
 * @date Wednesday, December 10, 2014
 */
public class MainActivity extends TabActivity {
	Scores userName;

	TabHost tabHost;

	/* (non-Javadoc)
	 * @see android.app.ActivityGroup#onCreate(android.os.Bundle)
	 */
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Get host object from super class
		final TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, Account_SetUp_Page.class);
		spec = tabHost
				.newTabSpec("Account SetUp")
				.setIndicator("Account SetUp",
						getResources().getDrawable(R.drawable.account))
				.setContent(intent);
		tabHost.addTab(spec);

		// Create the intent associated with the activity
		intent = new Intent().setClass(this, GamePage.class);
		intent.putExtra("tab", "Game");
		// Create a new TabSpec with a name, an icon and intent
		spec = tabHost
				.newTabSpec("Game")
				.setIndicator("Game",
						getResources().getDrawable(R.drawable.game))
				.setContent(intent);
		// Add it to the tab
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ScorePage.class);
		intent.putExtra("tab", "Statistics");
		spec = tabHost
				.newTabSpec("Statistics")
				.setIndicator("Statistics",
						getResources().getDrawable(R.drawable.stat))
				.setContent(intent);
		tabHost.addTab(spec);

		// Default tab is the first tab.
		userName = new Scores(getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE), getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE).edit(), this);
		String user = userName.getUserName();
		Log.i("user name", "is: " + user);
		if (user.isEmpty()) {

			tabHost.setCurrentTab(3);

		} else {
			tabHost.setCurrentTab(1);
			displayWelcomeMessege();
		}

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				setTabColor(tabHost);
			}

		});
		setTabColor(tabHost);
	}


	/**Sets the Tab color.
	 * @param tabHost to set the color to
	 */
	public void setTabColor(TabHost tabHost) {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildAt(i)
					.setBackgroundColor(Color.WHITE); // inactive tabs
		}
		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
				.setBackgroundColor(Color.RED); // selected, active tabs
	}

	/**
	 * Displays the Welcome Message when the game first started.
	 */
	private void displayWelcomeMessege() {
		Context context = getApplicationContext();
		userName = new Scores(getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE), getSharedPreferences("MyPREFERENCES",
				Context.MODE_PRIVATE).edit(), this);
		String user = userName.getUserName();
		CharSequence text = user + " Welcome to Game!";
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

}
