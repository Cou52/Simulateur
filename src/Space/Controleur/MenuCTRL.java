/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Controleur;


import Space.vue.JPanelInterface;
import static Space.vue.JPanelInterface.BRUTE_FORCE_GRAVITE;
import static Space.vue.JPanelInterface.FRACTAL_TRIANGLE;
import static Space.vue.JPanelInterface.OPTION_GENERAL;

import Space.vue.BoiteDialog.OptionCalculateur;


import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

 import Space.Modele.Calculateur.BruteForceCalculator;
/**
 *
 * @author cou8
 */
public class MenuCTRL {
   
      JPanelInterface interPrincipal;
      OptionCalculateur interOption;
       EcouteurOptionForceBrute listenOptionBrute;
      Ecouteur listen = new Ecouteur();
    public  MenuCTRL()
    { 
         interPrincipal = new JPanelInterface(); 
         setListener();
    }       
   public void setListener()
    {
         listen = new Ecouteur();
         interPrincipal.menuItem.addActionListener(listen);
         interPrincipal.menuItem2.addActionListener(listen);
         interPrincipal.menuItem3.addActionListener(listen);
   }  
    public static void main(String[] args)
   {
    
        MenuCTRL menu = new MenuCTRL();
    }  
    
    
     private class Ecouteur implements ActionListener  
    {
        public void actionPerformed(ActionEvent e) {
           JMenuItem source = ((JMenuItem) e.getSource());
                    System.out.println("ttttt");
                 System.out.println(source.getText());
             if (source.getText().equals(BRUTE_FORCE_GRAVITE))
                {    
                    interPrincipal.JMonkeyInnterface.removeAll();
                    interPrincipal.JMonkeyInnterface.bruteForce(150);
                }
                if (source.getText().equals(FRACTAL_TRIANGLE))
                {    
                    interPrincipal.JMonkeyInnterface.removeAll();
                    interPrincipal.JMonkeyInnterface.fractal_Triangle();
                }
                 if (source.getText().equals(OPTION_GENERAL))
                {    
                       
                      if (interOption == null)
                      {
                        interOption = new OptionCalculateur();   
                        ajouterEcouterInterOption();
                      }
                }
          }
          
        }
     
     public void ajouterEcouterInterOption()
     {
         listenOptionBrute = new EcouteurOptionForceBrute();
         interOption.Annuler.addActionListener(listenOptionBrute);
         interOption.Ok.addActionListener(listenOptionBrute);
          System.out.println("la");
     }
     private void menuOptionSelectionner()
     { 
          int particule = interOption.getNombreParticuleSelectionner();
          if (particule != -1);
          {
               interPrincipal.JMonkeyInnterface.removeAll();
               interPrincipal.JMonkeyInnterface.bruteForce(particule);
          }
     }      
     private class EcouteurOptionForceBrute implements ActionListener  
     {
            public void actionPerformed(ActionEvent e) {
             System.out.println("ici");
            Object source =  e.getSource();
            if (source.equals(interOption.Ok))
            {    
                 menuOptionSelectionner();
                 interOption.dispose();
                 interOption = null;
            }
      }
     }
     }

