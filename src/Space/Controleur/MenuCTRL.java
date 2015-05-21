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
 *Cette classe permet de partir l'application
 * @author cou8
 */
public class MenuCTRL {
   
     /**
      * Représente l'interface principal
      */ 
     JPanelInterface interPrincipal;
     
      OptionCalculateur interfaceOptionBrute;
      EcouteurOptionForceBrute ecouteurOptionBrute;
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
                    interPrincipal.JMonkeyInnterface.bruteForce(2500 , true);
                }
                if (source.getText().equals(FRACTAL_TRIANGLE))
                {    
                    interPrincipal.JMonkeyInnterface.removeAll();
                    interPrincipal.JMonkeyInnterface.fractal_Triangle();
                }
                 if (source.getText().equals(OPTION_GENERAL))
                {    
                       
                      if (interfaceOptionBrute == null)
                      {
                     
                         interfaceOptionBrute = new OptionCalculateur();
                         interfaceOptionBrute.nombreDeParticule.setText("" + interPrincipal.JMonkeyInnterface.getNumberOfParticule()); 
                                
                        ajouterEcouterInterOption();
                      }
                }
          }
          
        }
     
     public void ajouterEcouterInterOption()
     {
         ecouteurOptionBrute = new EcouteurOptionForceBrute();
         interfaceOptionBrute.Annuler.addActionListener(ecouteurOptionBrute);
         interfaceOptionBrute.Ok.addActionListener(ecouteurOptionBrute);
          System.out.println("la");
     }
     private void menuOptionSelectionner()
     { 
          int particule = interfaceOptionBrute.getNombreParticuleSelectionner();
          String sQuality = interfaceOptionBrute.getQuality();
          
          boolean quality = false;
          //TODO, cette classe devrait gérer le calculateur ou JmonkeyInterface devrait avoir des méthode pour y accéder plus facilement
          if (particule != -1)
          {
                
                if (particule != interPrincipal.JMonkeyInnterface.getNumberOfParticule())
                {
                 
                     interPrincipal.JMonkeyInnterface.removeAll();
                     interPrincipal.JMonkeyInnterface.bruteForce(particule, true);
                }

                if(sQuality.equals(OptionCalculateur.HIGH_QUALITY_TEXT))
                { 
                    quality = true;    
                }
                if( quality != interPrincipal.JMonkeyInnterface.getQualityParticule())
                {   

                     interPrincipal.JMonkeyInnterface.requestChangeQuality(quality);
                } 
          }    
          
     }      
     private class EcouteurOptionForceBrute implements ActionListener  
     {
         public void actionPerformed(ActionEvent e) 
         {
     
            Object source =  e.getSource();
            if (source.equals(interfaceOptionBrute.Ok))
            {    
                menuOptionSelectionner();
                interfaceOptionBrute.dispose();
                interfaceOptionBrute = null;
            }
            else if (source.equals(interfaceOptionBrute.Annuler))
            {    
               interfaceOptionBrute.dispose();
                interfaceOptionBrute = null;
            }
        }
     }
 }

