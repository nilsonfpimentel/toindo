package br.com.toindoapk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private SQLiteDatabase database;
	private static final String DATABASE_NAME = "toindo.db";
	private final static int VERSAO = 1;
	
	// TABELA USUARIO

	public static final String TABLE_NAME_USUARIO = "usuario";
	public static final String ID_USUARIO = "_id";
	public static final String ID_FACEBOOK = "idFacebook";
	public static final String NOME_USUARIO = "nome_usuario";
	public static final String EMAIL_USUARIO = "email";
	public static final String SQL_SCRIPT_USUARIO = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_USUARIO
			+ "( "
			+ ID_USUARIO
			+ " INTEGER PRIMARY KEY, "
			+  ID_FACEBOOK
			+ " VARCHAR (55) NOT NULL, "
			+ NOME_USUARIO
			+ " VARCHAR (15) NOT NULL, "
			+ EMAIL_USUARIO
			+ " VARCHAR (8) NOT NULL);";

	// TABELA ESTABELECIMENTO

	public static final String TABLE_NAME_ESTABELECIMENTO = "parceiro";
	public static final String ID_ESTABELECIMENTO = "_id";
	public static final String ID_FACEBOOK_ESTABELECIMENTO = "idFacebook";
	public static final String NOME_ESTABELECIMENTO = "nome_estabelecimento";
	public static final String EMAIL_ESTABELECIMENTO = "email";
	public static final String LOGRADOURO = "logradouro";
	public static final String NUMERO = "numero";
	public static final String BAIRRO = "bairro";
	public static final String CIDADE = "cidade";
	public static final String ESTADO = "estado";
	public static final String CEP_ESTABELECIMENTO = "cep";		
	public static final String WEB_SITE = "website";	
	public static final String TELEFONE1 = "telefone1";
	public static final String TELEFONE2 = "telefone2";
	public static final String COORDENADAS = "coordenadas";

	public static final String SQL_SCRIPT_ESTABELECIMENTO = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME_ESTABELECIMENTO + "( "
			+ ID_ESTABELECIMENTO + " INTEGER PRIMARY KEY, "
			+ ID_FACEBOOK_ESTABELECIMENTO + " VARCHAR (55) NOT NULL, "
			+ NOME_ESTABELECIMENTO + " VARCHAR (55) NOT NULL, "	
			+ EMAIL_ESTABELECIMENTO + " VARCHAR (55) NOT NULL, "
			+ LOGRADOURO + " VARCHAR (120) NOT NULL, "
			+ NUMERO + " INT (8), "
			+ BAIRRO + " VARCHAR (30) NOT NULL, "
			+ CIDADE + " VARCHAR (30) NOT NULL, "
			+ ESTADO + " VARCHAR (2) NOT NULL, "
			+ CEP_ESTABELECIMENTO + " VARCHAR (8), "			
			+ WEB_SITE + " VARCHAR (55), "
			+ TELEFONE1 + " VARCHAR (10), "
			+ TELEFONE2 + " VARCHAR (10), " 
			+ COORDENADAS + " BLOB"+ ");";
	
	// TABELA PROMOCOES
	
	public static final String TABLE_NAME_PROMOCAO="promocao";
	public static final String ID_PROMOCAO ="_id";
	public static final String ESTABELECIMENTO="estabelecimento";
	public static final String DATA_VALIDADE= "data_validade";
	public static final String PRECO_ANTERIOR="preco_anterior";
	public static final String PRECO_PROMOCIONAL ="preco_promocional";	
	public static final String DESCRICAO="descricao";
	public static final String IMAGEM="imagem";
	
	
	public static final String SQL_SCRIPT_PROMOCOES= "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_PROMOCAO +"( "
			+ ID_PROMOCAO +" INTEGER PRIMARY KEY, "
			+ ESTABELECIMENTO + " INTEGER,"
			+ DATA_VALIDADE + " VARCHAR(10) NOT NULL, "
			+ PRECO_ANTERIOR + " REAL, "
			+ PRECO_PROMOCIONAL + " REAL, "
			+ DESCRICAO + " VARCHAR(100), "
			+ IMAGEM + " BLOB, "
			+ "FOREIGN KEY"+"("+ ESTABELECIMENTO +")"+" REFERENCES "+ TABLE_NAME_ESTABELECIMENTO +"("+ID_ESTABELECIMENTO+"));";
	
	
	//TABELA HISTORICO
	public static final String TABLE_NAME_HISTORICO = "historico";
	public static final String ID_HISTORICO = "_id";
	public static final String ESTABELECIMENTO_FK= "estabelecimento";
	public static final String PROMOCAO_FK = "promocao";
	public static final String DATA = "data";
	
	public static final String SQL_SCRIPT_HISTORICO= "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_HISTORICO +"( "
			+ ID_HISTORICO +" INTEGER PRIMARY KEY, "
			+ ESTABELECIMENTO_FK + " INTEGER,"
			+ PROMOCAO_FK + " INTEGER,"
			+ DATA + " VARCHAR(10) NOT NULL,"
			+ "FOREIGN KEY"+"("+ESTABELECIMENTO_FK+")"+" REFERENCES "+TABLE_NAME_ESTABELECIMENTO+"("+ID_ESTABELECIMENTO+"),"
				+ "FOREIGN KEY"+"("+ PROMOCAO_FK+")"+" REFERENCES "+ TABLE_NAME_PROMOCAO +"("+ID_PROMOCAO+"));";
	
	
	

	public DatabaseHelper(Context context) {
		super(context,DATABASE_NAME, null, VERSAO);
		// TODO Auto-generated constructor stub
	}

	public void onCreate(SQLiteDatabase db) {
				
		db.execSQL(SQL_SCRIPT_USUARIO);
		
		db.execSQL(SQL_SCRIPT_ESTABELECIMENTO);
		
		db.execSQL(SQL_SCRIPT_PROMOCOES);
		
		db.execSQL( SQL_SCRIPT_HISTORICO);
		
		// TODO Auto-generated method stub

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + "to" + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USUARIO);
		onCreate(db);

		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + "to" + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ESTABELECIMENTO);
		onCreate(db);
		
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + "to" + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PROMOCAO);
		onCreate(db);
		
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + "to" + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_HISTORICO);
		onCreate(db);
		// TODO Auto-generated method stub

	}
	
	
	//ERA UTILIZADO NA CLASSE UsuarioDAO QUANDO EXTENDIA DE DatabaseHelper
	public SQLiteDatabase getDatabase() {
		if (database == null) {
		    database = getWritableDatabase();
		}
		return database;
	    }

}
