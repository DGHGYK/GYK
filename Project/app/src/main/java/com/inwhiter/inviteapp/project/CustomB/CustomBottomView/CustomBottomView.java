package com.inwhiter.inviteapp.project.CustomB.CustomBottomView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.inwhiter.inviteapp.project.R;

/**
 * Created by bthnorhan on 27.10.2017.
 */

public class CustomBottomView extends View {

    private static final String TAG = CustomBottomView.class.getSimpleName();
    private static final int padding = 16;
    private static final float maxScale = 1.2f;
    private static final float defaultScale = 1.0f;
    private static final long animationDuration = 75;

    private Paint paint,bitmapPaint;
    private CustomBottomViewLocation customBottomViewLocation = CustomBottomViewLocation.BOTTOM;
    private CustomBottomViewOption customBottomViewOption = null;
    private Bitmap add, home, user;
    private Resources resources;
    private CustomBottomViewListener customBottomViewListener;
    private ValueAnimator animatorZoomIn = null;
    private int width, height, barheight, radius;
    private float currentScale = defaultScale;
    private boolean isAnimated = false, isReached = false, isShowing = true;

    public CustomBottomView(Context context) {
        super(context);
        initCustomBottomView();
    }

    public CustomBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCustomBottomView();
    }

    public CustomBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomBottomView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCustomBottomView(canvas);
    }

    private void initCustomBottomView()
    {
        resources = getResources();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.parseColor("#C2B6D6"));
        bitmapPaint.setColor(Color.parseColor("#FFFFFF"));

        initBitmap();
        barheight = home.getHeight() + (padding*2);
        radius = add.getWidth() - padding;
    }

    private void drawCustomBottomView(Canvas canvas)
    {
        width = getWidth();
        height = getHeight();

        paint.setStyle(Paint.Style.FILL);

        switch (customBottomViewLocation)
        {
            case BOTTOM:
                canvas.drawCircle(width/2,height-radius-padding,radius+padding,paint);
                canvas.drawRect(0,height-barheight,width,height,paint);
                canvas.drawBitmap(add,width/2-(add.getWidth()/2),height-((add.getHeight()/2)+radius+padding),bitmapPaint);
                canvas.drawBitmap(home, width/5-home.getWidth()/2,height-(home.getHeight()+padding),bitmapPaint);
                canvas.drawBitmap(user,4*width/5-user.getWidth()/2,height-(user.getHeight()+padding),bitmapPaint);
                break;
            case TOP:
                canvas.drawCircle(width/2,radius+padding,radius+padding,paint);
                canvas.drawRect(0,barheight,width,0,paint);
                canvas.drawBitmap(add,width/2-(add.getWidth()/2),padding+radius-(add.getHeight()/2),bitmapPaint);
                canvas.drawBitmap(home, width/4-home.getWidth(),padding,bitmapPaint);
                canvas.drawBitmap(user,width-width/4,padding,bitmapPaint);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float cX = event.getX(0);
        float cY = event.getY(0);
        customBottomViewOption = null;

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            switch (customBottomViewLocation)
            {
                case BOTTOM:
                    if (cX > width/2-(add.getWidth()/2) && cX < width/2+(add.getWidth()/2)
                            && cY > height-((add.getHeight()/2)+radius+padding) && cY < height+((add.getHeight()/2)-radius-padding))
                    {
                        Log.d(TAG, "onTouchEvent: ADD BOTTOM imajına tıklandı.");
                        customBottomViewOption = CustomBottomViewOption.ADD;

                    }
                    else if (cX > width/4-home.getWidth() && cX < width/4
                            && cY > height-(home.getHeight()+padding) && cY < height-padding)
                    {
                        Log.d(TAG, "onTouchEvent: HOME BOTTOM imajına tıklandı.");
                        customBottomViewOption = CustomBottomViewOption.HOME;
                    }
                    else if (cX > width-width/4 && cX < width-width/4+user.getWidth()
                            && cY > height-(user.getHeight()+padding) && cY < height-padding)
                    {
                        Log.d(TAG, "onTouchEvent: USER BOTTOM imajına tıklandı.");
                        customBottomViewOption = CustomBottomViewOption.USER;
                    }
                    break;
                case TOP:
                    if (cX > width/2-(add.getWidth()/2) && cX < width/2+(add.getWidth()/2)
                            && cY > radius-(add.getHeight()/2) && cY < radius+(add.getHeight()/2))
                    {
                        Log.d(TAG, "onTouchEvent: ADD TOP imajına tıklandı.");
                        customBottomViewOption = CustomBottomViewOption.ADD;
                    }
                    else if (cX > width/4-home.getWidth() && cX < width/4
                            && cY > padding && cY < padding+home.getHeight())
                    {
                        Log.d(TAG, "onTouchEvent: HOME TOP imajına tıklandı.");
                        customBottomViewOption = CustomBottomViewOption.HOME;
                    }
                    else if (cX > width-width/4 && cX < width-width/4+user.getWidth()
                            && cY > padding && cY < padding+user.getHeight())
                    {
                        Log.d(TAG, "onTouchEvent: USER TOP imajına tıklandı.");
                        customBottomViewOption = CustomBottomViewOption.USER;
                    }
                    break;
            }
            if  (customBottomViewListener != null && customBottomViewOption!=null)
            {
                customBottomViewListener.itemClickListener(customBottomViewOption);
                startAnimation();
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            initBitmap();
        }
        return super.onTouchEvent(event);
    }

    public void setCustomBottomViewListener(CustomBottomViewListener customBottomViewListener)
    {
        this.customBottomViewListener = customBottomViewListener;
    }

    public void setLocation(CustomBottomViewLocation customBottomViewLocation)
    {
        this.customBottomViewLocation = customBottomViewLocation;
        invalidate();
    }

    public CustomBottomViewLocation getCustomBottomViewLocation()
    {
        return customBottomViewLocation;
    }

    private void initBitmap()
    {
        add = BitmapFactory.decodeResource(resources, R.drawable.ic_invite_add);
        home = BitmapFactory.decodeResource(resources, R.drawable.ic_home);
        user = BitmapFactory.decodeResource(resources, R.drawable.ic_user);

    }

    private void startAnimation()
    {
        if (isAnimated == false)
        {
            isReached = false;
            currentScale = defaultScale;
            animatorZoomIn = ValueAnimator.ofFloat(defaultScale,maxScale);
            animatorZoomIn.setDuration(animationDuration);

            animatorZoomIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    initBitmap();
                    isAnimated = false;
                    CustomBottomView.this.invalidate();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            animatorZoomIn.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Matrix matrix = new Matrix();

                    if (currentScale < maxScale && !isReached)
                    {
                        currentScale += 0.007f;
                    }
                    else if (currentScale > maxScale && !isReached)
                    {
                        isReached = true;
                        currentScale = defaultScale;
                    }

                    matrix.setScale(currentScale,currentScale);
                    switch (customBottomViewOption)
                    {
                        case ADD:
                            add = Bitmap.createBitmap(add,0,0,add.getWidth(),add.getHeight(),matrix,true);
                            break;
                        case HOME:
                            home = Bitmap.createBitmap(home,0,0,home.getWidth(),home.getHeight(),matrix,true);
                            break;
                        case USER:
                            user = Bitmap.createBitmap(user,0,0,user.getWidth(),user.getHeight(),matrix,true);
                            break;
                    }
                    CustomBottomView.this.invalidate();
                }
            });

            animatorZoomIn.start();
            isAnimated = true;
        }
        invalidate();
    }

    public void hide()
    {
        if (isShowing == true)
        {
            CustomBottomView.this.setVisibility(GONE);
            isShowing = false;
        }
    }

    public boolean isShowing(){ return isShowing; }

    public void setShowing(boolean showState)
    {
        if (showState)
        {
            if (isShowing == false)
            {
                CustomBottomView.this.setVisibility(VISIBLE);
                isShowing = true;
            }
        }
        else
        {
            if (isShowing == true)
            {
                CustomBottomView.this.setVisibility(GONE);
                isShowing = false;
            }
        }
    }
}
