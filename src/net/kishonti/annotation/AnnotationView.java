package net.kishonti.annotation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class AnnotationView extends FrameLayout {

	protected static final String TAG = "AnnotationView";
	private View mContent;
	private Rect mContentRect;

	public AnnotationView(Context context) {
		super(context);
		setup(context, R.layout.annotation_default_content);
	}

	public AnnotationView(Context context, int contentLayout) {
		super(context);
		setup(context, contentLayout);
	}

	public AnnotationView(Context context, AttributeSet attrs) {
		super(context, attrs);
	    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AnnotationView, 0, 0);
	    int contentLayout = ta.getResourceId(R.styleable.AnnotationView_contentLayout, R.layout.annotation_default_content);
	    ta.recycle();
	    setup(context, contentLayout);
	}

	public AnnotationView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AnnotationView, 0, 0);
	    int contentLayout = ta.getResourceId(R.styleable.AnnotationView_contentLayout, R.layout.annotation_default_content);
	    ta.recycle();
	    setup(context, contentLayout);
	}

	private void setup(Context c, int contentLayout) {
		LayoutInflater inflater = LayoutInflater.from(c);
		this.setBackgroundResource(R.drawable.balloon_overlay_bg_selector);
		this.setPadding(9, 8, 9, 30);
		mContent = inflater.inflate(contentLayout, this);
		this.setOnClickListener(null);
		mContentRect = new Rect();
		Animation anim = AnimationUtils.loadAnimation(c, R.anim.annotation_show);
		this.setAnimation(anim);
	}

	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mContentRect.set(
			getPaddingLeft(),
			getPaddingTop(),
			getMeasuredWidth() - getPaddingRight(),
			getMeasuredHeight() - getPaddingBottom()
		);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mContentRect.contains((int) ev.getX(), (int)ev.getY())) {
			super.onTouchEvent(ev);
			return true;
		} else {
			return false;
		}
	}

	public View getContentView() {
		return mContent;
	}

	public void setContentView(View v) {
		removeAllViews();
		addView(v);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		this.offsetLeftAndRight((left-right)/2);
		this.offsetTopAndBottom((top-bottom));
	}

}
