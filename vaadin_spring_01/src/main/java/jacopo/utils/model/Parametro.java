package jacopo.utils.model;

public interface Parametro 
{
	public String getPath();
	public String getAttributeName();
	public String getTable();
	public String getSchema();
	public Class<?> getClasse();
	public boolean canNull();
	public String getReference();
	public boolean isAuto();
	public boolean join();
}
