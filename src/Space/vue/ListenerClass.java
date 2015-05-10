/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.vue;

/**
 *
 * @author cou8
 */


/**
 * Cette classe est pour les evenement de la souris plus difficile a gérer, (double click)
 * @author cou8
 */
public class ListenerClass 
{
   public double lastClick = 0;; 
   public boolean islastClick = false;
   public double timeforDoubleClick = 200;
   public boolean ckecknow = true;
   
   public  ListenerClass(Main pApplication)
   {  
   }     
   
   /**
    * Vérifie si l'utilisateur a fait un double ckick gauche
    * @param now le click actuel
    * @return  Retourne vrai si l'utilisateur a fait un double click
    */
   public boolean checkDoubleClick(double now )
   {
       boolean retour = false;
       // A t-il eu un ckick avant

       if (ckecknow) 
       {
           if (islastClick)
           {   
                double dif =  now - lastClick;
                 // si le temps est plus petit que le temps pour faire un double click, c'est un double click
                 if (timeforDoubleClick >= dif)
                 {   
                    retour = true;
                    islastClick = false;
                 }
                else
                {
                    lastClick = now;
                }
            } 
            else
            { 
                islastClick = true;  
                lastClick = now;
            }   
       
        }
   return retour;
   } 
   
}
