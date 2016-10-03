package com.snapchat.team2.snapchat.customWidget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by bm on 3/10/2016.
 */

public class CamerEditText extends EditText {

    private Paint paint;
    public CamerEditText(Context context) {
        super(context);
        init();
    }

    public CamerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLUE);




    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
