package com.example.android.apis.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
//import android.os.SystemClock;

public class HelloOpenGLES10Renderer implements GLSurfaceView.Renderer {

	private FloatBuffer triangleVB;
	
	public float mAngle;
	
	// called once to set up the GLSurfaceView environment
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
        // Set the background frame color
        gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        
        // initialize the triangle vertex array
        initShapes();
        
        // Enable use of vertex arrays
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }
    
	// called for each redraw of the GLSurfaceView
	@Override
    public void onDrawFrame(GL10 gl) {
		
        // Redraw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();   // reset the matrix to its default state
        
        // When using GL_MODELVIEW, you must set the view point
        GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        
        // Use the mAngle member as the rotation value
        gl.glRotatef(mAngle, 0.0f, 0.0f, 1.0f);
        /*
        // Create a rotation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        gl.glRotatef(angle, 0.0f, 0.0f, 1.0f);
        */
        
        // Draw the triangle
        gl.glColor4f(0.63671875f, 0.76953125f, 0.22265625f, 0.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleVB);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
    }
    
	// called if the geometry of the GLSurfaceView changes, for example when the device's screen orientation changes
	@Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        
        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
    }

	private void initShapes(){
	    
        float triangleCoords[] = {
            // X, Y, Z
            -0.5f, -0.25f, 0,
             0.5f, -0.25f, 0,
             0.0f,  0.559016994f, 0
        }; 
        
        // initialize vertex Buffer for triangle  
        ByteBuffer vbb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                triangleCoords.length * 4); 
        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
        triangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
        triangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
        triangleVB.position(0);            // set the buffer to read the first coordinate
    
    }
	
}
