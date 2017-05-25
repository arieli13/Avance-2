package com.principal;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Segmenta una imagen, etiqueta las c�lulas y las enumera.
 * 
 * @author Ariel Rodr�guez arieli13.10@gmail.com
 * @version 1.0
 * @since 1.0
 */
public class Segmentacion extends Algoritmo{
  
  /**
   * Cantidad de c�lulas contadas en la imagen.
   */
  int cantCelulas;
  
  /**
   * Arreglo con los centroides de cada c�lula.
   */
  ArrayList<Point> centroides;
  
  /**
   * Arreglo con el �rea de cada c�lula.
   */
  ArrayList<Double> areas;
  
  /**
   * Area m�nima de un objeto para segmentar
   */
  int minArea;
  
  
  public Segmentacion(){
    this.centroides = new ArrayList<Point>();
    this.areas = new ArrayList<Double>();
    this.cantCelulas = 0;
    this.minArea = 50;
  }
  
  public Segmentacion(int minArea){
    this.centroides = new ArrayList<Point>();
    this.areas = new ArrayList<Double>();
    this.cantCelulas = 0;
    this.minArea = minArea;
  }
  
  /**
   * Segmenta una imagen, en caso de no estar en escala de grises, la convierte.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com
   * 
   * usa {@link #bwareaopen(Mat, int, Scalar)} para aplicar la segmentaci�n.
   * @throws Exception Cuando la imagen es nula
   */
  @Override
  public void ejecutar(Imagen imagen) throws Exception {
    if(!(imagen instanceof ImagenMatOpenCv)){
      return;
    }
    imagen.setCanales(Canales.C3);
    ((ImagenMatOpenCv)imagen).setImagen(bwareaopen(((ImagenMatOpenCv)imagen).getImagen(), minArea, new Scalar(0)));
    return;
  }
  
  /**
   * Genera reporte del algoritmo.
   * 
   * Genera un reporte del algoritmo realizado, indica la cantidad de c�lulad y por cada
   * c�lula muestra su centroide y su �rea.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com
   * 
   * @return Retorna el reporte, un String.
   */
  @Override
  public String generarReporte() {
    String s = "Total de celulas;"+this.cantCelulas+"\n";
    s+="Cell Number;X Centroid;Y Centroid;Area\n";
    for(int i = 0; i<this.cantCelulas;i++){
      Point x = this.centroides.get(i);
      s+= i+1+";"+x.x+";"+x.y+";"+this.areas.get(i)+"\n";
    }
    return s;
  }
  
  /**
   * Calcula el centroide de un conjunto de puntos.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com
   * 
   * @param knots arreglo de pares ordenados, (x, y).
   * 
   * @return Centroide de los pares ordenados.
   */
  private Point centroid(Point[] knots)  {
    int centroidX = 0, centroidY = 0;
    for(Point knot : knots) {
      centroidX += knot.x;
      centroidY += knot.y;
    }
    return new Point(centroidX / knots.length, centroidY / knots.length);
  }

    /**
     * Calcula un n�mero random en un rango.
     * 
     * @author Ariel Rodr�guez arieli13.10@gmail.com
     * 
     * @param minimo Cota menor para el random.
     * 
     * @param maximo Cota mayor para el random.
     * 
     * @return N�mero entero generado.
     */
  private int getRandom(int minimo, int maximo){
    return minimo + (int)(Math.random() * maximo);
  }
  
  /**
   * Segmenta una imagen, etiqueta las c�lulas y las enumera.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com
   * 
   * @param input Imagen a ser segmentada.
   * @param area Tama�o m�nimo del �rea de un grupo de pixeles, para tomar como objeto.
   * @param color Color para pintar los pixeles que no son objetos.
   * 
   * @return Imagen segmentada.
   * @throws Exception Cuando la imagen es nula
   */
  private Mat bwareaopen(Mat input, int area, Scalar color) throws Exception
  {
    if(input == null){
      throw new Exception("No se pudo aplicar la segmentaci�n");
    }
    Mat output = input.clone();
    Imgproc.cvtColor(output, output, Imgproc.COLOR_GRAY2RGB);
    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    Imgproc.findContours(input, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
    int c = 255;
    for (int i = 0; i < contours.size(); i++){
      if (Imgproc.contourArea(contours.get(i)) < area){
        Imgproc.drawContours(output, contours, i, color, -1);
      }else{
        Imgproc.drawContours(output, contours, i, new Scalar(getRandom(10, 120), getRandom(0, 150), getRandom(0, 150)), -1);
        Point[] x = contours.get(i).toArray();
        this.cantCelulas++;
        Point centroide = centroid(x);
        this.centroides.add(centroide);
        this.areas.add(Imgproc.contourArea(contours.get(i)));
        Imgproc.putText(output, Integer.toString(this.cantCelulas), centroide, 1, 0.5, new Scalar(255, 255, 255));
        c-=30;
        if(c<=10){
          c = 255;
        }
      }
    }
    return output;
  }
  
  /**
   * Ejecuta la segmentaci�n de una imagen, pero no etiqueta ni enumera c�lulas.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com
   * 
   * @param imagen Imagen a ser segmentada.
   * 
   * usa {@link #bwareaopenPrueba(Mat, int)} para segmentar la imagen.
   * @throws Exception Cuando la imagen es nula
   */
  public void ejecutarSinEtiqueta(Imagen imagen) throws Exception {
    if(!(imagen instanceof ImagenMatOpenCv)){
      return;
    }
    imagen.setCanales(Canales.C3);
    ((ImagenMatOpenCv)imagen).setImagen(bwareaopenSinEtiqueta(((ImagenMatOpenCv)imagen).getImagen(), minArea));
  }
  
  /**
   * Segmenta una imagen, no etiqueta las c�lulas ni las enumera.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com
   * 
   * @param input Imagen a ser segmentada.
   * @param area Tama�o m�nimo del �rea de un grupo de pixeles, para tomar como objeto.
   * 
   * @return Imagen segmentada.
   * @throws Exception Cuando la imagen es nula
   */
  public Mat bwareaopenSinEtiqueta(Mat input, int area) throws Exception
  {
    if(input == null){
      throw new Exception("No se pudo aplicar la segmentaci�n");
    }
    Mat output = input.clone();
    Imgproc.cvtColor(output, output, Imgproc.COLOR_GRAY2RGB);
    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    Imgproc.findContours(input, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
    
    for (int i = 0; i < contours.size(); i++){
      if (Imgproc.contourArea(contours.get(i)) < area){
        Imgproc.drawContours(output, contours, i, new Scalar(0, 0, 0), -1);
      }else{
        Imgproc.drawContours(output, contours, i, new Scalar(255, 255, 255), -1);
        Point[] x = contours.get(i).toArray();
        this.cantCelulas++;
        Point centroide = centroid(x);
        this.centroides.add(centroide);
        this.areas.add(Imgproc.contourArea(contours.get(i)));
      }
    }
    return output;
  }
  
  /**
   * Asigna el valor del area m�nima
   * @param minArea
   */
  public void setMinArea(int minArea){
    this.minArea = minArea;
  }
  
}
