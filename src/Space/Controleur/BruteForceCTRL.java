/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Controleur;

import Space.Modele.bufferBody;
import Space.Modele.Calculateur.BruteForceCalculator;
import Space.Modele.Body;
/**
 *
 * @author cou8 Controleur du bruteForceCalculateuré
 */
public class BruteForceCTRL implements CTRLInterface{
    
    BruteForceCalculator calculateur;
    
    public BruteForceCTRL()
    {   
        calculateur = new BruteForceCalculator(32768);
    }    
    public void requestNewFrame()
    {
        calculateur.requestNewFrame();
    }
    
    public Body[] getBodies()
    {  
        Body[] retour = null;
        if (calculateur.roule == false)
        {
           retour = calculateur.bodies;
         
        }
        else
        { 
           throw new RuntimeException("Ne peut pas accéder au objet quand le calculateur est en éxécution");
        }  
        return retour;
    }     
    public bufferBody frameActuel()
    {
        return calculateur.frameActuel();
    }
    public boolean isRunning()
    {
        return  calculateur.roule;
    }      
    public void  StartCalculateur()
    { 
          System.out.println("1.5"); 
        //don't start if  already running
          if ( calculateur.roule != true)
          {   
                calculateur.roule = true; 
                System.out.println("2");
                Thread t = new Thread(calculateur);
                t.start();
          }
    }
    
    public void init()
    {
       calculateur.init();
    }
    public void bufferise(Body[] buffer)
    {
        calculateur.memoire = new bufferBody(buffer);
        calculateur.memoireDernierCalcule = calculateur.memoire;
    }
    public void stopCalculateur()
     {
         calculateur.roule =false;
     }
    
    
}
