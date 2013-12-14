package net.kishonti.annotation;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private MotionEvent mLastUpEvent;
	private AbsoluteLayout mLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLayout = (AbsoluteLayout) findViewById(R.id.AbsoluteLayout1);
		mLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i(TAG, "ev: " + event.getX() + ", " + event.getY());
				if (event.getAction() == MotionEvent.ACTION_UP) {
					mLastUpEvent = MotionEvent.obtain(event);
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onLayoutClick(View v) {
		Log.i(TAG, "clicked: " + mLastUpEvent.getX() + ", " + mLastUpEvent.getY());
		AnnotationView annotation = new AnnotationView(this);
		annotation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i(TAG, "annotation clicked: " + v.toString());
			}
		});
		mLayout.addView(annotation, new AbsoluteLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT,
				(int)mLastUpEvent.getX(), (int)mLastUpEvent.getY())
		);
		mLastUpEvent.recycle();
	}
}
