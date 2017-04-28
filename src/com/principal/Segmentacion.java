package com.principal;


import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Segmentacion extends Algoritmo{
  
  int cantCelulas;
  ArrayList<Point> centroides;
  ArrayList<Double> areas;
  
  
  public Segmentacion(){
    this.centroides = new ArrayList<Point>();
    this.areas = new ArrayList<Double>();
    this.cantCelulas = 0;
  }
  @Override
  public void ejecutar(Imagen imagen) {
    if(!(imagen instanceof ImagenMatOpenCv)){
      return;
    }
    imagen.setCanales(Canales.C3);
    ((ImagenMatOpenCv)imagen).setImagen(bwareaopen(((ImagenMatOpenCv)imagen).getImagen(), 50, new Scalar(0)));
    return;
  }

  @Override
  public String generarReporte() {
    String s = "Total de celulas: "+this.cantCelulas+"\n";
    for(int i = 0; i<this.cantCelulas;i++){
      Point x = this.centroides.get(i);
      s+= i+1+": Centroide: ("+x.x+", "+x.y+"), area: "+this.areas.get(i)+"\n";
    }
    return s;
  }
  
  private Point centroid(Point[] knots)  {
    double centroidX = 0, centroidY = 0;
    for(Point knot : knots) {
      centroidX += knot.x;
      centroidY += knot.y;
    }
    return new Point(centroidX / knots.length, centroidY / knots.length);
  }

  private int getRandom(int minimo, int maximo){
    return minimo + (int)(Math.random() * maximo);
  }
  
  private Mat bwareaopen(Mat input, int area, Scalar color)
  {
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
  
  public void ejecutarPrueba(Imagen imagen) {
    if(!(imagen instanceof ImagenMatOpenCv)){
      return;
    }
    imagen.setCanales(Canales.C1);
    ((ImagenMatOpenCv)imagen).setImagen(bwareaopenPrueba(((ImagenMatOpenCv)imagen).getImagen(), 50));
  }
  
  public Mat bwareaopenPrueba(Mat input, int area)
  {
    Mat output = input.clone();
    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    Imgproc.findContours(input, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
    for (int i = 0; i < contours.size(); i++){
      if (Imgproc.contourArea(contours.get(i)) < area){
        Imgproc.drawContours(output, contours, i, new Scalar(0), -1);
      }else{
        Imgproc.drawContours(output, contours, i, new Scalar(1), -1);
      }
    }
    return output;
  }
  
}
