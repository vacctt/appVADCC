package com.example.appvadcc.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appvadcc.MAP7;

import java.io.IOException;
import java.util.UUID;

public class ServerBT extends Thread
{
    private BluetoothServerSocket btServerSocket = null;
    private BluetoothAdapter btAdapter = null;
    private ConnectedThread myConnectionBT = null;
    private boolean btSocketRunning = true;
    private BluetoothSocket socket = null;

    // Identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static int REQUEST_BLUETOOTH = 0;
    private static final int REQUEST_ENABLE_BT = 0;

    private final MAP7 map7;

    public ServerBT(MAP7 map7)
    {
        this.map7 = map7;
        loadServer();
    }

    private void loadServer()
    {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;

        bluetoothAdapterContructor();

        try
        {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = btAdapter.listenUsingRfcommWithServiceRecord("Server", BTMODULEUUID);
        }
        catch (IOException e)
        {
            map7.notification("Error","Socket's listen() method failed" + e,1,1);
        }
        catch (SecurityException se)
        {
            map7.notification("Error","Error al crear el servidor " + se,1,1);
        }
        btServerSocket = tmp;
    }

    public void run()
    {

        while (true)
        {
            try {
                socket = btServerSocket.accept();
            } catch (IOException e)
            {
                map7.notification("Error","Socket's accept() method failed" + e,1,1);
                break;
            }

            if (socket != null)
            {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.

                try
                {
                    map7.notification("Conexion",socket.getRemoteDevice().getName(),4,4);
                    //Tools.generateToast(map7.getActivity(),"Hola");
                }
                catch (SecurityException se)
                {
                    map7.notification("Error","Error" + se,1,1);
                }
                myConnectionBT = new ConnectedThread(socket, this);
                myConnectionBT.start();
                map7.createRoute();
                while(btSocketRunning){}
                break;
            }
        }
    }

    public void bluetoothAdapterContructor()
    {
        this.btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            if (ContextCompat.checkSelfPermission(map7, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(map7, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                    return;
                }
            }
            if (ContextCompat.checkSelfPermission(map7, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(map7, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                    return;
                }
            }
            map7.startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        } else {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            map7.startActivityForResult(enableBT, REQUEST_ENABLE_BT);
            while (!btAdapter.isEnabled()) {}
        }
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel()
    {
        myConnectionBT.closeSocket();
        try
        {
            btServerSocket.close();
        } catch (IOException e) {
            map7.notification("Error","Error al cerrar el servicio " + e,1,1);
        }

        btSocketRunning = false;
    }

    public MAP7 getMap7()
    {
        return map7;
    }

    public void write(String message)
    {
        this.myConnectionBT.write(message);
    }

    public  BluetoothSocket getSocket()
    {
        return socket;
    }
}
