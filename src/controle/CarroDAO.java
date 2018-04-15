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
import modelo.Carro;
import modelo.Categoria;
import modelo.Cliente;

/**
 *
 * @author Renato
 */
public class CarroDAO {

    Conexao c = new Conexao();
    Connection con = c.getConnection();

    PreparedStatement ps;
    ResultSet rs;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ArrayList<Carro> buscarcarros() {
        ArrayList<Carro> lista = new ArrayList<>();
        String sql = "select * from carros, categorias where carros.car_categoria = categorias.cat_id";
        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Carro c = new Carro();
                c.setId(rs.getInt("car_id"));
                c.setNome(rs.getString("car_nome"));
                c.setPlaca(rs.getString("car_placa"));
                c.setMarca(rs.getString("car_marca"));
                c.setChasi(rs.getString("car_chasi"));
                c.setAno(rs.getDate("car_ano"));

                Categoria cat = new Categoria();
                cat.setId(rs.getInt("cat_id"));
                cat.setNome_cat(rs.getString("cat_nome"));
                cat.setValor(rs.getDouble("cat_valor"));

                c.setCategoria(cat);

                lista.add(c);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar carros\n" + e);
        }
        return lista;
    }


    public ArrayList<Carro> buscar(String nome) {
        ArrayList<Carro> lista = new ArrayList<>();
        String sql = "select * from carros,categorias where  lower(carros.car_nome) like lower(?) and carros.car_categoria = categorias.cat_id;";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nome + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Carro c = new Carro();
                c.setId(rs.getInt("car_id"));
                c.setNome(rs.getString("car_nome"));
                c.setPlaca(rs.getString("car_placa"));
                c.setMarca(rs.getString("car_marca"));
                c.setChasi(rs.getString("car_chasi"));
                c.setAno(rs.getDate("car_ano"));

                Categoria cat = new Categoria();
                cat.setId(rs.getInt("cat_id"));
                cat.setNome_cat(rs.getString("cat_nome"));
                cat.setValor(rs.getDouble("cat_valor"));

                c.setCategoria(cat);

                lista.add(c);

            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar Carro\n" + e);
        }
        return lista;
    }
    
    public ArrayList<Carro> buscar(Categoria cat,String nome) {
        ArrayList<Carro> lista = new ArrayList<>();
        String sql = "select * from carros,categorias where carros.car_categoria = categorias.cat_id and categorias.cat_id = ? and lower(carros.car_nome) like  ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cat.getId());
            ps.setString(2, "%" + nome + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Carro c = new Carro();
                c.setId(rs.getInt("car_id"));
                c.setNome(rs.getString("car_nome"));
                c.setPlaca(rs.getString("car_placa"));
                c.setMarca(rs.getString("car_marca"));
                c.setChasi(rs.getString("car_chasi"));
                c.setAno(rs.getDate("car_ano"));

                cat.setId(rs.getInt("cat_id"));
                cat.setNome_cat(rs.getString("cat_nome"));
                cat.setValor(rs.getDouble("cat_valor"));

                c.setCategoria(cat);

                lista.add(c);

            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar Carro\n" + e);
        }
        return lista;
    }

    public void inserir(Carro c) {
        String sql = "insert into carros (car_nome, car_placa, car_marca, car_chasi, car_ano, car_categoria) values (?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getPlaca());
            ps.setString(3, c.getMarca());
            ps.setString(4, c.getChasi());
            ps.setDate(5, new Date(c.getAno().getTime()));
            ps.setInt(6, c.getCategoria().getId());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Carro " + c.getNome() + " incluído com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir Carro "
                    + c.getNome() + "\n" + ex.getMessage());
        }

    }

    public void editar(Carro c) {
        String sql = "update carros set car_nome = ?,  car_placa = ?, car_marca = ?, car_chasi = ?, car_ano= ?,car_categoria = ?"
                + " where car_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getPlaca());
            ps.setString(3, c.getMarca());
            ps.setString(4, c.getChasi());
            java.sql.Date dtBanco = new java.sql.Date(c.getAno().getTime());
            ps.setDate(5, dtBanco);
            ps.setInt(6, c.getCategoria().getId());
            ps.setInt(7, c.getId());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Carro" + c.getNome() + " alterado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar Carro"
                    + c.getNome() + "\n" + ex.getMessage());
        }

    }

    public void remover(Carro c) {
        String sql = "delete from carros where car_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getId());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Carro " + c.getNome() + " excluído com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir Carro "
                    + c.getNome() + "\n" + ex);
        }

    }
}
