package jacopo.core.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import jacopo.core.model.GestoreModel;
import jacopo.utils.model.Oggetto;
import jacopo.utils.persistence.EasyConn;



public class ConnessioneDb implements EasyConn
{	
	private final String separator = System.getProperty("file.separator");
	private Connection conn;
	private String driver_String = "com.mysql.jdbc.Driver";
	private final String JDBC_URL = "jdbc:mysql:";
	private String fullname;
	private final String dbip = "localhost:3306";
	private final String nameDB = "FCLABASSA";
	private String userName;
	private Statement st;
	private GestoreModel g;
	
	public ConnessioneDb(GestoreModel ges, String user, String psw)
	{
		if(ges == null)
		{
			System.out.println("ERRORE, ConnessioneDb viene passato un GestoreModel == null");
			throw new IllegalArgumentException("ERRORE!!! All'oggetto che si connette al db viene passato GetsoreModel == null");
		}
		this.g = ges;
		this.userName = user;
		String pass = psw;
		this.conn = null;
		this.fullname = this.JDBC_URL + this.separator + this.separator + this.dbip + this.separator + this.nameDB; //+ "?useUnicode=yes&characterEncoding=UTF-8";
		Properties info = new Properties();
		info.put("user", this.userName);
		info.put("password", pass);		
		this.conn = startEasyConn(this.fullname, info);
		if(conn == null)
		{
			System.out.println("\tImpossibile connetersi al db");
		}
		else
		{
			this.getStatement(); 
		}
	}

	private Connection startEasyConn(String fullname, Properties info) 
	{
		
			//istanza dei Driver adeguati--- in questa mysql
			try 
			{
				System.out.println("Prima di istanziare i driver mysql");
				Class.forName(this.driver_String);
				System.out.println("Class.forName riuscito");
			} 
			catch (ClassNotFoundException e) 
			{	System.out.println("startEasyConn: ClassNotFoundException()");
				System.out.println(e.getCause());
				System.out.println(e.getMessage());
			}
		
			//tentativo di connessione al db
			try
			{
				this.conn = DriverManager.getConnection(fullname, info);
			}
			catch(SQLException e)
			{
				System.out.println("ERRORE!!! Durante il tenativo di connessione al db");
				System.out.println(e.getLocalizedMessage());
				System.out.println(e.getErrorCode());
				System.out.println(e.getSQLState());
			}
			return this.conn;
	}

	@Override
	public ResultSet getResultSet(String sql) 
	{
		ResultSet result = null;
		try
		{
			result = this.getStatement().executeQuery(sql);
			this.conn.close();
		}
		catch(Exception e)
		{
			System.out.println("Errore esecuzione sql dinamico");
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
		return result;
	}

	@Override
	public Statement getStatement() 
	{
		this.st = null;
		if(this.conn != null)
		{
			try
			{
				this.st = this.conn.createStatement();
			}
			catch(SQLException e)
			{
				System.out.println("Errore durante la creazione dello Statement");
				System.out.println(e.getMessage());
				System.out.println(e.getErrorCode());
				System.out.println(e.getSQLState());
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
			}
			if(this.conn != null)
			{
				System.out.println("Connected do Database @: " + this.fullname);
			}
			else
			{
				System.out.println("Il server non è stato istanziato correttamente");
			}
		}
		else
		{
		}
		return this.st;
	}

	@Override
	public String getDir() 
	{
		return this.dbip;
	}

	@Override
	public String getDBName() 
	{
		return this.nameDB;
	}

	@Override
	public String getUserName() 
	{
		return this.userName;
	}

	@Override
	public Oggetto getOggetto(int i) 
	{
		return this.g.getOggetti().get(i);
	}

	@Override
	public List<Oggetto> getOggetti() 
	{
		return this.g.getOggetti();
	}
	
}
