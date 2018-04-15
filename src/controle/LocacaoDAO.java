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
import modelo.Locacao;

/**
 *
 * @author Renato
 */
public class LocacaoDAO {

    Conexao c = new Conexao();
    Connection con = c.getConnection();

    PreparedStatement ps;
    ResultSet rs;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ArrayList<Locacao> carregarlocacoes() {
        ArrayList<Locacao> lista = new ArrayList<>();
        String sql = "select * from locacao l, clientes c,carros car, categorias cat where l.loc_cliente = c.cli_id and l.loc_carro = car.car_id and car.car_categoria = cat.cat_id order by loc_id desc";
        try {

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Locacao l = new Locacao();
                l.setId(rs.getInt("loc_id"));
                l.setData_de_saida(rs.getDate("loc_data_saida"));
                l.setData_de_chegada(rs.getDate("loc_data_chegada"));
                l.setValor_total(rs.getDouble("loc_valor_total"));

                Cliente cli = new Cliente();
                cli.setId(rs.getInt("cli_id"));
                cli.setNome(rs.getString("cli_nome"));
                cli.setEndereco(rs.getString("cli_endereco"));
                cli.setCpf(rs.getString("cli_cpf"));
                cli.setSexo(rs.getString("cli_sexo"));
                cli.setData_nascimento(rs.getDate("cli_data_nascimento"));
                l.setCliente(cli);

                Carro car = new Carro();
                car.setId(rs.getInt("car_id"));
                car.setNome(rs.getString("car_nome"));
                car.setPlaca(rs.getString("car_placa"));
                car.setMarca(rs.getString("car_marca"));
                car.setChasi(rs.getString("car_chasi"));
                car.setAno(rs.getDate("car_ano"));
                l.setCarro(car);
                
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("cat_id"));
                cat.setNome_cat(rs.getString("cat_nome"));
                cat.setValor(rs.getDouble("cat_valor"));
                car.setCategoria(cat);
                
                lista.add(l);
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar locações\n" + e);
        }
        return lista;
    }

    public void locar(Locacao l) {
        String sql = "insert into locacao (loc_data_saida,loc_cliente, loc_carro) values (?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);

            ps.setDate(1, new Date(l.getData_de_saida().getTime()));

            ps.setInt(2, l.getCliente().getId());
            ps.setInt(3, l.getCarro().getId());
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Locação do carro " + l.getCarro().getNome() + " feita com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao locar Carro "
                    + l.getCarro().getNome() + "\n" + ex.getMessage());
        }

    }

    public void devolver(Locacao l) {
        String sql = "update  locacao set loc_data_chegada = ?, loc_valor_total = ? where loc_id = ?";
        try {
            ps = con.prepareStatement(sql);

            ps.setDate(1, new Date(l.getData_de_chegada().getTime()));
            ps.setDouble(2, l.getValor_total());
            ps.setInt(3, l.getId());
          
            ps.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Locação do carro " + l.getCarro().getNome() + " devolvida com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao devolver Carro "
                    + l.getCarro().getNome() + "\n" + ex.getMessage());
        }

    }

}
