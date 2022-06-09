package com.example.languagetranslator.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.languagetranslator.R;
import com.example.languagetranslator.db.SQLiteHelper;
import com.example.languagetranslator.model.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment{
    private SQLiteHelper db;
    private Spinner fromsp, tosp;
    private TextInputEditText sourceEdit,Output;
    private ImageView micTv;
    private MaterialButton translateBtn;
    private TextView translatedTV;
    String[] fromLanguages = {"English","Thailand", "Vietnamese","Japanese"};
    String[] toLanguages = {"Vietnamese", "Thailand", "English","Japanese"};
    private static final int REQUEST_PERMINSSION_CODE = 1;
    int languageCode, fromLanguageCode, toLanguageCode = 0;
    String srcWord;
    String resWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fromsp = view.findViewById(R.id.idFromSpinner);
        tosp = view.findViewById(R.id.idToSpinner);
        sourceEdit = view.findViewById(R.id.idEditSoure);
        translateBtn = view.findViewById(R.id.idBtnTranslate);
//        translatedTV = view.findViewById(R.id.idTVTranslateTV);
        Output=view.findViewById(R.id.idOutput);
        micTv=view.findViewById(R.id.idIVMic);


        fromsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                fromLanguageCode = getLanguageCode(fromLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter fromAdapter = new ArrayAdapter(getContext(), R.layout.spinner_item, fromLanguages);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromsp.setAdapter(fromAdapter);
        tosp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                toLanguageCode = getLanguageCode(toLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter toAdapter = new ArrayAdapter(getContext(), R.layout.spinner_item, toLanguages);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tosp.setAdapter(toAdapter);

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                translatedTV.setText("");
                if (sourceEdit.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter your text to translate", Toast.LENGTH_SHORT).show();
                } else if (fromLanguageCode == 0) {
                    Toast.makeText(getContext(), "Please select soure language", Toast.LENGTH_SHORT).show();
                } else if (toLanguageCode == 0) {
                    Toast.makeText(getContext(), "Please select the language to make translate", Toast.LENGTH_SHORT).show();
                } else {
                    translateText(fromLanguageCode, toLanguageCode, sourceEdit.getText().toString());
                }
            }

        });
        micTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                itent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                itent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                itent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to convert into text");
                try {
                    startActivityForResult(itent, REQUEST_PERMINSSION_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMINSSION_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                sourceEdit.setText(result.get(0));
//                System.out.println(result.get(0));
            }
        }
    }
//    private void addItem() {
//        final Calendar calendar=Calendar.getInstance();
//        int year=calendar.get(Calendar.YEAR);

//        int month=calendar.get(Calendar.MONTH);
//        int day=calendar.get(Calendar.DAY_OF_MONTH);
//        String d=""+day+"/"+month+"/"+year+"";
//        Item i=new Item(srcWord,resWord,d);
//        db=new SQLiteHelper(getContext());
//        db.addItem(i);
//    }
    private void translateText(int fromLanguageCode, int toLanguageCode, String source) {
//        translatedTV.setText("Translating...");
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromLanguageCode)
                .setTargetLanguage(toLanguageCode)
                .build();
        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translator.translate(source).addOnSuccessListener(new OnSuccessListener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(String s) {
                        Output.setText(s);
                        resWord=Output.getText().toString();
                        srcWord=sourceEdit.getText().toString();
                        LocalDateTime dateTime=LocalDateTime.now();
                        String d=""+dateTime+"";
                        Item i=new Item(srcWord,resWord,d);
                        db=new SQLiteHelper(getContext());
//                        System.out.println(d+"{}"+dateTime);
                        db.addItem(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Fail to translate", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Fail to download language", Toast.LENGTH_SHORT).show();
            }
        });

}

    //String[] fromLanguages={"From","English","Vietnamese"};
    public int getLanguageCode(String language) {
        int languageCode = 0;
        switch (language) {
            case "English":
                languageCode = FirebaseTranslateLanguage.EN;
                break;
            case "Vietnamese":
                languageCode = FirebaseTranslateLanguage.VI;
                break;
            case "Thailand":
                languageCode = FirebaseTranslateLanguage.TH;
                break;
            case "Japanese":
                languageCode = FirebaseTranslateLanguage.JA;
                break;
            default:
                languageCode = 0;

        }
        return languageCode;

    }





}
