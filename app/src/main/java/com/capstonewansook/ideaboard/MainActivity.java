package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private RankingFragment rankingFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        rankingFragment = new RankingFragment();
        profileFragment = new ProfileFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();
        // 바텀 버튼 설정
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            // 각 버튼을 누르면 해당 프레그먼트로 이동
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction = fragmentManager.beginTransaction();
                switch(item.getItemId()){
                    case R.id.home_menu:{
                        transaction.replace(R.id.frameLayout, homeFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.chat_menu:{
                        transaction.replace(R.id.frameLayout, chatFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.ranking_menu:{
                        transaction.replace(R.id.frameLayout, rankingFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.profile_menu:{
                        transaction.replace(R.id.frameLayout, profileFragment).commitAllowingStateLoss();
                        return true;
                    }
                    default: return false;
                }
            }
        });
    }

}
