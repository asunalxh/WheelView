package com.asunalxh.wheelview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WheelView extends View {

    private final int FAST_MOVE = 1;
    private final int SPEED_MOVE = 2;

    private List<String> list;//显示内容

    private List<WheelItem> wheelItems = new ArrayList<>();//控件列表

    private int showCount = 3;//显示行数，只能为单数

    private int viewWidth;
    private int viewHeight;
    private int itemHeight = 0;

    private float selectY;//中间选择框上线坐标

    private int minSelect = 0;//选择上限
    private int maxSelect = -1;//选择下限

    private int textColor = Color.BLACK;//字体颜色
    private int textSize = 30;//字体大小
    private int selectBackgroundColor = 0;//选择框背景颜色
    private int selectLineColor = 0;
    private float selectLineHeight = 1;

    private int selectIndex = 0;//选择索引

    private float touchY;//点击的坐标

    private Handler handler;
    private HandlerThread handlerThread;

    private VelocityTracker velocityTracker;//滑动速度获取
    private float minSpeed = 15;//快速滑动最低速度
    private float maxSpeed = 100;//快速滑动最高速度
    private float acceleration = 1;//滑动减速度

    private boolean isScroll = false;//是否滑动

    private boolean isRecyclable = false;//是否循环

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null)
            velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isScroll) {
                    stopMove();
                }
                touchY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                onMove(event.getY() - touchY);
                touchY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(10, maxSpeed);
                float speed = velocityTracker.getYVelocity();
                if (Math.abs(speed) > minSpeed)
                    onFastMove(speed);
                else reLocation();
                velocityTracker.recycle();
                velocityTracker = null;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);

        if (itemHeight == 0) {
            viewHeight = MeasureSpec.getSize(heightMeasureSpec);
            itemHeight = viewHeight / showCount;
        } else {
            viewHeight = itemHeight * showCount;
        }

        selectY = itemHeight * (showCount / 2);
        intiWheelItems();
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        handlerThread = new HandlerThread("handlerThread");
        handlerThread.start();
        handler = new MoveHander(handlerThread.getLooper());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handlerThread.quit();
        handlerThread = null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        if (selectBackgroundColor != 0) {
            paint.setColor(selectBackgroundColor);
            canvas.drawRect(0, selectY, viewWidth, selectY + itemHeight, paint);
        }
        if (selectLineColor != 0) {
            paint.setColor(selectLineColor);
            canvas.drawLine(0, selectY, viewWidth, selectY + selectLineHeight, paint);
            canvas.drawLine(0, selectY + itemHeight - selectLineHeight, viewWidth, selectY + itemHeight, paint);
        }
        for (WheelItem item : wheelItems)
            item.onDraw(canvas);
    }


    /**
     *速度递减移动
     *
     * @param speed 初始速度
     *              maxSpeed为初始最大速度
     */
    private void onFastMove(float speed) {
        isScroll = true;
        Message message = handler.obtainMessage();
        message.what = FAST_MOVE;
        Bundle bundle = new Bundle();
        bundle.putFloat("speed", speed);
        message.setData(bundle);
        handler.sendMessage(message);
    }


    /**
     *以相同速度移动固定距离
     * @param dy 距离
     * @param speed 速度
     */
    private void onSpeedMove(float dy, float speed) {
        isScroll = true;
        Message message = handler.obtainMessage();
        message.what = SPEED_MOVE;
        Bundle bundle = new Bundle();
        bundle.putFloat("dy", dy);
        bundle.putFloat("speed", speed);
        message.setData(bundle);
        handler.sendMessage(message);
    }


    /**
     * 停止当前移动动画
     */
    private void stopMove() {
        isScroll = false;
        handler.removeMessages(FAST_MOVE);
        handler.removeMessages(SPEED_MOVE);
    }


    private float sumMove=0;
    /**
     * 控件移动一定距离
     *
     * @param dy 坐标变化
     */
    private void onMove(float dy) {
        for (WheelItem item : wheelItems)
            item.move(dy);
        invalidate();
    }


    /**
     * 使居中显示
     */
    private void reLocation() {
        if (!isRecyclable) {
            if (wheelItems.get(0).getStartY() >= selectY - minSelect * itemHeight) {
                onSpeedMove(selectY - minSelect * itemHeight - wheelItems.get(0).getStartY(), 20);
            } else if (wheelItems.get(0).getStartY() <= selectY - maxSelect * itemHeight) {
                onSpeedMove(selectY - maxSelect * itemHeight - wheelItems.get(0).getStartY(), 20);
            }
        }
        for (WheelItem item : wheelItems)
            if (item.getStartY() > (selectY - itemHeight / 2) && item.getStartY() <= (selectY + itemHeight / 2)) {
                selectIndex = list.indexOf(item.getText());
                onSpeedMove(selectY - item.getStartY(), 5);
                return;
            }
    }


    /**
     * 初始化控件
     */
    private void intiWheelItems() {
        wheelItems.clear();
        if (!isRecyclable) {
            int select = Math.max(selectIndex, minSelect);
            int min = (select - showCount / 2 - 1) >= 0 ? (select - showCount / 2 - 1) : 0;
            for (int i = 0; i < list.size(); i++) {
                int index = i + min;
                wheelItems.add(new WheelItem(list.get(index), selectY + itemHeight * (index - select)));
            }
        } else {
            while (list.size() < showCount + 4) {
                int size = list.size();
                for (int i = 0; i < size; i++)
                    list.add(list.get(i));
            }
            for (int i = 0; i < list.size(); i++) {
                int index = i + selectIndex - showCount / 2 - 2;
                if (index < 0)
                    index += list.size();
                else if (index >= list.size())
                    index -= list.size();
                wheelItems.add(new WheelItem(list.get(index), itemHeight * (i - 2)));
            }
        }
    }

    public WheelView setMinSelect(int minSelect) {
        this.minSelect = minSelect;
        return this;
    }

    public WheelView setMaxSelect(int maxSelect) {
        this.maxSelect = maxSelect;
        return this;
    }

    public WheelView setSelectLineColor(int color) {
        this.selectLineColor = color;
        return this;
    }

    public WheelView setSelectLineHeight(float height) {
        this.selectLineHeight = height;
        return this;
    }

    public String getSelectString() {
        return list.get(selectIndex);
    }

    public WheelView setSelectBackgroundColor(int color) {
        this.selectBackgroundColor = color;
        return this;
    }

    public WheelView setMinSpeed(float minSpeed) {
        this.minSpeed = minSpeed;
        return this;
    }

    public WheelView setMaxSpeed(float maxSpeed) {
        this.minSpeed = maxSpeed;
        return this;
    }

    public WheelView setAcceleration(float acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    public WheelView setShowCount(int showCount) {
        this.showCount = showCount;
        return this;
    }

    public WheelView setList(List<String> list) {
        this.list = list;
        if (maxSelect == -1)
            maxSelect = list.size() - 1;
        return this;
    }

    public WheelView setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public WheelView setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public WheelView setIsRelcyclable(boolean b) {
        this.isRecyclable = b;
        return this;
    }

    public WheelView(Context context) {
        super(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private class MoveHander extends Handler {

        public MoveHander(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case FAST_MOVE:
                    float speed_fast = msg.getData().getFloat("speed");
                    float ac = 0;
                    if (speed_fast < 0) {
                        ac = -acceleration;
                        speed_fast = (-speed_fast) < maxSpeed ? speed_fast : (-maxSpeed);
                    } else if (speed_fast > 0) {
                        ac = acceleration;
                        speed_fast = speed_fast < maxSpeed ? speed_fast : maxSpeed;
                    }
                    while (Math.abs(speed_fast) > 0) {
                        if (!isScroll)
                            break;
                        else if (!isRecyclable && wheelItems.get(0).getStartY() >= selectY ||
                                !isRecyclable && wheelItems.get(wheelItems.size() - 1).getStartY() <= selectY)
                            break;

                        onMove(speed_fast);

                        if (Math.abs(speed_fast) <= acceleration)
                            break;

                        speed_fast -= ac;

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isScroll = false;
                    reLocation();
                    break;


                case SPEED_MOVE:
                    Bundle data = msg.getData();
                    float dy_slow = data.getFloat("dy");
                    float moveSpeed = data.getFloat("speed");
                    float speed_slow = 0;
                    if (dy_slow < 0)
                        speed_slow = -moveSpeed;
                    else if (dy_slow > 0)
                        speed_slow = moveSpeed;
                    while (Math.abs(dy_slow) > 0) {
                        if (!isScroll)
                            break;
                        if (Math.abs(dy_slow) < moveSpeed) {
                            onMove(dy_slow);
                            break;
                        } else onMove(speed_slow);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dy_slow -= speed_slow;
                    }
                    isScroll = false;
                    break;
            }
        }
    }

    private class WheelItem {

        private String text;//显示的文字

        private RectF rectF = new RectF();//坐标

        private float startY;//起始Y坐标

        public void move(float dy) {
            //循环显示
            if(isRecyclable) {
                if (startY >= (list.size() - 2) * itemHeight-itemHeight/2)
                    startY -= itemHeight * list.size();
                else if (startY <= -(list.size() - showCount - 2) * itemHeight-itemHeight/2)
                    startY += itemHeight * list.size();
            }
            startY += dy;
            rectF.top = startY;
            rectF.bottom = startY + itemHeight;
        }

        public void onDraw(Canvas canvas) {
            //不显示时候不绘制
            if (startY < -itemHeight || startY > itemHeight * showCount)
                return;

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG/*消除锯齿*/);
            paint.setColor(textColor);
            paint.setTextSize(textSize);

            //文字宽度
            float textWidth = paint.measureText(text);
            Paint.FontMetrics metrics = paint.getFontMetrics();
            //文字准线坐标
            float baseLine = rectF.centerY() - (metrics.top + metrics.bottom) / 2;

            //透明度与距离成反比（透明度范围0-255）
            int alpha = (int) (255 -(showCount*10)* Math.abs(rectF.centerY()-(selectY+itemHeight/2))/itemHeight);
            paint.setAlpha(alpha);

            //居中绘制
            canvas.drawText(text, rectF.centerX() - textWidth / 2, baseLine, paint);
        }

        public String getText() {
            return text;
        }

        public float getStartY() {
            return startY;
        }

        public WheelItem(String text, float startY) {
            this.text = text;
            this.startY = startY;

            rectF.top = startY;
            rectF.bottom = startY + itemHeight;
            rectF.left = 0;
            rectF.right = viewWidth;
        }
    }
}
