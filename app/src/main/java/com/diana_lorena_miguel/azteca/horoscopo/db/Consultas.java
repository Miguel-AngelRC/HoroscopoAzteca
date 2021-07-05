package com.diana_lorena_miguel.azteca.horoscopo.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class Consultas {

    //Clase para realizar las consultas

    private DbHelper dbHelper;
    private SQLiteDatabase db;


    /********************************************************
     Consultas() => Constructor para crear base de datos
     *********************************************************/
    public Consultas (Context contexto){
        dbHelper = new DbHelper(contexto); //Instancia para crear base de datos
        db = dbHelper.getWritableDatabase(); //Obtener la base de datos

        if(db != null){
            Toast.makeText(contexto, "BASE DE DATOS CREADA Y POBLADA",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(contexto, "ERROR AL CREAR LA BASE DE DATOS",Toast.LENGTH_LONG).show();
        }
    }

    /********************************************************
     consultaHoroscopo() => Recupera los datos de Horoscopo
     *********************************************************/
    public String consultaHoroscopo (String fechaBusqueda){
        /** Datos a recuperar*/
        String nombre;
        String descripcion;
        String planeta;
        String divinidad;
        String mensaje ="";

        //bandera para horoscopo doble
        boolean bandera = dobleSigno(fechaBusqueda);
        /**Consulta*/

        Cursor consulta = db.rawQuery(
                "SELECT idDia,nombre,descripcion,planeta,divinidad " +
                        "FROM horoscopoFechas  inner join horoscopoDatos on horoscopoFechas.claveSigno = horoscopoDatos.claveSigno " +
                        "   where " + fechaBusqueda + " = horoscopoFechas.idDia", null);

        try {
            consulta.moveToFirst();

            nombre = consulta.getString(consulta.getColumnIndex("nombre"));
            descripcion = consulta.getString(consulta.getColumnIndex("descripcion"));
            planeta = consulta.getString(consulta.getColumnIndex("planeta"));
            divinidad = consulta.getString(consulta.getColumnIndex("divinidad"));

        }catch (Exception e){
            return "No hay Horoscopo para esta fecha";
        }

        mensaje +=   nombre + "\n" +
                     descripcion + "\n" +
                    "Planeta: " + planeta + "\n" +
                    "Divinidad: " + divinidad + "\n";


        /**Buscar el segundo signo si es el caso*/
        if (bandera) {
            fechaBusqueda+="2";

            consulta = db.rawQuery(
                    "SELECT idDia,nombre,descripcion,planeta,divinidad " +
                            "FROM horoscopoFechas  inner join horoscopoDatos on horoscopoFechas.claveSigno = horoscopoDatos.claveSigno " +
                            "   where " + fechaBusqueda + " = horoscopoFechas.idDia", null);

            try {
                consulta.moveToFirst();

                nombre = consulta.getString(consulta.getColumnIndex("nombre"));
                descripcion = consulta.getString(consulta.getColumnIndex("descripcion"));
                planeta = consulta.getString(consulta.getColumnIndex("planeta"));
                divinidad = consulta.getString(consulta.getColumnIndex("divinidad"));
            }catch (Exception e){
                return "No hay Horoscopo para esta fecha";
            }

            mensaje += "\nSu segundo signo es: "+
                        nombre + "\n" +
                        descripcion + "\n" +
                        "Planeta: " + planeta + "\n" +
                        "Divinidad: " + divinidad + "\n";
        }


        return mensaje;
    }

    public boolean dobleSigno (String fecha){
        return  fecha.equals("1801") || fecha.equals("0904") || fecha.equals("1810");
    }

    /********************************************************
     consultaCalendario() => Recupera los datos de Calendario
     *********************************************************/
    public String consultaCalendario (String fechaBusqueda){
        /** Datos a recuperar*/
        int id;
        String nombre;
        String dios;
        String frase;
        String representa;

        /**Consulta*/

        Cursor consulta = db.rawQuery(
                "SELECT id,nombre,dios,frase,representa " +
                        "FROM calendarioFechas  inner join calendarioDatos on calendarioFechas.idGrado = calendarioDatos.idGrado " +
                        "   where "+fechaBusqueda+" = calendarioFechas.id", null);

        try{
            consulta.moveToFirst();
            // do {
            id = consulta.getInt(consulta.getColumnIndex("id"));
            nombre = consulta.getString(consulta.getColumnIndex("nombre"));
            dios = consulta.getString(consulta.getColumnIndex("dios"));
            frase = consulta.getString(consulta.getColumnIndex("frase"));
            representa = consulta.getString(consulta.getColumnIndex("representa"));
            //}while(consulta.moveToNext());
        }catch (Exception e){
            return "No hay Calendario para esta fecha";
        }

        String mensaje =     nombre + "\n" +
                            "Dios regente: " + dios + "\n" +
                             frase + "\n" +
                             representa + "\n";

        return mensaje;
    }
}

