/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Space.vue;


import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;




/**
 *
 * @author cou8
 */
public class JPanelInterface {
    public static String BRUTE_FORCE_GRAVITE = "brute force";
    public static String FRACTAL_TRIANGLE = "Fractal triangle";
    public static String OPTION_GENERAL = "Option";
    public static String Version = "Simulateur 0.1.2";
    public Main JMonkeyInnterface;
    public JPanel MainCanvas;
    public JPanel panneauNord;
    public JMenuItem menuItem;
    public JMenuItem menuItem2;
    public JMenuItem menuItem3;
    
    JMenu menu ;
     JMenu Option ;
     JPanel panel;

    
    public JPanelInterface()
    {   
         
        // create new JME appsettings
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1024);
        settings.setHeight(768);
        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Fractal");
        Option = new JMenu("Option");
        menuItem = new JMenuItem(FRACTAL_TRIANGLE);
        menuItem2 = new JMenuItem(BRUTE_FORCE_GRAVITE);
        
        menuItem3 = new JMenuItem(OPTION_GENERAL);
      
        menu.add(menuItem);
        menu.add(menuItem2);
        
        Option.add(menuItem3);
     
        // create new canvas application
        JMonkeyInnterface = new Main();
        JMonkeyInnterface.setSettings(settings);
        JMonkeyInnterface.createCanvas(); // create canvas!
        JmeCanvasContext ctx = (JmeCanvasContext) JMonkeyInnterface.getContext();
        ctx.setSystemListener(JMonkeyInnterface);
        Dimension dim = new Dimension(1024, 768);
        ctx.getCanvas().setPreferredSize(dim);
        menuBar.add(menu);
          menuBar.add(Option);
        // Create Swing window
        JFrame window = new JFrame(Version);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panneauNord = new JPanel();
        panneauNord.setLayout(new GridLayout(0, 4));
        panneauNord.add(menuBar);
      
        // Fill Swing window with canvas and swing components
         panel = new JPanel(new FlowLayout()); // a panel
        panel.setLayout(new BorderLayout());
        panel.add(panneauNord, BorderLayout.NORTH);
        panel.add(ctx.getCanvas() , BorderLayout.CENTER);  // add JME canvas
        // add some Swing
        window.add(panel);
        window.pack();

        // Display Swing window including JME canvas!
        window.setVisible(true);
        JMonkeyInnterface.startCanvas();
      

  
    }   
    public static void main(String[] args)
   {
    
        JPanelInterface inter = new JPanelInterface();

  
    }    
   
}
