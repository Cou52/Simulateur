/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.Modele;

import com.jme3.math.Vector3f;

/**
 *
 * @author cou8
 */
public class bufferBody {
    public double position;
    private Body[] bodies;
    private bufferBody nextBuffer;
    
    public  bufferBody( Body[] pBodies)
    {     
         bodies = pBodies;
    }  
    
    public Body[] getBodys()
    {
        Body[] retour = new Body[bodies.length];
        for (int i = 0 ;i < bodies.length ; i++)
        {        
            retour[i] = new Body(bodies[i].getPosition() ,bodies[i].getSpeed() , bodies[i].force , bodies[i].mass );   
            retour[i].representation = bodies[i].representation;
            retour[i].radiussize =  bodies[i].radiussize;
            retour[i].highQuality =  bodies[i].highQuality;
        }
        return retour;
    }
    /**
     * 
     * @param next
     * @return 
     */
    public bufferBody setNextBuffer(bufferBody next)
    {    
        this.nextBuffer = next ;
        return getNextBuffer();
    }   
    
    public bufferBody getNextBuffer() 
    { 
        return nextBuffer;
    }
    public boolean isLastBuffer()
    {
        return nextBuffer == null;
    }
    public int getNumberBufferAfterHim()
    {
        int nombre = 0;
        bufferBody iterator = this;
        while (!iterator.isLastBuffer())
        {
            iterator = iterator.getNextBuffer();
            nombre++;
        }
        return nombre;
    }
}
