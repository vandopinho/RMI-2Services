
package rmi1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi2.*;

public class Servidor extends UnicastRemoteObject implements Servico{

    int bancoAux;
    public Servidor() throws RemoteException {
        super();
        bancoAux =0;
    }
        
    public static void main(String[] args) throws SQLException
    {

        try {
            Servidor servidor = new Servidor();
            Registry registry = LocateRegistry.createRegistry(12345);
            registry.bind("TESTE",  servidor);
            System.out.println("Servidor 1 iniciado...");
                        
            
        } catch (AlreadyBoundException | RemoteException ex) {
            System.out.println("Erro: " + ex.getMessage() );
        }
    }
        


    @Override
    public boolean ConectaBanco(String nome, int banco) throws RemoteException
    {
        Banco b = new Banco();
        bancoAux = banco;
        int count = 0;
        if (banco == 1){
        try {
            Connection con = b.Conexao();
            Statement stmt = null;
            String sql = "select * from cliente where nome = '" + 
                    nome + "'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                count++;
            }
            System.out.println(count);
        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        if(banco == 2){
            try {
            Banco2 b2 = new Banco2();
            Connection con = b2.Conexao();
            Statement stmt = null;
            String sql = "select * from cliente2 where nome = '" + 
                    nome + "'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                count++;
            }
            System.out.println(count);
        } catch (SQLException ex) {
            Logger.getLogger(Servidor2.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
 
    }

    @Override
    public double Saldo(String nome) throws RemoteException {
        double valor = 0;
        if (bancoAux == 1){
                Banco b = new Banco();
                String texto;
                try {
                    Connection con = b.Conexao();
                    Statement stmt = null;
                    String sql = "SELECT saldo FROM cliente WHERE nome = '" + 
                    nome + "'";
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next())
                    {
                        texto = rs.getString("saldo");
                        valor = Double.parseDouble(texto);
                        valor = rs.getDouble("saldo");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        else {
                        Banco2 b2 = new Banco2();
                        String texto;
                        try {
                            Connection con = b2.Conexao();
                            Statement stmt = null;
                            String sql = "SELECT saldo FROM cliente2 WHERE nome = '" + 
                            nome + "'";
                            stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(sql);
                            while(rs.next())
                            {
                                texto = rs.getString("saldo");
                                valor = Double.parseDouble(texto);
                                valor = rs.getDouble("saldo");
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                        }
        }
        return valor;
    }

    @Override
    public double atualizarSaldo(String nome, double Saldo, Double saldoNovo) throws RemoteException {
        double saldo = 0;
        if (bancoAux == 1){
                Banco b = new Banco();
                try {
                    Connection con = b.Conexao();
                    Statement stmt = null;
                    String sql = "SELECT saldo FROM cliente WHERE nome = '" + nome + "'";
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next())
                    {
                        saldo = rs.getDouble("saldo");
                    }
                    saldo = saldo + saldoNovo;
                    Statement stmt2 = null;
                    String sql2 = "UPDATE cliente SET saldo ='" + String.valueOf(saldo) + "' WHERE nome = '"+ nome +"'";
                    stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next())
                    {
                        saldoNovo = rs.getDouble(sql2);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        else {
                Banco2 b2 = new Banco2();
        try {
            Connection con = b2.Conexao();
            Statement stmt = null;
            String sql = "SELECT saldo FROM cliente2 WHERE nome = '" + nome + "'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                saldo = rs.getDouble("saldo");
            }
            saldo = saldo + saldoNovo;
            Statement stmt2 = null;
            String sql2 = "UPDATE cliente2 SET saldo ='" + String.valueOf(saldo) + "' WHERE nome = '"+ nome +"'";
            stmt2 = con.createStatement();
            ResultSet rs2 = stmt2.executeQuery(sql2);
            while (rs2.next())
            {
                saldoNovo = rs.getDouble(sql2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
        return saldoNovo; 
    }

    @Override
    public double depositar(String nome, double Saldo, Double saldoAtualizado) throws RemoteException {
        double valor = 0, valorAux = 0;
        Banco b = new Banco();
        String texto;
        try {
            Connection con = b.Conexao();
            Statement stmt = null;
            String sql = "UPDATE cliente SET saldo = '"+Double.toString(valorAux)+"' WHERE nome = '" + 
                    nome +"'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                texto = rs.getString("saldo");
                valor = Double.parseDouble(texto);
                valor = rs.getDouble("saldo");   
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }

    @Override
    public double sacar(String nome, double Saldo, double saldoNovo) throws RemoteException {
        double saldo = 0;
        if (bancoAux == 1) {
            Banco b = new Banco();
            try {
                Connection con = b.Conexao();
                Statement stmt = null;
                String sql = "SELECT saldo FROM cliente WHERE nome = '" + nome + "'";
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next())
                {
                    saldo = rs.getDouble("saldo");
                }
                if (saldo> saldoNovo){
                     saldo = saldo - saldoNovo;
                    Statement stmt2 = null;
                    String sql2 = "UPDATE cliente SET saldo ='" + String.valueOf(saldo) + "' WHERE nome = '"+ nome +"'";
                    stmt2 = con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(sql2);
                    while (rs2.next())
                    {
                    saldoNovo = rs.getDouble(sql2);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Valor a ser sacado maior que o saldo !!");
                }
           
        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else {
            Banco2 b2 = new Banco2();
        try {
            Connection con = b2.Conexao();
            Statement stmt = null;
            String sql = "SELECT saldo FROM cliente2 WHERE nome = '" + nome + "'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                saldo = rs.getDouble("saldo");
            }
            if (saldo> saldoNovo){
                 saldo = saldo - saldoNovo;
                Statement stmt2 = null;
                String sql2 = "UPDATE cliente2 SET saldo ='" + String.valueOf(saldo) + "' WHERE nome = '"+ nome +"'";
                stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(sql2);
                while (rs2.next())
                {
                saldoNovo = rs.getDouble(sql2);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Valor a ser sacado maior que o saldo !!");
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        return saldoNovo;
    }

    @Override
    public double extrato(String nome) throws RemoteException {
        double valor = 0;
        if (bancoAux == 1){
            Banco b = new Banco();
                try {
                    Connection con = b.Conexao();
                    Statement stmt = null;
                    String sql = "select * from cliente where nome ='" + nome +"'";
                    stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next())
                    {
                        valor = rs.getDouble("saldo"); 
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        else {
            Banco2 b2 = new Banco2();
        try {
            Connection con = b2.Conexao();
            Statement stmt = null;
            String sql = "select * from cliente2 where nome ='" + nome +"'";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next())
            {
                valor = rs.getDouble("saldo"); 
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        return valor; 
    }
    }


