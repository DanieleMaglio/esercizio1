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

/**
 *
 * @author Utente
 */
@Controller
public class InerimentoController {
    
    List<String> continenti;
    
    @GetMapping("/")
    public String main(Model model){
        continenti=DbConnector.getContinenti();
        model.addAttribute("continenti", continenti);
        
        return "continente";
    }
    
}
