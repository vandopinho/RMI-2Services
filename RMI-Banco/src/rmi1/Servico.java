
package rmi1;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;



public interface Servico extends Remote{

    public boolean ConectaBanco(String nome, int banco) throws RemoteException;
    public double Saldo(String nome) throws RemoteException;
    public double atualizarSaldo(String nome, double Saldo, Double saldoAtualizado) throws RemoteException;
    public double depositar(String nome, double Saldo, Double saldoAtualizado) throws RemoteException;
    public double sacar(String nome, double Saldo, double saldoAtualizado) throws RemoteException;
    public double extrato(String nome)throws RemoteException;
}