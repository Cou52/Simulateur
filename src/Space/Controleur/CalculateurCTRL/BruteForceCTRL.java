/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Controleur.CalculateurCTRL;

import Space.Controleur.Interface.CTRLInterface;
import Space.Controleur.Interface.CalculatorListener;
import Space.Modele.bufferBody;
import Space.Modele.Calculateur.BruteForceCalculator;
import Space.Modele.Body;
/**
 *
 * @author cou8 Controleur du bruteForceCalculateuré
 */
public class BruteForceCTRL implements CTRLInterface ,CalculatorListener{
    
    BruteForceCalculator calculateur;
    private boolean requestChangeQuality;
    
    public BruteForceCTRL()
    {   
        calculateur = new BruteForceCalculator(15000);
    } 
      
      public BruteForceCTRL(int nombreParticule)
    {   
        calculateur = new BruteForceCalculator(nombreParticule);
    }  
    public void requestNewFrame()
    {
        calculateur.requestNewFrame();
    }
    public void  CalculateurHasStopped(BruteForceCalculator cal)
    {   
        if (requestChangeQuality)
        {  
            
        }   
    } 
    
    private void changeQualityT(boolean quality)
    {  
        for (int i = 0; i < calculateur.bodies.length; i++) 
        { 
            calculateur.bodies[i].highQuality = quality;
        }  
    }   
    public void changeQuality(boolean quality)
    {
        requestChangeQuality = true;
        if (calculateur.IsStooped())
        { 
            changeQualityT(quality);
        }
        else
        {  
            stopCalculateur();
        } 
    }
    public void ChangerNombreParticuleCalculateur(int nombre)
    { 
        stopCalculateur();
        calculateur.ChangerNombreParticule(nombre);
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
    public void viderCalculateur()
    {  
        calculateur.memoire = null;
        calculateur.memoireDernierCalcule = null;
    }    
    public void stopCalculateur()
     {
         calculateur.roule =false;
     }
    
    
}
