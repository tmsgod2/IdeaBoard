package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.RankRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.RankRecyclerViewData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class RankingFragment extends Fragment {
    private static final String TAG = "RankingFragment";
    public StorageReference mStorageRef;
    public int flag=1;
    public ArrayList<RankingTiimeData> tiimeData;
    public ArrayList<RankRecyclerViewData> rankList = new ArrayList<>();
    public  Calendar cal = Calendar.getInstance();
    public  Calendar nowCal= Calendar.getInstance();
    public ViewGroup rootView;
    public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    boolean dbReadUser =false;
    boolean dbReadpost =false;
    boolean dbInsertStar = false;
    boolean dbToRankingData=false;
    int i = 0;
    int j = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nowCal.setTime(new Date());

        rankList = new ArrayList<>();

            mStorageRef = FirebaseStorage.getInstance().getReference();
            final FirebaseFirestore db = FirebaseFirestore.getInstance();

            rootView = (ViewGroup)inflater.inflate(R.layout.fragment_ranking, container, false);

        tiimeData = new ArrayList<>();

        db.collection("users").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                if(task.getResult() != null)
                                {
                                 for(QueryDocumentSnapshot snap: task.getResult()) {
                                     if (snap.get("name") != null) {
                                         try{
                                             rankList.add(new RankRecyclerViewData(snap.get("name").toString(), snap.get("office").toString(), 0,snap.getId()));
                                         } catch(NullPointerException e){}
                                     }
                                 }
                                    db.collection("posts").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        if(task.getResult() != null)
                                                        {
                                                            for(QueryDocumentSnapshot snap: task.getResult()) {
                                                                cal.setTime(((Timestamp)snap.get("date")).toDate());
                                                                tiimeData.add(new RankingTiimeData(snap.getId(),snap.get("uid").toString(),cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE),Integer.parseInt(snap.get("stars").toString())));
//                                        cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE)
                                                            }

                                                        }
                                                        dbReadpost = true;
                                                        //프레그먼트 생성시 화면비춤
                                                        if(flag==3){
                                                            TimeYearCalcul(rankList,rootView);
                                                            dbToRankingData = false;
                                                        }else if(flag==2){
                                                            TimeMonthCalcul(rankList,rootView);
                                                            dbToRankingData = false;
                                                        }else{
                                                            TimeDateCalcul(rankList,rootView);
                                                            dbToRankingData = false;
                                                        }
                                                        Log.d(TAG, i+" wwwwwwwwwww "+j);


                                                    }
                                                }
                                            });

                                 }

                                dbReadUser = true;

                            }
                        }
                    });





        //시간별 순위
        RadioGroup rg = rootView.findViewById(R.id.ranking_radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ranking_year_radioButton) {
                    flag=3;
                    TimeYearCalcul(rankList,rootView);
                    dbToRankingData = false;

                } else if (checkedId == R.id.ranking_mon_radioButton) {
                    flag=2;
                    TimeMonthCalcul(rankList,rootView);
                    dbToRankingData = false;

                } else if (checkedId == R.id.ranking_day_radioButton) {
                    flag=1;
                    TimeDateCalcul(rankList,rootView);
                    dbToRankingData = false;


                }
            }
        });

        return  rootView;
    }





    public void TimeYearCalcul(ArrayList<RankRecyclerViewData> rankList, ViewGroup rootView){

        //데이터 rankingdata에 삽입
        while(!dbToRankingData){

            if(dbReadpost&&dbReadUser){
                for(j=0;j<rankList.size();j++){
                    rankList.get(j).setStar(0);
                }
                for(i=0;i<tiimeData.size();i++){

                    if(nowCal.get(Calendar.YEAR)==tiimeData.get(i).getYear()){
                        for(j=0;j<rankList.size();j++){
                            if(rankList.get(j).getUid().equals(tiimeData.get(i).getUid())){
                                rankList.get(j).setStar(tiimeData.get(i).getStar(),"+");
                            }

                        }

                    }

                }
                Collections.sort(rankList);
                final RecyclerView recyclerView = rootView.findViewById(R.id.rankingRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                RankRecyclerViewAdapter adapter = new RankRecyclerViewAdapter(rankList);
                recyclerView.setAdapter(adapter);
                dbToRankingData=true;

            }

        }

    }

    public void TimeMonthCalcul(ArrayList<RankRecyclerViewData> rankList, ViewGroup rootView){

        //데이터 rankingdata에 삽입
        while(!dbToRankingData){

            if(dbReadpost&&dbReadUser){
                for(j=0;j<rankList.size();j++){
                    rankList.get(j).setStar(0);
                }
                for(i=0;i<tiimeData.size();i++){

                    if(nowCal.get(Calendar.YEAR)==tiimeData.get(i).getYear()){
                        if(nowCal.get(Calendar.MONTH)==tiimeData.get(i).getMonth()) {
                            for (j = 0; j < rankList.size(); j++) {
                                if (rankList.get(j).getUid().equals(tiimeData.get(i).getUid())) {
                                    rankList.get(j).setStar(tiimeData.get(i).getStar(), "+");
                                }

                            }
                        }
                    }

                }
                Collections.sort(rankList);
                final RecyclerView recyclerView = rootView.findViewById(R.id.rankingRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                RankRecyclerViewAdapter adapter = new RankRecyclerViewAdapter(rankList);
                recyclerView.setAdapter(adapter);
                dbToRankingData=true;

            }

        }




    }








    public void TimeDateCalcul(ArrayList<RankRecyclerViewData> rankList, ViewGroup rootView){

        //데이터 rankingdata에 삽입
        while(!dbToRankingData){

            if(dbReadpost&&dbReadUser){
                for(j=0;j<rankList.size();j++){
                    rankList.get(j).setStar(0);
                }
                for(i=0;i<tiimeData.size();i++){

                    if(nowCal.get(Calendar.YEAR)==tiimeData.get(i).getYear()){
                        if(nowCal.get(Calendar.MONTH)==tiimeData.get(i).getMonth()) {
                            if(nowCal.get(Calendar.DATE)==tiimeData.get(i).getDay()) {
                                for (j = 0; j < rankList.size(); j++) {

                                    if (rankList.get(j).getUid().equals(tiimeData.get(i).getUid())) {
                                        rankList.get(j).setStar(tiimeData.get(i).getStar(), "+");
                                    }

                                }
                            }
                        }
                    }

                }
                Collections.sort(rankList);
                final RecyclerView recyclerView = rootView.findViewById(R.id.rankingRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
                RankRecyclerViewAdapter adapter = new RankRecyclerViewAdapter(rankList);
                recyclerView.setAdapter(adapter);
                dbToRankingData=true;

            }

        }


    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView recyclerView = rootView.findViewById(R.id.rankingRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        RankRecyclerViewAdapter adapter = new RankRecyclerViewAdapter(rankList);
        recyclerView.setAdapter(adapter);
    }
}









