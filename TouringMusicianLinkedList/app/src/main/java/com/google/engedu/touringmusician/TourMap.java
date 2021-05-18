/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Iterator;

public class TourMap extends View {

    private Bitmap mapImage;
    private CircularLinkedList list = new CircularLinkedList();
    private String insertMode = "Add";
    private Paint mPointPaint;
    private Paint mLinePaint;

    public TourMap(Context context) {
        super(context);
        mapImage = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.map);
        mPointPaint = new Paint();
        mLinePaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mapImage, 0, 0, null);

        mPointPaint.setColor(Color.RED);
        mLinePaint.setColor(Color.BLACK);
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Iterator<Point> listIterator = list.iterator();
        if(listIterator.hasNext()) {
            Point headPoint = listIterator.next();
            int headX = headPoint.x;
            int headY = headPoint.y;
            int prevX = headX;
            int prevY = headY;
            int counter = 0;

            while (listIterator.hasNext()) {
                Point currentPoint = listIterator.next();
                int curX = currentPoint.x;
                int curY = currentPoint.y;
                //draw line
                canvas.drawLine(prevX, prevY, curX, curY, mLinePaint);
                prevX = curX;
                prevY = curY;
                counter++;
            }

            if(counter>1)
                canvas.drawLine(headX, headY, prevX, prevY, mLinePaint);
        }
        for (Point p : list) {
            /**
             **
             **  YOUR CODE GOES HERE
             **
             **/

            canvas.drawCircle(p.x, p.y, 20, mPointPaint);
        }
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Point p = new Point((int) event.getX(), (int)event.getY());
                if (insertMode.equals("Closest")) {
                    list.insertNearest(p);
                } else if (insertMode.equals("Smallest")) {
                    list.insertSmallest(p);
                } else {
                    list.insertBeginning(p);
                }
                TextView message = (TextView) ((Activity) getContext()).findViewById(R.id.game_status);
                if (message != null) {
                    message.setText(String.format("Tour length is now %.2f", list.totalDistance()));
                }
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void reset() {
        list.reset();
        invalidate();
    }

    public void setInsertMode(String mode) {
        insertMode = mode;
    }
}
