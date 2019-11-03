package ec.edu.uce.database;

import android.provider.BaseColumns;

public final class Tables {

    private Tables() {
    }

    public static class USUARIO implements BaseColumns {
        public static final String TABLE_NAME = "usuario";
        public static final String COL_USERNAME = "nombre";
        public static final String COL_PASSWORD = "telefono";
    }

    public static class VEHICULO implements BaseColumns {
        public static final String TABLE_NAME = "vehiculo";
        public static final String COL_PLACA = "placa";
        public static final String COL_MARCA = "marca";
        public static final String COL_FECHA = "fechaFabricacion";
        public static final String COL_COSTO = "costo";
        public static final String COL_MATRICULADO = "matriculado";
        public static final String COL_COLOR = "color";
    }

    protected static final String SQL_CREATE_USUARIO =
            "CREATE TABLE " + USUARIO.TABLE_NAME
                    + " ("
                    + USUARIO.COL_USERNAME + " TEXT PRIMARY KEY, "
                    + USUARIO.COL_PASSWORD + " TEXT"
                    + ")";;

    protected static final String SQL_DELETE_USUARIO =
            "DROP TABLE IF EXISTS " + USUARIO.TABLE_NAME;

    protected static final String SQL_CREATE_VEHICULO =
            "CREATE TABLE " + VEHICULO.TABLE_NAME
                    + " ("
                    + VEHICULO.COL_PLACA + " TEXT PRIMARY KEY, "
                    + VEHICULO.COL_MARCA + " TEXT, "
                    + VEHICULO.COL_FECHA + " TEXT, "
                    + VEHICULO.COL_COSTO + " REAL, "
                    + VEHICULO.COL_MATRICULADO + " TEXT, "
                    + VEHICULO.COL_COLOR + " INTEGER"
                    + ")";

    protected static final String SQL_DELETE_VEHICULO =
            "DROP TABLE IF EXISTS " + VEHICULO.TABLE_NAME;

}
