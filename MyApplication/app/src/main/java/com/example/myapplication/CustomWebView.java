package com.example.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * CustomWebView is a subclass of WebView that customizes touch event handling.
 * It ensures that touch events are not intercepted by parent views when
 * certain actions are detected.
 * It was used in foodbank profile to ensure that the google map web view touch events
 * are not intercepted by parent scroll view
 *
 * @author Zhi Li
 * <p>
 * Bibliography:
 * - <a href="https://stackoverflow.com/questions/20590236/scrollview-with-children-view-how-to-intercept-scroll-conditionally">...</a>
 */
public class CustomWebView extends WebView {

    /**
     * Constructor that takes only a Context.
     *
     * @param context the Context in which the WebView is running
     */
    public CustomWebView(Context context) {
        super(context);
    }

    /**
     * Constructor that takes a Context and an AttributeSet.
     *
     * @param context the Context in which the WebView is running
     * @param attrs the AttributeSet to use to create the WebView
     */
    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor that takes a Context, an AttributeSet, and a default style attribute.
     *
     * @param context the Context in which the WebView is running
     * @param attrs the AttributeSet to use to create the WebView
     * @param defStyleAttr an attribute in the current theme that contains a reference
     *                     to a style resource that supplies defaults values for the view
     */
    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Override the onTouchEvent method to customize touch event handling.
     * Prevents parent views from intercepting touch events during ACTION_DOWN and ACTION_MOVE.
     *
     * @param event the MotionEvent object containing full information about the event
     * @return true if the event was handled, false otherwise
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // Disallow parent views to intercept touch events during ACTION_DOWN and ACTION_MOVE
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // Allow parent views to intercept touch events during ACTION_UP and ACTION_CANCEL
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }
}
