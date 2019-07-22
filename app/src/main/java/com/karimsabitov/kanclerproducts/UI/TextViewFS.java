package com.karimsabitov.kanclerproducts.UI;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.karimsabitov.kanclerproducts.R;

public class TextViewFS extends android.support.v7.widget.AppCompatTextView {

    public TextViewFS(Context context) {
        super(context);
    }

    public TextViewFS(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TextViewFS(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextViewFS);
        int font = array.getInt(R.styleable.TextViewFS_customFont, 0);
        Typeface typeface = FontsLoader.getInstance(context).getTypeface(font);
        setTypeface(typeface);
        array.recycle();
    }

}
