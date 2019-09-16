/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.esercizio1.controller;

import it.esercizio1.connector.DbConnector;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Utente
 */
@Controller
public class InerimentoController {
    
    private List<String> continenti;
    private List<String> paesi;
    private List<String> citta;
    @GetMapping("/")
    public String main(Model model){
        continenti=DbConnector.getContinenti();
        model.addAttribute("continenti", continenti);
        
        return "continente";
    }
    
    @GetMapping("/stato")
    public String mainWithResources(
            @RequestParam(name="continente", required=true,defaultValue="")String continente, Model model){
        paesi=DbConnector.getPaesi(continente);
        model.addAttribute("varStato", paesi);
        
        return "stato";
    }
    
    @PostMapping("/citta")
    public String mainPost(@RequestParam("stato")String stato, Model model){
        System.out.println(stato);
        citta=DbConnector.getCitta(stato);
        
        model.addAttribute("citta", citta);
        
        return "citta";
    }
    @GetMapping("/risultato")
    public String fine(
    @RequestParam(name="nome",required=true,defaultValue="") 
    String nome, 
     @RequestParam(name="distretto",required=true,defaultValue="")String distretto, Model model){
        DbConnector.inerimentoCitta(nome,distretto);
        model.addAttribute("messaggio", "Bravissimo compa sei un campione");
        
        
        return "risultato";
    }
    
    
}
