package sjsu.cs175.final_project.test;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import sjsu.cs175.final_project.Account_SetUp_Page;
import sjsu.cs175.final_project.R;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<Account_SetUp_Page> {

	public MainActivityTest() {
		super("sjsu.cs175.final_project", 
			     Account_SetUp_Page.class); 
	}

	protected void setUp() throws Exception {
		super.setUp(); 
			}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testHelloTextVisibility() { 
		   Button startbutton = (Button) getActivity().findViewById(R.id.button1); 
		   assertEquals("Save", startbutton.getText()+ ""); 
		 }
	

}
