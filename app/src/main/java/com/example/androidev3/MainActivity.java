package com.example.androidev3;

import android.os.Bundle;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    // Méthode appelée lors de la création de l'activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Appel de la méthode onCreate de la superclasse AppCompatActivity
        super.onCreate(savedInstanceState);
        // Définition de la vue associée à cette activité
        setContentView(R.layout.activity_main);
        // Récupération du bouton "Connecter" de la vue
        Button demarrer = findViewById(R.id.buttonConnect);
        // Ajout d'un écouteur sur le bouton "Connecter"
        demarrer.setOnClickListener(new View.OnClickListener(){
            @Override
            // Méthode appelée lorsque le bouton est cliqué
            public void onClick(View v){
                // Création d'une intention pour ouvrir l'activité ViewConnection
                Intent intent = new Intent(MainActivity.this,ViewConnection.class);
                // Démarrage de l'activité ViewConnection
                startActivity(intent);
            }
        });
    }

}