package com.demo.appsforbb.randomsquares;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jitendra on 6/5/15.
 */
public class SquaresView extends View {
    private int height, width;
    private float cordX, cordY;
    List<Rectangle> listRect = new ArrayList<Rectangle>();
    private Paint paint;
    private Canvas canvas;
    private Handler hanlder = new Handler();

    public SquaresView(Context context) {
        super(context);
    }

    public SquaresView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaresView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        super.onDraw(canvas);
        height = getHeight();
        width = getWidth();
        drawRectangles(canvas);

    }

    private void drawRectangles(Canvas canvas) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        for (int i = 0; i < listRect.size(); i++) {
            Rectangle rectangle = listRect.get(i);
            float left = rectangle.getLeft();
            float top = rectangle.getTop();
            float right = rectangle.getRight();
            float bottom = rectangle.getBottom();
            int alpha = rectangle.getAlpha();
            paint.setAlpha(alpha);
            canvas.drawRect(left, top, right, bottom, paint);
            left = left - 2;
            top = top - 2;
            right = right + 2;
            bottom = bottom + 2;
            alpha = alpha - 5;
            if (left > 0 && right < width && top > 0 && bottom < height) {
                rectangle.setLeft(left);
                rectangle.setRight(right);
                rectangle.setTop(top);
                rectangle.setBottom(bottom);
                rectangle.setAlpha(alpha);
            } else {
                listRect.remove(rectangle);
                i--;
            }

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                listRect.clear();
                cordX = event.getX();
                cordY = event.getY();
                listRect.add(new Rectangle(cordX - 10, cordY - 10, cordX + 10, cordY + 10, 100));
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                cordX = event.getX();
                cordY = event.getY();
                listRect.add(new Rectangle(cordX - 10, cordY - 10, cordX + 10, cordY + 10, 200));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                runHandler();
                break;
        }
        return true;
    }

    private void runHandler() {
       /* hanlder.post(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        invalidate();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
        hanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("Handler run");
                if (listRect.size() >= 0) {
                    invalidate();
                    hanlder.postDelayed(this, 10);
                }
            }
        }, 10);
    }

    private class Rectangle {
        private float left, top, right, bottom;
        int alpha;


        private Rectangle(float left, float top, float right, float bottom, int alpha) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.alpha = alpha;
        }

        public void setLeft(float left) {
            this.left = left;
        }

        public void setTop(float top) {
            this.top = top;
        }

        public void setRight(float right) {
            this.right = right;
        }

        public void setBottom(float bottom) {
            this.bottom = bottom;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public float getLeft() {
            return left;
        }

        public float getTop() {
            return top;
        }

        public float getRight() {
            return right;
        }

        public float getBottom() {
            return bottom;
        }

        public int getAlpha() {
            return alpha;
        }

    }
}
