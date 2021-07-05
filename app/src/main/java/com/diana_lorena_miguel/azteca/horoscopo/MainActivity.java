package com.diana_lorena_miguel.azteca.horoscopo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diana_lorena_miguel.azteca.horoscopo.db.Consultas;

public class MainActivity extends AppCompatActivity {

    static EditText fecha;
    static EditText telefono;
    static EditText servicio;
    static EditText respuesta;
    static Consultas consultar;

    /*Elementos para hacer prueba sin pedir mensajes SMS*/
    static EditText msjPrueba;
    static Button btnPrueba;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fecha = findViewById(R.id.resFecha);
        telefono = findViewById(R.id.resTelefono);
        servicio = findViewById(R.id.resServicio);
        respuesta = findViewById(R.id.respuesta);

        /*Elementos para hacer prueba sin pedir mensajes SMS*/
        msjPrueba = findViewById(R.id.mjsprueba);
        btnPrueba = findViewById(R.id.btnEnviar);

        /**Añade scroll al EditText de respuesta*/
        respuesta.setMovementMethod(new ScrollingMovementMethod());


        /** Crea la base de datos y contiene los metodos de consulta*/
        consultar = new Consultas(MainActivity.this);

        /**Escuchador para botón de prueba que incia el servicio sin SMS*/
        btnPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msjTemp = msjPrueba.getText().toString();
                ejcutarServicio("",msjTemp, false);
            }
        });
        checksSMSStatePermission();
    }

    /********************************************************
     ejcutarServicio() => Inicia tod0 el servicio a partir de un SMS recibido
     *********************************************************/
    public static void ejcutarServicio(String tel, String msj, boolean b){
        String [] arrRespuestas = Horoscopo.servicio(tel,msj,consultar,b);

        /**Coloca respuestas en la interfaz*/
        fecha.setText(arrRespuestas[0]);
        telefono.setText(tel);
        servicio.setText(arrRespuestas[1]);
        respuesta.setText(arrRespuestas[2]);
    }


    /********************************************************
     checksSMSStatePermission() => Compruena los permisos de recibit, enviar y leer SMS
     *********************************************************/
    private void checksSMSStatePermission(){

        /**Verificar permiso para Recibir SMS*/
        int perssionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECEIVE_SMS);
        if (perssionCheck != PackageManager.PERMISSION_GRANTED){
            Log.i ("Mensaje","No tienes para recibir SMS");
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.RECEIVE_SMS},255);
        }else
            Log.i("Mensaje","Si tienes permiso para recibir SMS");

        /**Verificar permiso para Enviar SMS*/
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED){
            Log.i("Mensaje","No tienes permiso para enviar SMS");
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS},225);
        } else{
            Log.i("Mensaje","Si tienes permisos para Enviar SMS");
        }


        /**Verificar permiso para Leer SMS*/
        perssionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_SMS);
        if (perssionCheck != PackageManager.PERMISSION_GRANTED){
            Log.i ("Mensaje","No tienes para leer SMS");
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_SMS},255);
        }else
            Log.i("Mensaje","Si tienes permiso para leer SMS");
    }

}