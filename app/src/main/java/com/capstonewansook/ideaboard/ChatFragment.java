package com.capstonewansook.ideaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewAdapter;
import com.capstonewansook.ideaboard.recyclerview.ChatRecyclerViewData;

import java.util.ArrayList;
import java.util.Date;


public class ChatFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup)inflater.inflate(R.layout.fragment_chat, container, false);
        ArrayList<ChatRecyclerViewData> list = new ArrayList<>();

        list.add(new ChatRecyclerViewData(R.drawable.iconfinder_32_111002,"이선행","안녕하세요",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"눈사슴","총이 넘어지면? 우당탕",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"모르는분","이건 이렇게 하는게 어떠세요",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"박상준","ㅎㅇ",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"유기봄","ㅁㅇㅁㅇㅁㅇㅁㅇ",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"이선행","안녕하세요",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"눈사슴","총이 넘어지면? 우당탕",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"모르는분","이건 이렇게 하는게 어떠세요",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"박상준","ㅎㅇ",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"유기봄","ㅁㅇㅁㅇㅁㅇㅁㅇ",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"이선행","안녕하세요",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"눈사슴","총이 넘어지면? 우당탕",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"모르는분","이건 이렇게 하는게 어떠세요",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"박상준","ㅎㅇ",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"유기봄","ㅁㅇㅁㅇㅁㅇㅁㅇ",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"이선행","안녕하세요",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"눈사슴","총이 넘어지면? 우당탕",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"모르는분","이건 이렇게 하는게 어떠세요",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"박상준","ㅎㅇ",new Date(System.currentTimeMillis())));
        list.add(new ChatRecyclerViewData(R.drawable.ic_person_black,"유기봄","ㅁㅇㅁㅇㅁㅇㅁㅇ",new Date(System.currentTimeMillis())));

        RecyclerViewSet(rootView,list,(RecyclerView)rootView.findViewById(R.id.chat_recyclerView),new ChatRecyclerViewAdapter(list),R.id.chat_re_name_textView);
        return  rootView;
    }

    private void RecyclerViewSet(final ViewGroup view, ArrayList list, RecyclerView recyclerView, RecyclerView.Adapter adapter, final int toastPosition){
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_DOWN){
                    View child = rv.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null) {
                        TextView name = (TextView) rv.getChildViewHolder(child).itemView.findViewById(toastPosition);
                        Toast.makeText(view.getContext(), "이름" + name.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
}
