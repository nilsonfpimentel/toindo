package br.com.toindoapk;


import com.facebook.widget.ProfilePictureView;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class CadastroActivity extends ActionBarActivity {
	
	protected static final String CATEGORIA = "MyApp_CadastroActivity";
	private Usuario usuario;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		
		usuario = new Usuario();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		//Recuperando os paramatros
		
		Intent intent = getIntent();		
		
		if (intent != null){
			Bundle params = intent.getExtras();
			
			if (params != null){
				Log.i(CATEGORIA,"parametros foram diferentes de null!");
				
				//String id = params.getString("user_id");
				usuario.setIdFacebook(params.getString("user_id"));
				Log.i(CATEGORIA, "ID na Tela Cadastro " + usuario.getIdFacebook());
								
				//String name = params.getString("user_name");
				usuario.setUserName(params.getString("user_name"));
				Log.i(CATEGORIA, "NAME na Tela Cadastro " + usuario.getUserName());
							
				//String email = params.getString("user_email");
				usuario.setUserEmail(params.getString("user_email"));
				Log.i(CATEGORIA,"EMAIL na Tela Cadastro " + usuario.getUserEmail());				
				
			}		
		}		
	}
	
	@Override
	public void onResume() {		
	    super.onResume();
	    
	    Log.i(CATEGORIA, ".onResume() chamado.");
	    
	   //Setando as informacoes no EditText
	    
	    ProfilePictureView ppv = (ProfilePictureView) findViewById(R.id.fbImg);
	    ppv.setProfileId(usuario.getIdFacebook());
	    		
		EditText edtxId = (EditText) findViewById(R.id.edtx_idFacebook);
		edtxId.setText(usuario.getIdFacebook());
		
		EditText edtxNome = (EditText) findViewById(R.id.edtx_name);	
		edtxNome.setText(usuario.getUserName());	
		
		EditText edtxEmail = (EditText) findViewById(R.id.edtx_email);
		edtxEmail.setText(usuario.getUserEmail());
		
		Button botaoconfirmar = (Button) findViewById(R.id.button_confirmar);
		
		
		
		
		//BANCO DE DADOS
		final SQLiteDatabase db = openOrCreateDatabase("toindo.db",Context.MODE_PRIVATE, null);		
		
		botaoconfirmar.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				EditText edtxIdFacebook = (EditText) findViewById(R.id.edtx_idFacebook);
				EditText edtxNome = (EditText)findViewById(R.id.edtx_name);
				EditText edtxEmail = (EditText)findViewById(R.id.edtx_email);
				
				if(edtxNome.getText().toString().length() <= 0){
		    		edtxNome.setError("Insira um Nome!");
		    		edtxNome.requestFocus();    	
		    	}  
				else if(edtxEmail.getText().toString().length() <= 0){
					edtxEmail.setError("Insira um Email!");
					edtxEmail.requestFocus(); 
					
				}else {
		    		try{
			    		
			    		ContentValues ctv = new ContentValues();
			    		
			    		ctv.put("idFacebook", edtxIdFacebook.getText().toString());
			    		ctv.put("nome_usuario",edtxNome.getText().toString()); 
			    		ctv.put("email", edtxEmail.getText().toString());				    		
			    	
			    		if(db.insert("usuario", "_id", ctv) > 0){
			    			Toast.makeText(getBaseContext(), "Sucesso ao enviar", Toast.LENGTH_SHORT).show();
			    			finish();
			    			
			    			
			    		} else {
			    			Toast.makeText(getBaseContext(), "Erro ao enviar", Toast.LENGTH_SHORT).show();
			    			
			    		}
			    		db.close();
		    		} catch(Exception ex){
		    			Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
			    	}
				    
				}
			}
		});
	    
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_cadastro,
					container, false);
			return rootView;
		}
	}

}
