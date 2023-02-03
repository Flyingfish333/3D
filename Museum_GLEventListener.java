import gmaths.*;
import java.nio.*;
import com.jogamp.common.nio.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.glsl.*;


/* I declare that this code is my own work */
/* Author <SiYuan Yu> <syu33@sheffield.ac.uk> */  
public class Museum_GLEventListener implements GLEventListener {
    
  public Museum_GLEventListener(Camera camera) {
    this.camera = camera;
    this.camera.setPosition(new Vec3(4f,12f,18f));
  }
  
  // ***************************************************
  /*
   * METHODS DEFINED BY GLEventListener
   */

  /* Initialisation */
  public void init(GLAutoDrawable drawable) {   
    GL3 gl = drawable.getGL().getGL3();
    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 
    gl.glClearDepth(1.0f);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glDepthFunc(GL.GL_LESS);
    gl.glFrontFace(GL.GL_CCW);    // default is 'CCW'
    gl.glEnable(GL.GL_CULL_FACE); // default is 'not enabled'
    gl.glCullFace(GL.GL_BACK);   // default is 'back', assuming CCW
    initialise(gl);
    startTime = getSeconds();
  }
  
  /* Called to indicate the drawing surface has been moved and/or resized  */
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL3 gl = drawable.getGL().getGL3();
    gl.glViewport(x, y, width, height);
    float aspect = (float)width/(float)height;
    camera.setPerspectiveMatrix(Mat4Transform.perspective(45, aspect));
  }

  /* Draw */
  public void display(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    render(gl);
  }

  /* Clean up memory, if necessary */
  public void dispose(GLAutoDrawable drawable) {
    GL3 gl = drawable.getGL().getGL3();
    light.dispose(gl);
    light_lim.dispose(gl);
    floor.dispose(gl);
    sphere.dispose(gl);
    cube.dispose(gl);
    cube2.dispose(gl);
    back_floor.dispose(gl);
    left_floor_1.dispose(gl);
    left_floor_2.dispose(gl);
    left_floor_3.dispose(gl);
    left_floor_4.dispose(gl);
    left_floor_5.dispose(gl);
    left_floor_6.dispose(gl);
    left_floor_7.dispose(gl);
    left_floor_8.dispose(gl);
    outside_view.dispose(gl);
    gate.dispose(gl);
    cube_egg.dispose(gl);
    phone.dispose(gl);
    cube_phone.dispose(gl);
    egg.dispose(gl);
    light_cube_1.dispose(gl);
    light_cube_2.dispose(gl);
    light_cube_3.dispose(gl);
    light_cube_4.dispose(gl);
    robot_leg.dispose(gl);
    robot_body.dispose(gl);
    robot_neck.dispose(gl);
    robot_head.dispose(gl);
    robot_eyes.dispose(gl);
    robot_hair.dispose(gl);
  }
  

  //first place
  public void first_Position() {
    xPosition = -3.5f;
    yPosition = 0f;
    zPosition = -5f;
    Y_angle = 0;
    hair_angle = -45f;
    body_angle = 15f;
    updateMove();
  }

  //second place
  public void second_Position() {
    xPosition = 2f;
    yPosition = 0f;
    zPosition = -4f;
    Y_angle = 135;
    hair_angle = 15f;
    body_angle = -15f;
    updateMove();
  }

  //thirth place
  public void thirth_Position() {
    xPosition = 2f;
    yPosition = 0f;
    zPosition = 4f;
    Y_angle = 90;
    hair_angle = -30f;
    body_angle = 20f;
    updateMove();
  }

  //fourth place
  public void fourth_Position() {
    xPosition = 0f;
    yPosition = 0f;
    zPosition = 4f;
    Y_angle = 180;
    hair_angle = -60f;
    body_angle = 0f;
    updateMove();
  }

  //fifth place
  public void fifth_Position() {
    xPosition = -5f;
    yPosition = 0f;
    zPosition = 0f;
    Y_angle = 270;
    hair_angle = 30f;
    body_angle = -15f;
    updateMove();
  }
 
  private void updateMove() {
    robotMoveTranslate.setTransform(Mat4Transform.translate(xPosition,yPosition,zPosition));
    robotMoveRotato.setTransform(Mat4Transform.rotateAroundY(Y_angle));
    hairRotate.setTransform(Mat4Transform.rotateAroundX(hair_angle));
    bodyRotate.setTransform(Mat4Transform.rotateAroundX(body_angle));
    robotMoveTranslate.update();
  }
  
  // ***************************************************
  /* THE SCENE
   * Now define all the methods to handle the scene.
   * This will be added to in later examples.
   */

  private Camera camera;
  private Model floor, sphere, cube, cube2, back_floor, left_floor_1, left_floor_2, left_floor_3, left_floor_4, left_floor_5, left_floor_6, 
  left_floor_7, left_floor_8, outside_view, gate, cube_egg, cube_phone, phone, egg, light_cube_1, light_cube_2, light_cube_3, light_cube_4, 
  robot_leg, robot_body, robot_neck, robot_head, robot_eyes, robot_hair;
  private Light light;
  private Light light_lim;
  private SGNode robotRoot;
  
  private float xPosition = -3.5f;//x location of robot
  private float yPosition = 0f;//y location of robot
  private float zPosition = -5f;//z location of robot
  private float Y_angle = 0f;// angle of robot
  private float hair_angle = -45f;// angle of robot
  private float eye_Location = 0.2f;// location of robot
  private float body_angle = 15f;// angle of robot body
  private TransformNode robotMoveTranslate, hairRotate, bodyRotate, robotMoveRotato;
  
  private void initialise(GL3 gl) {
    createRandomNumbers();
    int[] textureId0 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId1 = TextureLibrary.loadTexture(gl, "textures/jade.jpg");
    int[] textureId2 = TextureLibrary.loadTexture(gl, "textures/jade_specular.jpg");
    int[] textureId3 = TextureLibrary.loadTexture(gl, "textures/container2.jpg");
    int[] textureId4 = TextureLibrary.loadTexture(gl, "textures/container2_specular.jpg");
    int[] textureId5 = TextureLibrary.loadTexture(gl, "textures/wattBook.jpg");
    int[] textureId6 = TextureLibrary.loadTexture(gl, "textures/wattBook_specular.jpg");
    int[] textureId_back_floor = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_left_floor_1 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_left_floor_2 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_left_floor_3 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_left_floor_4 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_left_floor_5 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_left_floor_6 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_left_floor_7 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_left_floor_8 = TextureLibrary.loadTexture(gl, "textures/wooden_floor.jpg");
    int[] textureId_outside_view = TextureLibrary.loadTexture(gl, "textures/cloud.jpg");
    int[] textureId_gate = TextureLibrary.loadTexture(gl, "textures/door.jpg");
    int[] textureId_light_cube = TextureLibrary.loadTexture(gl, "textures/light_cube.jpg");
    int[] textureId_robot_sphere = TextureLibrary.loadTexture(gl, "textures/robot_color.jpg");
    int[] textureId_phone = TextureLibrary.loadTexture(gl, "textures/mobile_phone.jpg");
    
        
    light = new Light(gl);
    light_lim = new Light(gl);
    light.setCamera(camera);
    light_lim.setCamera(camera);
    
    //ground floor
    Mesh mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    Shader shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_06.txt");
    Material material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    Mat4 modelMatrix = Mat4Transform.scale(15,1f,15);
    floor = new Model(gl, camera, light_lim, shader, material, modelMatrix, mesh, textureId0);
    
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    sphere = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId1, textureId2);
    
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    cube = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId3, textureId4);
    
    cube2 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId5, textureId6); 

    //back floor
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0, 3f, -7.5f),Mat4Transform.scale(15,6,15));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundX(90));
    back_floor = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_back_floor);

    //left floor
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-7.5f, 5, 5),Mat4Transform.scale(5,2,5));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    left_floor_1 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_left_floor_1);
    
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-7.5f, 5, 0),Mat4Transform.scale(5,2,5));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    left_floor_2 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_left_floor_2);
    
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-7.5f, 5, -5),Mat4Transform.scale(5,2,5));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    left_floor_3 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_left_floor_3);
    
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-7.5f, 3, 5),Mat4Transform.scale(5,2,5));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    left_floor_4 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_left_floor_4);
    
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-7.5f, 3, -5),Mat4Transform.scale(5,2,5));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    left_floor_5 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_left_floor_5);
    
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-7.5f, 1, 5),Mat4Transform.scale(5,2,5));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    left_floor_6 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_left_floor_6);
    
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-7.5f, 1, 0),Mat4Transform.scale(5,2,5));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    left_floor_7 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_left_floor_7);
    
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-7.5f, 1, -5f),Mat4Transform.scale(5,2,5));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    left_floor_8 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_left_floor_8);

    //outside view
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-9, 3, 0),Mat4Transform.scale(16,8,16));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundZ(-90));
    outside_view = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_outside_view);

    //the gate
    mesh = new Mesh(gl, TwoTriangles.vertices.clone(), TwoTriangles.indices.clone());
    shader = new Shader(gl, "vs_tt_05.txt", "fs_tt_05.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.33f), new Vec3(1.0f, 0.5f, 0.33f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(-3.5f, 2, -7.3f),Mat4Transform.scale(2.5f,4,2.5f));
    modelMatrix = Mat4.multiply( modelMatrix,Mat4Transform.rotateAroundX(90));
    gate = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_gate);

    //egg
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0, 2.5f, 0),Mat4Transform.scale(2.5f,4,2.5f));
    egg = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId1, textureId2);

    //egg cube
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(0, 0.25f, 0),Mat4Transform.scale(2.5f,0.5f,2.5f));
    cube_egg = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId3, textureId4);

    //phone
    mesh = new Mesh(gl, Cube_phone.vertices.clone(), Cube_phone.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(4, 2.5f, -6f),Mat4Transform.scale(2.5f,4f,0.5f));
    phone = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_phone);

    //phone cube
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(4, 0.25f, -6f),Mat4Transform.scale(2.5f,0.5f,2.5f));
    cube_phone = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId3, textureId4);

    //light cube
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(7, 0.125f, 4),Mat4Transform.scale(1,0.25f,1));
    light_cube_1 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_light_cube);

    
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(7, 2.5f, 4),Mat4Transform.scale(0.25f,5,0.25f));
    light_cube_2 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_light_cube);

    
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(5.85f, 5.125f, 4),Mat4Transform.scale(2.5f,0.25f,0.25f));
    light_cube_3 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_light_cube);

    
    mesh = new Mesh(gl, Cube.vertices.clone(), Cube.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.translate(4.6f, 4.75f, 4),Mat4Transform.scale(0.5f,0.5f,0.5f));
    light_cube_4 = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_light_cube);

    //robot body
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    robot_leg = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_robot_sphere);

    
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    robot_body = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_robot_sphere);
    
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    robot_neck = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_robot_sphere);
    
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    robot_head = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_robot_sphere);

    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    robot_eyes = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_robot_sphere);
    
    mesh = new Mesh(gl, Sphere.vertices.clone(), Sphere.indices.clone());
    shader = new Shader(gl, "vs_cube_04.txt", "fs_cube_04.txt");
    material = new Material(new Vec3(1.0f, 0.5f, 0.31f), new Vec3(1.0f, 0.5f, 0.31f), new Vec3(0.5f, 0.5f, 0.5f), 32.0f);
    modelMatrix = Mat4.multiply(Mat4Transform.scale(4,4,4), Mat4Transform.translate(0,0.5f,0));
    robot_hair = new Model(gl, camera, light, shader, material, modelMatrix, mesh, textureId_robot_sphere);


    // robot

    float bodyHeight = 1.5f;
    float bodyWidth = 1f;
    float bodyDepth = 0.5f;
    float legScale = 0.3f;
    float neckScale = 0.2f;
    float headHeight = 0.5f;
    float headWidth = 1.5f;
    float eyeScale = 0.2f;
    float hairWidth = 0.3f;
    float hairHeight = 1f;
    
    robotRoot = new NameNode("root");
    robotMoveTranslate = new TransformNode("robot transform",Mat4Transform.translate(xPosition,yPosition,zPosition));
    robotMoveRotato = new TransformNode("robot Rotato", Mat4Transform.rotateAroundY(Y_angle));
    TransformNode robotTranslate = new TransformNode("robot transform",Mat4Transform.translate(0,legScale,0));
    

    NameNode body = new NameNode("body");
      Mat4 m = Mat4Transform.scale(bodyWidth,bodyHeight,bodyDepth);
      bodyRotate = new TransformNode("body rotate",Mat4Transform.rotateAroundX(body_angle));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode bodyTransform = new TransformNode("body transform", m);
        ModelNode bodyShape = new ModelNode("Sphere(body)", robot_body);

    NameNode leg = new NameNode("leg");
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.scale(legScale,legScale,legScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,-0.5f,0));
      TransformNode legTransform = new TransformNode("body transform", m);
        ModelNode legShape = new ModelNode("Sphere(leg)", robot_leg);     


    NameNode neck = new NameNode("neck"); 
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.translate(0,bodyHeight,0));
      m = Mat4.multiply(m, Mat4Transform.scale(neckScale,neckScale,neckScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode neckTransform = new TransformNode("head transform", m);
        ModelNode neckShape = new ModelNode("Sphere(neck)", robot_neck);

    NameNode head = new NameNode("head");
      TransformNode headTranslate = new TransformNode("head translate", 
                                         Mat4Transform.translate(0,neckScale+bodyHeight,0));
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.scale(headWidth,headHeight,headWidth));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode headScale = new TransformNode("head scale", m);
        ModelNode headShape = new ModelNode("Sphere(head)", robot_head);

    NameNode lefteye = new NameNode("left eye");
      TransformNode leftEyeTranslate = new TransformNode("lefteye translate", 
                                           Mat4Transform.translate(eye_Location,headHeight+bodyHeight+eyeScale,headWidth*0.25f));
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.scale(eyeScale,eyeScale,eyeScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode leftEyeScale = new TransformNode("lefteye scale", m);
        ModelNode leftEyeShape = new ModelNode("Sphere(left eye)", robot_eyes);

    NameNode righteye = new NameNode("left eye");
      TransformNode rightEyeTranslate = new TransformNode("righteye translate", 
                                           Mat4Transform.translate(-eye_Location,headHeight+bodyHeight+eyeScale,headWidth*0.25f));
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.scale(eyeScale,eyeScale,eyeScale));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode rightEyeScale = new TransformNode("righteye scale", m);
        ModelNode rightEyeShape = new ModelNode("Sphere(right eye)", robot_eyes);

    NameNode hair = new NameNode("hair");
      TransformNode hairTranslate = new TransformNode("hair translate", 
                                           Mat4Transform.translate(0,headHeight+bodyHeight+eyeScale,-headWidth*0.25f));
      hairRotate = new TransformNode("hair rotate",Mat4Transform.rotateAroundX(hair_angle));
      m = new Mat4(1);
      m = Mat4.multiply(m, Mat4Transform.scale(hairWidth,hairHeight,hairWidth));
      m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
      TransformNode hairScale = new TransformNode("hair scale", m);
        ModelNode hairShape = new ModelNode("Sphere(hair)", robot_hair);
        
  //   NameNode body = new NameNode("body");
  //     Mat4 m = Mat4Transform.scale(bodyWidth,bodyHeight,bodyDepth);
  //     m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
  //     TransformNode bodyTransform = new TransformNode("body transform", m);
  //       ModelNode bodyShape = new ModelNode("Cube(body)", cube);

  //   NameNode head = new NameNode("head"); 
  //     m = new Mat4(1);
  //     m = Mat4.multiply(m, Mat4Transform.translate(0,bodyHeight,0));
  //     m = Mat4.multiply(m, Mat4Transform.scale(headScale,headScale,headScale));
  //     m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
  //     TransformNode headTransform = new TransformNode("head transform", m);
  //       ModelNode headShape = new ModelNode("Sphere(head)", sphere);
    
  //  NameNode leftarm = new NameNode("left arm");
  //     TransformNode leftArmTranslate = new TransformNode("leftarm translate", 
  //                                          Mat4Transform.translate((bodyWidth*0.5f)+(armScale*0.5f),bodyHeight,0));
  //     leftArmRotate = new TransformNode("leftarm rotate",Mat4Transform.rotateAroundX(180));
  //     m = new Mat4(1);
  //     m = Mat4.multiply(m, Mat4Transform.scale(armScale,armLength,armScale));
  //     m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
  //     TransformNode leftArmScale = new TransformNode("leftarm scale", m);
  //       ModelNode leftArmShape = new ModelNode("Cube(left arm)", cube2);
    
  //   NameNode rightarm = new NameNode("right arm");
  //     TransformNode rightArmTranslate = new TransformNode("rightarm translate", 
  //                                           Mat4Transform.translate(-(bodyWidth*0.5f)-(armScale*0.5f),bodyHeight,0));
  //     rightArmRotate = new TransformNode("rightarm rotate",Mat4Transform.rotateAroundX(180));
  //     m = new Mat4(1);
  //     m = Mat4.multiply(m, Mat4Transform.scale(armScale,armLength,armScale));
  //     m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
  //     TransformNode rightArmScale = new TransformNode("rightarm scale", m);
  //       ModelNode rightArmShape = new ModelNode("Cube(right arm)", cube2);
        
  //   NameNode leftleg = new NameNode("left leg");
  //     m = new Mat4(1);
  //     m = Mat4.multiply(m, Mat4Transform.translate((bodyWidth*0.5f)-(legScale*0.5f),0,0));
  //     m = Mat4.multiply(m, Mat4Transform.rotateAroundX(180));
  //     m = Mat4.multiply(m, Mat4Transform.scale(legScale,legLength,legScale));
  //     m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
  //     TransformNode leftlegTransform = new TransformNode("leftleg transform", m);
  //       ModelNode leftLegShape = new ModelNode("Cube(leftleg)", cube);
    
  //   NameNode rightleg = new NameNode("right leg");
  //     m = new Mat4(1);
  //     m = Mat4.multiply(m, Mat4Transform.translate(-(bodyWidth*0.5f)+(legScale*0.5f),0,0));
  //     m = Mat4.multiply(m, Mat4Transform.rotateAroundX(180));
  //     m = Mat4.multiply(m, Mat4Transform.scale(legScale,legLength,legScale));
  //     m = Mat4.multiply(m, Mat4Transform.translate(0,0.5f,0));
  //     TransformNode rightlegTransform = new TransformNode("rightleg transform", m);
  //       ModelNode rightLegShape = new ModelNode("Cube(rightleg)", cube);
        
    robotRoot.addChild(robotMoveTranslate);
      robotMoveTranslate.addChild(robotMoveRotato);
      robotMoveRotato.addChild(robotTranslate);
        robotTranslate.addChild(body);
          body.addChild(bodyRotate);
            bodyRotate.addChild(bodyTransform);
            bodyTransform.addChild(bodyShape);
          body.addChild(leg);
            leg.addChild(legTransform);
            legTransform.addChild(legShape);
          bodyRotate.addChild(neck);
            neck.addChild(neckTransform);
              neckTransform.addChild(neckShape);
              neck.addChild(head);
                head.addChild(headTranslate);
                headTranslate.addChild(headScale);
                  headScale.addChild(headShape);
                head.addChild(lefteye);
                  lefteye.addChild(leftEyeTranslate);
                  leftEyeTranslate.addChild(leftEyeScale);
                  leftEyeScale.addChild(leftEyeShape);
                head.addChild(righteye);
                  righteye.addChild(rightEyeTranslate);
                  rightEyeTranslate.addChild(rightEyeScale);
                  rightEyeScale.addChild(rightEyeShape);
                head.addChild(hair);
                  hair.addChild(hairTranslate);
                  hairTranslate.addChild(hairRotate);
                  hairRotate.addChild(hairScale);
                  hairScale.addChild(hairShape);
          // body.addChild(leftarm);
          //   leftarm.addChild(leftArmTranslate);
          //   leftArmTranslate.addChild(leftArmRotate);
          //   leftArmRotate.addChild(leftArmScale);
          //   leftArmScale.addChild(leftArmShape);
          // body.addChild(rightarm);
          //   rightarm.addChild(rightArmTranslate);
          //   rightArmTranslate.addChild(rightArmRotate);
          //   rightArmRotate.addChild(rightArmScale);
          //   rightArmScale.addChild(rightArmShape);
          // body.addChild(leftleg);
          //   leftleg.addChild(leftlegTransform);
          //   leftlegTransform.addChild(leftLegShape);
          // body.addChild(rightleg);
          //   rightleg.addChild(rightlegTransform);
          //   rightlegTransform.addChild(rightLegShape);
    
    robotRoot.update();  // IMPORTANT - don't forget this
    //robotRoot.print(0, false);
    //System.exit(0);
  }
 
  private void render(GL3 gl) {
    Mat4 m = new Mat4(1);
    m = Mat4.multiply(m, Mat4Transform.translate(4.6f, 4.75f, 4));
    m = Mat4.multiply(m, Mat4Transform.translate(0f, 0.25f, 0));
    m = Mat4.multiply(m, Mat4Transform.scale(0.5f,0.5f,0.5f));
    m = Mat4.multiply(m, Mat4Transform.rotateAroundX(getLightCubePosition()));
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    light.setPosition(getSunLightPosition());  // sun light position
    light.render(gl);
    light_lim.setPosition(getLimLightPosition());  // light position
    light_lim.render(gl);
    floor.render_spotlight(gl, getLightPosition());
    back_floor.render(gl); 
    left_floor_1.render(gl); 
    left_floor_2.render(gl); 
    left_floor_3.render(gl); 
    left_floor_4.render(gl); 
    left_floor_5.render(gl); 
    left_floor_6.render(gl); 
    left_floor_7.render(gl); 
    left_floor_8.render(gl); 
    outside_view.render(gl); 
    gate.render(gl); 
    cube_egg.render(gl); 
    phone.render(gl); 
    cube_phone.render(gl); 
    egg.render(gl); 
    light_cube_1.render(gl); 
    light_cube_2.render(gl); 
    light_cube_3.render(gl);
    light_cube_4.setModelMatrix(m);
    light_cube_4.render(gl);
    robotRoot.draw(gl);
  }
  
  // The light's postion is continually being changed, so needs to be calculated for each frame.
  private Vec3 getLightPosition() {
    double elapsedTime = getSeconds()-startTime;
    float x = 0;
    float y = -4;
    float z = (float)(Math.cos(Math.toRadians(elapsedTime*3)*(-30)));
    return new Vec3(x,y,z);
  }

  // The light cube angle.
  private float getLightCubePosition() {
    float angle = 90;
    return angle;   
  }

  // The sun light's postion.
  private Vec3 getSunLightPosition() { 
    return new Vec3(0,10,0);
  }

  // The light's postion.
  private Vec3 getLimLightPosition() { 
    return new Vec3(4.6f, 5f, 4);
  }

  
  // ***************************************************
  /* TIME
   */ 
  
  private double startTime;
  
  private double getSeconds() {
    return System.currentTimeMillis()/1000.0;
  }

  // ***************************************************
  /* An array of random numbers
   */ 
  
  private int NUM_RANDOMS = 1000;
  private float[] randoms;
  
  private void createRandomNumbers() {
    randoms = new float[NUM_RANDOMS];
    for (int i=0; i<NUM_RANDOMS; ++i) {
      randoms[i] = (float)Math.random();
    }
  }
  
}