/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.esercizio1.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Utente
 */
public class DbConnector {
    private static final String URL="jdbc:mysql://localhost:3306/world";
    private static final String USER="admin";
    private static final String PASSWORD="admin";
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SELECT_CONTINENTS="select Continent from country;";
    
    public static Connection connetti() throws SQLException{
        try{
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection conn=DriverManager.getConnection(URL,USER,PASSWORD);
        return conn;
    }
    
    public static List<String> getContinenti(){
        List<String> continenti=null;
   
        try(Connection conn=connetti();
                PreparedStatement pstmt=conn.prepareStatement(SELECT_CONTINENTS);){
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                if(continenti==null){
                    continenti=new ArrayList();
                    continenti.add(rs.getString("Continent"));
                }
                if((continenti.contains(rs.getString("Continent")))==false){
                    continenti.add(rs.getString("Continent"));
                }
            }
        }   catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return continenti;
    }
}
