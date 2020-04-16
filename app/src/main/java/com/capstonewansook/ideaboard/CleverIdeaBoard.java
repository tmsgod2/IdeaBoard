package com.capstonewansook.ideaboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.RadioGroup;

public class CleverIdeaBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clever_idea_board);

        RadioGroup rg = (RadioGroup)findViewById(R.id.rdgroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String result;
                if(checkedId == R.id.rb1_newidea){
                    Bundle bundle = new Bundle();
                    bundle.putString("fromFrag1","프레그먼트1");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    IdeaFragment1 fragment1 = new IdeaFragment1();
                    fragment1.setArguments(bundle);
                    transaction.replace(R.id.fr,fragment1);
                    transaction.commit();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("fromFrag2","프레그먼트2");
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    IdeaFragment2 fragment2 = new IdeaFragment2();
                    fragment2.setArguments(bundle);
                    transaction.replace(R.id.fr,fragment2);
                    transaction.commit();

                }
            }
        });
    }
}
