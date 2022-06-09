package com.example.languagetranslator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.languagetranslator.R;
import com.example.languagetranslator.model.Item;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder>{
    private List<Item> list;
    private ItemListener itemListener;
    public RecycleViewAdapter() {
        list=new ArrayList<>();
    }

    public Item getItem(int position) {
        return list.get(position);

    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //tra ve View
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item= list.get(position);
        holder.srcWord.setText(item.getSrcWord());
        holder.resWord.setText(item.getResWord());


    }



    @Override
    //tra ve so pha tu
    public int getItemCount() {
        return list.size();
    }
//cho tich chuot
    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView srcWord, resWord;


        public HomeViewHolder(@NonNull View view) {
            super(view);
            srcWord=view.findViewById(R.id.srcWord);
            resWord=view.findViewById(R.id.resWord);
            view.setOnClickListener(this);
        }

    @Override
    public void onClick(View view) {
        if(itemListener!=null){
            itemListener.onItemClick(view,getAdapterPosition());
        }
    }
}
    public interface ItemListener{
        void  onItemClick(View view, int position);

    }
}
