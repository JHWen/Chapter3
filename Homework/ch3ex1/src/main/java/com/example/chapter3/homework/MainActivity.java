package com.example.chapter3.homework;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class MainActivity extends AppCompatActivity {
    private LottieAnimationView animationView;
    private SeekBar seekBar;
    private CheckBox checkboxPlay;
    private CheckBox checkboxCycle;
    //动画执行过程中改变seekBar状态，seekBar不会反过去修改动画的进度
    private boolean isSeekBarEnable;

    private static final String TAG = "wenjiahao";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animationView = findViewById(R.id.animation_view);
        seekBar = findViewById(R.id.seekbar);
        checkboxPlay = findViewById(R.id.checkbox_play);
        checkboxCycle = findViewById(R.id.checkbox_cycle_arrow);
        isSeekBarEnable = true;


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO 3: 这里应该调用哪个函数呢
                // 提示1：可以参考 https://airbnb.io/lottie/android/android.html#custom-animators
                // 提示2：SeekBar 的文档可以把鼠标放在 OnProgressChanged 中间，并点击 F1 查看，
                // 或者到官网查询 https://developer.android.com/reference/android/widget/SeekBar.OnSeekBarChangeListener.html#onProgressChanged(android.widget.SeekBar,%20int,%20boolean)
                // 设置动画进度 浮点数
                if (isSeekBarEnable) {
                    animationView.setProgress(progress / 100.0f);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd");
                //动画结束，播放按钮变成停止
                checkboxPlay.setChecked(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, "onAnimationCancel");
            }
        });

        animationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //动画播放时，更新进度条进度
                Log.d(TAG, "onAnimationUpdate: " + animation.getAnimatedFraction());
                seekBar.setProgress((int) (animation.getAnimatedFraction() * 100));
            }
        });

        checkboxPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    animationView.resumeAnimation();
                    isSeekBarEnable = false;
                } else {
                    animationView.pauseAnimation();
                    isSeekBarEnable = true;
                }
            }
        });

        checkboxCycle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //设置循环播放动画
                    animationView.setRepeatCount(LottieDrawable.INFINITE);
                } else {
                    //取消循环播放动画
                    animationView.setRepeatCount(0);
                }
            }
        });


    }
}
