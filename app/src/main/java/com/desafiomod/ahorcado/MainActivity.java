package com.desafiomod.ahorcado;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static int MIN_CHARS = 3;
    public final static String EXTRA_WORD = "palabra";

    @BindView(R.id.edit_text) EditText wordSelectEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ok_button)
    public void onOkButton(View view) {
        String selectedWord = wordSelectEditText.getText().toString();
        if(selectedWord.contains(" ")) {
            MaterialDialog warning = new MaterialDialog.Builder(this)
                    .title("Ojo!")
                    .content("No pueden haber espacios :/")
                    .positiveText("Ok")
                    .build();
            warning.show();
        }
        else if(selectedWord.length() < MIN_CHARS) {
            MaterialDialog warning = new MaterialDialog.Builder(this)
                    .title("Ojo!")
                    .content("La palabra tiene que tener al menos " + MIN_CHARS + " caracteres.")
                    .positiveText("Ok")
                    .build();
            warning.show();
        }
        else {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(EXTRA_WORD, selectedWord);
            startActivity(intent);
            wordSelectEditText.setText("");
        }
    }
}
