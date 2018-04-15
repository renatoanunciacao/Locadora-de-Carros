/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cliente;

/**
 *
 * @author Renato
 */
public class ClienteDAO {

    Conexao c = new Conexao();
    Connection con = c.getConnection();

    PreparedStatement ps;
    ResultSet rs;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ArrayList<Cliente> buscarclientes() {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "select * from clientes order by cli_id";
        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("cli_id"));
                c.setNome(rs.getString("cli_nome"));
                c.setEndereco(rs.getString("cli_endereco"));
                c.setCpf(rs.getString("cli_cpf"));
                c.setSexo(rs.getString("cli_sexo"));
                c.setData_nascimento(rs.getDate("cli_data_nascimento"));

                lista.add(c);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar clientes\n" + e);
        }
        return lista;
    }
    
    public ArrayList<Cliente> buscar(String nome) {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "select * from clientes where  lower(cli_nome) like lower(?);";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nome + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("cli_id"));
                c.setNome(rs.getString("cli_nome"));
                c.setEndereco(rs.getString("cli_endereco"));
                c.setCpf(rs.getString("cli_cpf"));
                c.setSexo(rs.getString("cli_sexo"));
                c.setData_nascimento(rs.getDate("cli_data_nascimento"));               

                lista.add(c);

            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar Cidade\n" + e);
        }
        return lista;
    }

    public void inserir(Cliente c) {
        String sql = "insert into clientes (cli_nome, cli_endereco, cli_cpf, cli_sexo, cli_data_nascimento) values (?, ?, ?, ? , ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getEndereco());
            ps.setString(3, c.getCpf());
            ps.setString(4, c.getSexo());
            ps.setDate(5, new Date(c.getData_nascimento().getTime()));
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Cliente " + c.getNome() + " incluído com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir Cliente "
                    + c.getNome() + "\n" + ex.getMessage());
        }

    }

    public void editar(Cliente c) {
        String sql = "update clientes set cli_nome = ?,  cli_endereco = ?, cli_cpf = ?, cli_sexo = ?, cli_data_nascimento= ?"
                + " where cli_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getEndereco());
            ps.setString(3, c.getCpf());
            ps.setString(4, c.getSexo());
            java.sql.Date dtBanco = new java.sql.Date(c.getData_nascimento().getTime());
            ps.setDate(5, dtBanco);
            ps.setInt(6, c.getId());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Cliente " + c.getNome() + " alterado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar Cliente "
                    + c.getNome() + "\n" + ex.getMessage());
        }

    }

    public void remover(Cliente c) {
        String sql = "delete from clientes where cli_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getId());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Cliente " + c.getNome() + " excluído com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir Cliente "
                    + c.getNome() + "\n" + ex);
        }

    }

}
