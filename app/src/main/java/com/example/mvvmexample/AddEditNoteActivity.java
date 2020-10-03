package com.example.mvvmexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE =
            "com.example.mvvmexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.mvvmexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.mvvmexample.EXTRA_PRIORITY";
    public static final String EXTRA_ID =
            "com.example.mvvmexample.EXTRA_ID";

    private EditText etTitle;
    private EditText etDescription;
    private NumberPicker npPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = findViewById(R.id.edtTitle);
        etDescription = findViewById(R.id.edtDescription);
        npPriority = findViewById(R.id.npPriority);

        npPriority.setMinValue(1);
        npPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            etTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            etDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            npPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }
        else {
            setTitle("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        int priority = npPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "Title and Description cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_PRIORITY, priority);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();

    }
}