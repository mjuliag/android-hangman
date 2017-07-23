package com.desafiomod.ahorcado;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    private static final int MAX_ATTEMPTS = 10;

    @BindView(R.id.attempts) TextView attemptsView;
    @BindView(R.id.word) TextView wordView;
    @BindView(R.id.letter) EditText letter;

    int attempts = MAX_ATTEMPTS;
    String word;
    boolean[] guesses;
    int guessesQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        attemptsView.setText(String.valueOf(attempts));
        word = getIntent().getStringExtra(MainActivity.EXTRA_WORD);
        guesses = new boolean[word.length()];
        updateMockup();
    }

    @OnClick(R.id.ok_button)
    public void onOkButton(View view) {
        String selectedLetter = letter.getText().toString();
        if(selectedLetter.length() != 1) {
            MaterialDialog warning = new MaterialDialog.Builder(this)
                    .title("Ojo!")
                    .content("Tenés que ingresar solo una letra.")
                    .positiveText("Ok")
                    .build();
            warning.show();
        }
        else {
            boolean letterIsRight = checkLetter(selectedLetter.charAt(0));
            if(letterIsRight) {
                updateMockup();
                if(guessesQuantity == word.length()) {
                    MaterialDialog warning = new MaterialDialog.Builder(this)
                            .title("¡Ganaste!")
                            .content("Felicitaciones, gato.")
                            .positiveText("Ok")
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    finish();
                                }
                            })
                            .build();
                    warning.show();
                }
            }
            else {
                attempts--;
                attemptsView.setText(String.valueOf(attempts));
                if(attempts == 0) {
                    MaterialDialog warning = new MaterialDialog.Builder(this)
                            .title("Cagaste")
                            .content("No más intentos, gato.")
                            .positiveText("Ok")
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    finish();
                                }
                            })
                            .build();
                    warning.show();
                }
            }
        }
        letter.setText("");
    }

    private boolean checkLetter(Character letter) {
        boolean letterIsHere = false;
        for(int i=0; i<word.length(); i++) {
            if(letter == word.charAt(i)) {
                if(!guesses[i]) {
                    guessesQuantity++;
                    guesses[i] = true;
                }
                letterIsHere = true;
            }
        }
        return letterIsHere;
    }

    private void updateMockup() {
        String mockupWord = "";
        for(int i=0; i<word.length(); i++) {
            if(guesses[i]) {
                mockupWord = mockupWord + word.charAt(i) + " ";
            }
            else {
                mockupWord = mockupWord + "_ ";
            }
        }
        wordView.setText(mockupWord);
    }
}
