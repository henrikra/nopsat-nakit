package fi.metropolia.speedgame;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class NakkiActivity extends FragmentActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nakki);
		
		PeliFragment newFragment = new PeliFragment();

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.add(R.id.fragment_container, newFragment);
		transaction.commit();
	}
	
	//tällä saa focusin tiettyyn nappiin
	/*nappi1.setFocusable(true);
	nappi1.setFocusableInTouchMode(true);
	nappi1.requestFocus();*/


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nakki, menu);
		return true;
	}

}
