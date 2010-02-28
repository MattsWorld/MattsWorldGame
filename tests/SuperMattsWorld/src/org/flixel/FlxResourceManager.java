package org.flixel;

import java.lang.reflect.Field;

import flash.display.*;
import android.content.*;
import android.graphics.drawable.*;

public class FlxResourceManager 
{
	static public Context context = null;
	static public Class<? extends Object> R = null;
	
	static BitmapData getImage(int resource)
	{
		Drawable drawable = context.getResources().getDrawable(resource);
		BitmapData bitmapData = new BitmapData(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(bitmapData.getCanvas());
		return bitmapData;
	}
	
	static protected int getResource(String resourceClass, String name)
	{
		try 
		{
			Class[] classes = R.getDeclaredClasses();
			for (Class declaredClass : classes)
			{
				if (declaredClass.getSimpleName().equals(resourceClass))
				{
					Field field = declaredClass.getDeclaredField(name);
					return field.getInt(null);
				}
			}
			
		} 
		catch (Exception e) 
		{
			System.out.println(e.toString());
		}
		
		return -1;
	}
	
	static public int getDrawableResource(String name)
	{
		return getResource("drawable", name);
	}
	
	static public int getRawResource(String name)
	{
		return getResource("raw", name);
	}
}
