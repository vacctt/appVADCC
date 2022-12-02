package com.example.appvadcc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.appvadcc.map2.DeviceBTAdapter;
import com.example.appvadcc.database.DeviceBT;
import com.example.appvadcc.tools.Tools;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Set;

public class MAP2 extends AppCompatActivity
{
    //Bluetooth
    public static int REQUEST_BLUETOOTH = 0;
    private static final int REQUEST_ENABLE_BT = 0;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //Lista de dispositivos bluetooth
    ArrayList<DeviceBT> devicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        devicesList = new ArrayList<DeviceBT>();
        ListView listado = (ListView) findViewById(R.id.listadoName);

        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
        if (BTAdapter == null)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Tu telefono no tiene bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    });
        }
        if (BTAdapter.isEnabled())
        {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                    return;
                }
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                    return;
                }
            }
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
        else
        {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_ENABLE_BT);
            while (!BTAdapter.isEnabled()) {}
        }

        Set<BluetoothDevice> pairedDevices = BTAdapter.getBondedDevices();

        if (pairedDevices.size() > 0)
        {
            for (BluetoothDevice device : pairedDevices) {
                devicesList.add(new DeviceBT(device.getName(), device.getAddress()));
            }
        }

        DeviceBTAdapter adapter = new DeviceBTAdapter(this, devicesList);
        listado.setAdapter(adapter);

        listado.setClickable(true);
        listado.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                createDeviceDB(position);
            }
        });
    }

    private void createDeviceDB(int position)
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        //databaseReference.setValue("Personas");
        databaseReference.child("Device").child(devicesList.get(position).getName()).setValue(devicesList.get(position));

        Tools.goActivity(this, MAP3.class);
    }
}