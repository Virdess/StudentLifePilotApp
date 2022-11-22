package kz.studentlife.studenlifepilotapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import kz.studentlife.studenlifepilotapp.databinding.ActivityMainPage2Binding;
import kz.studentlife.studenlifepilotapp.databinding.ActivityTeacherMainPageBinding;

public class TeacherMainPage extends AppCompatActivity {

    ActivityTeacherMainPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFrameLayout(new TeacherMainFragment());



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.teacher_main:
                    replaceFrameLayout(new TeacherMainFragment());
                    break;
                case R.id.teacher_profile:
                    replaceFrameLayout(new TeacherProfileFragment());
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