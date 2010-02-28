package org.myname.flixeldemo;

import org.flixel.*;

import android.view.KeyEvent;

public class MenuState extends FlxState 
{
	public MenuState()
	{
		super();
		add(new FlxText(10, 10, 250, "Press the center DPad key to continue."));
	}
	
	public void update()
	{
		super.update();
		if (FlxG.keys.justPressed(KeyEvent.KEYCODE_DPAD_CENTER))
			FlxG.switchState(GameState.class);
	}
}
