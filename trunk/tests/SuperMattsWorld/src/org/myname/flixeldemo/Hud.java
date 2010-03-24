package org.myname.flixeldemo;

import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxText;

import android.graphics.Color;

public class Hud extends FlxSprite
{
	protected FlxText hudText;
	protected FlxSprite background;

	public Hud(){
		  hudText = new FlxText((int)(-FlxG.scroll.x), (int)(-FlxG.scroll.y), (int)(FlxG.width));

		  // TODO Change Font of hudText		  
		  background = new FlxSprite((int)(-FlxG.scroll.x), (int)(-FlxG.scroll.y),
				  					null, false, (int)(FlxG.width), 50, Color.GRAY);
		  background.setAlpha(0.4f);
	}

	public void updateHud(String str){
		// Update HUD
		// attempt to stop text bounce
		hudText.x =  -FlxG.scroll.x/2 * 2; 
		hudText.y =  -FlxG.scroll.y/2 * 2;
		hudText.width = FlxG.width;
		background.x =  -FlxG.scroll.x/2 * 2;
		background.y =  -FlxG.scroll.y/2 * 2;
		background.width = FlxG.width;
		hudText.setText(str);
	}

	public FlxText getHudText() {
		return hudText;
	}

	public void setHudText(FlxText hudText) {
		this.hudText = hudText;
	}

	public FlxSprite getBackground() {
		return background;
	}

	public void setBackground(FlxSprite background) {
		this.background = background;
	}
}