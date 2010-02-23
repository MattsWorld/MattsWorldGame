package com.example.helloandroid;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;


public class HelloAndroid extends Activity {
	DemoView demoview;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		demoview = new DemoView(this);
		setContentView(demoview);
	}
	

	private class DemoView extends View {
		
		
		public DemoView(Context context) {
			super(context);
		}
		

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
		 
			int width = canvas.getWidth(); 
			int height = canvas.getHeight();
			
			//canvas.drawBitmap(bitmap, left, top, paint)
			//Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			
			Paint p = new Paint();
			
			p.setColor(Color.BLUE);
			canvas.drawRect(0, 0, width, height, p);
			
			p.setColor(Color.GREEN);
			canvas.drawRect(0, height/2, width, height, p);
			
			p.setColor(Color.RED);
			canvas.drawRect(0, (int)(height*3.0/4), width, height, p);
			
			p.setColor(Color.BLACK);
			canvas.drawRect(40, (int)(height*3.0/4)-40, 80, (int)(height*3.0/4), p);
			
			canvas.drawText("Matt!", 90, (int)(height*3.0/4)-40, p);
			canvas.drawText("Sky!", 50, 20, p);
			canvas.drawText("Middle Background!", 120, (height/2) + 15, p);
			canvas.drawText("Beach!", 50, (int)(height*3.0/4) + 15, p);
		}
		
		
	}
	
	
}