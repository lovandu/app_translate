package com.example.languagetranslator.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.languagetranslator.R;
import com.example.languagetranslator.adapter.RecycleViewAdapter;
import com.example.languagetranslator.db.SQLiteHelper;
import com.example.languagetranslator.model.Item;

import java.util.Calendar;
import java.util.List;


public class HistoryFragment extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private SQLiteHelper db;
    private SearchView searchView;//search o fragment search
    private RecycleViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        db=new SQLiteHelper(getContext());
        adapter=new RecycleViewAdapter();
//        final Calendar calendar=Calendar.getInstance();
//        int year=calendar.get(Calendar.YEAR);
//        int month=calendar.get(Calendar.MONTH);
//        int day=calendar.get(Calendar.DAY_OF_MONTH);
//        String d=day+"/"+month+"/"+year;
////        Item i=new Item("hi","xin chao",""+day+"");
////        db.addItem(i);
        List<Item> list=db.getAll();
        adapter.setList(list);
        LinearLayoutManager manager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //filter ra khi nhap vao o tim kiem
                List<Item> list=db.getByKey(s);
//                List<Item> list=db.getByRand();
                adapter.setList(list);
                return true;
            }
        });

    }

    private void initView(View view) {
        recyclerView=view.findViewById(R.id.reycleView);
        searchView=view.findViewById(R.id.search);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        List<Item> list=db.getAll();
//        List<Item> list=db.getByRand();
        adapter.setList(list);
    }
}