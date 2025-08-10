package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNoteName;
    private RadioButton radioButtonLow, radioButtonMedium;
    private Button buttonSave;

    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.add_note_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);

        viewModel.getCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean closeScreen) {
                if (closeScreen){
                    finish();
                }
            }
        });

        initViews();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void initViews(){
        editTextNoteName = findViewById(R.id.editTextNoteName);
        radioButtonLow = findViewById(R.id.radioButtonLow);
        radioButtonMedium = findViewById(R.id.radioButtonMedium);
        buttonSave = findViewById(R.id.buttonSave);
    }

    private void saveNote(){
        int priority = getPriority();
        String nameNote = editTextNoteName.getText().toString().trim();
        viewModel.saveNote(new Note(0, nameNote, priority));
    }

    private int getPriority(){
        int priority;
        if (radioButtonLow.isChecked()){
            priority = 0;
        }else if (radioButtonMedium.isChecked()){
            priority = 1;
        }else{
            priority = 2;
        }
        return priority;
    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }
}