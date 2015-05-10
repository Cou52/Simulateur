package Space.vue;

import Space.Controleur.CTRLInterface;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Geometry;
import com.jme3.material.Material;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.math.Ray;
import com.jme3.scene.Spatial;
import Space.Modele.Calculateur.BruteForceCalculator;
import Space.Controleur.BruteForceCTRL;
import Space.Controleur.fractal_CTRL;
import Space.Modele.bufferBody;
import Space.Modele.Body;
import com.jme3.font.BitmapText;
 import com.jme3.scene.Mesh;
 import com.jme3.scene.Mesh.Mode;
import com.jme3.util.BufferUtils;
import com.jme3.scene.VertexBuffer.Type;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.Dimension;
import java.awt.FlowLayout;



/**
 * http://beltoforion.de/barnes-hut/barnes-hut_en.html
 * http://homepages.inf.ed.ac.uk/cdubach/papers/dubach12pldi.pdf
 * http://math.unice.fr/~brunov/download/Alg%C3%A8bre%20lin%C3%A9aire%20pour%20tous.pdf
 * t
 * est
 * @author normenhansen
 */
public class Main extends SimpleApplication  implements AnimEventListener  {

  private MainCamera Oeil;
  private Geometry mark;
  private Node pivot;     
  private ChaseCamera chaseCam;
  private CTRLInterface calulateur;
  private ListenerClass doubleclick ;
  private bufferBody currentFrame;
  private float frametime = 0.008f;
  private float current = 0;
  Mesh mesh = new Mesh();
  
  private double lastTimeFrampe =0;
  private int frameTotal = 0;;
  private int time = 0;
  BitmapText frameperSecond;
  Vector3f verticle[] ;
  int itera = 0;
  public String mode;
  
  public static void main(String[] args) {
    
      Main main = new Main();

  
  }
  
  @Override
  public void simpleInitApp() {
      doubleclick = new ListenerClass(this);
      bruteForce();
  }
  public void fractal_Triangle()
  {
       itera = 0;
      
        initMark();
        initKeys();
        mode = JPanelInterface.FRACTAL_TRIANGLE;
        verticle = new Vector3f[6000000];
        calulateur = new fractal_CTRL();
        calulateur.init();
        Body bodies[] = calulateur.getBodies();


        Oeil = new MainCamera();
        Oeil.setPointObservation(new Vector3f(0 ,0, 0));


        Oeil.gettwoAngle(cam.getLocation(), Oeil.getPointObservation());  
        cam.setLocation(Oeil.getPositionActuel());
        Oeil.setRayon(50);

        cam.lookAt(Oeil.getPointObservation(), new Vector3f(0, 1, 0)); 
        pivot = new Node("pivot");
        rootNode.attachChild(pivot);
        frameperSecond = new BitmapText(guiFont, false);
        frameperSecond.setSize(guiFont.getCharSet().getRenderedSize());
        frameperSecond.setLocalTranslation(100, 700, 0);
        frameperSecond.setText("t"); // crosshairs

        frameperSecond.setColor(ColorRGBA.Blue);

         



        guiNode.attachChild(frameperSecond);
        Body buffer[] = new Body[bodies.length];
        for (int i = 1 ;i < bodies.length  ; i++)
        {                         
                sphereRepresentation(bodies[i].position , bodies[i] );    
                buffer[i] = new Body(bodies[i].position , bodies[i].speed, bodies[i].mass);
                buffer[i].representation =  bodies[i].representation;
        }

        sphereRepresentationBlack(bodies[0].position ,  bodies[0]);
        //sphereRepresentationBlack(bodies[bodies.length -1].position ,  bodies[bodies.length - 1]);

        buffer[0] = new Body(bodies[0].position , bodies[0].speed, bodies[0].mass);
        buffer[0].representation =  bodies[buffer.length -1].representation;
                //buffer[buffer.length -1] = new Body(bodies[buffer.length -1].position , bodies[buffer.length -1].speed, bodies[buffer.length -1].mass);
                //buffer[buffer.length -1].representation =  bodies[buffer.length -1].representation;
     
        calulateur.bufferise(buffer);
        Geometry geo = new Geometry("OurMesh", mesh);
        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(verticle));
        //calulateur.StartCalculateur();
        mesh.setMode(Mode.Points);
        mesh.setStatic();
         mesh.setPointSize(1.2f);
          mesh.updateBound();
        Material mat = new Material(assetManager, 
       "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geo.setMaterial(mat);
        rootNode.attachChild(geo);
        
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
  }
  public void bruteForce()
  { 
         Oeil = new MainCamera();
        Oeil.setPointObservation(new Vector3f(0 ,0, 0));
          verticle = new Vector3f[600000];
       itera = 0;
        Oeil.gettwoAngle(cam.getLocation(), Oeil.getPointObservation());  
        cam.setLocation(Oeil.getPositionActuel());
        Oeil.setRayon(50);
        initMark();
        initKeys();
      
        mode = JPanelInterface.BRUTE_FORCE_GRAVITE;
        pivot = new Node("pivot");
       
        calulateur = new BruteForceCTRL();
        calulateur.init();
          rootNode.attachChild(pivot);
        Body bodies[] = calulateur.getBodies();
        frameperSecond = new BitmapText(guiFont, false);
        frameperSecond.setSize(guiFont.getCharSet().getRenderedSize());
        frameperSecond.setLocalTranslation(100, 700, 0);
        frameperSecond.setText("t"); // crosshairs

        frameperSecond.setColor(ColorRGBA.Blue);
        
        guiNode.attachChild(frameperSecond);
        Body buffer[] = new Body[bodies.length];
        for (int i = 1 ;i < bodies.length  ; i++)
        {                         
                bodies[i].highQuality = true;
                sphereRepresentation(bodies[i].position , bodies[i] );    
                buffer[i] = new Body(bodies[i].position , bodies[i].speed, bodies[i].mass);
                buffer[i].representation =  bodies[i].representation;
                buffer[i].highQuality = true;
        }

        sphereRepresentationBlack(bodies[0].position ,  bodies[0]);
        
         buffer[0] = new Body(bodies[0].position , bodies[0].speed, bodies[0].mass);
         buffer[0].representation =  bodies[buffer.length -1].representation;
                //buffer[buffer.length -1] = new Body(bodies[buffer.length -1].position , bodies[buffer.length -1].speed, bodies[buffer.length -1].mass);
                //buffer[buffer.length -1].representation =  bodies[buffer.length -1].representation;
         bodies[0].highQuality = true;
         buffer[0].highQuality = true;
        Geometry geo = new Geometry("OurMesh", mesh);
        mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(verticle));
        //calulateur.StartCalculateur();
        mesh.setMode(Mode.Points);
        
         mesh.setPointSize(3.0f);
          mesh.updateBound();
        Material mat = new Material(assetManager, 
       "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geo.setMaterial(mat);
        rootNode.attachChild(geo);
        calulateur.bufferise(buffer);
      
        rootNode.attachChild(frameperSecond);
        calulateur.StartCalculateur();
         viewPort.setBackgroundColor(ColorRGBA.LightGray);
       
  }      
  public void removeAll()
  { 
      calulateur.stopCalculateur();
      rootNode.detachAllChildren();
  }       
  public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
 
  }
 
  public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    // unused
  }
  /* Use the main event loop to trigger repeating actions. */
    @Override
    public void simpleUpdate(float tpf) {
       //TODO Verifier si le frame n'a pas encore changer
       
        itera = 0;
       current += tpf;
       if  (frametime <= current)
       {       
           current =  current - frametime; 
           calulateur.requestNewFrame();
           bufferBody affichage = calulateur.frameActuel();
           if (calulateur.frameActuel() != null)
            {

               if (currentFrame != affichage)
               {   
                   frameperSecond.setText("t=");
                   currentFrame = affichage;
                   Body[] allbody = currentFrame.getBodys();
                   //La grosse phere noire
                   sphereRepresentationBlack(allbody[0].getPosition() , allbody[0]);
                  
                    for (int i = 1 ;i < allbody.length ; i++)
                    {                         
                       
                        sphereRepresentation(allbody[i].getPosition() , allbody[i] );   
                        
                    }
                     mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(verticle));
                     mesh.updateBound();
                     
                    double frame = 1000 / (System.currentTimeMillis() - lastTimeFrampe) ;
                     if (time < 20000)
                     {
                        frameTotal+= 1;
                        if ( 1< frameTotal)
                        {
                            time+= (System.currentTimeMillis() - lastTimeFrampe);
                        }
                      }
                     //System.out.println(itera + "t= " + frame + " :total = " + frameTotal);
                    frameperSecond.setText("t= " + frame + " :total = " + frameTotal);
                    lastTimeFrampe = System.currentTimeMillis();
                    
               }
            }
          }  
    }
    
    
     public void sphereRepresentationBlack(Vector3f position , Body b)
    {
       //System.out.println(position);
       if(b.representation == null)
       {
        
                Sphere mass1 = new Sphere(30, 30, 5f, true , true);
                Spatial yellow = new Geometry("", mass1);
                 Material mat1 = new Material(assetManager, 
                         "Common/MatDefs/Misc/Unshaded.j3md");
                mat1.setColor("Color", ColorRGBA.Black);
                yellow.setLocalTranslation(position);
                yellow.setMaterial(mat1);
                b.representation = yellow;
                pivot.attachChild(yellow);

                itera++;
           
        }
       else
        {
           //System.out.println("modification");
            b.representation.setLocalTranslation(position);
        }
        
    }
    
    public void sphereRepresentation(Vector3f position , Body b)
    {
       //System.out.println(position);
      
            
           if  ( b.highQuality)
           {
                if (b.representation == null)
                {
                    Sphere mass1 = new Sphere(10, 10, 0.3f, true , true);
                    Spatial yellow = new Geometry("", mass1);
                    Material mat1 = new Material(assetManager, 
                            "Common/MatDefs/Misc/Unshaded.j3md");
                    mat1.setColor("Color", ColorRGBA.randomColor());
                    yellow.setLocalTranslation(position);
                    yellow.setMaterial(mat1);
                    b.representation = yellow;
                    pivot.attachChild(yellow);
                }
                else
                 {   
                     b.representation.setLocalTranslation(position);
                 }
           }
           else
            { 
                  verticle[itera] = position;
                  itera++;
            }  
  
    }
  /** Custom Keybinding: Map named actions to inputs. */
  private void initKeys() {
    inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addMapping("ZoomIn" , new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
    inputManager.addMapping("ZoomOut" , new MouseAxisTrigger(MouseInput.AXIS_WHEEL , true));
   
    inputManager.addMapping("rotation" , new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addMapping("rotationR" , new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
    
    inputManager.addMapping("doubleclick" , new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addMapping("stop" , new KeyTrigger(KeyInput.KEY_P));
     inputManager.addMapping("start" , new KeyTrigger(KeyInput.KEY_S));
    
    this.flyCam.setEnabled(false);
    inputManager.addListener(actionListener, "doubleclick");
    inputManager.addListener(actionListener, "stop");
   inputManager.addListener(actionListener, "start");
    
    inputManager.addListener(analogListener, "Walk");
    inputManager.addListener(analogListener, "rotation");
    inputManager.addListener(analogListener, "rotationR");
    inputManager.addListener(analogListener, "ZoomIn");
    inputManager.addListener(analogListener, "ZoomOut");
  }
 private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("doubleclick") && !keyPressed) {
          if (doubleclick.checkDoubleClick(System.currentTimeMillis()))
          {
               selectobject();
          }
       
            
      }
        System.out.println(name);
      if (name.equals("stop"))
      {   
         
           System.out.println("t");
          calulateur.stopCalculateur();
           
          
         
      }
      if (name.equals("start"))
      { 
          System.out.println("1");
          calulateur.StartCalculateur();
      }
    }
  };
  private AnalogListener analogListener = new AnalogListener() {
    public void onAnalog(String name, float value, float tpf) 
    { 
    
         
         if(name.equals("ZoomIn"))
         {
             Oeil.setRayon(Oeil.getRayon() - 3000.0 * tpf);
             deplacerRotation(0.0 , 0.0 ,new Vector3f(0 ,0 ,0));
         }
         if(name.equals("ZoomOut"))
         {
             Oeil.setRayon(Oeil.getRayon() +  3000.0 * tpf);
             deplacerRotation(0.0 , 0.0 ,new Vector3f(0 ,0 ,0));
           
         }              
         if (name.equals("rotation")) 
         {                             
            deplacerRotation( 0.0 , 30 * tpf ,new Vector3f(0 ,0 ,0));
         }  
         if(name.equals("rotationR"))
         { 
            deplacerRotation(  30 * tpf, 0.0 ,new Vector3f(0 ,0 ,0));
         }  
       
         
    }
  };
  
  public void selectobject()
  { 
            System.out.println("DoubleClick");
            CollisionResults results = new CollisionResults();

            Vector2f click2d = inputManager.getCursorPosition();
           
            Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f);

            Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
            Ray ray = new Ray(click3d, dir);
            rootNode.collideWith(ray, results);
            /*
            for (int i = 0; i < results.size(); i++) {             
                float dist = results.getCollision(i).getDistance();
                Vector3f pt = results.getCollision(i).getContactPoint();
                String target1 = results.getCollision(i).getGeometry().getName();
                System.out.println("Selection #" + 0 + ": " + target1 + " at " + pt + ", " + dist);
            }
            * */
          if (results.getClosestCollision()!= null)
          {
             // System.out.println(results.getClosestCollision().getGeometry().getName());
              Oeil.setPointObservation(results.getClosestCollision().getGeometry().getLocalTranslation());
              Oeil.gettwoAngle(Oeil.getPositionActuel(), Oeil.getPointObservation());
              //System.out.print(Oeil.getPositionActuel());
            
          }
           
  }
  /**
   * Permet le déplacement circulaire de la camera autour d'un objet. 
   * @param deltaPolar  le changement de l'anflew Polair +-
   * @param deltaAzimuthal le changement de l'angle azimuthal +-
   * @param pointAregarder Useless for now
   */
  public void deplacerRotation(double deltaPolar ,double deltaAzimuthal, Vector3f pointAregarder )
  {    
        Oeil.deplacerRotation(deltaPolar ,deltaAzimuthal );
        cam.setLocation(Oeil.getPositionActuel());
        cam.lookAt(Oeil.getPointObservation(), Oeil.getVecteurUP());        
  }    
   /** A red ball that marks the last spot that was "hit" by the "shot". */
  protected void initMark() {
    Sphere sphere = new Sphere(5, 5, 1f);
    mark = new Geometry("BOOM!", sphere);
    Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mark_mat.setColor("Color", ColorRGBA.Red);
    mark.setMaterial(mark_mat);
  }

}


