package com.diana_lorena_miguel.azteca.horoscopo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;


public class SMSReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage [] smsMessages = null;
        String telefono ="";
        String msj = "";

        if(null != bundle){

            Object [] pdus = (Object[]) bundle.get("pdus");
            smsMessages = new SmsMessage[pdus.length];
            for (int i=0; i<pdus.length;i++) {
                smsMessages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                telefono =   smsMessages[i].getDisplayOriginatingAddress();
                msj = smsMessages[i].getDisplayMessageBody().toString();
            }

            boolean b= true;

            /**Inicia Servicio después de obtener los datos del SMS*/
            MainActivity.ejcutarServicio(telefono,msj,b);
        }
    }





}
