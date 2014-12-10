package sjsu.cs175.final_project.test;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import sjsu.cs175.final_project.Account_SetUp_Page;
import sjsu.cs175.final_project.R;

public class AccountSetupActivityTest extends
		ActivityInstrumentationTestCase2<Account_SetUp_Page> {

	public AccountSetupActivityTest() {
		super("sjsu.cs175.final_project", 
			     Account_SetUp_Page.class); 
	}

	protected void setUp() throws Exception {
		super.setUp(); 
			}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSaveButtonTextVisibility() { 
		   Button savebutton = (Button) getActivity().findViewById(R.id.button1); 
		   assertEquals("Save", savebutton.getText()+ ""); 
		 }
	
	public void testUsernameTextVisibility() { 
		   TextView username = (TextView) getActivity().findViewById(R.id.textView1); 
		   assertEquals("Enter Your Name", username.getText()+ ""); 
		 }
	
	public void testGameSpeedTextVisibility() { 
		   TextView seekbar = (TextView) getActivity().findViewById(R.id.textView2);
		   String contents = seekbar.getText()+ "";
		   assertEquals("Choose Your Game Speed", contents.substring(0, 22)); 
		 }

}
