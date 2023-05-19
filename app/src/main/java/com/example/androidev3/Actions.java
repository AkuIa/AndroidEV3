package com.example.androidev3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;

import android.content.Intent;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;


import java.io.IOException;

public class Actions extends AppCompatActivity {

    public ConnectionBluetooth bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Appel de la méthode onCreate de la classe parent (AppCompatActivity)
        super.onCreate(savedInstanceState);

        // Charge le layout associé à l'activité
        setContentView(R.layout.activity_actions);

        // Instanciation de l'objet ConnectionBluetooth
        bluetooth = new ConnectionBluetooth();

        // Instanciation d'un objet AlertDialog.Builder pour créer une boîte de dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(Actions.this);

        // Configuration de la boîte de dialogue avec un message et un bouton positif
        builder.setMessage("Démarrer la connexion Bluetooth ?");
        builder.setPositiveButton("Autoriser la connexion Bluetooth", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Si le bouton est cliqué, définir la permission Bluetooth sur "true" et répondre à la demande de connexion
                bluetooth.setPermissionBluetooth(true);
                bluetooth.response();
            }
        });

        // Création d'un bouton pour annuler la connexion Bluetooth
        builder.setNegativeButton("Annuler la connexion", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                bluetooth.setPermissionBluetooth(false);
                bluetooth.response();
            }
        });

        // Initialisation de la connexion Bluetooth
        if(!bluetooth.initialisationConnexionBluetooth()){
            Intent intent = new Intent(Actions.this, ViewConnection.class);
            startActivity(intent);
        }

        // Récupération de l'adresse MAC de l'appareil à connecter
        String adresseMac = getIntent().getExtras().getString("adresseMacDeEv3");

        // Connexion à l'appareil Bluetooth avec l'adresse MAC récupérée précédemment
        try {
            if(!bluetooth.connexionVersEv3(adresseMac)){
                Intent intent = new Intent(Actions.this, ViewConnection.class);
                startActivity(intent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        // Récupération des boutons de l'interface graphique
        Button retour = findViewById(R.id.buttonRetour);
        Button trierLesBriques = findViewById(R.id.buttonTrierLesBriques);
        Button contenuBacs = findViewById(R.id.buttonContenuBacs);

        // Définition de l'action à effectuer lors du clic sur le bouton "Retour"
        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Création d'une nouvelle intention pour lancer l'activité MainActivity
                Intent intent = new Intent(Actions.this,MainActivity.class);
                // Démarrage de l'activité MainActivity
                startActivity(intent);
            }
        });

        // Définition de l'action à effectuer lors du clic sur le bouton "Trier"
        trierLesBriques.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    // Envoi d'un message de tri à l'appareil Bluetooth
                    bluetooth.messageTelecommandeVersEv3(3);
                    // Attente de 300ms
                    Thread.sleep(300);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Définition de l'action à effectuer lors du clic sur le bouton "boutonContenuBacs"
        contenuBacs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    // Envoi d'un message d'affichage du contenu des bacs à l'appareil Bluetooth
                    bluetooth.messageTelecommandeVersEv3(2);
                    // Attente de 300ms
                    Thread.sleep(300);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button scannerLesBriques = findViewById(R.id.buttonScannerLesBriques);
        scannerLesBriques.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    // Envoie un message de type 1 à l'appareil Bluetooth distant
                    bluetooth.messageTelecommandeVersEv3(1);
                    // Pause l'exécution de la thread actuelle pendant 300 millisecondes
                    Thread.sleep(300);

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button poubelle = findViewById(R.id.buttonViderLesBacs);
        poubelle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    // Envoie un message de type 4 à l'appareil Bluetooth distant
                    bluetooth.messageTelecommandeVersEv3(4);
                    // Pause l'exécution de la thread actuelle pendant 300 millisecondes
                    Thread.sleep(300);

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button Update = findViewById(R.id.buttonMettreAJourRampe);
        Update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    // Envoie un message de type 5 à l'appareil Bluetooth distant
                    bluetooth.messageTelecommandeVersEv3(5);
                    // Pause l'exécution de la thread actuelle pendant 300 millisecondes
                    Thread.sleep(300);

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });


        // Récupération du bouton "Stop" depuis le layout
        Button Arreter = findViewById(R.id.buttonArreter);

        // Ajout d'un listener pour le clic sur le bouton "Stop"
        Arreter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    // Envoi d'un message "10" via Bluetooth pour stopper une action en cours
                    bluetooth.messageTelecommandeVersEv3(10);
                    // Pause de 300ms pour éviter des erreurs de communication
                    Thread.sleep(300);

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}