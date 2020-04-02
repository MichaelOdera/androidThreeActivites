package com.example.myfitnessapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitnessapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FadeActivity extends AppCompatActivity implements Animation.AnimationListener{
    @BindView(R.id.getStartedButton)
    Button mGetStartedButton;
    @BindView(R.id.gymnasiumImageView)
    ImageView mGymnasiumsImageView;
    TextView errorMessage;

    // Animation
    Animation animationFadeIn;
    Animation animationZoomOut;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fade);
        ButterKnife.bind(this);
        errorMessage = (TextView) findViewById(R.id.errorTextView);

        // load the animation
        animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fades_in);
        animationZoomOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);

        mGymnasiumsImageView.setOnTouchListener((v, event) -> {
            mGymnasiumsImageView.startAnimation(animationZoomOut);
            return false;
        });

        mGetStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMessage.setVisibility(View.VISIBLE);
                errorMessage.startAnimation(animationFadeIn);
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == animationFadeIn) {
            Toast.makeText(getApplicationContext(), "IT WAS A NICE ANIMATION FOR THE RESPONSE",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
