/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import controle.ClienteDAO;

/**
 *
 * @author Renato
 */
public class TesteConexao {
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();
        System.out.println(dao.buscarclientes());
        
    }
}
