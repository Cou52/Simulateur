/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Controleur.CalculateurCTRL;

import Space.Controleur.Interface.CTRLInterface;
import Space.Modele.Body;
import Space.Modele.bufferBody;
import Space.Modele.Calculateur.fractal_triangle_mainCalculator;
import com.jme3.math.Vector3f;

/**
 *
 * @author cou8
 */
public class fractal_CTRL implements CTRLInterface {
   /**
     * Cette méthode est appelé quand l'interface veut une nouvelle image
     * Le calculateur va mettre l'image suivante si elle est disponible
     * 
     */
    fractal_triangle_mainCalculator calculateur;
  
    //A faire
    public void ChangerNombreParticuleCalculateur(int nombre)
    {
     // TODO finir cette methode
    } 
    public fractal_CTRL()
    {   
        calculateur = new fractal_triangle_mainCalculator( 3000000, new Vector3f(0 ,0 ,0) ,100);
    }    
    public void requestNewFrame()
    {  
        calculateur.requestNewFrame();
    }      
    
    /**
     * Un fractal se termine par lui meme en étant fini //ToDO
     * @return 
     */
     public boolean isRunning()
     {
        return  true; //calculateur.roule;
     }   
    /**
     * 
     * @return la liste des objet dans l'image actuel
     */
    public bufferBody frameActuel()
    { 
        return calculateur.frameActuel();
    }
     /**
     * 
     * Arrete le calulateur 
     */
    public void stopCalculateur()
     {    
     }    
     /**
     * Initialise le calculateur, le calulateur initialise par la suite ses objet pour retourner la premiere framme
     * 
     */
    /**
     * 
     * Pars le calculateur.
     */
     public void StartCalculateur()
     {
         Thread t = new Thread(calculateur);
         t.start();
     }
     public void changeQuality(boolean quality)
    {
    }
     /**
      *  Cette méthode est utilisé par l'interface graphique pour avoir les objets
      * du calculateur. L'interface graphique va affecter une représentation graphique par la suite.
      * 
      * Note: Toute classe qui hérite de cette interface ne devrait pas permettre 
      * l'acces au objet quand le calculateur est en éxécution.
      * @return la liste des body du calculateur. 
      * 
      */
     public Body[] getBodies()
     {
         return calculateur.ListePoint;
     }
     /**
      * Met en memoire les objets du calculateur
      */
    public void bufferise(Body[] buffer)
    {     
        calculateur.memoire = new bufferBody(buffer);
        calculateur.memoireDernierCalcule = calculateur.memoire;
    }    
    
    public void init()
    {
        calculateur.run();
    }
}
