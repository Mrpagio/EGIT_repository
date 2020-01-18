package jacopo.core.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jacopo.utils.model.MyParametro;
import jacopo.utils.model.Oggetto;
import jacopo.utils.model.Parametro;


public class Persona implements Oggetto 
{
	private List<Parametro> persona_params;
	private List<Oggetto> persona_references;
	private String persona_codice_fiscale;
	private String persona_documento;
	private String persona_nome;
	private String persona_cognome;
	private Date persona_data_nascita;
	
	//costruttore utilizzato da GestoreModel
	public Persona()
	{
		this.persona_params = new ArrayList<Parametro>();
		this.persona_references = new ArrayList<Oggetto>();
		setParams();
		setReferences();
	}
	
	private void setReferences()
	{
		//in questo caso l'oggetto Persona non referenzia nessun oggetto
		this.persona_references = new ArrayList<Oggetto>();
	}
	
	@Override
	public void setReferences(List<Oggetto> lista)
	{
		this.persona_references = lista;
	}
	
	private void setParams()
	{
		Parametro temp = null;
		temp = new MyParametro("FCLABASSA.Persona.Nome", String.class, "", false);
		this.persona_params.add(temp);
		temp = new MyParametro("FCLABASSA.Persona.Cognome", String.class, "", false);
		this.persona_params.add(temp);
		temp = new MyParametro("FCLABASSA.Persona.data_nascita", Date.class, "", false);
		this.persona_params.add(temp);
		temp = new MyParametro("FCLABASSA.Persona.codice_fiscale", String.class, "",false);
		this.persona_params.add(temp);
		temp = new MyParametro("FCLABASSA.Persona.documento", String.class, "", false);
		this.persona_params.add(temp);
	}

	@Override
	public List<Parametro> getParams() 
	{
		return this.persona_params;
	}

	@Override
	public List<Oggetto> getReferences() 
	{
		return this.persona_references;
	}

	@Override
	public String getSchema() 
	{
		return this.persona_params.get(0).getSchema();	
	}

	@Override
	public String getTable() 
	{
		return this.persona_params.get(0).getTable();
	}
	
	public String getNome()
	{
		return this.persona_nome;
	}
	
	public String getCognome()
	{
		return this.persona_cognome;
	}
	
	public String getCodiceFiscale()
	{
		return this.persona_codice_fiscale;
	}
	
	public String getDocumento()
	{
		return this.persona_documento;
	}
	
	public Date getDataNascita()
	{
		return this.persona_data_nascita;
	}
	
	public Persona(String nome, String cognome, Date nascita, String documento, String codicefiscale)
	{
		if(nome.trim().equals("") | nome == null)
		{
			throw new IllegalArgumentException("Durante la creazione di Persona, il nome passato è vuota o non inizializzato");
		}
		if(cognome.trim().equals("") | cognome == null)
		{
			throw new IllegalArgumentException("Durante la creazione di Persona, il cognome passato è vuoto o non inizializzato");
		}
		if(nascita == null)
		{
			throw new IllegalArgumentException("Durata la creazione di Persona, la data di nascita non è stata inizializzata");
		}
		if(documento.trim().equals("") | documento == null)
		{
			throw new IllegalArgumentException("Durante la creazione di Persona, il documento è vuoto o non inizializzato");
		}
		if(codicefiscale.trim().equals("") | codicefiscale == null)
		{
			throw new IllegalArgumentException("Durante la creazione di Persona, il codice fiscale è vuoto o non inizializzato");
		}
		this.persona_nome = nome;
		this.persona_cognome = cognome;
		this.persona_data_nascita = nascita;
		this.persona_documento = documento;
		this.persona_codice_fiscale = codicefiscale;
	}

}
