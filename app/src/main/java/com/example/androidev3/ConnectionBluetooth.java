package com.example.androidev3;

// Importation de la classe SuppressLint du framework Android qui permet de supprimer les avertissements
import android.annotation.SuppressLint;
// Importation de la classe qui gère la connexion Bluetooth
import android.bluetooth.BluetoothSocket;

// Importation des classes pour lire des données
import java.io.DataInputStream;
import java.io.BufferedInputStream;

// Importation des classes pour gérer les erreurs d'entrée/sortie
import java.io.IOException;

// Importation des classes pour gérer les connexions Bluetooth
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

// Importation des classes pour écrire des données
import java.io.OutputStreamWriter;
import java.io.Writer;

// Importation de la classe pour afficher des logs
import android.util.Log;


import static java.util.UUID.fromString;

public class ConnectionBluetooth {
    // Variables booléennes pour la permission Bluetooth et la réponse à l'alerte
    private boolean messageAlerteReponse = false;

    // Variables booléennes pour la permission Bluetooth et la réponse à l'alerte
    private boolean permissionBluetooth = false;

    // Socket Bluetooth pour se connecter à l'EV3
    public BluetoothSocket socketRobotEv3;

    // UUID pour le protocole SPP (Serial Port Profile)
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    // Adapter Bluetooth qui permet de rechercher les appareils Bluetooth disponibles et d'établir une connexion Bluetooth
    BluetoothAdapter bluetoothAdapter;

    // Variable booléenne pour savoir si la connexion Bluetooth a réussi
    boolean success = false;

    //Cette fonction initialise la connexion bluetooth de l'appareil et retourne une valeur booléenne true si la connexion est établie avec succès.
    public boolean initialisationConnexionBluetooth() {
        // Récupérer l'adaptateur Bluetooth par défaut
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Vérifier si l'adaptateur est activé
        return bluetoothAdapter.isEnabled();
    }

    // Méthode pour définir la permission Bluetooth
    public void setPermissionBluetooth(boolean permissionBluetooth) {
        this.permissionBluetooth = permissionBluetooth;
    }

    // Méthode pour envoyer la réponse de l'utilisateur à l'alerte
    public void response() {
        // retourne la réponse à l'utilisateur
        this.messageAlerteReponse = true;
    }


    // Envoie un message à l'appareil connecté via Bluetooth
    public void messageTelecommandeVersEv3(int action) throws InterruptedException, IOException {
        Writer outputMessage = new OutputStreamWriter(socketRobotEv3.getOutputStream());
        // Écriture du message dans le flux de sortie
        outputMessage.write(action);
        // Vide le flux
        outputMessage.flush();
        // Pause pour laisser le temps à l'appareil de traiter le message
        Thread.sleep(1000);
    }

    /**

     Connecte l'appareil bluetooth à l'EV3

     @param addressMac adresse MAC de l'EV3

     @return true si la connexion a réussi, false sinon

     @throws IOException en cas d'erreur de connexion
     */
    @SuppressLint("MissingPermission")
    public boolean connexionVersEv3(String addressMac) throws IOException {

        // Récupération du périphérique Bluetooth correspondant à l'adresse MAC donnée
        BluetoothDevice robotEv3Bluetooth = bluetoothAdapter.getRemoteDevice(addressMac);

        try {
            // Création d'un socket RFCOMM pour la connexion
            socketRobotEv3 = robotEv3Bluetooth.createRfcommSocketToServiceRecord(fromString(SPP_UUID));
            // Tentative de connexion au périphérique
            socketRobotEv3.connect();
            success = true;
        } catch (IOException e) {
            // Si la connexion échoue, on log l'erreur et on retourne false
            Log.d("Erreur Bluetooth","Erreur: Connexion impossible ou appareil non trouvé " + addressMac);
            success=false;
        }
        return success;
    }




    // Lit un message envoyé par l'appareil connecté via Bluetooth
    public int lireMessageEv3VersTelecommande() throws InterruptedException, IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(socketRobotEv3.getInputStream()));
        // Lecture du message dans le flux d'entrée
        return in.read();
    }
}
