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
;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;



/**
 *
 * @author cou8
 */
public class JPanelInterface {
    public static String BRUTE_FORCE_GRAVITE = "brute force";
     public static String FRACTAL_TRIANGLE = "Fractal triangle";
    
    public Main JMonkeyInnterface;
    public JPanel MainCanvas;
    public JPanel panneauNord;
    JMenu menu ;
     JPanel panel;
    private ecouteur listen = new ecouteur();
    
    public JPanelInterface()
    {   
           java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        // create new JME appsettings
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1024);
        settings.setHeight(768);
        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Fractal");
        JMenuItem menuItem = new JMenuItem(FRACTAL_TRIANGLE);
        JMenuItem menuItem2 = new JMenuItem(BRUTE_FORCE_GRAVITE);
        menu.add(menuItem);
        menu.add(menuItem2);
        menuItem.addActionListener(listen);
         menuItem2.addActionListener(listen);
        // create new canvas application
        JMonkeyInnterface = new Main();
        JMonkeyInnterface.setSettings(settings);
        JMonkeyInnterface.createCanvas(); // create canvas!
        JmeCanvasContext ctx = (JmeCanvasContext) JMonkeyInnterface.getContext();
        ctx.setSystemListener(JMonkeyInnterface);
        Dimension dim = new Dimension(1024, 768);
        ctx.getCanvas().setPreferredSize(dim);
        menuBar.add(menu);
        // Create Swing window
        JFrame window = new JFrame("Swing Application");
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
    });
  
    }   
    public static void main(String[] args)
   {
    
        JPanelInterface inter = new JPanelInterface();

  
    }    
    
    private class ecouteur implements ActionListener , ItemListener 
    {
        public void actionPerformed(ActionEvent e) {
           JMenuItem source = ((JMenuItem) e.getSource());
           if (!source.getText().equals(JMonkeyInnterface.mode))
           {
                if (source.getText().equals(BRUTE_FORCE_GRAVITE))
                {    
                    JMonkeyInnterface.removeAll();
                    JMonkeyInnterface.bruteForce();
                }
                if (source.getText().equals(FRACTAL_TRIANGLE))
                {    
                    JMonkeyInnterface.removeAll();
                    JMonkeyInnterface.fractal_Triangle();
                }
          }
          
        }

        public void itemStateChanged(ItemEvent e) {
           System.out.println( e.paramString());
         }
    }
}
