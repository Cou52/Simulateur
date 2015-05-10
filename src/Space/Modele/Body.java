/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Modele;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;


/**
 *
 * @author cou8
 */
public class Body {
    private static final float G = 6.673e-11f;
    public Vector3f force;
     public Vector3f position;
    public Vector3f speed;
    public float mass;
    public Spatial representation;
    public float radiussize;
    public boolean udapte = false;
 
public Body(Vector3f pPosition)
{   
    position = pPosition;
    speed = new Vector3f(0 , 0 ,0);
}
 public Body(Vector3f pPosition , Vector3f pSpeed, float pMass)
    {
       
        //no validation for speed
         speed = pSpeed;
         position = pPosition;
         mass = pMass ;
         resetForce();
    }
    
     public Body(Vector3f pPosition , Vector3f pSpeed, Vector3f pforce,float pMass)
    {
         //no validation for speed
         speed = pSpeed;
         position = pPosition;
         mass = pMass ;
         force = pforce;
         resetForce();
    }
     
    public Vector3f getForce() {
        return new Vector3f(force.x , force.y , force.z);
    }

    public Vector3f getPosition() {
        return new Vector3f(position.x ,position.y , position.z );
    }

    public Vector3f getSpeed() {
        return new Vector3f(speed.x ,speed.y , speed.z );
    }

    public double getMass() {
        return mass;
    }
   
    
    
    public Vector3f vectorUpdate(float dt)
    { 
        Vector3f deplacement;
        deplacement = new Vector3f(force.x , force.y , force.z);
        deplacement = deplacement.mult(dt);
        deplacement = deplacement.divide(mass);
        return deplacement;
    }       
    
    public void update(float dt)
    {               
            speed.x += dt *  this.force.x / mass;
            speed.y += dt * this.force.y / mass;
            speed.z += dt * this.force.z / mass;
             //System.out.println("Force");
            //System.out.println(force.x);
             //   System.out.println("Speed");
            //System.out.println(speed.x);
            position.x += dt * speed.x;
            position.y += dt * speed.y;
            position.z += dt * speed.z;
            
  
          
    } 
   public double distance(Body b)
   {  
       float x = this.position.x - b.position.x;
       float y = this.position.y - b.position.y;
       float z = this.position.z - b.position.z;
       return   Math.sqrt((x * x) + (y * y) + (z * z));
 
   }
    
   public void resetForce()
   {
       force = new Vector3f(0 , 0, 0);
   }
      
    
      // compute the net force acting between the body a and b, and
  // add to the net force acting on a
  public void addForce(Body b) {
    Body a = this;
    float EPS = 0.5f;      // softening parameter (just to avoid infinities)
    float dx = b.position.x - a.position.x;
    float dy = b.position.y - a.position.y;
    float dz = b.position.z - a.position.z;
   
    float dist = (float)Math.sqrt((dx*dx) + (dy*dy) +(dz*dz) );
    float F = (G * a.mass * b.mass) / (dist*dist + EPS*EPS);
   
    a.force.x += F * dx / dist;
    a.force.y += F * dy / dist;
    a.force.z += F * dz / dist;
   
   
  }
}
