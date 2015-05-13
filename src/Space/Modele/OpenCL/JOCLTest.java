

package Space.Modele.OpenCL;


import Space.Modele.Body;
import Space.Modele.Calculateur.BruteForceListener;
import com.jme3.math.Vector3f;

import java.util.ArrayList;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_command_queue;
import org.jocl.cl_context;
import org.jocl.cl_context_properties;
import org.jocl.cl_device_id;
import org.jocl.cl_kernel;
import org.jocl.cl_mem;
import org.jocl.cl_platform_id;
import org.jocl.cl_program;
import javax.swing.event.EventListenerList;



/*
 * JOCL - Java bindings for OpenCL
 * 
 * Copyright 2009 Marco Hutter - http://www.jocl.org/
 */




import static org.jocl.CL.*;
import static org.jocl.CL.CL_DEVICE_MAX_WORK_GROUP_SIZE;

/**
 * A small JOCL sample.
 */
public class JOCLTest implements Runnable
{
        public int nombreThread;
        public int id;
        cl_device_id device;
        cl_context context;
        cl_command_queue commandQueue;
        cl_kernel kernel;
        cl_program program;
         public EventListenerList  listListenner   = new EventListenerList();;
        public static int local_size = 256;
    
   private static String franctalTriangle = ""
           + "sampleKernel(__global const float *a,"+
        "             __global const float *b,"
            +         "__global const float *c,"
            +          "__global const float *mass,"
            +           "__global const float *bnarticule," +
        "             __global float *d)" 
           + "int gid = get_global_id(0);"
           + ""
           + ""
           + ""
           + ""
           + ""
           + ""
           + ""
           + ""
           + "";
    
  
    private static String programSource =
        "__kernel void "+
        "sampleKernel(__global const float *a,"+
        "             __global const float *b,"
            +         "__global const float *c,"
            +          "__global const float *mass,"
            +           "__global const float *bnarticule," +
        "             __global float *d)"+
        "{"+
           "      int gid = get_global_id(0);"
            + "                  "
            + "                      "  
            + " float G = 6.673e-11;"
            + " "
            + " "
            + "  float xthis = a[gid];"
            + " float ythis = b[gid]; "
            + " float zthis = c[gid];"
            + " "
            + ""
            + "int nb= bnarticule[0];"
            + "int droit = gid * 3;"
            + "" +
              
                ""
            +     ""
            + " "
            + "  ;"
            + "  "
            + ""
            + "float ix = 0;"
            + "   float iy = 0;"
            + "   float iz = 0; "
            + "for(int i=0; i < nb; i++) {" 
                
            + "     if (i != gid) {"
            + "     float dx = a[i] - xthis;"
            + "     float dy = b[i] - ythis;"
            + "     float dz = c[i] - zthis;"
            + "     float dis = sqrt((dx * dx) + (dy * dy) + (dz * dz) ); "
            + "     float f = (G * mass[gid] * mass[i] ) / (dis*dis + 2.0f)    ;"
            + "      "
            + "     ix += (f * dx / dis) ; "
            +       "" 
            + "     iy += f * dy / dis ;"
            + "     iz += f * dz / dis; "
            + "    "
            + ""
             + "    "
           
            + "                           "
            + "  } "
            + "  d[droit] = ix;"
              + "d[droit + 1] = iy;"
              + "d[droit + 2] = iz;"
            + "                   "
            + "} " +
           
        "   "+
        "}";
    
    
    private static String programSource2 =
        "__kernel void "+
        "sampleKernel(__global const float *a,"+
        "             __global const float *b,"
            +         "__global const float *c,"
            +          "__global const float *mass,"
            +           "__global const float *bnarticule," +
        "             __global float *d,"
            + "        __global int *nbKernel,  "
            + "        __global int *totalKernel  )"+
        "{"+
           "      int gid = get_global_id(0);"
            + "                  "
            + " int thisKernel =  nbKernel[0] ;"
            + "                   "  
            + " float G = 6.673e-11;"
            + " float nb= bnarticule[0];"
            + " int thisParticule = gid + (thisKernel *  (nb / totalKernel[0]) );"
            + " "
            + "  float xthis = a[thisParticule];"
            + " float ythis = b[thisParticule]; "
            + " float zthis = c[thisParticule];"
            + " float masse = mass[thisParticule];"
            + " "
            + "int ti = get_local_id(0);"
            + ""
            + "int droit = thisParticule * 3;"
            + ""
            + " __local float X["+ local_size + "];"
            + "__local float Y["+ local_size + "];"
            + "__local float Z["+ local_size + "];"
            + "__local float M["+ local_size + "];" +
              
                ""
            +     ""
            + " float nbP = nb/"+ local_size + ";"
            + "  "
            + "  "
            + ""
            + "float ix = 0;"
            + "   float iy = 0;"
            + "   float iz = 0; "
            + "for(int jb=0; jb < nbP; jb++) {" +
                "   int maxi = nb -(jb * "+ local_size + "); "
             +   " if ("+ local_size + " < maxi){"
            + "       maxi ="+ local_size + " ;  "
            + "} "
            + "    int pos = jb * "+ local_size + " + ti; "
            + "      X[ti]=a[pos];"
             +       "Y[ti]=b[pos];"
                +    "Z[ti]=c[pos];"
                 +   "M[ti]=mass[pos];"
            + "      int itera = jb * "+ local_size + "; "
            + "barrier(CLK_LOCAL_MEM_FENCE);"
            + "     for(int i=0; i < maxi; i++) {"
            + "    " 
            + " "
            + "     if ((itera+ i) != thisParticule) {"
            + "     float dx = X[i] - xthis;"
            + "     float dy = Y[i] - ythis;"
            + "     float dz = Z[i] - zthis;"
            + "     float dis = sqrt((dx * dx) + (dy * dy) + (dz * dz) ); "
            + "     float f = (G * masse * M[i] ) / (dis*dis + 0.25f)    ;"
            + "     if (dis == 0){"
            + "      dis = 0.01f;"
            + "} "
            + " "
            + "     ix += f * dx / (dis) ; "
            +       "" 
            + "     iy += f * dy / (dis) ;"
            + "     iz += f * dz /(dis );"
    + "                 } "
    + "             }"
            + "barrier(CLK_LOCAL_MEM_FENCE);"
            + "    "
            + ""
             + "    "
           
            + "                           "
            + "  } "
            + "d[droit] =  ix;"
              + "d[droit + 1] = iy;"
              + "d[droit + 2] = iz;"
            + "                   "
            + "} " +
           
        "   "+
        "";
     public Body bodies[];
     
     
     public void addBruteForceListener(BruteForceListener listener)
     {
         listListenner.add(BruteForceListener.class ,listener);
     }
      public BruteForceListener[] getBruteForceListener() {
        return listListenner.getListeners(BruteForceListener.class);
    }
     public void finishN2()
     {  
           for(BruteForceListener listener : getBruteForceListener()) {
                listener.TerminerOpenCl(this);
            }
     }
       public void init()
       {
  
               
               
          bodies = new Body[20001];
          
        Body buffer[] = new Body[bodies.length];
        bodies[0] = new Body(new Vector3f (0, 1 ,0) ,new Vector3f (0, 0 ,0) , 4 * 10e12f);
         buffer[0] = new Body(new Vector3f (0, 1 ,0) ,new Vector3f (0, 0 ,0) , 4 * 10e12f);
         int  iterator = 0;
           for (int i = 0 ;i < 200 ; i++)
           {  
                for (int j = 0 ;j < 5 ; j++)
                { 
                     for (int z = 0 ;z < 10 ; z++)
                    { 
                        iterator++;
                        bodies[iterator] = new Body(new Vector3f (i * 4, -2 * j * 4 , z * 3) ,new Vector3f (0, 3 ,0) , 400000000);
                        buffer[iterator] = new Body(new Vector3f (i * 4, -2 * j * 4 , z * 3) ,new Vector3f (0, 3 ,0) , 400000000);
                        //System.out.println(iterator);
                        //System.out.println(bodies[iterator].position);
                    }
                }
           }   
           
           
            for (int i = 0 ;i < 200 ; i++)
           {  
                for (int j = 0 ;j < 5 ; j++)
                { 
                     for (int z = 0 ;z < 10 ; z++)
                    { 
                        iterator++;
                        bodies[iterator] = new Body(new Vector3f (-i * 4 -20, 2 * j * 4 , -z * 3) ,new Vector3f (0, 3 ,0) , 400000000);
                        buffer[iterator] = new Body(new Vector3f (-i * 4  -20, 2 * j * 4 , -z * 3) ,new Vector3f (0, 3 ,0) , 400000000);
                        //System.out.println(iterator);
                        //System.out.println(bodies[iterator].position);
                    }
                }
           }
      
      
               System.out.println(iterator);
      }
      public JOCLTest(int nbDevice, int pnombreThread)
      {
            id =   nbDevice;
            nombreThread = pnombreThread;
            final int platformIndex = 0;
            final long deviceType = CL_DEVICE_TYPE_ALL;
            final int deviceIndex = nbDevice;
          // Obtain the number of platforms
            int numPlatformsArray[] = new int[1];
            clGetPlatformIDs(0, null, numPlatformsArray);
            int numPlatforms = numPlatformsArray[0];
            cl_platform_id platforms[] = new cl_platform_id[numPlatforms];
            clGetPlatformIDs(platforms.length, platforms, null);
            cl_platform_id platform = platforms[platformIndex];
            
             // Initialize the context properties
            cl_context_properties contextProperties = new cl_context_properties();
            contextProperties.addProperty(CL_CONTEXT_PLATFORM, platform);

            // Obtain the number of devices for the platform
            int numDevicesArray[] = new int[1];
            clGetDeviceIDs(platform, deviceType, 0, null, numDevicesArray);
            int numDevices = numDevicesArray[0];
            
            // Obtain a device ID 
            cl_device_id devices[] = new cl_device_id[numDevices];
            clGetDeviceIDs(platform, deviceType, numDevices, devices, null);
             device = devices[deviceIndex];
             System.out.print(getString(device, CL_DEVICE_NAME));
             CL.setExceptionsEnabled(true);
              context = clCreateContext(
            contextProperties, 1, new cl_device_id[]{device}, 
            null, null, null);
            
                  commandQueue = 
             clCreateCommandQueue(context, device, 0, null);
              // Create a context for the selected device
             program = clCreateProgramWithSource(context,
            1, new String[]{ programSource2 }, null, null);
        
            
                
                // Build the program
            clBuildProgram(program, 0, null, null, null, null);
        
                // Create the kernel
              kernel = clCreateKernel(program, "sampleKernel", null);
           // init();

      }       
    /**
     * The entry point of this sample
     * 
     * @param args Not used
     */
   
    public void run()  
    {
       // Create input- and output data 
        // System.out.println(System.currentTimeMillis()); 
           int nb =bodies.length;
          float dstArray[] = new float[nb * 3];
         float srcArrayA[]  = new float[nb];
         float srcArrayB[]  = new float[nb];
       
         float srcArrayC[]  = new float[nb];
         float scrMass[] = new float[nb];
         double debut = System.currentTimeMillis();
      
        for (int i = 0; i < nb; i++) 
        {  
                Vector3f v = bodies[i].getPosition();
                srcArrayA[i] =  v.x;
                srcArrayB[i] =  v.y;
                 srcArrayC[i] =  v.z ;
                if( i == 1)
                   {  
                        // srcArrayC[i] =  v.z + 0.5f ;
                   }  
               
                scrMass[i] =  (float)bodies[i].getMass();
                if( nb == 150)
                { 
                   //System.out.println( bodies[i].getPosition());
                }
        }
  
  
       
     
        
        Pointer srcA = Pointer.to(srcArrayA);
        Pointer srcB = Pointer.to(srcArrayB);
        Pointer srcC = Pointer.to(srcArrayC);
        Pointer srcMass = Pointer.to(scrMass);
        float nombre[] = new float[1];
        nombre[0] =  nb;
        Pointer srcNumber = Pointer.to(nombre);
        Pointer dst = Pointer.to(dstArray);
        int nbThread[] = new int[1];
        int idT[] = new int[1];
        idT[0] = id;
        nbThread[0] =  nombreThread;
        //System.out.println(id);
        //System.out.println(nombreThread);
        Pointer numberThread = Pointer.to(nbThread);
         Pointer idThread = Pointer.to(idT);
 
        // The platform, device type and device number
        // that will be used
       
        
        // Enable exceptions and subsequently omit error checks in this sample
        

       
      
           
      //System.out.println(nb);
       
        // Allocate the memory objects for the input- and output data
        cl_mem memObjects[] = new cl_mem[8];
        memObjects[0] = clCreateBuffer(context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float4 * nb, srcA, null);
        memObjects[1] = clCreateBuffer(context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb, srcB, null);
        memObjects[2] = clCreateBuffer(context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb, srcC, null);
        memObjects[3] = clCreateBuffer(context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb, srcMass, null);
        memObjects[4] = clCreateBuffer(context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb,  srcNumber, null);
        
                  // Create a command-queue for the selected device
        memObjects[5] = clCreateBuffer(context, 
            CL_MEM_READ_WRITE, 
            Sizeof.cl_float * nb * 3, null, null);
        
        memObjects[6] = clCreateBuffer(context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_int ,  idThread, null);
        
        memObjects[7] = clCreateBuffer(context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_int ,  numberThread, null);
        
        // Create the program from the source code
       
      
          
        // Set the arguments for the kernel
        clSetKernelArg(kernel, 0, 
            Sizeof.cl_mem, Pointer.to(memObjects[0]));
        clSetKernelArg(kernel, 1, 
            Sizeof.cl_mem, Pointer.to(memObjects[1]));
        clSetKernelArg(kernel, 2, 
            Sizeof.cl_mem, Pointer.to(memObjects[2]));
         clSetKernelArg(kernel, 3, 
            Sizeof.cl_mem, Pointer.to(memObjects[3]));
          clSetKernelArg(kernel, 4, 
            Sizeof.cl_mem, Pointer.to(memObjects[4]));
               
       
             
            
            clSetKernelArg(kernel, 5, 
            Sizeof.cl_mem, Pointer.to(memObjects[5]));
 clSetKernelArg(kernel, 6, 
            Sizeof.cl_mem, Pointer.to(memObjects[6]));
  clSetKernelArg(kernel, 7, 
            Sizeof.cl_mem, Pointer.to(memObjects[7]));
         
        int nombreT = (nb / nombreThread) + (local_size -(nb%local_size));
        //System.out.println(nombreT);
        // Set the work-item dimensions
        long global_work_size[] = new long[]{(nombreT)};
        long local_work_size[] = new long[]{local_size};
        debut = System.currentTimeMillis();
      //System.out.println("time1: "+ ( System.currentTimeMillis() - debut));  
        // Execute the kernel
        clEnqueueNDRangeKernel(commandQueue, kernel, 1, null,
            global_work_size, local_work_size, 0, null, null);
      
         // Read the output data
        clEnqueueReadBuffer(commandQueue, memObjects[5], CL_TRUE, 0,
            nb * 3* Sizeof.cl_float, dst, 0, null, null);
         
        
   
        // Obtain a platform ID
         // System.out.println("time: "+ ( System.currentTimeMillis() - debut)); 
         //  System.out.println(System.currentTimeMillis()); 
            
        // Release kernel, program, and memory objects
        clReleaseMemObject(memObjects[0]);
        clReleaseMemObject(memObjects[1]);
        clReleaseMemObject(memObjects[2]);
        clReleaseMemObject(memObjects[3]);
        clReleaseMemObject(memObjects[4]);
        clReleaseMemObject(memObjects[5]);
          clReleaseMemObject(memObjects[6]);
        clReleaseMemObject(memObjects[7]);

     
 
        //System.out.println("Test "+(passed?"PASSED":"FAILED"));
       
           int debutT = id * (nb /nombreThread);
           int fin = debutT + (nb /nombreThread);
             for (int i = debutT; i < fin; i++) 
             {
                
                bodies[i].force.x = dstArray[i * 3  ];
                bodies[i].force.y = dstArray[i * 3 + 1];
                bodies[i].force.z = dstArray[i * 3  + 2];
                 if( nb == 150)
                { 
                  //System.out.println("Result:" + i +  " " +  bodies[i].force);
                }
                   // System.out.println("Result:" + i +  " " +  bodies[i].force);
               
             }
        
         
         
         finishN2();
        
    }
    
    
    /**
     * The entry point of this sample
     * 
     * @param args Not used
     */
    public static void main(String args[])
    {
       getAllDevice();
        /* My shit
        JOCLTest T = new JOCLTest();
        T.init();
        long numBytes[] = new long[1];
        
        float srcArrayX[]  = new float[T.bodies.length];
         float srcArrayY[]  = new float[T.bodies.length];
        float srcArrayZ[]  = new float[T.bodies.length];
          float scrMass[] = new float[T.bodies.length];
          int size[] = new int[1];
          size[0] = 2501;
          for (int i = 0; i < T.bodies.length; i++) 
          {  
             
                srcArrayX[i] = T.bodies[i].getPosition().x;
                srcArrayY[i] = T.bodies[i].getPosition().y;
                srcArrayZ[i] = T.bodies[i].getPosition().z;
                scrMass[i] =  (float)T.bodies[i].getMass();
          }
                
       

  
        size[0] = srcArrayX.length;
        long before = System.nanoTime();
        int dstArray[] = new int[size[0]/90000];


        Pointer srcX = Pointer.to(srcArrayX);
        Pointer srcY = Pointer.to(srcArrayY);
        Pointer srcZ = Pointer.to(srcArrayZ);
        Pointer srcM = Pointer.to(scrMass);

        */
        
        // Create input- and output data 
        int n = 10;
        /*
     
        
         JOCLTest T = new JOCLTest(0 , 1);
          T.init();
           int nb =T.bodies.length;
            float dstArray[] = new float[nb * 3];
         float srcArrayA[]  = new float[nb];
         float srcArrayB[]  = new float[nb];
       
         float srcArrayC[]  = new float[nb];
         float scrMass[] = new float[nb];
         double debut = System.currentTimeMillis();
      
        for (int i = 0; i < nb; i++) 
        {  
                Vector3f v = T.bodies[i].getPosition();
                srcArrayA[i] =  v.x;
                srcArrayB[i] =  v.y;
                srcArrayC[i] =  v.z;
                scrMass[i] =  (float)T.bodies[i].getMass();
                //System.out.println(scrMass[i]);
        }
     
      
        Pointer srcA = Pointer.to(srcArrayA);
        Pointer srcB = Pointer.to(srcArrayB);
        Pointer srcC = Pointer.to(srcArrayC);
        Pointer srcMass = Pointer.to(scrMass);
        float nombre[] = new float[1];
        nombre[0] =  nb;
        Pointer srcNumber = Pointer.to(nombre);
        Pointer bx = Pointer.to(new float[nb]);
        Pointer by = Pointer.to(new float[nb]);
        Pointer bz = Pointer.to(new float[nb]);
        Pointer bmass = Pointer.to(new float[nb]);
        Pointer dst = Pointer.to(dstArray);

        // The platform, device type and device number
        // that will be used
       
        
        // Enable exceptions and subsequently omit error checks in this sample
        

       
        
       
           
     
       
        // Allocate the memory objects for the input- and output data
        cl_mem memObjects[] = new cl_mem[10];
        memObjects[0] = clCreateBuffer(T.context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb, srcA, null);
        memObjects[1] = clCreateBuffer(T.context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb, srcB, null);
        memObjects[2] = clCreateBuffer(T.context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb, srcC, null);
        memObjects[3] = clCreateBuffer(T.context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb, srcMass, null);
        memObjects[4] = clCreateBuffer(T.context, 
            CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
            Sizeof.cl_float * nb,  srcNumber, null);
        memObjects[5] = clCreateBuffer(T.context, 
            CL_MEM_READ_WRITE,
            Sizeof.cl_float * nb,  null, null);
        memObjects[6] = clCreateBuffer(T.context, 
            CL_MEM_READ_WRITE,
            Sizeof.cl_float * nb,  null, null);
        memObjects[7] = clCreateBuffer(T.context, 
            CL_MEM_READ_WRITE,
            Sizeof.cl_float * nb,  null, null);
        
         memObjects[8] = clCreateBuffer(T.context, 
            CL_MEM_READ_WRITE,
            Sizeof.cl_float * nb,  null, null);  
        
                  // Create a command-queue for the selected device
        memObjects[9] = clCreateBuffer(T.context, 
            CL_MEM_READ_WRITE, 
            Sizeof.cl_float * nb * 3, null, null);
        
        // Create the program from the source code
       
      
          
        // Set the arguments for the kernel
        clSetKernelArg(T.kernel, 0, 
            Sizeof.cl_mem, Pointer.to(memObjects[0]));
        clSetKernelArg(T.kernel, 1, 
            Sizeof.cl_mem, Pointer.to(memObjects[1]));
        clSetKernelArg(T.kernel, 2, 
            Sizeof.cl_mem, Pointer.to(memObjects[2]));
         clSetKernelArg(T.kernel, 3, 
            Sizeof.cl_mem, Pointer.to(memObjects[3]));
          clSetKernelArg(T.kernel, 4, 
            Sizeof.cl_mem, Pointer.to(memObjects[4]));
               
       
             
            
            clSetKernelArg(T.kernel, 5, 
            Sizeof.cl_mem, Pointer.to(memObjects[9]));

         System.out.println(T.device.toString());
        
        // Set the work-item dimensions
        long global_work_size[] = new long[]{nb};
        long local_work_size[] = new long[]{320};
       // debut = System.currentTimeMillis();
       System.out.println("time1: "+ ( System.currentTimeMillis() - debut));  
        // Execute the kernel
      
        clEnqueueNDRangeKernel(T.commandQueue, T.kernel, 1, null,
            global_work_size, local_work_size, 0, null, null);
        
         // Read the output data
        clEnqueueReadBuffer(T.commandQueue, memObjects[9], CL_TRUE, 0,
            nb * 3* Sizeof.cl_float, dst, 0, null, null);

        // Obtain a platform ID
            System.out.println("time: "+ ( System.currentTimeMillis() - debut));  
        // Release kernel, program, and memory objects
        clReleaseMemObject(memObjects[0]);
        clReleaseMemObject(memObjects[1]);
        clReleaseMemObject(memObjects[2]);
        clReleaseMemObject(memObjects[3]);
        clReleaseMemObject(memObjects[4]);
        clReleaseMemObject(memObjects[5]);
         clReleaseMemObject(memObjects[6]);
         clReleaseMemObject(memObjects[7]);
         clReleaseMemObject(memObjects[8]);
         clReleaseMemObject(memObjects[9]);
         
        clReleaseKernel(T.kernel);
        clReleaseProgram(T.program);
        clReleaseCommandQueue(T.commandQueue);
        clReleaseContext(T.context);
     
        // Verify the result
        boolean passed = true;
        final float epsilon = 1e-7f;
 
        //System.out.println("Test "+(passed?"PASSED":"FAILED"));
        if (n <= 10)
        {
             for (int i = 0; i < nb; i++) 
             {
                
                T.bodies[i].force.x = dstArray[i * 3  ];
                T.bodies[i].force.y = dstArray[i * 3 + 1];
                T.bodies[i].force.z = dstArray[i * 3  + 2];
            
             }
        }
         // System.out.println("Result: "+java.util.Arrays.toString(dstArray));
         System.out.println("time: "+ ( System.currentTimeMillis() - debut));  
        */
    }
    
    public static  void getAllDevice()
    {
          
   
            final int platformIndex = 0;
            final long deviceType = CL_DEVICE_TYPE_ALL;
       
          // Obtain the number of platforms
            int numPlatformsArray[] = new int[1];
            clGetPlatformIDs(0, null, numPlatformsArray);
            int numPlatforms = numPlatformsArray[0];
            cl_platform_id platforms[] = new cl_platform_id[numPlatforms];
            clGetPlatformIDs(platforms.length, platforms, null);
            cl_platform_id platform = platforms[platformIndex];
            
             // Initialize the context properties
            cl_context_properties contextProperties = new cl_context_properties();
            contextProperties.addProperty(CL_CONTEXT_PLATFORM, platform);

            // Obtain the number of devices for the platform
            int numDevicesArray[] = new int[1];
            clGetDeviceIDs(platform, deviceType, 0, null, numDevicesArray);
            int numDevices = numDevicesArray[0];
            
            // Obtain a device ID 
            cl_device_id devices[] = new cl_device_id[numDevices];
            clGetDeviceIDs(platform, deviceType, numDevices, devices, null);
             for (int i = 0; i < numDevices; i++) 
             { 
                System.out.print(getString(devices[i], CL_DEVICE_NAME));
                System.out.print("\n");   
                System.out.print(getString(devices[i], CL_DEVICE_MAX_WORK_GROUP_SIZE));
                System.out.print("\n");   
             }
   
        
             
    }        
    private static String getString(cl_device_id device, int paramName)
    {
        // Obtain the length of the string that will be queried
        long size[] = new long[1];
        clGetDeviceInfo(device, paramName, 0, null, size);
        System.out.print((int)size[0]);   
        // Create a buffer of the appropriate size and fill it with the info
        byte buffer[] = new byte[(int)size[0]];
        clGetDeviceInfo(device, paramName, buffer.length, Pointer.to(buffer), null);

        // Create a string from the buffer (excluding the trailing \0 byte)
        return new String(buffer, 0, buffer.length-1);
    }
}
