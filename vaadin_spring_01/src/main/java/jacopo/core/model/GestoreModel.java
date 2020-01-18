package jacopo.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jacopo.utils.model.Oggetto;
import jacopo.utils.model.Parametro;


public class GestoreModel 
{
	private List<Oggetto> oggetti;

	public GestoreModel()
	{
		List<Oggetto> lista = new ArrayList<Oggetto>();
		Oggetto obj_temp = new Persona();
		lista.add(obj_temp);
		if(lista.isEmpty() | lista == null)
		{
			throw new IllegalArgumentException("ERRORE!!! nessun oggetto all'interno del GestoreModel");
		}
		System.out.println("Il gestore riceve in ingresso una lista con " + lista.size() + " elementi");
		this.oggetti = lista;
		//Aggiungo le referenze ad ogni oggetto
		for(Oggetto o : this.oggetti)
		{
			System.out.println("Creazione gestore " + o.getTable());
			List<Oggetto> temp = new ArrayList<Oggetto>();
			for(Parametro p : o.getParams())
			{
				System.out.println("verifica " + p.getAttributeName());
				if(p.join())
				{
					System.out.println(p.getAttributeName() + " join " + p.getReference());
					Oggetto obj = getFromPath(p.getReference());
					System.out.println("ritorna " + obj.getTable());
					if(obj != null)
					{
						temp.add(obj);
					}
				}
				o.setReferences(temp);
			}
		}
	}
	
	public Oggetto getFromPath(String fullPath)
	{
		Oggetto result = null;
		boolean trovato = false;
		for(int i = 0; i < this.oggetti.size() && trovato == false; i++)
		{
			System.out.println("getFromPath() " + this.oggetti.get(i).getTable());
			for (int j = 0; j < this.oggetti.get(i).getParams().size() && trovato == false; j++)
			{
				Parametro temp = this.oggetti.get(i).getParams().get(j);
				System.out.println(temp.getPath() + " = " + fullPath);
				if (temp.getSchema().equals(getSchema(fullPath)))
				{
					System.out.println("Stesso schema");
					if (temp.getTable().equals(getTable(fullPath)))
					{
						System.out.println("Stessa tabella");
						if (temp.getAttributeName().equals(getAttribute(fullPath)))
						{
							System.out.println("trovato");
							trovato = true;
							result = this.oggetti.get(i);
						}
					}
				}
			}
			System.out.println("Fuori dal ciclo de3i parametri di " + this.oggetti.get(i).getTable());
		}
		if(trovato == false)
		{
			System.out.println("Non è stato trovato l'oggetto referenziato");
		}
		else
		{
			System.out.println("getFromPath() result = " + result.getTable());
		}
		return result;
	}
	
	public List<Oggetto> getOggetti()
	{
		return this.oggetti;
	}
	
	public List<Parametro> getParametriJoin(List<Parametro> in, Parametro par)
	{
		List<Parametro> result = new ArrayList<Parametro>();
		if(!in.contains(par))
		{
			throw new IllegalArgumentException("Il parametro che referenzia il join non è presente tra i parametri di ingresso");
		}
		List<Oggetto> fromRefs = getFromPath(par.getPath()).getReferences();
		Oggetto to = getFromPath(par.getReference());
		if(to == null)
		{
			throw new IllegalArgumentException("Oggetto to == null");
		}
		if(!fromRefs.contains(to))
		{
			throw new IllegalArgumentException("L'oggetto referenziato non appartiene alla lista di oggetti fromRefs");
		}
		for (Parametro p : to.getParams())
		{
			result.add(p);
			if(p.getTable().equals(par.getTable()) && p.getAttributeName().equals(par.getAttributeName()))
			{
				for (Parametro out : to.getParams())
				{
					if(!out.getAttributeName().equals(getAttribute(par.getReference())))
					{
						result.add(out);
					}
				}
			}
		}
		return result;
	}
	
	private String getAttribute(String fullpath)
	{
		String result = null;
		StringTokenizer tk = new StringTokenizer(fullpath, ".");
		tk.nextToken();
		tk.nextToken();
		result = tk.nextToken();
		return result;
	}
	
	private String getTable(String fullpath)
	{
		String result = null;
		StringTokenizer tk = new StringTokenizer(fullpath, ".");
		tk.nextToken();
		result = tk.nextToken();
		return result;
	}
	
	private String getSchema(String fullpath)
	{
		String result = null;
		StringTokenizer tk = new StringTokenizer(fullpath, ".");
		result = tk.nextToken();
		return result;
	}
	
	public List<Parametro> getParametri(List<Parametro> in, String name)
	{
		Oggetto from = getFromPath(name);
		List<Oggetto> fromRefs = from.getReferences();
		Parametro temp = null;
		//sub verrà popolato dai soli parametri della lista in ingressi i quali ritorneranno true alla funzione join()
		List<Parametro> sub = new ArrayList<Parametro>();
		for (Parametro p : from.getParams())
		{
			if(p.join())
			{
				sub.add(p);
			}
		}
		for (Parametro p: sub)
		{
			if(p.getSchema().equals(getSchema(name)))
			{
				if(p.getTable().equals(getTable(name)))
				{
					if(p.getAttributeName().equals(getAttribute(name)))
					{
						System.out.println("trovato il parametroc he joina");
						temp = p;
					}
				}
			}
		}
		if(temp == null)
		{
			throw new IllegalArgumentException("ERRORE non è stato trovato il parametro referenziato");
		}
		Oggetto to = getFromPath(temp.getReference());
		if(to == null)
		{
			throw new IllegalArgumentException("Oggetto to == null");
		}
		if(!fromRefs.contains(to))
		{
			throw new IllegalArgumentException("L'oggeto referenziato non appartiene a fromRefs di oggetto from");
		}
		List<Parametro> result = new ArrayList<Parametro>();
		for(Parametro out : in)
		{
			if(!out.getAttributeName().equals(temp.getReference()))
			{
				result.add(out);
			}
		}
		return result;
	}
}
