package sjsu.cs175.final_project.test;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import sjsu.cs175.final_project.GamePage;
import sjsu.cs175.final_project.R;

public class GamePageActivityTest extends
		ActivityInstrumentationTestCase2<GamePage> {

	public GamePageActivityTest() {
		super("sjsu.cs175.final_project", 
			     GamePage.class); 
	}

	protected void setUp() throws Exception {
		super.setUp(); 
			}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testGame1ButtonTextVisibility() { 
		   Button game1button = (Button) getActivity().findViewById(R.id.game1); 
		   assertEquals("TicTacToe", game1button.getText()+ ""); 
		 }
	
	public void testGame2TextVisibility() { 
		   Button game2button = (Button) getActivity().findViewById(R.id.game2); 
		   assertEquals("Animal", game2button.getText()+ ""); 
		 }
	
}
