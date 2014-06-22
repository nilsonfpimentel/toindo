package br.com.toindoapk;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {
	
	private final static String TABLE = "usuario";
	
	private SQLiteDatabase database;
	private DatabaseHelper sqliteOpenHelper;

	public UsuarioDAO(Context context) {
		sqliteOpenHelper = new DatabaseHelper(context);
		// TODO Auto-generated constructor stub
	}
	
	public void open () throws SQLException {
		database = sqliteOpenHelper.getWritableDatabase ();
		 }

	public void close () {
		sqliteOpenHelper.close ();
		 }
	
	
	//INSERIR USUARIO NO BANCO_DE_DADOS
	public void insert(Usuario usuario) throws Exception {
		ContentValues values = new ContentValues();

		values.put("idFacebook", usuario.getIdFacebook());
		values.put("nome_usuario", usuario.getUserName());
		values.put("email", usuario.getUserEmail());
		
		database.insert(TABLE, null, values);			
	}
	
	public void update(Usuario usuario) throws Exception {
		ContentValues values = new ContentValues();
		
		values.put("idFacebook", usuario.getIdFacebook());
		values.put("nome_usuario", usuario.getUserName());
		values.put("email", usuario.getUserEmail());
		
		database.update(TABLE, values, "id = ?", new String[] { "" + usuario.getUserId() });
		//getDatabase().update(TABLE, values, "id = ?", new String[] { "" + usuario.getUserId() });
	}
	
	public Usuario findById(Integer id) {

		String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
		String[] selectionArgs = new String[] { "" + id };
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		//Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
		cursor.moveToFirst();

		return montaUsuario(cursor);
	}
	public Usuario montaUsuario(Cursor cursor) {
		if (cursor.getCount() == 0) {
			return null;
		}
		Integer id = cursor.getInt(cursor.getColumnIndex("_id"));
	
		String idFacebook = cursor.getString(cursor.getColumnIndex("idFabebook"));
		String nomeUsuario = cursor.getString(cursor.getColumnIndex("nome_usuario"));
		String email = cursor.getString(cursor.getColumnIndex("email"));
		
		Usuario usuario = new Usuario();
		usuario.setUserId(id.toString());
		usuario.setIdFacebook(idFacebook);
		usuario.setUserName(nomeUsuario);
		usuario.setUserEmail(email);
		return usuario;
		//return new Usuario(id, idFacebook, nomeUsuario,email);

	}
	
	public List<Usuario> findAll() throws Exception {
		List<Usuario> retorno = new ArrayList<Usuario>();
		String sql = "SELECT * FROM " + TABLE;
		Cursor cursor = database.rawQuery(sql, null);
		//Cursor cursor = getDatabase().rawQuery(sql, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			retorno.add(montaUsuario(cursor));
			cursor.moveToNext();
		}
		return retorno;
	}


}
