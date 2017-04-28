package com.principal;


public class Kittler extends Algoritmo{
  
  private double umbral;
  
  @Override
  public void ejecutar(Imagen imagen) {
    double umbral = kittler(imagen.getHistogramaNormalizado())[0];
    int filas = imagen.getFilas();
    int columnas = imagen.getColumnas();
    for(int i = 0; i<filas;i++){
      for(int y = 0; y<columnas;y++){
        double[] z = new double[1];
          if(imagen.getPixelAt(i, y)[0]<umbral){
            z[0] = 0;
          }else{
            z[0] = 255;
          }
            imagen.setPixelAt(i, y, z);
      }
    }
    imagen.actualizarHistograma();
  }

  @Override
  public String generarReporte() {
    String s = "Algoritmo de Kittler, Umbral óptimo: "+Double.toString(this.umbral)+"\n";
    return s;
  }
  
  public double[] kittler(double[] y){
    double[] pixelOptimo = new double[5];
    double umbralOptimo = Double.MAX_VALUE;
    
    for(int i = 0; i<256;i++){
      double p1 = sumatoriaHistograma(0, i, y);
      double p2 = 1-p1;
      if(p1 == 0|| p2 == 0){
        continue;
      }
      double u1 = sumatoriaEsperanzaHistograma(0, i, y)/p1;
      double u2 = sumatoriaEsperanzaHistograma(i+1, 255, y)/p2;
      double o1 = sumatoriaVarianciaHistograma(0, i, u1, y)/p1;
      double o2 = sumatoriaVarianciaHistograma(i+1, 255, u2, y)/p2;
        //o1+=Double.MIN_VALUE;
        //o2+=Double.MIN_VALUE;
      if(o1 == 0 || o2 == 0){
        continue;
      }
      double umbral = 1+2*( p1*Math.log(Math.sqrt(o1)) + p2*Math.log(Math.sqrt(o2))) - 2*(p1*Math.log(p1) + p2*Math.log(p2)  );
      if(umbral<umbralOptimo){
        umbralOptimo = umbral;
        pixelOptimo[0] = i;
        pixelOptimo[1] = u1;
        pixelOptimo[2] = u2;
        pixelOptimo[3] = o1;
        pixelOptimo[4] = o2;
      }
    }
    this.umbral = pixelOptimo[0];
    return pixelOptimo;
 }

  private double sumatoriaHistograma(int inicio, int fin, double[] histogramaNormalizado){
    double suma = 0;
     for(int i = inicio; i<=fin;i++){
       suma = suma+histogramaNormalizado[i];
     }
     return suma;
  }
  
  private double sumatoriaVarianciaHistograma(int inicio, int fin, double u, double[] histogramaNormalizado){
    double suma = 0;
    for(int i = inicio; i<=fin;i++){
      suma+=histogramaNormalizado[i]*Math.pow((i-u), 2);
    }
    return suma;
  }
  
  private double sumatoriaEsperanzaHistograma(int inicio, int fin, double[] histogramaNormalizado){
    double suma = 0;
    for(int i = inicio; i<=fin;i++){
      suma = suma+(i*histogramaNormalizado[i]);
    }
    return suma;
  }
  
  public double getUmbral(){
    return this.umbral;
  }
  
}
