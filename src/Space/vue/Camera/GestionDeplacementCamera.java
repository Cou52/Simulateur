/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.vue.Camera;
import Space.vue.Main;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
/**
 *
 * @author cou8
 */
public class GestionDeplacementCamera {
    public String modeDeplacement;
    
    public static enum Mode {
        LookAt, Free
    }
    private Vector2f PositionSouris;
    private long time;
    private int deltaTime;
    private float speedDeplacementCamera;
    public GestionDeplacementCamera()
    {  
        speedDeplacementCamera = 0.01f;
    }
    
     public GestionDeplacementCamera(float speed)
    {  
        setspeedDeplacementCamera(speed);
    }
    
    public void setspeedDeplacementCamera(float speed)
    {        
        speedDeplacementCamera = speed;
    }
    public float getspeedDeplacementCamera()
    {        
        return speedDeplacementCamera;
    }
    
    public void getModeDeplacement()
    {
    
    }
    public void setModeDeplacement(Enum mode)
    {
    
    } 
    public void AvancerCamera(Camera cam)
    {   
      Vector3f direction = cam.getDirection();
      Vector3f deplacement = direction.mult(speedDeplacementCamera);
      DeplacerCamera(deplacement, cam);
   }    
    
    public void ReculerCamera(Camera cam)
    {   
      Vector3f direction = cam.getDirection();
      Vector3f deplacement = direction.mult(-speedDeplacementCamera);
      DeplacerCamera(deplacement, cam);
     
   } 
    
   public void DeplacerCamera(Vector3f deplacement,  Camera cam) 
   {  
       Vector3f position = cam.getLocation().add(deplacement);
       cam.setLocation(position);
       cam.update();
   }        
    
    
            
}
