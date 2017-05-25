package com.principal;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


/**
 * Instancia de Imagen que por dentro utiliza la librería OpenCV para manejar la imagen.
 * 
 * @author Ariel Rodríguez arieli13.10@gmail.com
 *
 * @version 1.0
 * 
 * @since 1.0
 */
public class ImagenMatOpenCv extends Imagen{
  
  /**
   * Objeto Mat de OpenCV para manejar la imagen
   */
  private Mat imagen;

  /**
   * Crea una nueva imagen
   * 
   * @param filas Número de filas de la imagen
   * @param columnas Número de columnas de la imagen
   * @param nombre Nombre de la imagen
   * @param formato Formato de la imagen
   * @param canales Canales de la imagen
   * @throws Exception Cuando no se puede crear la imagen y esta es nula
   */
  public ImagenMatOpenCv(int filas, int columnas, String nombre, String formato, Canales canales) throws Exception {
    super(nombre, formato);
    this.canales = canales;
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    this.filas = filas;
    this.columnas = columnas;
    if(canales == Canales.C1){
      this.imagen = new Mat(filas, columnas, CvType.CV_8UC1);
    }else{
      this.imagen = new Mat(filas, columnas, CvType.CV_8UC3);
    }
    if(this.imagen == null){
      throw new Exception("Error al crear la imagen");
    }
    this.calcularHistograma();
  }
  
  /**
   * Crea una nueva imagen
   * 
   * @param nombre Nombre de la imagen
   * @param formato Formato de la imagen
   * @param bytes Arreglo de Bytes para crear la imagen
   * @throws Exception Cuando no se puede crear la imagen y esta es nula
   */
  public ImagenMatOpenCv(String nombre, String formato, byte[] bytes) throws Exception {
    super(nombre, formato);
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    BufferedImage x = createImageFromBytes(bytes);
    this.filas = x.getHeight();
    this.columnas = x.getWidth();
    switch(x.getRaster().getNumDataElements()){
      case 1:
        this.canales = Canales.C1;
        this.imagen = new Mat(filas, columnas, CvType.CV_8UC1);
        break;
      case 2:
        this.canales = Canales.C2;
        this.imagen = new Mat(filas, columnas, CvType.CV_8UC2);
      case 3:
        this.canales = Canales.C3;
        this.imagen = new Mat(filas, columnas, CvType.CV_8UC3);
        break;
      case 4:
        this.canales = Canales.C4;
        this.imagen = new Mat(filas, columnas, CvType.CV_8UC4);
      default:
        break;
    }
    this.imagen.put(0, 0, (((DataBufferByte) x.getRaster().getDataBuffer()).getData()));
    if(this.imagen == null){
      throw new Exception("Error al crear la imagen");
    }
    this.calcularHistograma();
  }
  
  /**
   * Crea la imagen Mat apartir de un arreglo de bytes.
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   * 
   * @param imageData Arreglo de Bytes de la imagen
   * 
   * @return Imagen
   * @throws IOException Cuando no se puede crear la imagen
   */
  private BufferedImage createImageFromBytes(byte[] imageData) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
    return ImageIO.read(bais);
  }

  /**
   * Hace una clonación profunda del objeto
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   * @throws Exception Cuando no se puede crear la imagen
   */
  @Override
  public Imagen clonar() throws Exception {
    ImagenMatOpenCv x = new ImagenMatOpenCv(filas, columnas, nombre, formato, canales);
    for(int i = 0; i<filas;i++){
      for(int j = 0; j<columnas;j++){
        x.setPixelAt(i, j, this.getPixelAt(i, j));
      }
    }
    //x.setImagen(this.imagen.clone());
    return x;
  }

  /**
   * Obtiene el histograma normalizado de la imagen
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   */
  @Override
  public double[] getHistogramaNormalizado() {
    if(canales != Canales.C1){
      return null;
    }
    double[] nuevo = new double[256]; 
    int numeroPixeles = filas*columnas;
    for(int i = 0; i<256;i++){
      nuevo[i] = (double) histograma[i]/numeroPixeles;
    }
    return nuevo;
  }

  /**
   * Calcula el histograma de la imagen
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   */
  @Override
  public void calcularHistograma() {
    double[] pixeles = new double[256];
    for(int i = 0; i<filas; i++){
      for(int y = 0; y<columnas;y++){
        double[] x = this.imagen.get(i, y);
        int pixel = (int)x[0];
        pixeles[pixel] += 1;
      }
    }
    this.histograma = pixeles;
  }

  /**
   * Inserta un pixel en la posición x, y de la imagen
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   * 
   * @param x Fila del pixel 
   * @param y Columna del pixel
   * @param pixel nuevo pixel
   */
  @Override
  public void setPixelAt(int x, int y, double[] pixel) {
    this.imagen.put(x, y, pixel);
  }

  /**
   * Obtiene el pixel de una imagen en cierta posición
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   * 
   * @param Fila del pixel
   * @param Columna del pixel
   */
  @Override
  public double[] getPixelAt(int x, int y) {
    return this.imagen.get(x, y);
  }
  
  /**
   * Brinda el objeto Mat
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   * 
   * @return Objeto Mat
   */
  public Mat getImagen(){
    return this.imagen;
  }
  
  /**
   * Cambia la imagen Mat
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   * 
   * @param imagen Nueva imagen
   * @throws Exception Cuando no se puede actualizar la imagen
   */
  public void setImagen(Mat imagen) throws Exception{
    if(imagen == null){
      throw new Exception("Error al actualizar imagen");
    }
    this.imagen = imagen;
    this.calcularHistograma();
  }
  
  /**
   * Convierte una imagen Mat en una BufferedImage
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   * 
   * @param m Imagen Mat a convertir
   * 
   * @return Imagen BufferedImage nueva
   */
  
  private BufferedImage Mat2BufferedImage(Mat m) {
    int type = BufferedImage.TYPE_BYTE_GRAY;
    if( m.channels() > 1 ){
        type = BufferedImage.TYPE_3BYTE_BGR;
    }
    int bufferSize = m.channels()*m.cols()*m.rows();
    byte [] b = new byte[bufferSize];
    m.get(0,0,b); // get all the pixels
    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    System.arraycopy(b, 0, targetPixels, 0, b.length);  
    return image;
  }
  
  /**
   * Obtiene el arreglo de bytes de la imagen Mat
   * 
   * @author Ariel Rodríguez arieli13.10@gmail.com
   * @throws IOException Cuando no se puede obtener el arreglo de Bytes de la imagen
   */
  
  @Override
  public byte[] getByteArray() throws IOException {
    BufferedImage x = this.Mat2BufferedImage(this.imagen);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write( x, "jpg", baos );
    baos.flush();
    byte[] imageInByte = baos.toByteArray();
    baos.close();
    return imageInByte;
  }
  
  /**
   * Convierte la imagen a escala de grises
   * 
   * @author Ariel Rodríguez Jiménez
   * @throws Exception Cuando no se puede actualizar la imagen
   */
  @Override
  public void toGreyScale() throws Exception {
    if(this.canales != Canales.C1){
      Mat nueva = new Mat(this.filas, this.columnas, CvType.CV_8UC1);
      Imgproc.cvtColor(this.imagen, nueva, Imgproc.COLOR_BGR2GRAY);
      this.canales = Canales.C1;
      this.setImagen(nueva);
    }
  }
  
}
