package net.kishonti.annotation;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.PointF;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

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
			private PointF lastPos = new PointF();
			private PointF lastDownPos = new PointF();
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				if (ev.getAction() == MotionEvent.ACTION_UP) {
					mLastUpEvent = MotionEvent.obtain(ev);
					float dx = (ev.getX() - lastDownPos.x);
					float dy = (ev.getY() - lastDownPos.y);
					double d = Math.sqrt(dx*dx+dy*dy);
					if (d > 10) {
						return true;
					}
				} else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
					lastDownPos.set(ev.getX(), ev.getY());
				} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
					for (int i = 0; i < mLayout.getChildCount(); ++i) {
						View c = mLayout.getChildAt(i);
						float dx = ev.getX() - lastPos.x;
						float dy = ev.getY() - lastPos.y;
						AbsoluteLayout.LayoutParams lp = (AbsoluteLayout.LayoutParams) c.getLayoutParams();
						lp.x += (int) dx;
						lp.y += (int) dy;
					}
					mLayout.requestLayout();
				}
				lastPos.set(ev.getX(), ev.getY());
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
				AnnotationView a = (AnnotationView) v;
				TextView t = new TextView(MainActivity.this);
				t.setText("Hello World");
				a.setContentView(t);
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
