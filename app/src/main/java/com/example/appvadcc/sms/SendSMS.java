package com.example.appvadcc.sms;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.telephony.SmsManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.appvadcc.tools.Tools;

import java.util.ArrayList;

public class SendSMS
{
    public static void sendSMS(Activity context, String number,String message)
    {
        checkSMSStatePermission(context);
        SmsManager sms = SmsManager.getDefault();
        ArrayList messageParts = sms.divideMessage(message);
        sms.sendMultipartTextMessage(number, null, messageParts , null, null);
        Tools.generateToast(context,"send");
    }

    private static void checkSMSStatePermission(Activity context)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(
                context, Manifest.permission.SEND_SMS);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            Tools.generateToast(context,"No se tiene permiso para enviar SMS");
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.SEND_SMS}, 225);
        }
    }
}
