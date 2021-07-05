package com.diana_lorena_miguel.azteca.horoscopo;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.diana_lorena_miguel.azteca.horoscopo.db.Consultas;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Horoscopo {
    /**Esta clase contiene todos lo métodos necesarios para validar el SMS y devolver el resultado
      si es correcto el mensaje SMS*/

    /**########################################################################*
     *                          Métodos principales                            *
     * ########################################################################*/

    private  static  String tel;
    private  static  String tipoServicio;
    private  static  boolean enviaSMS;
    static Consultas consultar;


    /********************************************************
     servicio() => Inicia el proceso para hacer el servicio
     *********************************************************/
    public static String []  servicio(String telefono, String mensaje, Consultas consul,Boolean b) {
        tel = telefono;
        tipoServicio=mensaje;
        consultar = consul;
        enviaSMS= b;
        return  procesoSegunCaracteres();
    }

    /********************************************************
     procesoSegunCaracteres() => Realiza proceos según el número de carácteres
     *********************************************************/
    private static String []  procesoSegunCaracteres() {
        int largo = tipoServicio.length();

        switch (largo) {
            case 1:  return  caso1(); //break;
            case 2:  return caso2(); //break;
            case 5:  return caso3(); //break;
            case 6:  return caso4(); //break;
            default:
                if (enviaSMS) {enviarErrorSMS();}
                return new String[] {"","",enviarError()};
        }
    }

    /********************************************************
     caso1() => solo horoscopo o calendario con fecha actual
     *********************************************************/
    private static String []  caso1 (){
        String fecha;
        String respuesta;

        switch (validaServicio()) {
            case 1: //Horoscopo
                fecha = getFecha("Horoscopo");
                respuesta = servicioHoroscopo(fecha);
                if (enviaSMS) {enviaSMS(respuesta);}
                return new String[] {fecha,"Horoscopo",respuesta};
                //break;

            case 2: //calendario}
                fecha = getFecha("Calendario");
                respuesta = servicioCalendario(fecha);
                if (enviaSMS){ enviaSMS(respuesta);}
                return new String[] {fecha,"Calendario",respuesta};
                //break;
            default:
                if (enviaSMS) {enviarErrorSMS();}
                return new String[] {"","",enviarError()};
        }
    }

    /********************************************************
     caso2() => horoscopo y calendario con fecha actual
     *********************************************************/
    private static String []   caso2 (){
        String fechaHoros;
        String fechaCalend;
        String respuesta;

        if (validaServicioDoble()){
            fechaHoros = getFecha("horoscopo");
            fechaCalend = getFecha("calendario");
            respuesta = servicioHorosCalend(fechaHoros,fechaCalend);
            if (enviaSMS) {enviaSMS(respuesta);}
            return new String[] {"H: "+fechaHoros + " C:"+ fechaCalend,"Ambos Servicios",respuesta};
        }else
            if (enviaSMS) {enviarErrorSMS();}
            return new String[] {"","",enviarError()};
    }
    /********************************************************
     caso3() => solo horoscopo o calendario con la fecha recibida
     *********************************************************/
    private static String []  caso3 (){
        String respuesta;
        String [] servicioFecha = separa_Servicio_y_Fecha();
        tipoServicio = servicioFecha[0];
        String fecha = servicioFecha[1];

        if (validaFecha(fecha)){
            switch (validaServicio()) {
                case 1: //Horoscopo
                    respuesta = servicioHoroscopo(fecha);
                    if (enviaSMS) {enviaSMS(respuesta);}
                    return new String[] {fecha,"Horoscopo",respuesta};
                    //break;
                case 2: //calendario
                    String dia = separa_Dia_y_Mes(fecha)[0];
                    respuesta = servicioCalendario(dia);
                    if (enviaSMS) {enviaSMS(respuesta);}
                    return new String[] {fecha,"Calendario",respuesta};
                    //break;
                default:
                    if (enviaSMS) {enviarErrorSMS();}
                    return new String[] {"","",enviarError()};
            }
        }else
            if (enviaSMS) {enviarErrorSMS();}
            return new String[] {"","",enviarError()};
    }

    /********************************************************
     caso4() => horoscopo y calendario con la fecha recibida
     *********************************************************/
    private static String []  caso4 (){
        String respuesta;
        String [] servicioFecha = separa_Servicio_y_FechaDoble();
        tipoServicio = servicioFecha[0];
        String fechaHoros = servicioFecha[1];
        String fechaCalend = servicioFecha[2];

        if (validaFecha(fechaHoros)){
            if (validaServicioDoble()){
                respuesta = servicioHorosCalend(fechaHoros,fechaCalend);
                if (enviaSMS) {enviaSMS(respuesta);}
                return new String[] {"H: "+fechaHoros + " C:"+ fechaCalend,"Ambos Servicios",respuesta};
            }else
                if (enviaSMS) {enviarErrorSMS();}
                return  new String[] {"","",enviarError()};
        }else
            if (enviaSMS) {enviarErrorSMS();}
            return new String[] {"","",enviarError()};
    }


    /**########################################################################*
     *                          Métodos complementarios                        *
     * ########################################################################*/


    /********************************************************
     validaServicio() => Validad y retorna el tipo de servicio
    *********************************************************/
    private static int validaServicio(){
        int tipo; // 1 => Horoscopo, 2 => Calendario, -1 => Error

        if (tipoServicio.equalsIgnoreCase("H"))
            tipo=1;
        else if (tipoServicio.equalsIgnoreCase("C"))
            tipo=2;
        else
            tipo=-1;

        return  tipo ;
    }

    /********************************************************
     validaServicioDoble() => Valdia que sea correcto un servicio doble
     *********************************************************/
    private static boolean validaServicioDoble(){
        return tipoServicio.equalsIgnoreCase("CH") || tipoServicio.equalsIgnoreCase("HC");
    }

    /********************************************************
     getFecha() => Obtiene el "día" o "día y mes" actual, según el servicio
     *********************************************************/
    @SuppressLint("SimpleDateFormat")
    private static String getFecha(String servicio){
        String estiloDeFormato="ddMM"; // por default el formato será día y mes

        if (servicio.equalsIgnoreCase("calendario")) // si es true devoldera formato del día
            estiloDeFormato = "dd";

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat formato = new SimpleDateFormat(estiloDeFormato);
        formato.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));

        return formato.format(date);
    }

    /********************************************************
     validaFecha() => valida el día y mes
     *********************************************************/
    private static boolean validaFecha(String fecha){
        String [] diaMes = separa_Dia_y_Mes(fecha);
        return validaFecha_Dia(diaMes[0],diaMes[1]) && validaFecha_Mes(diaMes[1]);
    }

    /********************************************************
     validaFecha_Dia() => valida el día según el mes
     *********************************************************/
    private static boolean validaFecha_Dia(String día,String mes){
        int numdia;
        int numMes ;
        try{
            numdia = Integer.parseInt(día);
            numMes = Integer.parseInt(mes);
        }catch (Exception e){
            return false;
        }

        switch (numMes){
            case 4: case 6: case 9: case 11: //Meses con 30 días
                return numdia >= 1 && numdia <= 30;
            case 2: //Mese de febrero
                return numdia >= 1 && numdia <= 29;
            case 1: case 3: case 5: case 7: case 8: case 10: case 12: //Meses con 31 días
                return numdia >= 1 && numdia <= 31;
            default:
                return false;
        }
    }

    /********************************************************
     validaFecha_Mes() => valida el mes
     *********************************************************/
    private static boolean validaFecha_Mes(String mes){
        int numMes;
        try{
             numMes = Integer.parseInt(mes);
        }catch (Exception e){
            return false;
        }
        return numMes >= 1 && numMes <=12;
    }


    /********************************************************
     separa_Dia_y_Mes() => ej. 3006 => [30,06]
     *********************************************************/
    private static  String [] separa_Dia_y_Mes(String fecha){
        //Estructura [día,mes] ej. [30,06]
        return  new String [] {fecha.substring(0,2),fecha.substring(2)};
    }

    /********************************************************
     separa_Servicio_y_Fecha() => Separa servicio y fecha del SMS
     *********************************************************/
    private static String [] separa_Servicio_y_Fecha (){
        //Estructura [servicio,fecha] ej. [H,3006]
        return  new String [] {tipoServicio.substring(0,1) , tipoServicio.substring(1)};
    }

    /********************************************************
     separa_Servicio_y_FechaDoble() => Separa servicio y fecha del SMS
     *********************************************************/
    private static String [] separa_Servicio_y_FechaDoble (){
        //Estructura [servicio,fechaH,FechaC] ej. [HC,3006,30]
        return  new String [] {tipoServicio.substring(0,2) , tipoServicio.substring(2),tipoServicio.substring(2,4)};
    }


    /**########################################################################*
     *                      Métodos para devolver servicio                     *
     * ########################################################################*/


    private static String servicioHoroscopo(String fecha){
        return "\n" +
                "Este es su Horoscopo Azteca: " +
                "\n" +
                consultar.consultaHoroscopo(fecha);
    }

    private static String servicioCalendario(String fecha){
        return "\n" +
                "Este es su Calendario Azteca: " +
                "\n" +consultar.consultaCalendario(fecha);
    }

    private static String servicioHorosCalend(String fechaHoros, String fechaCalend) {
        return servicioHoroscopo(fechaHoros)  + servicioCalendario(fechaCalend) ;
    }


    /**########################################################################*
     *                          Métodos para enviar SMS                        *
     * ########################################################################*/

    private static void enviarErrorSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(tel,null, "El formato fue incorrecto, por favor " +
                "intentelo de nuevo.",null, null);
    }

    private static void enviaSMS(String mens){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(tel,null, mens,null, null);

    }

    private static String  enviarError() {
        return "El formato fue incorrecto, por favor " + "intentelo de nuevo.";
    }
}