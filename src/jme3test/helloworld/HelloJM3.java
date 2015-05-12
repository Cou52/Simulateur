package jme3test.input;

import com.jme3.app.SimpleApplication;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import java.awt.event.KeyEvent;

public class HelloJM3 extends SimpleApplication implements AnalogListener, ActionListener {

    private Geometry teaGeom;
    private ChaseCamera chaseCam;
    private Node pivot;

    public static void main(String[] args) {
        HelloJM3 app = new HelloJM3();
        app.start();
    }

    public void simpleInitApp() {
          Sphere mass1 = new Sphere(30, 30, 5f, true , true);
                Spatial teaGeom = new Geometry("", mass1);
                 Material mat1 = new Material(assetManager, 
                         "Common/MatDefs/Misc/Unshaded.j3md");
                mat1.setColor("Color", ColorRGBA.Black);
       
        pivot = new Node("pivot");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/ShowNormals.j3md");
        teaGeom.setMaterial(mat);
        pivot.attachChild(teaGeom);
        
        Geometry ground = new Geometry("ground", new Quad(50, 50));
        ground.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        ground.setLocalTranslation(-25, -1, 25);
        ground.setMaterial(mat);
        pivot.attachChild(ground);

        // Disable the flyby cam
        flyCam.setEnabled(false);

        // Enable a chase cam
        chaseCam = new ChaseCamera(cam, teaGeom, inputManager);
        
        //Uncomment this to invert the camera's vertical rotation Axis 
        //chaseCam.setInvertVerticalAxis(true);

        //Uncomment this to invert the camera's horizontal rotation Axis
        //chaseCam.setInvertHorizontalAxis(true);

        //Comment this to disable smooth camera motion
        chaseCam.setSmoothMotion(true);
        
        //Uncomment this to disable trailing of the camera 
        //WARNING, trailing only works with smooth motion enabled and is the default behavior
        //chaseCam.setTrailingEnabled(false);

        //Uncomment this to look 3 world units above the target
        //chaseCam.setLookAtOffset(Vector3f.UNIT_Y.mult(3));

        //Uncomment this to enable rotation when the middle mouse button is pressed (like Blender)
        //WARNING : setting this trigger disable the rotation on right and left mouse button click
        //chaseCam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));

        //Uncomment this to set mutiple triggers to enable rotation of the cam
        //Here spade bar and middle mouse button
        //chaseCam.setToggleRotationTrigger(new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE),new KeyTrigger(KeyInput.KEY_SPACE));
        
        //registering inputs for target's movement
        regsiterInput();

        rootNode.attachChild(pivot);
    }

    public void regsiterInput() {
        inputManager.addMapping("moveForward", new KeyTrigger(keyInput.KEY_UP));
        inputManager.addMapping("moveBackward", new KeyTrigger(keyInput.KEY_DOWN));
        inputManager.addMapping("moveRight", new KeyTrigger(keyInput.KEY_RIGHT));
        inputManager.addMapping("moveLeft", new KeyTrigger(keyInput.KEY_LEFT));
        inputManager.addMapping("displayPosition", new KeyTrigger(keyInput.KEY_P));
        inputManager.addListener(this, "moveForward", "moveBackward", "moveRight", "moveLeft");
        inputManager.addListener(this, "displayPosition");
    }

    public void onAnalog(String name, float value, float tpf) {
        if (name.equals("moveForward")) {
            teaGeom.move(0, 0, -5 * tpf);
        }
        if (name.equals("moveBackward")) {
            teaGeom.move(0, 0, 5 * tpf);
        }
        if (name.equals("moveRight")) {
            teaGeom.move(5 * tpf, 0, 0);
        }
        if (name.equals("moveLeft")) {
            teaGeom.move(-5 * tpf, 0, 0);

        }

    }

    public void onAction(String name, boolean keyPressed, float tpf) {
        if (name.equals("displayPosition") && keyPressed) {
            teaGeom.move(10, 10, 10);

        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

        //  teaGeom.move(new Vector3f(0.001f, 0, 0));
        // pivot.rotate(0, 0.00001f, 0);
        //   rootNode.updateGeometricState();
    }
//    public void update() {
//        super.update();
//// render the viewports
//        float tpf = timer.getTimePerFrame();
//        state.getRootNode().rotate(0, 0.000001f, 0);
//        stateManager.update(tpf);
//        stateManager.render(renderManager);
//        renderManager.render(tpf);
//    }
}

