package com.example.appvadcc.bluetooth;

import android.bluetooth.BluetoothSocket;

import com.example.appvadcc.MAP7;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ConnectedThread extends Thread
{
    private BluetoothSocket socket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;

    private MAP7 map7 = null;
    private ServerBT serverBT = null;

    public ConnectedThread(BluetoothSocket socket, ServerBT serverBT)
    {
        this.socket = socket;
        this.serverBT = serverBT;
        this.map7 = serverBT.getMap7();
    }

    public void run()
    {
        getInputOutputSocket();

        if (mmInStream!= null && mmOutStream!=null)
        {
            byte[] buffer = new byte[2048];
            int bytes;

            while (socket.isConnected())
            {
                try
                {
                    buffer = new byte[2048];
                    bytes=mmInStream.read(buffer);
                    if (bytes>0)
                    {
                        String str = "";
                        str = new String(buffer, StandardCharsets.UTF_8);

                        String cd = str.substring(0,2);
                        String con = str.substring(3,str.length());

                        switch (cd)
                        {
                            case "se":
                            {
                                map7.createSection(con);
                                break;
                            }
                            case "sm":
                            {
                                map7.sendSMS();
                                break;
                            }
                            case "ab":
                            {
                                map7.setAlarmActivationsBuzzers(con);
                                break;
                            }
                            case "av":
                            {
                                map7.setAlarmActivationsModules(con);
                                break;
                            }
                            case "tt":
                            {
                                map7.setTimeTotal(con);
                                break;
                            }
                            case "st":
                            {
                                map7.setSleepyTime(con);
                                write("DATA-OK");
                                map7.finishProcess();
                                break;
                            }
                        }
                    }
                } catch (IOException e)
                {
                    notification("Error","Error al recibir datos" + e,1,1);
                }
            }
        }

        if (serverBT != null)
        {
            serverBT.cancel();
        }
    }

    public void getInputOutputSocket()
    {
        try
        {
            mmInStream = socket.getInputStream();
            mmOutStream = socket.getOutputStream();
        }
        catch (IOException e)
        {
            notification("Error","Error al asignar los Streams "+e,1,1);
        }
        catch (SecurityException se) {
            notification("Error","Error de permiso para los streams" + se,1,1);
        }
    }

    public void write(String input)
    {
        if(mmOutStream!=null)
        {
            try
            {
                mmOutStream.write(input.getBytes());
            }
            catch (IOException e)
            {
                //si no es posible enviar datos se cierra la conexión
                notification("Error","La Conexión fallo: "+ e,1,1);
            }
        }
        else
        {
            notification("Error","mmOutStream es null",1,1);
        }
    }

    public void closeSocket()
    {
        try
        {
            socket.close();
        }
        catch (IOException ioe)
        {
            notification("Error","Error al cerrar el socket "+ioe,1,1);
        }
    }

    public void notification(String title, String content, int icon, int NOTIFICATION_ID)
    {
        map7.notification(title,content,icon,NOTIFICATION_ID);
    }
}
