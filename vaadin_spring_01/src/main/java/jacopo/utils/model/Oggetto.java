package jacopo.utils.model;

import java.util.List;

public interface Oggetto 
{	
	public List<Parametro> getParams();
	public List<Oggetto> getReferences();
	public String getSchema();
	public String getTable();
	public void setReferences(List<Oggetto> lista);
}
