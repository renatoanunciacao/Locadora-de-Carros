/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Carro;
import modelo.Categoria;
import modelo.Cliente;

/**
 *
 * @author Renato
 */
public class CategoriasDAO {

    Conexao c = new Conexao();
    Connection con = c.getConnection();

    PreparedStatement ps;
    ResultSet rs;

    public ArrayList<Categoria> buscarcategorias() {
        ArrayList<Categoria> lista = new ArrayList<>();
        String sql = "select * from categorias order by cat_id";
        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("cat_id"));
                c.setNome_cat(rs.getString("cat_nome"));
                c.setValor(rs.getDouble("cat_valor"));

                lista.add(c);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar categorias\n" + e);
        }
        return lista;
    }

        public ArrayList<Categoria> buscarcategorias(String nome) {
        ArrayList<Categoria> lista = new ArrayList<>();
        String sql = "select * from categorias where  lower(cat_nome) like ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nome + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
           

                Categoria cat = new Categoria();
                cat.setId(rs.getInt("cat_id"));
                cat.setNome_cat(rs.getString("cat_nome"));
                cat.setValor(rs.getDouble("cat_valor"));

            

                lista.add(cat);

            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar Categoria\n" + e);
        }
        return lista;
    }
    public void inserir(Categoria c) {
        String sql = "insert into categorias (cat_nome, cat_valor) values (?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome_cat());
            ps.setDouble(2, c.getValor());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Categoria " + c.getNome_cat()+ " incluída com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir Categoria "
                    + c.getNome_cat()+ "\n" + ex.getMessage());
        }

    }

    public void editar(Categoria c) {
        String sql = "update categorias set cat_nome = ?,  cat_valor = ? "
                + " where cat_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome_cat());
            ps.setDouble(2, c.getValor());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Categoria " + c.getNome_cat()+ " alterada com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar Categoria "
                    + c.getNome_cat()+ "\n" + ex.getMessage());
        }

    }
    
        public void remover(Categoria c) {
        String sql = "delete from categorias where cat_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getId());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Categoria " + c.getNome_cat()+ " excluída com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir Categoria "
                    + c.getNome_cat()+ "\n" + ex);
        }

    }

}
