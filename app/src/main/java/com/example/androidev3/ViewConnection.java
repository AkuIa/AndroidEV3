package com.example.androidev3;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import android.widget.EditText;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.content.Intent;
import androidx.navigation.ui.AppBarConfiguration;
import android.widget.Button;
import androidx.navigation.ui.NavigationUI;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.view.View;

import com.example.androidev3.databinding.ActivityViewConnectionBinding;

public class ViewConnection extends AppCompatActivity {

    private EditText adresseMacRobotEv3;
    private Button validerAdresseMac;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_connection);

        // Initialisation des vues
        validerAdresseMac = (Button) findViewById(R.id.buttonConnect);
        adresseMacRobotEv3 = (EditText) findViewById(R.id.editTextMacAddress);

        // Définition du comportement du bouton "Valider"
        validerAdresseMac.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Si l'adresse MAC n'est pas valide, on affiche une alerte
                if((adresseMacRobotEv3.getText().toString().length()!=17)){
                    // Notifie l'utilisateur qu'il faut entrer une adresse MAC
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewConnection.this);
                    builder.setMessage("Entrez une adresse Mac pour faire la liaison avec le robot Ev3");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // L'utilisateur clique sur le bouton OK
                        }
                    });
                    // Crée l'alerte
                    AlertDialog alerteMac = builder.create();
                    alerteMac.show();
                }else{
                    // Si l'adresse MAC est valide, on lance l'activité "Actions"
                    Intent intent = new Intent(ViewConnection.this, Actions.class);
                    intent.putExtra("adresseMacDeEv3",adresseMacRobotEv3.getText().toString().toUpperCase());
                    startActivity(intent);
                }
            }
        });
    }

}