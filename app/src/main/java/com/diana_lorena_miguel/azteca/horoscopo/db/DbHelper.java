package com.diana_lorena_miguel.azteca.horoscopo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    /**En esta clase se contiene tod0 lo necesario para crear, poblar y consultar la base de datos
     * que se ocupara para almacenar la información.
     * */


    private static final int DATABASE_VERSION = 1;//version de la base de datos
    private static final String DATABASE_NOMBRE = "calendarioAzteca.db";//nombre de la base de datos
    private static final String TABLE_calendarioFechas = "calendarioFechas";//nombre de la tabla calendarioFechas
    private static final String TABLE_calendarioDatos = "calendarioDatos";//nombre de la tabla calendarioDatos
    private static final String TABLE_horoscopoFechas = "horoscopoFechas";//nombre de la tabla horoscopoFechas
    private static final String TABLE_horoscopoDatos = "horoscopoDatos";//nombre de la tabla horoscopoDatos


    /********************************************************
     DbHelper() => Contrustor para crear la BD
     *********************************************************/
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }


    /********************************************************
     onCreate() => Método para iniciar la creación y poblacioón de las tablas
     *********************************************************/
    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablasCalendario(db);
        crearTablasHoroscopo(db);

        insertarDatosCalendario(db);
        insertarDatosHoroscopo(db);
    }

    /********************************************************
     crearTablasCalendario() => Crea las tablas para el Calendario
     *********************************************************/
    private  void  crearTablasCalendario(SQLiteDatabase db){
        //Tabla con para guardar los datos del calendario
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_calendarioDatos + "(" +
                "idGrado INTEGER NOT NULL PRIMARY KEY," +
                "nombre TEXT NOT NULL," +
                "dios TEXT NOT NULL," +
                "frase TEXT NOT NULL," +
                "representa TEXT NOT NULL )");

        //Tabla con para correlacionar la fecha con su información del calendario
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_calendarioFechas + "(" +
                "id INTEGER NOT NULL PRIMARY KEY," +
                "idGrado INTEGER NOT NULL REFERENCES "+TABLE_calendarioDatos+" (idGrado))");
    }

    /********************************************************
     crearTablasHoroscopo() => Crea las tablas para el Horoscopo
     *********************************************************/
    private  void  crearTablasHoroscopo(SQLiteDatabase db){
        //Tabla con para guardar los datos del Horoscopo
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_horoscopoDatos + "(" +
                "claveSigno INTEGER NOT NULL PRIMARY KEY," +
                "nombre	TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "planeta TEXT NOT NULL," +
                "divinidad	TEXT NOT NULL)");

        //Tabla con para correlacionar la fecha con su información del Horoscopo
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_horoscopoFechas + "(" +
                "idDia INTEGER NOT NULL PRIMARY KEY," +
                "claveSigno INTEGER NOT NULL REFERENCES "+TABLE_horoscopoDatos+" (id))");
    }

    /********************************************************
     insertarDatosCalendario() => Insertar datos para el Calendario
     *********************************************************/
    private  void  insertarDatosCalendario(SQLiteDatabase db){
        //información del calendario
        db.execSQL("INSERT INTO " + TABLE_calendarioDatos + "(idGrado, nombre, dios, frase, representa) VALUES " +
                "(1,'Cocodrilo', 'Tonacatecuhtli', 'Señor que nos da el sustento', 'Representa al dios anciano, que es la deificación de la tierra.')" +
                "," + "(2,'Viento','Ehécatl','Viento','Representa a Dios Viento Creador, que es la deificación de la vida.')" +
                "," + "(3,'Casa','Tepeyolotl','Corazón del monte','Representa a Dios Jaguar, que es la deificación del Inframundo o Mundo de los Muertos.')" +
                "," + "(4,'Lagartija','Huehuecóyotl','Coyote viejo','Representa a Dios Venado, que es la deificación del Cielo.')" +
                "," + "(5,'Serpiente','Chalchiuhtlicue','La de la falda de jades','Representa a Diosa Serpiente, que es la deificación del mar.')" +
                "," + "(6,'Cráneo','Texitécatl-Mextli','La Luna Oscura','Representa a Diosa Oscuridad, que es la Luna antes de la creación del mundo.')" +
                "," + "(7,'Venado','Tláloc','Néctar de la Tierra','Representa a Dios Lluvia, que es la deificación de la lluvia.')" +
                "," + "(8,'Conejo','Mayahuel','El maguey que rodea (a la tierra)','Representa a Diosa Maguey, que es la deificación del Supramundo o Mundo de los Vivos.')" +
                "," + "(9,'Agua','Xiuhtecuhtli','Señor Fuego','Representa a Dios Fuego, que es la deificación del fuego.')" +
                "," + "(10,'Perro','Mictlantecuhtli','El señor de los descarnados','Representa a Dios Muerte, que es la deificación de la muerte.')" +
                "," + "(11,'Mono','Xochipilli','Flor-Niño','Representa a Dios Florecimiento, que es la deificación de la Estación Florida, lo que ahora')" +
                "," + "(12,'Hierba','Patécatl','El que es medicinal','Representa a Dios Luna en su tránsito por el Mundo de los Vivos.')" +
                "," + "(13,'Caña','Tezcatlipoca','Reflejo Negro','Representa a Dios Reflejo Negro, que es la deificación del Cielo Diurno.')" +
                "," + "(14,'Jaguar','Tlazoltéotl','La devoradora de la oscuridad','Representa a Diosa Luna, que es la deificación de la Luna en su tránsito por el Mundo de los')" +
                "," + "(15,'Águila Arpía','Xipetótec','Dios Desollador','Representa a Dios Desollador, que es la deificación del Cielo Nocturno.')" +
                "," + "(16,'Zopilote','Itzpápalotl','Mariposa de Obsidiana','Representa a Dios Mariposa de Obsidiana, que es la deificación de la Estación de Sacrificio, lo que ahora conocemos como otoño e invierno y que antiguamente formaban un solo temporal.')" +
                "," + "(17,'Movimiento','Nanahuatzin','El que es cuidado por su madre','Representa a Dios Enfermo, que es la deificación del Sol antes de la creación del mundo.')" +
                "," + "(18,'Pedernal','Chalchiuhtotolin','Guajolote Precioso','Representa a Dios Guajolote, que es la deificación del Sol en su tránsito por el Mundo de los Muertos.')" +
                "," + "(19,'Lluvia','Tonatiuh','El que nos alumbra','Representa a Dios Sol, que es la deificación del Sol.')" +
                "," + "(20,'Flor','Xochiquetzal','Flor Preciosa o Flor-Quetzal','Representa a Diosa Quetzal que es la deificación de la madre del Sol o el fuego en su versión femenina.');"
        );

        //fechas del calendario y su relación con su información
        db.execSQL("INSERT INTO " + TABLE_calendarioFechas + "(id, idGrado) VALUES (1,1)" +","+"(2,2)" +","+ "(3,3)" +","+ "(4,4)" +","+ "(5,5)" +","+ "(6,6)" +","+ "(7,7)" +","+ "(8,8)" +","+ "(9,9)" +","+ "(10,10)" +","+ "(11,11)" +","+ "(12,12)" +","+ "(13,13)" +","+ "(14,14)" +","+ "(15,15)" +
                ","+"(16,16)" +","+ "(17,17)" +","+ "(18,18)" +","+ "(19,19)" +","+ "(20,20)" +","+ "(21,1)" +","+ "(22,2)" +","+ "(23,3)" +","+ "(24,4)" +","+ "(25,5)" +","+ "(26,6)" +","+ "(27,7)" +","+ "(28,8)" +","+ "(29,9)" +","+ "(30,10)" +","+ "(31,11);");


    }


    /********************************************************
     insertarDatosHoroscopo() => Insertar datos para el Horoscopo
     *********************************************************/
    private  void  insertarDatosHoroscopo(SQLiteDatabase db){
        //información del Horoscopo
        db.execSQL("INSERT INTO " + TABLE_horoscopoDatos + "(claveSigno, nombre, descripcion, planeta, divinidad) VALUES " +
                "(1,'CAIMÁN O COCODRILO (CIPACTLI)','El caimán simboliza el conocimiento y es el punto inicial del calendario azteca. Además, es un signo que venera la lógica y el buen sentido.','Venus','Tonacatecuhtli')" +
                "," + "(2,'CASA (CALLI)','La casa representa la generosidad y se relaciona con el mundo interior, el hogar, la mujer, entre otros elementos. Las personas que nacieron bajo este signo rara vez se encuentran solas y disfrutan la vida en familia.','Venus','Tepeyolohti')" +
                "," + "(3,'FLOR (XOCHITI)','Las personas que nacieron bajo el signo de la flor se caracterizan por su fuerte sentido de la intuición y sensibilidad.','Venus','Xochiquetzal')" +
                "," + "(4,'SERPIENTE (CÓATL)','Las serpientes son personas luchadoras, intrépidas, valientes y combativas, además de líderes natos que persiguen sus objetivos.','Saturno','Chalchiúhtlicue')" +
                "," + "(5,'JAGUAR (OCÉLOTL)','Los jaguares se distinguen por su nobleza, energía, aunque también por su carácter fuerte. Además, son ambiciosos y orgullosos.','Júpiter','Tlazoltéotl')" +
                "," + "(6,'CAÑA (ACATL)','Los del signo de la caña son personas que adoran las contradicciones y que son muy polifacéticas. Se adaptan a cualquier lugar y son muy encantadoras.','Júpiter','Tezcatlipoca')" +
                "," + "(7,'CONEJO (TOCHTLI)','Les gustan los placeres de la vida y son muy bondadosos, sociables, cariñosos y familiares. Se caracterizan también por sufrir en exceso por las emociones.','La Luna','Mayáhuel')" +
                "," + "(8,'ÁGUILA (CUAYHTLI)','Nacieron para ser líderes, son personas orgullosas, fuertes, valientes, autoritarias y dinámicas. Se sienten atraídos por retos que les permitan demostrar de qué están hechos.','El Sol','Xipe Totec')" +
                "," + "(9,'MONO (OZOMATLI)','Son personas idealistas que nunca pierden la esperanza de conseguir sus objetivos a pesar de los obstáculos de la vida. Aprecia vivir bien y disfrutar de los placeres de la vida.','Marte','Xochipilli')" +
                "," + "(10,'SÍLEX (TÉCPATL)','La impulsividad y la ambición son las claves de este signo porque gozan de gran fuerza de voluntad. Al mismo tiempo, son personas de gran imaginación y creatividad.','Mercurio','Tezcatlipoca')" +
                "," + "(11,'PERRO (ITZCUINTLI)','Las personas que nacieron bajo este signo son analíticas y amantes del orden. Son humildes, pero a la vez ambiciosos porque no se conforman con lo que tienen.','Marte','Mictlantecuhtli')" +
                "," + "(12,'CIERVO (MÁZATL)','Transmite confianza, dulzura y parecen ser extrovertidos, pero internamente saben que solo se sienten bien con un grupo reducido de personas. Es un signo pacífico que detesta los problemas.','Sin información','Sin información')");

        //fechas del Horoscopo y su relación con su información
        db.execSQL("INSERT INTO " + TABLE_horoscopoFechas + "(idDia, claveSigno) VALUES (0401, 1)" + "," + "(1601, 1)" + "," + "(18012, 1)" + "," + "(0202, 1)" + "," + "(1003, 1)" + "," + "(2203, 1)" + "," + "(0304, 1)" + "," + "(1504, 1)" + "," +
                "(2704, 1)" + "," + "(0905, 1)" + "," + "(2105, 1)" + "," + "(0206, 1)" + "," + "(1406, 1)" + "," + "(2606, 1)" + "," + "(0807, 1)" + "," + "(2007, 1)" + "," + "(0108, 1)" + "," + "(1308, 1)" + "," + "(2508, 1)" + "," + "(0609, 1)" + "," +
                "(1809, 1)" + "," + "(3009, 1)" + "," + "(1210, 1)" + "," + "(2410, 1)" + "," + "(0511, 1)" + "," + "(1711, 1)" + "," + "(2911, 1)" + "," + "(1112, 1)" + "," + "(2312, 1)" + "," + "(0501, 2)" + "," + "(1701, 2)" + "," + "(2901, 2)" + "," +
                "(0302, 2)" + "," + "(1502, 2)" + "," + "(2702, 2)" + "," + "(1103, 2)" + "," + "(2303, 2)" + "," + "(0404, 2)" + "," + "(1604, 2)" + "," + "(2804, 2)" + "," + "(1005, 2)" + "," + "(2205, 2)" + "," + "(0306, 2)" + "," + "(1506, 2)" + "," +
                "(2706, 2)" + "," + "(0907, 2)" + "," + "(2107, 2)" + "," + "(0208, 2)" + "," + "(1408, 2)" + "," + "(2608, 2)" + "," + "(0709, 2)" + "," + "(1909, 2)" + "," + "(0110, 2)" + "," + "(1310, 2)" + "," + "(2510, 2)" + "," + "(0611, 2)" + "," +
                "(1811, 2)" + "," + "(3011, 2)" + "," + "(1212, 2)" + "," + "(2412, 2)" + "," + "(0601, 3)" + "," + "(1801, 3)" + "," + "(3001, 3)" + "," + "(0402, 3)" + "," + "(1602, 3)" + "," + "(2802, 3)" + "," + "(1203, 3)" + "," + "(2403, 3)" + "," +
                "(0504, 3)" + "," + "(1704, 3)" + "," + "(2904, 3)" + "," + "(1105, 3)" + "," + "(2305, 3)" + "," + "(0406, 3)" + "," + "(1606, 3)" + "," + "(2806, 3)" + "," + "(1007, 3)" + "," + "(2207, 3)" + "," + "(0308, 3)" + "," + "(1508, 3)" + "," +
                "(2708, 3)" + "," + "(0809, 3)" + "," + "(2009, 3)" + "," + "(0210, 3)" + "," + "(1410, 3)" + "," + "(2610, 3)" + "," + "(0711, 3)" + "," + "(1911, 3)" + "," + "(0112, 3)" + "," + "(1312, 3)" + "," + "(2512, 3)" + "," + "(0701, 4)" + "," +
                "(1901, 4)" + "," + "(3101, 4)" + "," + "(0502, 4)" + "," + "(1702, 4)" + "," + "(0103, 4)" + "," + "(1303, 4)" + "," + "(2503, 4)" + "," + "(0604, 4)" + "," + "(1804, 4)" + "," + "(3004, 4)" + "," + "(1205, 4)" + "," + "(2405, 4)" + "," +
                "(0506, 4)" + "," + "(1706, 4)" + "," + "(2906, 4)" + "," + "(1107, 4)" + "," + "(2307, 4)" + "," + "(0408, 4)" + "," + "(1608, 4)" + "," + "(2808, 4)" + "," + "(0909, 4)" + "," + "(2109, 4)" + "," + "(0310, 4)" + "," + "(1510, 4)" + "," +
                "(2710, 4)" + "," + "(0811, 4)" + "," + "(2011, 4)" + "," + "(0212, 4)" + "," + "(1412, 4)" + "," + "(2612, 4)" + "," + "(0901, 5)" + "," + "(2101, 5)" + "," + "(0702, 5)" + "," + "(1902, 5)" + "," + "(0303, 5)" + "," + "(1503, 5)" + "," +
                "(2703, 5)" + "," + "(0804, 5)" + "," + "(2004, 5)" + "," + "(0205, 5)" + "," + "(1405, 5)" + "," + "(2605, 5)" + "," + "(0706, 5)" + "," + "(1906, 5)" + "," + "(0107, 5)" + "," + "(1307, 5)" + "," + "(2507, 5)" + "," + "(0608, 5)" + "," +
                "(1808, 5)" + "," + "(3008, 5)" + "," + "(1109, 5)" + "," + "(2309, 5)" + "," + "(0510, 5)" + "," + "(1710, 5)" + "," + "(2910, 5)" + "," + "(1011, 5)" + "," + "(2211, 5)" + "," + "(0412, 5)" + "," + "(1612, 5)" + "," + "(2812, 5)" + "," +
                "(1001, 6)" + "," + "(2201, 6)" + "," + "(0802, 6)" + "," + "(2002, 6)" + "," + "(0403, 6)" + "," + "(1603, 6)" + "," + "(2803, 6)" + "," + "(09042, 6)" + "," + "(2104, 6)" + "," + "(0305, 6)" + "," + "(1505, 6)" + "," + "(2705, 6)" + "," +
                "(0806, 6)" + "," + "(2006, 6)" + "," + "(0207, 6)" + "," + "(1407, 6)" + "," + "(2607, 6)" + "," + "(0708, 6)" + "," + "(1908, 6)" + "," + "(3108, 6)" + "," + "(1209, 6)" + "," + "(2409, 6)" + "," + "(0610, 6)" + "," + "(18102, 6)" + "," +
                "(3010, 6)" + "," + "(1111, 6)" + "," + "(2311, 6)" + "," + "(0512, 6)" + "," + "(1712, 6)" + "," + "(2912, 6)" + "," + "(1101, 7)" + "," + "(2301, 7)" + "," + "(0902, 7)" + "," + "(2102, 7)" + "," + "(0503, 7)" + "," + "(1703, 7)" + "," +
                "(2903, 7)" + "," + "(1004, 7)" + "," + "(2204, 7)" + "," + "(0405, 7)" + "," + "(1605, 7)" + "," + "(2805, 7)" + "," + "(0906, 7)" + "," + "(2106, 7)" + "," + "(0307, 7)" + "," + "(1507, 7)" + "," + "(2707, 7)" + "," + "(0808, 7)" + "," +
                "(2008, 7)" + "," + "(0109, 7)" + "," + "(1309, 7)" + "," + "(2509, 7)" + "," + "(0710, 7)" + "," + "(1810, 7)" + "," + "(1910, 7)" + "," + "(3110, 7)" + "," + "(1211, 7)" + "," + "(2411, 7)" + "," + "(0612, 7)" + "," + "(1812, 7)" + "," +
                "(3012, 7)" + "," + "(1201, 8)" + "," + "(2401, 8)" + "," + "(1002, 8)" + "," + "(2202, 8)" + "," + "(0603, 8)" + "," + "(1803, 8)" + "," + "(3003, 8)" + "," + "(1104, 8)" + "," + "(2304, 8)" + "," + "(0505, 8)" + "," + "(1705, 8)" + "," +
                "(2905, 8)" + "," + "(1006, 8)" + "," + "(2206, 8)" + "," + "(0407, 8)" + "," + "(1607, 8)" + "," + "(2807, 8)" + "," + "(0908, 8)" + "," + "(2108, 8)" + "," + "(0209, 8)" + "," + "(1409, 8)" + "," + "(2609, 8)" + "," + "(0810, 8)" + "," +
                "(2010, 8)" + "," + "(0111, 8)" + "," + "(1311, 8)" + "," + "(2511, 8)" + "," + "(0712, 8)" + "," + "(1912, 8)" + "," + "(3112, 8)" + "," + "(0101, 9)" + "," + "(1301, 9)" + "," + "(2501, 9)" + "," + "(1102, 9)" + "," + "(2302, 9)" + "," +
                "(0703, 9)" + "," + "(1903, 9)" + "," + "(3103, 9)" + "," + "(1204, 9)" + "," + "(2404, 9)" + "," + "(0605, 9)" + "," + "(1805, 9)" + "," + "(3005, 9)" + "," + "(1106, 9)" + "," + "(2306, 9)" + "," + "(0507, 9)" + "," + "(1707, 9)" + "," +
                "(2907, 9)" + "," + "(1008, 9)" + "," + "(2208, 9)" + "," + "(0309, 9)" + "," + "(1509, 9)" + "," + "(2709, 9)" + "," + "(0910, 9)" + "," + "(2110, 9)" + "," + "(0211, 9)" + "," + "(1411, 9)" + "," + "(2611, 9)" + "," + "(0812, 9)" + "," +
                "(2012, 9)" + "," + "(0201, 10)" + "," + "(1401, 10)" + "," + "(2601, 10)" + "," + "(1202, 10)" + "," + "(2402, 10)" + "," + "(0803, 10)" + "," + "(2003, 10)" + "," + "(0104, 10)" + "," + "(1304, 10)" + "," + "(2504, 10)" + "," +
                "(0705, 10)" + "," + "(1905, 10)" + "," + "(3105, 10)" + "," + "(1206, 10)" + "," + "(2406, 10)" + "," + "(0607, 10)" + "," + "(1807, 10)" + "," + "(3007, 10)" + "," + "(1108, 10)" + "," + "(2308, 10)" + "," + "(0409, 10)" + "," +
                "(1609, 10)" + "," + "(2809, 10)" + "," + "(1010, 10)" + "," + "(2210, 10)" + "," + "(0311, 10)" + "," + "(1511, 10)" + "," + "(2711, 10)" + "," + "(0912, 10)" + "," + "(2112, 10)" + "," + "(0301, 11)" + "," + "(1501, 11)" + "," +
                "(2701, 11)" + "," + "(1302, 11)" + "," + "(2502, 11)" + "," + "(0903, 11)" + "," + "(2103, 11)" + "," + "(0204, 11)" + "," + "(1404, 11)" + "," + "(2604, 11)" + "," + "(0805, 11)" + "," + "(2005, 11)" + "," + "(0106, 11)" + "," +
                "(1306, 11)" + "," + "(2506, 11)" + "," + "(0707, 11)" + "," + "(1907, 11)" + "," + "(3107, 11)" + "," + "(1208, 11)" + "," + "(2408, 11)" + "," + "(0509, 11)" + "," + "(1709, 11)" + "," + "(2909, 11)" + "," + "(1110, 11)" + "," +
                "(2310, 11)" + "," + "(0411, 11)" + "," + "(1611, 11)" + "," + "(2811, 11)" + "," + "(1012, 11)" + "," + "(2212, 11)" + "," + "(0801, 12)" + "," + "(2001, 12)" + "," + "(0102, 12)" + "," + "(0602, 12)" + "," + "(1802, 12)" + "," +
                "(0203, 12)" + "," + "(1403, 12)" + "," + "(2603, 12)" + "," + "(0704, 12)" + "," + "(0904, 12)" + "," + "(1904, 12)" + "," + "(0105, 12)" + "," + "(1305, 12)" + "," + "(2505, 12)" + "," + "(0606, 12)" + "," + "(1806, 12)" + "," +
                "(3006, 12)" + "," + "(1207, 12)" + "," + "(2407, 12)" + "," + "(0508, 12)" + "," + "(1708, 12)" + "," + "(2908, 12)" + "," + "(1009, 12)" + "," + "(2209, 12)" + "," + "(0410, 12)" + "," + "(1610, 12)" + "," + "(2810, 12)" + "," +
                "(0911, 12)" + "," + "(2111, 12)" + "," + "(0312, 12)" + "," + "(1512, 12)" + "," + "(2712, 12)");
    }


    /********************************************************
     onUpgrade() => Actualizar la BD
     *********************************************************/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_calendarioDatos);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_calendarioFechas);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_horoscopoDatos);
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_horoscopoFechas);
        onCreate(db);
    }
}


