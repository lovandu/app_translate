package com.example.languagetranslator.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.languagetranslator.R;
import com.example.languagetranslator.adapter.RecycleViewAdapter;
import com.example.languagetranslator.db.SQLiteHelper;
import com.example.languagetranslator.model.Item;

import java.util.List;


public class GameFragment extends Fragment implements View.OnClickListener{
    private SQLiteHelper db;
    private TextView tvQuestion;
    private TextView tvContent;
    private TextView tvAnswer1,tvAnswer2,tvAnswer3,tvAnswer4;
    private int currItem=0;
    private Item itemG;
    private int count=0;
    private int correctAnswer, incorrectAnswer;    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        db=new SQLiteHelper(getContext());
        List<Item> list=db.getByRand();
        count= list.size();
        setDataQuestion(list,currItem);
        tvAnswer1.setOnClickListener(this);
        tvAnswer2.setOnClickListener(this);
        tvAnswer3.setOnClickListener(this);
        tvAnswer4.setOnClickListener(this);


    }

    private void setDataQuestion(List<Item> list,int curr) {

        Item qs1, qs2, qs3, qs4;
        if(list.isEmpty()){
            return;
        }
        tvAnswer1.setBackgroundResource(R.drawable.bg_ans_conner_30);
        tvAnswer2.setBackgroundResource(R.drawable.bg_ans_conner_30);
        tvAnswer3.setBackgroundResource(R.drawable.bg_ans_conner_30);
        tvAnswer4.setBackgroundResource(R.drawable.bg_ans_conner_30);

        qs1=list.get(curr);
        itemG=qs1;
        qs2=list.get(curr+4);
        qs3=list.get(curr+2);
        qs4=list.get(curr+3);
        tvQuestion.setText("Question "+(curr+1)+"/10");
        tvContent.setText(qs1.getSrcWord().toString());
        if(curr%2==0){
            tvAnswer1.setText(qs1.getResWord().toString());
            tvAnswer2.setText(qs2.getResWord().toString());
            tvAnswer3.setText(qs3.getResWord().toString());
            tvAnswer4.setText(qs4.getResWord().toString());
        }
        else if(curr%3==0){
            tvAnswer1.setText(qs3.getResWord().toString());
            tvAnswer2.setText(qs2.getResWord().toString());
            tvAnswer3.setText(qs1.getResWord().toString());
            tvAnswer4.setText(qs4.getResWord().toString());

        }else if(curr%2!=0){
            tvAnswer1.setText(qs3.getResWord().toString());
            tvAnswer2.setText(qs1.getResWord().toString());
            tvAnswer3.setText(qs2.getResWord().toString());
            tvAnswer4.setText(qs4.getResWord().toString());

        }else{
            tvAnswer1.setText(qs3.getResWord().toString());
            tvAnswer2.setText(qs4.getResWord().toString());
            tvAnswer3.setText(qs2.getResWord().toString());
            tvAnswer4.setText(qs1.getResWord().toString());

        }







    }

//    private List<Item> getListItem(){
//        db=new SQLiteHelper(getContext());
//        List<Item> list=db.getByRand();
//        return list;
//    }

    private void initView(View view) {
        tvQuestion=view.findViewById(R.id.tvQuestion);
        tvContent=view.findViewById(R.id.tvContent);
        tvAnswer1=view.findViewById(R.id.tvAnswer1);
        tvAnswer2=view.findViewById(R.id.tvAnswer2);
        tvAnswer3=view.findViewById(R.id.tvAnswer3);
        tvAnswer4=view.findViewById(R.id.tvAnswer4);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvAnswer1:
                tvAnswer1.setBackgroundResource(R.drawable.bg_yello_ans_conner_30);
                checkAnswer(tvAnswer1,itemG);
                break;
            case R.id.tvAnswer2:
                tvAnswer2.setBackgroundResource(R.drawable.bg_yello_ans_conner_30);
                checkAnswer(tvAnswer2,itemG);
                break;
            case R.id.tvAnswer3:
                tvAnswer3.setBackgroundResource(R.drawable.bg_yello_ans_conner_30);
                checkAnswer(tvAnswer3,itemG);
                break;
            case R.id.tvAnswer4:
                tvAnswer4.setBackgroundResource(R.drawable.bg_yello_ans_conner_30);
                checkAnswer(tvAnswer4,itemG);
                break;
        }
    }
    private void checkAnswer(TextView textView, Item item){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String an=textView.getText().toString().trim();
                String res=item.getResWord().toString().trim();

                if(an.equalsIgnoreCase(res)){
                    textView.setBackgroundResource(R.drawable.bg_green_ans_conner_30);
                    nextQuestion();
                    correctAnswer++;
                }
                else{
                    textView.setBackgroundResource(R.drawable.bg_red_ans_conner_30);
                    showAnswerCorrect(item);
                    incorrectAnswer++;
                }
            }


        },1000);
    }

    private void showAnswerCorrect(Item item) {
        if(item==null || item.getResWord()==null){
            return ;
        }
        String an1=tvAnswer1.getText().toString().trim();
        String an2=tvAnswer2.getText().toString().trim();
        String an3=tvAnswer3.getText().toString().trim();
        String an4=tvAnswer4.getText().toString().trim();
        String res=item.getResWord().toString().trim();

        if(an1.equalsIgnoreCase(res)){
            tvAnswer1.setBackgroundResource(R.drawable.bg_green_ans_conner_30);
            nextQuestion();
        }else if(an2.equalsIgnoreCase(res)){
            tvAnswer2.setBackgroundResource(R.drawable.bg_green_ans_conner_30);
            nextQuestion();
        }else if(an3.equalsIgnoreCase(res)) {
            tvAnswer1.setBackgroundResource(R.drawable.bg_green_ans_conner_30);
            nextQuestion();
        }else if(an4.equalsIgnoreCase(res)) {
            tvAnswer4.setBackgroundResource(R.drawable.bg_green_ans_conner_30);
            nextQuestion();
        }

    }

    private void nextQuestion() {
        if(currItem>=9){
            showDialog("Bạn Đã hoàn thành " +correctAnswer+"/"+10);
            correctAnswer=0;
            incorrectAnswer=0;
        }else {
            currItem++;
            new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      db=new SQLiteHelper(getContext());
                      List<Item> list=db.getByRand();
                      setDataQuestion(list,currItem);
                  }
              },2000);
        }
    }
    private void showDialog(String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                currItem=0;
                db=new SQLiteHelper(getContext());
                List<Item> list=db.getByRand();
                setDataQuestion(list,currItem);
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}