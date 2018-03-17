package kzk.com.uiproject.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import kzk.com.uiproject.R;

/**
 * user ：ZunKun.ke
 * time :2018/3/16 21:59
 * email：zunkun.ke@neulion.com.cn
 */
public class CustomerProgressBar extends View
{

    private Bitmap mBitmap;
    private Paint mRectPaint;
    private Paint mPaint;
    private Rect mDstRect;
    private Rect mSrcRect;
    private PorterDuffXfermode porterDuffXfermode;
    private int mImgResId;
    private int mProgressBarColor;
    private String mProgress;
    private float mProgressWidth;
    private int mViewWidth;
    private int mViewHeight;

    public CustomerProgressBar(Context context)
    {
        super(context);
    }

    public CustomerProgressBar(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomerProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomerProgressBar, defStyleAttr, 0);

        if (typedArray.hasValue(R.styleable.CustomerProgressBar_imgRes))
        {
            mImgResId = typedArray.getResourceId(R.styleable.CustomerProgressBar_imgRes, R.drawable.ic_ask_message);
        }
        else
        {
            mImgResId = R.drawable.ic_ask_message;
        }

        if (typedArray.hasValue(R.styleable.CustomerProgressBar_progressBarColor))
        {
            mProgressBarColor = typedArray.getColor(R.styleable.CustomerProgressBar_progressBarColor, getResources().getColor(R.color.colorAccentTran));
        }
        else
        {
            mProgressBarColor = context.getResources().getColor(R.color.colorAccentTran);
        }

        if (typedArray.hasValue(R.styleable.CustomerProgressBar_progress))
        {
            mProgress = typedArray.getString(R.styleable.CustomerProgressBar_progress);
        }
        else
        {
            mProgress = "0";
        }
        if (mImgResId <= 0)
        {
            throw new RuntimeException("image res can not find");
        }
        mBitmap = BitmapFactory.decodeResource(getResources(), mImgResId);
        mRectPaint = new Paint();
        mPaint = new Paint();
        mProgressWidth = (Float.valueOf(mProgress) / 100 * mViewHeight);
        mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mDstRect = new Rect(0, 0, mViewWidth, mViewHeight);
        porterDuffXfermode = new PorterDuffXfermode(Mode.SRC_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = widthSize;
        mViewHeight = heightSize;
        if (widthMeasureMode == MeasureSpec.AT_MOST)
        {
            mViewWidth = Math.min(widthSize, mBitmap.getWidth());
        }

        if (heightMeasureMode == MeasureSpec.AT_MOST)
        {
            mViewHeight = Math.min(heightSize, mBitmap.getHeight());
        }
        mDstRect.set(0, 0, mViewWidth, mViewHeight);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        int layerId = canvas.saveLayer(0, 0, mViewWidth, mViewHeight, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, mPaint);

        mRectPaint.setColor(mProgressBarColor);

        mRectPaint.setXfermode(porterDuffXfermode);

        canvas.drawRect(0, 0, mProgressWidth, mViewHeight, mRectPaint);

        mRectPaint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    public void setProgress(int progress)
    {
        mProgressWidth = (float) progress / 100 * mViewWidth;

        invalidate();
    }
}
