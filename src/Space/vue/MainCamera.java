/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.vue;

import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;

/**
 * Cette classe est la camera principal de l'application
 * @author cou8
 */
public class MainCamera {
    
    
    private double anglePolair;
    private double angleAzimuthal ;
    private double rayon = 20;
    private int wait = 11;
    private  Vector3f Up ;
    private  Vector3f pointObservation ;
    private  Vector3f positionActuel;
    /**
     * Derniere position de la souris connue, Utilisé pour déplacer la camera
     */
    private Vector2f lastMousePosition;
    
    /**
     * Vitesse de déplacement de la camera
     */
 
    private float speed;
    
    public MainCamera()
    {  
        anglePolair = 0;
        anglePolair = 0;
        Up = new Vector3f(0 , 0 , -1);
    }     
    public Vector3f getPositionActuel() {
        return positionActuel;
    }
    public Vector2f getLastMousePosition()
    { 
        Vector2f retour = null;
        if  (lastMousePosition!= null)
        {  
            retour = new Vector2f( lastMousePosition.x , lastMousePosition.y);
        } 
        return retour;
    }  
    
 
    public void setLastMousePosition(Vector2f pLastMousePosition)
    { 
        lastMousePosition = pLastMousePosition;
    } 
    
    public Vector3f getVecteurUP()
    {     
        return new Vector3f(Up.x , Up.y , Up.z);
    }
    private void setPositionActuel(Vector3f positionActuel) {
        this.positionActuel = positionActuel;
    }
       
    /**
     * Modifie le point d'observation, si null, la camera n'aura aucun point d'observation
     * @param paramPointObservation 
     */
    public void setPointObservation(Vector3f paramPointObservation)
    {       
       pointObservation = paramPointObservation ;
    }
    /**
     * Modifie le point d'observation et la camera va regarder le point
     * @param paramPointObservation 
     */
    public void setPointObservationAndLookAt(Vector3f paramPointObservation )
    { 
       if (paramPointObservation != null)
       {
            setPointObservation(paramPointObservation);
            this.gettwoAngle(positionActuel, pointObservation);
            deplacerRotation(0 , 0);
       } 
    }     
    
   /** 
    * @return un vecteur qui représente le point observé de la camera princiapl
    */
    public Vector3f getPointObservation()
    { 
       return new Vector3f(pointObservation.x ,pointObservation.y , pointObservation.z );
    }      
    
    /**
     * @return  etourne l'angle polaire entre le point observé et la camera
     */
    public double getAnglePolair() {
        return anglePolair;
    }
    
    private void setAnglePolair(double anglePolair) {
        this.anglePolair = anglePolair;
        if (anglePolair >= Math.PI)
        {    
            Up.z = -1;
        }
        else
        {  
             Up.z = 1;
        }  
    }
    
    /**
     * 
     * @return  l'angle azimuthat entre le point d'observation et la camera
     */
    public double getAngleAzimuthal() {
        return angleAzimuthal;
    }
    /**
     * set l'angle Azimuthat
     * @param angleAzimuthal 
     */
    private void setAngleAzimuthal(double angleAzimuthal) {
        this.angleAzimuthal = angleAzimuthal;
    }
    
    /**
     * 
     * @return la distance entre le point observé et la camera
     */
    public double getRayon() {
        return rayon;
    }
    /**
     * Modifie la distance entre la camera et le point d'observation
     * @param rayon la distance entre le rayon et la camera
     */
    public void setRayon(double rayon) {
        this.rayon = rayon;
    }
    /**
     * 
     * @return le nombre de cycle d'attente
     */
    public int getWait() {
        return wait;
    }
    
    /**
     * Modifie le nombre de cicle d'attente
     * @param wait le nouvel nombre d'attente
     */
    public void setWait(int wait) {
        this.wait = wait;
    }
    public void addPolar(double deltapolar)
    {
        this.setAnglePolair(this.getAnglePolair() + deltapolar);
        if (anglePolair > Math.PI * 2)
        {
            anglePolair = anglePolair - (Math.PI  * 2);
        }
        else if (0 > anglePolair)
        { 
             anglePolair = anglePolair + (Math.PI * 2);
        }      
                
    }
    
    
    public void addAngleAzimuthal(double deltaangleAzimuthal)
    {
        this.setAngleAzimuthal(this.getAngleAzimuthal() + deltaangleAzimuthal);
        if (angleAzimuthal > Math.PI * 2)
        {
            angleAzimuthal = angleAzimuthal - (Math.PI * 2);
        }
        else if (0 > angleAzimuthal)
        { 
             angleAzimuthal = angleAzimuthal + (Math.PI * 2);
        }      
                
    }
    /**
     * Déplace la camera selon le point d'observation et le rayon
     * @param deltaPolar
     * @param deltaAzimuthal
     * @param pointAregarder 
     */
    public void deplacerRotation(double deltaPolar ,double deltaAzimuthal )
    {    

        if (wait > 10)
        { 
            this.addPolar(deltaPolar);
            this.addAngleAzimuthal(deltaAzimuthal);
            Vector3f newPosition = new Vector3f();
            double x = rayon * Math.cos(angleAzimuthal) * Math.sin(anglePolair);
            double y = rayon * Math.sin(angleAzimuthal) * Math.sin( anglePolair);
            double z = rayon * Math.cos(anglePolair) ;

            newPosition.x = 0 +(float)x + pointObservation.x;
            newPosition.y = 0 +(float)y +  pointObservation.y;
            newPosition.z = 0 + (float)z + pointObservation.z;

            //System.out.println( newPosition);
            this.setPositionActuel(newPosition);
            wait = 0;
        }
        
        wait++;
       // System.out.println(newPosition);
        //System.out.println(newPosition);          
                    
  }    
  
    
    /**
   * Cette méthode trouve l'angle polair le rayon et l'angle azimuthal.
   * Fonction non terminé
   * @param position Le point a calculer
   * @param pointAregarder Le point 0, ne doit pas etre null
   * 
   */
  public void gettwoAngle(Vector3f position ,Vector3f pointAregarder )
  {  
      
      double r = Math.sqrt((position.x * position.x) + (position.y * position.y) + (position.z * position.z));
      
      double Tpolar =  position.z / r;
      //System.out.println("rayon r ");
      double polar = Math.acos(Tpolar);
      double zeroCausious = r * Math.sin(polar);
      double Tazimuthal = 0;
      double azimuthal = 0;
      
      if (zeroCausious == 0)
      {   
           System.out.println("tata");
      }    
      if (zeroCausious != 0 )
      {   
          Tazimuthal  = position.y / (r * Math.sin(polar));
          azimuthal = Math.asin(Tazimuthal);
      }
      
     
      this.setRayon(r);  
      this.setAngleAzimuthal(azimuthal);
      this.setAnglePolair(polar);
      this.deplacerRotation(0, 0);
   
  }
}
