package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import kz.studentlife.studenlifepilotapp.databinding.ActivityMainPage2Binding;

public class MainPage extends AppCompatActivity {

    ActivityMainPage2Binding binding;
    ImageView ahivementsBtn;
    public Context mainPageContext = MainPage.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPage2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrameLayout(new MainFragment());


        ahivementsBtn = findViewById(R.id.ahivementsBtn);


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.mainPageNavButton:
                    replaceFrameLayout(new MainFragment());
                    break;
                case R.id.timetableNavButton:
                    replaceFrameLayout(new TimeTableFragment());
                    break;
                case R.id.qrNavButton:
                    replaceFrameLayout(new QRFragment());
                    break;
                case R.id.messagesNavButton:
                    replaceFrameLayout(new MessagesFragment());
                    break;
                case R.id.profileNavButton:
                    replaceFrameLayout(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFrameLayout(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}