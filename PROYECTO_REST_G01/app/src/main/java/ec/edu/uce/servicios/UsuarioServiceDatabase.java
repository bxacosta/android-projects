package ec.edu.uce.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ec.edu.uce.componentes.CustomException;
import ec.edu.uce.database.DatabaseHelper;
import ec.edu.uce.database.Tables;
import ec.edu.uce.modelo.Usuario;

public class UsuarioServiceDatabase implements UsuarioService {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public UsuarioServiceDatabase(Context context) {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getReadableDatabase();
    }

    @Override
    public Usuario save(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(Tables.USUARIO.COL_USERNAME, usuario.getUsername());
        values.put(Tables.USUARIO.COL_PASSWORD, usuario.getPassword());

        long newRowId = db.insert(Tables.USUARIO.TABLE_NAME, null, values);

        if (newRowId != -1) {
            return usuario;
        } else {
            throw new CustomException("Error al guadar el usuario");
        }
    }

    @Override
    public Usuario findByUsername(String username) {
        String selection = Tables.USUARIO.COL_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                Tables.USUARIO.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Usuario usuario = new Usuario();
        if (cursor.moveToNext()) {
            usuario.setUsername(cursor.getString(cursor.getColumnIndex(Tables.USUARIO.COL_USERNAME)));
            usuario.setPassword(cursor.getString(cursor.getColumnIndex(Tables.USUARIO.COL_PASSWORD)));
            return usuario;
        }
        cursor.close();
        return usuario;
    }

    @Override
    public boolean login(String username, String password) {
        Usuario usuario = findByUsername(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
