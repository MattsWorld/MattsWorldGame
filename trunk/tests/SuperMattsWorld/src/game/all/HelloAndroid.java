package game.all;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;


public class HelloAndroid extends Activity
{
	private GameView demoview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		demoview = new GameView(this);
		super.setContentView(demoview);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			demoview.x += 10;
		break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			demoview.x -= 10;
		break;
		case KeyEvent.KEYCODE_DPAD_UP:
			demoview.y -= 10;
		break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			demoview.y += 10;
		break;		
		}
		demoview.invalidate();
		return super.onKeyDown(keyCode, event);
	}
}