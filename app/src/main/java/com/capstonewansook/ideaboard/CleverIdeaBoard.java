package com.capstonewansook.ideaboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.capstonewansook.ideaboard.recyclerview.IdeaRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CleverIdeaBoard extends AppCompatActivity implements IdeaRecyclerViewAdapter.onItemListener {


    boolean isManystar;
    FloatingActionButton newIdeaButton;
    IdeaFragment1 fragment1;
    IdeaFragment2 fragment2;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clever_idea_board);

        //상단 액션바의 뒤로가기 버튼을 위해 작성
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("아이디어");

        actionBar.setDisplayHomeAsUpEnabled(true);

        final SearchView searchView = findViewById(R.id.cleveridea_searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if(flag==1) {
                    fragment1.StartAdapter(s);
                }
                else{
                    fragment2.StartAdapter(s);
                }
                return false;
            }
        });

        Intent intent = getIntent();
        isManystar = intent.getExtras().getBoolean("isManystar");
        RadioGroup rg = findViewById(R.id.rdgroup);
        if(!isManystar){
            flag=1;
            rg.check(R.id.rb1_newidea);
            Bundle bundle = new Bundle();
            bundle.putString("fromFrag1","프레그먼트1");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment1 = new IdeaFragment1();
            fragment1.setArguments(bundle);
            transaction.replace(R.id.fr,fragment1);
            transaction.commit();
        }else {
            flag=2;
            rg.check(R.id.rb2_newidea);
            Bundle bundle = new Bundle();
            bundle.putString("fromFrag2","프레그먼트2");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment2 = new IdeaFragment2();
            fragment2.setArguments(bundle);
            transaction.replace(R.id.fr,fragment2);
            transaction.commit();

        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String result;
                if(checkedId == R.id.rb1_newidea){
                    flag=1;
                    Bundle bundle = new Bundle();
                    bundle.putString("fromFrag1","프레그먼트1");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    fragment1 = new IdeaFragment1();
                    fragment1.setArguments(bundle);
                    transaction.replace(R.id.fr,fragment1);
                    transaction.commit();
                }else{
                    flag=2;
                    Bundle bundle = new Bundle();
                    bundle.putString("fromFrag2","프레그먼트2");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    fragment2 = new IdeaFragment2();
                    fragment2.setArguments(bundle);
                    transaction.replace(R.id.fr,fragment2);
                    transaction.commit();

                }
            }
        });

        newIdeaButton = (FloatingActionButton)findViewById(R.id.clever_newIdea_Button);
        newIdeaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIdeaIntent = new Intent(getApplicationContext(), newpost.class);
                startActivity(newIdeaIntent);
            }
        });

    }

    //상단의 뒤로가기 버튼 클릭시 뒤로 감
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClicked(int position) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(flag==1){
            Bundle bundle = new Bundle();
            bundle.putString("fromFrag1","프레그먼트1");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment1 = new IdeaFragment1();
            fragment1.setArguments(bundle);
            transaction.replace(R.id.fr,fragment1);
            transaction.commit();
        }else{
            Bundle bundle = new Bundle();
            bundle.putString("fromFrag2","프레그먼트2");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment2 = new IdeaFragment2();
            fragment2.setArguments(bundle);
            transaction.replace(R.id.fr,fragment2);
            transaction.commit();

        }
    }
}
