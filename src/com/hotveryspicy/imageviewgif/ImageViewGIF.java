package com.hotveryspicy.imageviewgif;

import java.io.InputStream;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewGIF extends ImageView{
	InputStream mInputStream;
	Movie mMovie;
	long movieStart;
	RuntimeException mException = null;
	int gifSource=0;
	
	public ImageViewGIF(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GIF, 0, 0);
		gifSource = a.getResourceId(R.styleable.GIF_image, 0);
		if (gifSource == 0) {
			mException = new IllegalArgumentException(a.getPositionDescription() + 
					": The content attribute is required and must refer to a valid image.");
		}
		
		if (mException != null) 
			throw mException;
		
		mInputStream= getContext().getResources().openRawResource(gifSource);
		mMovie = Movie.decodeStream(mInputStream);
		a.recycle();
		
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {

	canvas.drawColor(Color.BLACK);
	super.onDraw(canvas);
	long now=android.os.SystemClock.uptimeMillis();

	if (movieStart == 0) { // first time
		movieStart = now;

	}

	int relTime = (int)((now - movieStart) % mMovie.duration()) ;

	mMovie.setTime(relTime);
	mMovie.draw(canvas,0,0);
	this.invalidate();
	}

}
