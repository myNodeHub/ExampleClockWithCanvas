package com.example.trainiginterapp2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import org.w3c.dom.Attr;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends View {

        public int height, weight = 0;
        int padding = 0;
        int fontSize = 0;
        int numeralSpacing = 0;
        int handTruncation, hourTruncation = 0;
        int radius = 0;
        Paint paint;
        boolean isInit;
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        Rect rect = new Rect();

        // координаты для рисования
//        float x = 100;
//        float y = 100;
//        int side = 100;

        // переменные для перетаскивания
//        boolean drag = false;
//        float dragX = 0;
//        float dragY = 0;

        //конструктор 1
        public MyView(Context context) {
            super(context);
        }

        //конструктор 2
        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        //конструктор 3
        public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        private void initClock() {
            height = getHeight();
            weight = getWidth();
            padding = numeralSpacing + 50;
            fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics());
            int min = Math.min(height, weight);
            radius = min / 2 - padding;
            handTruncation = min / 20;
            hourTruncation = min / 6;
            paint = new Paint();
            isInit = true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (!isInit) {
                initClock();
            }
//             рисуем фигуры
//            canvas.drawRect(x, y, x + side, y + side, paint);

            canvas.drawColor(Color.BLACK);
            drawCircle(canvas);
            drawCentre(canvas);
            drawNumeral(canvas);
            drawHands(canvas);

            postInvalidateDelayed(500);
            invalidate(); //перерисовка!!!
        }

        private void drawHand(Canvas canvas, double loc, boolean isHour) {
            double angle = Math.PI * loc / 30 - Math.PI / 2; //вот этот угол надо меннять вручную, передавая параметры из MotionEvent.ACTION_MOVE
            int handRadius = isHour ? radius - handTruncation - hourTruncation : radius - handTruncation;

            canvas.drawLine(weight / 2, height / 2,
                    (float) (weight / 2 + Math.cos(angle) * handRadius),
                    (float) (height / 2 + Math.sin(angle) * handRadius),
                    paint);
        }

        private void drawHands(Canvas canvas) {
            Calendar c = Calendar.getInstance();
            float hour = c.get(Calendar.HOUR_OF_DAY);
            hour = hour > 12 ? hour - 12 : hour;
            drawHand(canvas, (hour + c.get(Calendar.MINUTE) / 60) * 5f, true);
            drawHand(canvas, c.get(Calendar.MINUTE), false);
            drawHand(canvas, c.get(Calendar.SECOND), false);
        }

        private void drawNumeral(Canvas canvas) {
            paint.setTextSize(fontSize);
            for (int number : numbers) {
                String tmp = String.valueOf(number);
                paint.getTextBounds(tmp, 0, tmp.length(), rect);
                double angle = Math.PI / 6 * (number - 3);
                int xx = (int) (weight / 2 + Math.cos(angle) * radius - rect.width() / 2);
                int yy = (int) (height / 2 + Math.sin(angle) * radius + rect.height() / 2);
                canvas.drawText(tmp, xx, yy, paint);
            }
        }

        private void drawCentre(Canvas canvas) {
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(weight / 2, height / 2, 12, paint);
        }

        void drawCircle(Canvas canvas) {
            paint.reset();
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(5);
            paint.setStyle(paint.getStyle().STROKE);
            paint.setAntiAlias(true);
            canvas.drawCircle(weight / 2, height / 2, radius + padding - 10, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
//            // координаты Touch-события
//            float evX = event.getX();
//            float evY = event.getY();
//
//            switch (event.getAction()) {
//                // касание началось
//                case MotionEvent.ACTION_DOWN:
//                    // если касание было начато в пределах квадрата
//                    if (evX >= x && evX <= x + side && evY >= y && evY <= y + side) {
//                        // включаем режим перетаскивания
//                        drag = true;
//                        // разница между левым верхним углом квадрата и точкой касания
//                        dragX = evX - x;
//                        dragY = evY - y;
//                    }
//                    break;
//                // тащим
//                case MotionEvent.ACTION_MOVE:
//                    // если режим перетаскивания включен
//                    if (drag) {
//                        // определеяем новые координаты для рисования
//                        x = evX - dragX;
//                        y = evY - dragY;
//                        // перерисовываем экран
//                        invalidate();
//                    }
//                    break;
//                // касание завершено
//                case MotionEvent.ACTION_UP:
//                    // выключаем режим перетаскивания
//                    drag = false;
//                    break;
//            }
            return true;
        }
    }
}