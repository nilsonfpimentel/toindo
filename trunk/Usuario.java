package br.com.toindoapk;

public class Usuario {
	private String idUsuario;	
	private String idFacebook;
	private String nome_usuario;
	private String email_usuario;
	
	public void setUserId(String id){
		this.idUsuario=id;	
	}
	public String getUserId(){
		return this.idUsuario;
	}
	
	public String getIdFacebook() {
		return idFacebook;
	}
	public void setIdFacebook(String idFacebook) {
		this.idFacebook = idFacebook;
	}
	
	public String getUserName() {
		return nome_usuario;
	}
	public void setUserName(String name) {
		this.nome_usuario = name;
	}
	public String getUserEmail() {
		return email_usuario;
	}
	public void setUserEmail(String email) {
		this.email_usuario = email;
	}	

}
