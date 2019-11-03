package ec.edu.uce.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.database.DatabaseHelper;
import ec.edu.uce.database.Tables;
import ec.edu.uce.modelo.Vehiculo;

public class VehiculoServiceDatabase implements VehiculoService {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public VehiculoServiceDatabase(Context context) {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getReadableDatabase();
    }

    @Override
    public Vehiculo save(Vehiculo vehiculo) {
        ContentValues values = new ContentValues();
        values.put(Tables.VEHICULO.COL_PLACA, vehiculo.getPlaca());
        values.put(Tables.VEHICULO.COL_MARCA, vehiculo.getMarca());
        values.put(Tables.VEHICULO.COL_COLOR, vehiculo.getColor());
        values.put(Tables.VEHICULO.COL_COSTO, vehiculo.getCosto());
        values.put(Tables.VEHICULO.COL_MATRICULADO, vehiculo.getMatriculado().toString());
        values.put(Tables.VEHICULO.COL_FECHA, converDateToString(vehiculo.getFechaFabricacion()));

        long newRowId = db.insert(Tables.VEHICULO.TABLE_NAME, null, values);

        if (newRowId != -1) {
            return vehiculo;
        } else {
            throw new CustomException("Error al guadar el vehiculo");
        }
    }

    @Override
    public List<Vehiculo> saveAll(List<Vehiculo> vehiculos) {
        for (Vehiculo vehiculo : vehiculos) {
            save(vehiculo);
        }
        return vehiculos;
    }

    @Override
    public List<Vehiculo> findAll() {
        Cursor cursor = db.query(
                Tables.VEHICULO.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        List<Vehiculo> vehiculos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setPlaca(cursor.getString(cursor.getColumnIndex(Tables.VEHICULO.COL_PLACA)));
            vehiculo.setMarca(cursor.getString(cursor.getColumnIndex(Tables.VEHICULO.COL_MARCA)));
            vehiculo.setColor(cursor.getString(cursor.getColumnIndex(Tables.VEHICULO.COL_COLOR)));
            vehiculo.setCosto(cursor.getDouble(cursor.getColumnIndex(Tables.VEHICULO.COL_COSTO)));
            vehiculo.setMatriculado(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(Tables.VEHICULO.COL_MATRICULADO))));
            vehiculo.setFechaFabricacion(convertStringToDate(cursor.getString(cursor.getColumnIndex(Tables.VEHICULO.COL_MATRICULADO))));

            vehiculos.add(vehiculo);
        }
        cursor.close();
        return vehiculos;
    }

    @Override
    public Vehiculo update(String placa, Vehiculo vehiculo) {
        ContentValues values = new ContentValues();
        values.put(Tables.VEHICULO.COL_MARCA, vehiculo.getMarca());
        values.put(Tables.VEHICULO.COL_COLOR, vehiculo.getColor());
        values.put(Tables.VEHICULO.COL_COSTO, vehiculo.getCosto());
        values.put(Tables.VEHICULO.COL_MATRICULADO, vehiculo.getMatriculado().toString());
        values.put(Tables.VEHICULO.COL_FECHA, converDateToString(vehiculo.getFechaFabricacion()));

        String selection = Tables.VEHICULO.COL_PLACA + " LIKE ?";
        String[] selectionArgs = {placa};

        int count = db.update(
                Tables.VEHICULO.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            vehiculo.setPlaca(placa);
            return vehiculo;
        } else {
            throw new CustomException("Error al actualizar el vehiculo");
        }
    }

    @Override
    public void delete(String placa) {
        String selection = Tables.VEHICULO.COL_PLACA + " LIKE ?";
        String[] selectionArgs = {placa};

        db.delete(Tables.VEHICULO.TABLE_NAME, selection, selectionArgs);
    }

    private String converDateToString(Date date) {
        return sdf.format(date);
    }

    private Date convertStringToDate(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
