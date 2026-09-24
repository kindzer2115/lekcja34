package com.example.lekcja33;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText etImie, etNazwisko, etEmail, etHaslo;
    private Button btnRejestruj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etImie = findViewById(R.id.etImie);
        etNazwisko = findViewById(R.id.etNazwisko);
        etEmail = findViewById(R.id.etEmail);
        etHaslo = findViewById(R.id.etHaslo);
        btnRejestruj = findViewById(R.id.btnRejestruj);

        btnRejestruj.setOnClickListener(v -> sprawdzIUtworzKonto());
    }

    private void sprawdzIUtworzKonto() {
        String imie = etImie.getText().toString().trim();
        String nazwisko = etNazwisko.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String haslo = etHaslo.getText().toString().trim();


        if (imie.isEmpty() || nazwisko.isEmpty() || email.isEmpty() || haslo.isEmpty()) {
            Toast.makeText(this, "Błąd: Wszystkie pola muszą zostać uzupełnione!", Toast.LENGTH_LONG).show();
            return;
        }


        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, "Błąd: Adres e-mail musi zawierać znak '@' oraz '.'", Toast.LENGTH_LONG).show();
            return;
        }


        String bladHasla = walidujHaslo(haslo);
        if (bladHasla != null) {
            Toast.makeText(this, "Błąd hasła: " + bladHasla, Toast.LENGTH_LONG).show();
            return;
        }


        boolean sukces = zapiszDoPliku(imie, nazwisko, email, haslo);
        if (sukces) {
            Toast.makeText(this, "Rejestracja udana! Dane zapisano do pliku.", Toast.LENGTH_LONG).show();
            wyczyscPola();
        } else {
            Toast.makeText(this, "Błąd podczas zapisu do pliku.", Toast.LENGTH_LONG).show();
        }
    }

    private String walidujHaslo(String haslo) {
        if (haslo.length() < 16) {
            return "Hasło musi mieć minimum 16 znaków.";
        }
        boolean maMalaLitere = false;
        boolean maWielkaLitere = false;
        boolean maCyfre = false;
        boolean maSpecjalny = false;

        String specjalneZnaki = "!@#$%^&*()_+-=[]{}|;':\",./<>?`~";

        for (int i = 0; i < haslo.length(); i++) {
            char c = haslo.charAt(i);
            if (Character.isLowerCase(c)) {
                maMalaLitere = true;
            } else if (Character.isUpperCase(c)) {
                maWielkaLitere = true;
            } else if (Character.isDigit(c)) {
                maCyfre = true;
            } else if (specjalneZnaki.indexOf(c) >= 0) {
                maSpecjalny = true;
            }
        }

        if (!maMalaLitere) return "Hasło musi zawierać co najmniej jedną małą literę.";
        if (!maWielkaLitere) return "Hasło musi zawierać co najmniej jedną wielką literę.";
        if (!maCyfre) return "Hasło musi zawierać co najmniej jedną cyfrę.";
        if (!maSpecjalny) return "Hasło musi zawierać co najmniej jeden znak specjalny (np. !, @, #, $, %).";

        return null;
    }

    private boolean zapiszDoPliku(String imie, String nazwisko, String email, String haslo) {
        String dane = "Imię: " + imie + ", Nazwisko: " + nazwisko + ", Email: " + email + ", Hasło: " + haslo + "\n";
        try {
            FileOutputStream fos = openFileOutput("uzytkownicy.txt", MODE_APPEND);
            fos.write(dane.getBytes());
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void wyczyscPola() {
        etImie.setText("");
        etNazwisko.setText("");
        etEmail.setText("");
        etHaslo.setText("");
    }
}