package jacopo.utils.persistence;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import jacopo.utils.model.Oggetto;


public interface EasyConn 
{
	public ResultSet getResultSet(String sql);
	public Statement getStatement();
	public String getDir();
	public String getDBName();
	public String getUserName();
	public Oggetto getOggetto(int i);
	public List<Oggetto> getOggetti();
}
