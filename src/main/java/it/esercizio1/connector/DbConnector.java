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
import java.util.Random;
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
    private static final String SELECT_STATES="select Name from country where Continent=(?);";
    private static final String SELECT_CODE="select Code from country where Name=(?);";
    private static final String INSERT="insert into city(Name,CountryCode,District,Population) values(?,?,?,?);";
    
    private static String codice;
    private static List<Integer> popolazioni;
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
    
    public static List<String> getPaesi(String continente){
        List<String> paesi=new ArrayList();
        try(Connection conn=connetti();
                PreparedStatement pstmt=conn.prepareStatement(SELECT_STATES);){
            pstmt.setString(1, continente);
            ResultSet rs=pstmt.executeQuery();
            while(rs.next()){
                paesi.add(rs.getString("Name"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paesi;
    }
    public static List<String> getCitta(String stato){
        List<String> citta= new ArrayList();
        popolazioni=new ArrayList();
        try(Connection conn=connetti();
                PreparedStatement pstmt=conn.prepareStatement(SELECT_CODE);){
            pstmt.setString(1, stato);
            ResultSet rs=pstmt.executeQuery();
            rs.next();
            codice=rs.getString("Code");
            PreparedStatement pstmt2=conn.prepareStatement("select * from city where CountryCode=(?)");
            pstmt2.setString(1,codice);
            rs=pstmt2.executeQuery();
            while(rs.next()){
                citta.add(rs.getString("Name"));
                popolazioni.add(rs.getInt("Population"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return citta;
    }
    
    public static int calcolaMax(){
        int max=popolazioni.get(0);
        for(int i:popolazioni){
            if(i>max){
                max=i;
            }
        }
        return max;
    }
    
    public static int calcolaMin(){
        int min=popolazioni.get(0);
        for(int i:popolazioni){
            if(i<min)
                min=i;
        }
        return min;
    }
    
    public static void inerimentoCitta(String nome, String distretto){
        
    try(Connection conn=connetti();
            PreparedStatement pstmt=conn.prepareStatement(INSERT);){
        pstmt.setString(1, nome);
        pstmt.setString(2, codice);
        pstmt.setString(3, distretto);
        Random random=new Random();
        int min=calcolaMin();
        int max=calcolaMax() - min;
        
        int popolazione=random.nextInt(max)+ min;
        pstmt.setInt(4,popolazione);
        pstmt.executeQuery();
    }   catch (SQLException ex) {
            Logger.getLogger(DbConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
