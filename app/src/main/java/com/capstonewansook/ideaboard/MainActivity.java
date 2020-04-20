package com.capstonewansook.ideaboard;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private RankingFragment rankingFragment;
    private ProfileFragment profileFragment;

    public static Stack<FragmentData> fragmentStack = new Stack<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        rankingFragment = new RankingFragment();
        profileFragment = new ProfileFragment();

        fragmentStack.push(new FragmentData(homeFragment,R.id.home_menu));
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
                        if(fragmentStack.peek().menuId!=R.id.home_menu)
                            fragmentStack.push(new FragmentData(homeFragment,R.id.home_menu));
                        return true;
                    }
                    case R.id.chat_menu:{
                        transaction.replace(R.id.frameLayout, chatFragment).commitAllowingStateLoss();
                        if(fragmentStack.peek().menuId!=R.id.chat_menu)
                            fragmentStack.push(new FragmentData(chatFragment,R.id.chat_menu));
                        return true;
                    }
                    case R.id.ranking_menu:{
                        transaction.replace(R.id.frameLayout, rankingFragment).commitAllowingStateLoss();
                        if(fragmentStack.peek().menuId!=R.id.ranking_menu)
                            fragmentStack.push(new FragmentData(rankingFragment,R.id.ranking_menu));
                        return true;
                    }
                    case R.id.profile_menu:{
                        transaction.replace(R.id.frameLayout, profileFragment).commitAllowingStateLoss();
                        if(fragmentStack.peek().menuId!=R.id.profile_menu)
                            fragmentStack.push(new FragmentData(profileFragment,R.id.profile_menu));
                        return true;
                    }
                    default: return false;
                }
            }
        });
    }

    public void replaceFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment).commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    //메인 액티비티 메뉴바 설정
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_main_logout:
                Logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //로그아웃 확인창 함수. 예를 누르면 로그인 액티비티로, 아니오를 누르면 그대로
    private void Logout(){
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
        AlertDialog alert = alert_confirm.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        if(fragmentStack.size()>1&&bottomNavigationView.getSelectedItemId()!=R.id.home_menu){
            fragmentStack.pop();
            Fragment nextFragment = fragmentStack.peek().fragment;
            fragmentManager.beginTransaction().replace(R.id.frameLayout,nextFragment).commitAllowingStateLoss();
            bottomNavigationView.setSelectedItemId(fragmentStack.peek().menuId);
        }
        else {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}
