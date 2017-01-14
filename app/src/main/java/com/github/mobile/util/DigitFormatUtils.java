package com.github.mobile.util;

import android.graphics.Paint;
import android.widget.TextView;

import java.util.Arrays;
/* Helpers for measuring text to display */

/**
 * Created by shalini on 1/14/2017.
 */
/**
 * Get width of number of digits
 *
 * @param - view
 * @param - numberOfDigits
 * @return - number width
 */
public class DigitFormatUtils {
    public static int getWidth(TextView view, int numberOfDigits) {
        Paint paint = new Paint();
        paint.setTypeface(view.getTypeface());
        paint.setTextSize(view.getTextSize());
        char[] text = new char[numberOfDigits];
        Arrays.fill(text, '0');
        return Math.round(paint.measureText(text, 0, text.length));
    }

    /**
     * Find the maximum number of digits in the given numbers
     *
     * @param numbers
     * @return max digits
     */
    public static int getMaxDigits(int... numbers) {
        int max = 1;
        for (int number : numbers)
            max = Math.max(max, (int) Math.log10(number) + 1);
        return max;
    }
}
