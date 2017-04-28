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

public class ImagenMatOpenCv extends Imagen{
  
  private Mat imagen;

  public ImagenMatOpenCv(int filas, int columnas, String nombre, String formato, Canales canales) {
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
    this.calcularHistograma();
  }
  
  public ImagenMatOpenCv(String nombre, String formato, byte[] bytes) {
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
    this.calcularHistograma();
  }
  
  private BufferedImage createImageFromBytes(byte[] imageData) {
    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
    try {
      return ImageIO.read(bais);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Imagen clonar() {
    ImagenMatOpenCv x = new ImagenMatOpenCv(filas, columnas, nombre, formato, canales);
    x.setImagen(this.imagen.clone());
    return x;
  }

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

  @Override
  public void setPixelAt(int x, int y, double[] pixel) {
    this.imagen.put(x, y, pixel);
  }

  @Override
  public double[] getPixelAt(int x, int y) {
    return this.imagen.get(x, y);
  }
  
  public Mat getImagen(){
    return this.imagen;
  }
  
  public void setImagen(Mat imagen){
    this.imagen = imagen;
    this.calcularHistograma();
  }
  
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
  
  @Override
  public byte[] getByteArray() {
    BufferedImage x = this.Mat2BufferedImage(this.imagen);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    try {
      ImageIO.write( x, "jpg", baos );
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    try {
      baos.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    byte[] imageInByte = baos.toByteArray();
    
    try {
      baos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return imageInByte;
  }
  
  @Override
  public void toGreyScale() {
    if(this.canales != Canales.C1){
      Mat nueva = new Mat(this.filas, this.columnas, CvType.CV_8UC1);
      Imgproc.cvtColor(this.imagen, nueva, Imgproc.COLOR_BGR2GRAY);
      this.canales = Canales.C1;
      this.setImagen(nueva);
    }
  }
  
}
