/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Controleur;

import Space.Modele.bufferBody;
import Space.Modele.Body;
/**
 *
 * @author cou8
 * Interface qui permet a l'interface graphique de communiquer avec un calculateur
 * Utilisation:
 * Chaque classe qui implemente cette interface doit avoir un calculateur comme attribut.
 */
public interface CTRLInterface {
    /**
     * Cette méthode est appelé quand l'interface veut une nouvelle image
     * Le calculateur va mettre l'image suivante si elle est disponible
     * 
     */
    public void requestNewFrame();
    /**
     * 
     * @return la liste des objet dans l'image actuel
     */
    public bufferBody frameActuel();
     /**
     * 
     * Arrete le calulateur 
     */
    public void stopCalculateur();
     /**
     * Initialise le calculateur, le calulateur initialise par la suite ses objet pour retourner la premiere framme
     * 
     */
    /**
     * 
     * Pars le calculateur.
     */
     public void StartCalculateur();
     public boolean isRunning();
     /**
      *  Cette méthode est utilisé par l'interface graphique pour avoir les objets
      * du calculateur. L'interface graphique va affecter une représentation graphique par la suite.
      * 
      * Note: Toute classe qui hérite de cette interface ne devrait pas permettre 
      * l'acces au objet quand le calculateur est en éxécution.
      * @return la liste des body du calculateur. 
      * 
      */
     public Body[] getBodies();
     /**
      * Met en memoire les objets du calculateur
      */
     public void bufferise(Body[] buffer);
             
    
    public void init();
}
