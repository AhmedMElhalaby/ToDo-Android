package AhmedMElhalaby_University.com.thingstodo.Ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import AhmedMElhalaby_University.com.thingstodo.R;

public class SplashActivity extends AppCompatActivity {

    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initSetUp();
    }

    private void initSetUp() {
        layout = findViewById(R.id.layout);
        Animation animation_alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        layout.clearAnimation();
        layout.startAnimation(animation_alpha);

        animation_alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}