package org.myname.flixeldemo;

import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.myname.flixeldemo.parsing.Level;
import org.myname.flixeldemo.parsing.LevelParser;

import android.view.KeyEvent;

public class MenuState extends FlxState 
{
	public MenuState()
	{
		int titlex = 60;
		int titley = 100;
		FlxText text = new FlxText(titlex,  titley, 250, "Super Matt's World!");
		text.setSize(22);
		super.add(text);
		text = new FlxText(titlex + 10, titley + 100, 250, "Press the center DPad key to continue.");
		text.flicker(1F);
		super.add(text);
	}
	
	public void update()
	{
		super.update();
		//Level.nextLevel = R.raw.lvl_test2;
		if (FlxG.keys.justPressed(KeyEvent.KEYCODE_DPAD_CENTER) || FlxG.keys.justPressed(KeyEvent.KEYCODE_ENTER))
			Level.switchLevel("lvl_test2","");  //FlxG.switchState(Level.class);
	}
}
