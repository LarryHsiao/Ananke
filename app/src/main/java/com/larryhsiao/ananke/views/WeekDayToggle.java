package com.larryhsiao.ananke.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.larryhsiao.clotho.date.JdkWeekdays;
import com.larryhsiao.clotho.date.Weekday;
import com.larryhsiao.clotho.date.Weekdays;
import com.silverhetch.aura.view.measures.DP;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM;

/**
 * View for toggling weekdays.
 */
public class WeekDayToggle extends LinearLayout {
    public interface OnStateChangeListener {
        void onStateChanged(int state);
    }

    private final Weekdays weekdays = new JdkWeekdays();
    private OnStateChangeListener stateChangeListener = null;

    public WeekDayToggle(Context context) {
        super(context);
        init();
    }

    public WeekDayToggle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeekDayToggle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(LOLLIPOP)
    public WeekDayToggle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        for (Weekday day : weekdays.days()) {
            final TextView dayText = new TextView(getContext());
            final LayoutParams layoutParam = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            layoutParam.weight = 1;
            dayText.setLayoutParams(layoutParam);
            dayText.setText(day.name());
            dayText.setTag(false);
            setGravity(Gravity.CENTER);
            dayText.setLines(1);
            dayText.setMaxLines(1);
            dayText.setAlpha(0.4f);
            dayText.setAutoSizeTextTypeUniformWithConfiguration(
                10,
                14,
                1,
                AUTO_SIZE_TEXT_TYPE_UNIFORM
            );
            dayText.setTextAppearance(getContext(), android.R.style.TextAppearance_Material_Title);
            dayText.setOnClickListener(v -> {
                final boolean currentChecked = ((boolean) v.getTag());
                dayText.setTag(!currentChecked);
                if (currentChecked && getToggleState() == 0) {
                    dayText.setTag(true);
                    return;
                }
                if (currentChecked) {
                    dayText.setAlpha(0.4f);
                } else {
                    dayText.setAlpha(1f);
                }
                if (stateChangeListener != null) {
                    stateChangeListener.onStateChanged(getToggleState());
                }
            });
            addView(dayText);
        }
        if (isInEditMode()) {
            loadToggleState(0b1000101);
        }
    }

    /**
     * Set up teh toggle states.
     */
    public void loadToggleState(int state) {
        if (state == 0){
            state = 0b1111111;
        }
        for (int i = 0; i < getChildCount(); i++) {
            final View dayText = getChildAt(i);
            if (((state >> (getChildCount() - 1 -i)) &0b0000001) == 1) {
                dayText.setTag(true);
                dayText.setAlpha(1f);
            } else {
                dayText.setTag(false);
                dayText.setAlpha(0.4f);
            }
        }
    }

    /**
     * ex:
     * - Monday enables only: 0b1000000
     *
     * @return Toggle states. 1 true otherwise 0.
     */
    public int getToggleState() {
        int result = 0b0000000;
        for (int i = 0; i < getChildCount(); i++) {
            boolean enabled = ((boolean) getChildAt(i).getTag());
            if (enabled) {
                result = result | (0b1 << (getChildCount() - 1 - i));
            }
        }
        return result;
    }

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        this.stateChangeListener = listener;
    }
}