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
    private boolean quality = true;
    
    public BruteForceCTRL()
    {   
   
       
         calculateur = new BruteForceCalculator(15000);
          calculateur.addCalculatorListenerListener(this);
  
    } 
   
    public boolean getQualityParticule()
    { 
        return quality;
    }       
    
    public BruteForceCTRL(int nombreParticule)
    {   
        calculateur = new BruteForceCalculator(nombreParticule);
         
        calculateur.addCalculatorListenerListener(this);
    }  
    public void requestNewFrame()
    {
        calculateur.requestNewFrame();
    }
    /**
     * Cette méthode est appelé a chaque fois que le calculateur se met en arret, 
     * Elle change la qualité des particules s'il y a requette.
     * @param cal Le calculateur
     */
    public void  CalculateurHasStopped(BruteForceCalculator cal)
    {   
       
        if (requestChangeQuality)
        {  
            changeQualityT(quality);
            requestChangeQuality = false;
            StartCalculateur();
            
            
        }   
    } 
    
    /**
     * Cette méthode change la qualité des particules, 
     * @param quality vrai pour la haute qualité 
     */
    private void changeQualityT(boolean quality)
    {  
        //Pour l'instant la particule zero reste toujour en haute qualité
        for (int i = 1; i < calculateur.bodies.length; i++) 
        { 
            calculateur.bodies[i].highQuality = quality;     
           
        }  
         
    }   
    /**
     * Cette méthode est appelé quand on veut changer la qualité des particule
     * @param quality vrai pour la haute qualité 
     */
    public void requestChangeQuality(boolean quality)
    {
    
        requestChangeQuality = true;
        this.quality = quality;
        if (calculateur.IsStooped())
        { 
            changeQualityT(quality);
            requestChangeQuality = false;
        }
        else
        {  
            stopperCalculateur();
        } 
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
    
    public int GetNumberOfParticule()
    { 
    
        return calculateur.bodies.length;
        
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
    public void stopperCalculateur()
     {
         calculateur.roule =false;
     
     }
    
    
}
