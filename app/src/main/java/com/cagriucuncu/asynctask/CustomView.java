package com.cagriucuncu.asynctask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {
    private static final String LOG_TAG = CustomView.class.getSimpleName();
    private final String TAG = CustomView.class.getSimpleName();
    public final int RADIUS = 10;
    private Canvas canvas;
    List<Point> points = new ArrayList<>();
    String status = "";
    private boolean longPressDone;

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        Log.d(TAG, "  constructor called");
    }

    // defines paint and canvas
    private Paint drawPaint;

    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLUE);
        drawPaint.setAntiAlias(true);
        //drawPaint.setStrokeWidth(5);
        //drawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        Log.d(TAG, "  onDraw called");
        for (int i = 0; i < getPoints().size(); i++) {
            Point point = getPoints().get(i);
            drawRectangle(point.x, point.y);
            drawText("" + i, point.x, point.y);
        }
        drawLines();
        //drawPath();
        drawText("Distance: " + getDistance(getPoints()) + " Status: " + this.status, 0, 50);
    }

    private boolean longClickActive = false;
    float initialTouchX = 0;
    float initialTouchY = 0;
    private static final int MIN_CLICK_DURATION = 1000;
    private long startClickTime = 0;


    double squareSideHalf = 1 / Math.sqrt(2);
    //Consider pivot x,y as centroid.

    public void drawRectangle(int x, int y) {
        drawPaint.setColor(Color.RED);
        Rect rectangle = new Rect((int) (x - (squareSideHalf * RADIUS)), (int) (y - (squareSideHalf * RADIUS)), (int) (x + (squareSideHalf * RADIUS)), (int) (y + ((squareSideHalf * RADIUS))));
        canvas.drawRect(rectangle, drawPaint);

    }

    public void drawText(String s, int x, int y) {
        drawPaint.setColor(Color.BLACK);
        canvas.drawText(s, x, y, drawPaint);
    }

    public void drawLines() {
        drawPaint.setColor(Color.BLUE);
        canvas.drawLines(pointToFloatArray(), drawPaint);
    }

    public void drawPath() {
        drawPaint.setColor(Color.BLUE);
        canvas.drawPath(getPathOfPoints(), drawPaint);
    }

    private float[] pointToFloatArray() {
        float[] f = new float[points.size() * 4];
        int i, index;
        Point point;
        if(getPoints().size() == 0)
            return f;
        for (i = 0, index = 0; i < getPoints().size() - 1; i++) {
            point = getPoints().get(i);
            f[index++] = point.x;
            f[index++] = point.y;
            point = getPoints().get(i + 1);
            f[index++] = point.x;
            f[index++] = point.y;
        }
        point = getPoints().get(0);
        f[index++] = point.x;
        f[index++] = point.y;
        point = getPoints().get(i);
        f[index++] = point.x;
        f[index++] = point.y;
        return f;
    }

    public  float getDistance(List<Point> points){
        float d = 0;
        int i, index;
        Point point1, point2;
        if(points.size() == 0)
            return d;
        for (i = 0, index = 0; i < points.size() - 1; i++) {
            point1 = points.get(i);
            point2 = points.get(i + 1);
            d += Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));
        }
        point1 = points.get(0);
        point2 = points.get(i);
        d += Math.sqrt(Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2));

        return d;
    }

    private Path getPathOfPoints() {

        Path path = new Path();
        if(getPoints().size() == 0)
            return path;
        Point point = getPoints().get(0);
        path.moveTo(point.x, point.y);
        for (int i = 1; i < getPoints().size() - 1; i++) {
            point = getPoints().get(i);
            path.lineTo(point.x, point.y);
        }
        path.close();

        return path;
    }


    /*
    select three vertices of triangle. Draw 3 lines between them to form a traingle
     */
    public void drawTriangle(int x, int y, int width) {
        drawPaint.setColor(Color.GREEN);
        int halfWidth = width / 2;

        Path path = new Path();
        path.moveTo(x, y - halfWidth); // Top
        path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        path.lineTo(x + halfWidth, y + halfWidth); // Bottom right
        path.lineTo(x, y - halfWidth); // Back to Top
        path.close();
        canvas.drawPath(path, drawPaint);
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points, String status) {
        int X = 50, Y=50; //Offset
        this.points = new ArrayList<Point>();
        this.status = status;
        for (Point point: points) {
            this.points.add(new Point(point.x + X, point.y + Y ));
        }

        this.invalidate();
    }

}
