package rmi2;

import rmi1.*;
import rmi2.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco2 {
   public String url = "jdbc:postgresql://localhost:5432/banco2";  
   public String usuario = "postgres";  
   public String senha = "123456";  
   
   
   public Connection Conexao() throws SQLException
   {
        Connection con;   
        con = DriverManager.getConnection(url, usuario, senha);    
        System.out.println("Conexão realizada com sucesso.");  
        return con;
   }
}
