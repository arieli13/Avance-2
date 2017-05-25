package com.principal;

/**
 * Aplica algoritmo de Kittler a una imagen
 * 
 * Calcula el umbral óptimo (pixel medio) de una imagen para posteriormente binarizarla.
 * 
 * @author José Pablo Navarro j.pablonavarro95@gmail.com
 * 
 * @version 1.0
 * @since 1.0
 */
public class Kittler extends Algoritmo{
  
  /**
   * El umbral óptimo de la imagen.
   */
  private double umbral;
  
  /**
   * Aplica el algoritmo de Kittler a una imagen.
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * usa {@link #kittler(double[])} para calcular el umbral óptimo, luego por cada pixel
   * en la imagen, si este es menor al umbral se cambia por 0 sino se cambia por 1.
   * 
   * @param imagen imagen que se va a binarizar.
   * @throws Exception Cuando la imagen es nula
   * 
   */
  @Override
  public void ejecutar(Imagen imagen) throws Exception {
    if(imagen == null){
      throw new Exception("Error al aplicar el algoritmo de Kittler");
    }
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

  /**
   * Genera reporte del algoritmo.
   * 
   * Genera un reporte del algoritmo realizado, indica cual fue el umbral óptimo calculado.
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @return Retorna el reporte, un String.
   * 
   */
  @Override
  public String generarReporte() {
    String s = "Algoritmo de Kittler;Umbral optimo;"+Double.toString(this.umbral)+"\n";
    return s;
  }
  
  /**
   * Calcula el umbral óptimo de la imagen.
   * 
   * Mediante el histograma de la imagen calcula el umbral óptimo con el algoritmo de Kittler.
   * 
   * usa {@link #sumatoriaEsperanzaHistograma(int, int, double[])} para calcular esperanza de pixeles desde un inicio y un fin.
   * usa {@link #sumatoriaVarianciaHistograma(int, int, double, double[]) } para calcular varianza de pixeles desde un inicio y un fin.
   * usa {@link #sumatoriaHistograma(int, int, double[])} para calcular esperanza de pixeles desde un inicio y un fin.
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @param y es el histograma de la imagen, en escala de grises.
   * 
   * @return retorna un arreglo de double de 5 elementos. El 0: umbral óptimo, 1: esperanza 1, 2: esperanza 2, 3: varianza 1, 4: varianza 2
   */
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
  
  /**
   * Suma todos los pixeles de un histograma desde una pos inicial hasta una final.
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @param inicio indica el índice inicial para la sumatoria.
   * @param fin indica el índice final para la sumatoria.
   * @param histogramaNormalizado histograma para el cual se aplica la sumatoria.
   * 
   * @return retorna el resultado de la sumatoria.
   */
  private double sumatoriaHistograma(int inicio, int fin, double[] histogramaNormalizado){
    double suma = 0;
     for(int i = inicio; i<=fin;i++){
       suma = suma+histogramaNormalizado[i];
     }
     return suma;
  }
  
  /**
   * Suma todos los pixeles de un histograma menos su esperanza, el resultado se eleva al cuadrado; desde una pos inicial hasta una final.
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @param inicio indica el índice inicial para la sumatoria.
   * @param fin indica el índice final para la sumatoria.
   * @param histogramaNormalizado histograma para el cual se aplica la sumatoria.
   * 
   * @return retorna el resultado de la sumatoria.
   */
  private double sumatoriaVarianciaHistograma(int inicio, int fin, double u, double[] histogramaNormalizado){
    double suma = 0;
    for(int i = inicio; i<=fin;i++){
      suma+=histogramaNormalizado[i]*Math.pow((i-u), 2);
    }
    return suma;
  }
  
  /**
   * Suma todos los pixeles de un histograma desde una pos inicial hasta una final y divide el resultado entre la cantidad de pixeles.
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @param inicio indica el índice inicial para la sumatoria.
   * @param fin indica el índice final para la sumatoria.
   * @param histogramaNormalizado histograma para el cual se aplica la sumatoria.
   * 
   * @return retorna el resultado de la sumatoria.
   */
  private double sumatoriaEsperanzaHistograma(int inicio, int fin, double[] histogramaNormalizado){
    double suma = 0;
    for(int i = inicio; i<=fin;i++){
      suma = suma+(i*histogramaNormalizado[i]);
    }
    return suma;
  }
  
  /**
   * Indica el umbral óptimo de la imagen.
   *
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @return Retorna el umbral óptimo de la imagen.
   */
  public double getUmbral(){
    return this.umbral;
  }
  
}
