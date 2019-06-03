package soulfoam.bitsiegemainserver.main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class Database {

	private static final BasicDataSource dataSource = new BasicDataSource();
	
	public static final String URL = "jdbc:mysql://localhost:3306/bitsiege";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "";
	
	static{
		
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        
//        dataSource.setMaxTotal(100);
        dataSource.setMaxIdle(1000);
//        dataSource.setMaxOpenPreparedStatements(100);
//        dataSource.setMaxIdle(10);
//        dataSource.setDefaultAutoCommit(false);
//        dataSource.setLogAbandoned(true);
//        dataSource.setRemoveAbandonedTimeout(300);
//        dataSource.setInitialSize(15);
//        dataSource.setMaxWaitMillis(10000);

	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

//	public static Connection getConnection() throws SQLException {
//		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
//	}
}
