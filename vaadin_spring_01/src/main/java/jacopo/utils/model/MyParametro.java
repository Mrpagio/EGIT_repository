package jacopo.utils.model;

import java.util.StringTokenizer;

public class MyParametro implements Parametro 
{
	private String path;
	private String schema;
	private String tabella;
	private String attributo;
	private Class<?> tipo;
	private boolean nullable;
	private String reference;
	private boolean automatic;
	private boolean join;
	
	public MyParametro(String fullPath, Class<?> classe, String ref, boolean auto)
	{
		if(fullPath.trim().equals("") | fullPath == null)
		{
			throw new IllegalArgumentException("ERRORE CREAZIONE PARAMETRO: " + classe.toString() + "path null");
		}
		this.path = fullPath;
		StringTokenizer stk = new StringTokenizer(this.path, ".");
		try
		{
			this.schema = stk.nextToken();
			this.tabella = stk.nextToken();
			this.attributo = stk.nextToken();
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("ERRORE formattazione path");
		}
		if(classe == null)
		{
			throw new IllegalArgumentException("ERRORE Class<?> == null");
		}
		this.tipo = classe;
		if(ref == null)
		{
			throw new IllegalArgumentException("ERRORE inizializzazione ref");
		}
		if(ref.equalsIgnoreCase(null))
		{
			this.nullable = true;
		}
		else
		{
			this.nullable = false;
		}
		this.reference = ref;
		if(!this.reference.equals("") && !this.reference.equalsIgnoreCase("null"))
		{
			this.join = true;
		}
		else
		{
			this.join = false;
		}
		this.automatic = auto;
	}
	
	@Override
	public String getPath() 
	{
		return this.path;
	}

	@Override
	public String getAttributeName() 
	{
		return this.attributo;
	}

	@Override
	public String getTable() 
	{
		return this.tabella;
	}

	@Override
	public String getSchema() 
	{
		return this.schema;
	}

	@Override
	public Class<?> getClasse() 
	{
		return this.tipo;
	}

	@Override
	public boolean canNull() 
	{
		return this.nullable;
	}

	@Override
	public String getReference() 
	{
		return this.reference;
	}

	@Override
	public boolean isAuto() 
	{
		return this.automatic;
	}

	@Override
	public boolean join() 
	{
		return this.join;
	}

}
