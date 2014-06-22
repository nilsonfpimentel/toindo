package br.com.toindoapk;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.*;
import com.facebook.model.GraphUser;


public class MainActivity extends ActionBarActivity {
	
	/***
	 * VARIÁVEIS:
	 * CATEGORIA : UTILIZADA PARA CLASSIFICAR OS LOGS DE INFORMACAO NO LOGCAT
	 * uiHelper: UTILIZADA PELA BIBLIOTECA DO FACEBOOK
	 * actionbar UTILIZADA PARA MINIPULAR A ACTIONBAR
	 * params: UTILIZADA PARA FAZER A PASSAGEM DE INFORMACAO ENTRE AS TELAS
	 * dao: OBJETO PARA COMUNICACAO COM O BANCO
	 * usuario: OBJETO DA CLASSE USUARIO
	 */
	protected static final String CATEGORIA = "MyApp_Tela1";
	
	private UiLifecycleHelper uiHelper;
	private ActionBar actionbar;
	private MainFragment mainFragment;
	private Bundle params = new Bundle();	
	private UsuarioDAO usuarioDao;
	private Usuario usuario;
	
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChanged(session, state, exception);
			}
		};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario);
		
		usuario = new Usuario();		
		usuarioDao = new UsuarioDAO (this);
		usuarioDao.open();
		
		//ActionBar
		getObjects();
		setObjects();
		hideActionBar();		
		
		//https://developers.facebook.com/docs/facebook-login/permissions/v2.0
		//LoginButton lb = (LoginButton) findViewById(R.id.fb_login_button);
		//lb.setPublishPermissions(Arrays.asList("email", "public_profile", "user_friends"));		
		
		uiHelper = new UiLifecycleHelper(this, callback); 
		uiHelper.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
	        // Add the fragment on initial activity setup
	        mainFragment = new MainFragment();
	        getSupportFragmentManager()
	        .beginTransaction()
	        .add(android.R.id.content, mainFragment)
	        .commit();
	    } else {
	        // Or set the fragment from restored state info
	        mainFragment = (MainFragment) getSupportFragmentManager()
	        .findFragmentById(android.R.id.content);
	        }
				
	}
	
	//ActionBar 
	public void setObjects(){
		actionbar.setIcon(R.drawable.ic_launcher);
		actionbar.setTitle(R.string.app_name);
		actionbar.setDisplayHomeAsUpEnabled(true);
		}	
	public void getObjects(){		
		actionbar = getSupportActionBar();
	}
	public void hideActionBar(){
		actionbar.hide();		
	}    
	
	private String getClassName(){
		String s = getClass().getName();
		return s.substring(s.lastIndexOf("."));
	}
	
	@Override
	public void onResume() {		
	    super.onResume();
	    usuarioDao.open();
	    uiHelper.onResume();	    
	    
	    Session session = Session.getActiveSession();
	    if(session != null && (session.isClosed() || session.isOpened())){
	    	onSessionStateChanged(session, session.getState(), null);
	    	}	    
	    Log.i(CATEGORIA,getClassName() + ".onResume() chamado.");
	    
	} 
	
	@Override
	public void onPause() {
	    super.onPause();
	    usuarioDao.close();
	    uiHelper.onPause();
	    Log.i(CATEGORIA,getClassName() + ".onPause() chamado.");
	} 
	
	@Override
	public void onStop(){
		super.onStop();
		uiHelper.onStop();
		Log.i(CATEGORIA,getClassName() + ".onStop() chamado.");
	}
	
	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	    Log.i(CATEGORIA,getClassName() + ".onDestroy() chamado.");	    
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	
	@Override
	protected void onResumeFragments(){
	    super.onResumeFragments();
	    Log.i(CATEGORIA, "onResumeFragments() foi chamado");
	}	
		
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
    
	private void onSessionStateChanged(Session session,SessionState state, Exception exception) {			
		
		if(session != null && session.isOpened()){
								
			Log.i(CATEGORIA, "Usuario Conectado!");
			Request.newMeRequest(session, new Request.GraphUserCallback() {	
							
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null){							
																			
						try {
							usuario.setIdFacebook(user.getId().toString());						
							Log.i(CATEGORIA, usuario.getIdFacebook() );		
							params.putString("user_id", usuario.getIdFacebook());
						
							usuario.setUserName(user.getFirstName());
							Log.i(CATEGORIA, usuario.getUserName());						
							params.putString("user_name", usuario.getUserName());
							
							usuario.setUserEmail(user.getProperty("email").toString());
							Log.i(CATEGORIA, usuario.getUserEmail());
							params.putString("user_email", usuario.getUserEmail());
							
						} catch (Exception e) {
							Log.i(CATEGORIA,"ERRO:::: " + e.toString());
							
						}					
												
						//Intent intent = new Intent(MainActivity.this.getBaseContext(), OfertasActivity.class);
						Intent intent = new Intent(MainActivity.this.getBaseContext(), CadastroActivity.class);
						intent.putExtras(params);
						startActivity(intent);							
												
					}					
				}
			}).executeAsync();			
		}			
		else {		
			Log.i(CATEGORIA, "Usuario NÃO Conectado!");
		}
				
		if (state.isOpened()) {
			Log.i(CATEGORIA, "Logged In..."); 
			    
					                    
		} else if (state.isClosed()) {
			// Session closed
			Log.i(CATEGORIA, "Logged out...");           
		} 
    }
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usuario, menu);
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
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {			
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}
	
}
