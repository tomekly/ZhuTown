package com.dongbang.yutian.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created 黄海全
 * email: fxhhq@outlook.com
 */
public class AppEnvEditTextButton extends RelativeLayout
{
    private Button button   = null;
    private TextInputEditText editText = null;

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see # View(Context, AttributeSet, int)
     */
    public AppEnvEditTextButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        button   = new Button(getContext());
        editText = new TextInputEditText(getContext());

        String namespace = "http://schemas.android.com/apk/res/android";

        editText.setId(android.R.id.edit);
        editText.setHint(attrs.getAttributeValue(namespace, "hint"));
        editText.setEnabled(false);
        button.setId(android.R.id.button1);
        button.setText("数据分析");

        init();
    }

    private void init()
    {
        RelativeLayout.LayoutParams buttonRelativeLayoutLayoutParams= new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonRelativeLayoutLayoutParams.addRule(RelativeLayout.ALIGN_RIGHT, editText.getId());
        buttonRelativeLayoutLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonRelativeLayoutLayoutParams.height      = dip2px(getContext(), 37);
        buttonRelativeLayoutLayoutParams.width       = dip2px(getContext(), 100);

        RelativeLayout.LayoutParams editTextRelativeLayoutLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.addView(editText, editTextRelativeLayoutLayoutParams);
        relativeLayout.addView(button, buttonRelativeLayoutLayoutParams);
        RelativeLayout.LayoutParams relativeLayoutLayoutParams= new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(relativeLayout, relativeLayoutLayoutParams);
    }

    public void setOnClickListener(@Nullable OnClickListener l)
    {

        button.setOnClickListener(l);
    }

    public Editable getText()
    {
        return editText.getText();
    }

    public void setText(CharSequence text)
    {
        editText.setText(text);
    }

    public void setHint(CharSequence hint)
    {
        editText.setHint(hint);
    }

    public Button getButton()
    {
        return button;
    }

    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}