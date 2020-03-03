package com.example.myfitnessapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.myfitnessapp.RegistrationDialogFragment.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.getStartedButton)
    Button mGetStartedButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGetStartedButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v == mGetStartedButton){
            FragmentManager fm = getSupportFragmentManager();
            RegistrationDialogFragment registerDialogFragment = new RegistrationDialogFragment ();
            registerDialogFragment.show(fm, "registration");
        }
    }

}
