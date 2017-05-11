package com.principal;
  
  /**
   * Esta clase representa todos los datos
   * de una imagen cargada para ser usada
   * por los algoritmos, accediendo a todos
   * los datos de la img.
   * @author ariel
   *
   */
public abstract class Imagen implements Cloneable {
  //Atributos protegidos correspondientes a los datos de la imagen.
  protected int filas;
  protected int columnas;
  protected String nombre;
  protected String formato;
  protected double[] histograma;
  protected Canales canales;
  
  //Constructor de una Imagen
  public Imagen(String nombre, String formato){
    this.nombre = nombre;
    this.formato = formato;
  }
  
  public void setNombre(String nombre){
    this.nombre = nombre;
  }
  
  public void setFormato(String formato){
    this.formato = formato;
  }
  
  public void setFilas(int filas){
    this.filas = filas;
  }
  
  public void setColumnas(int columnas){
    this.columnas = columnas;
  }
  
  public void setCanales(Canales canales){
    this.canales = canales;
  }
  
  public String getNombre(){
      return this.nombre;
  }
  
  public String getFormato(){
      return this.formato;
  }
  
  public int getFilas(){
    return this.filas;
  }
  
  public int getColumnas(){
    return this.columnas;
  }
  
  public Canales getCanales(){
    return this.canales;
  }
  
  public double[] getHistograma(){
      return this.histograma;
  }
  
  //Funcion que actualiza el histograma con los nuevos datos de la imagen.
  public void actualizarHistograma(){
      calcularHistograma();
  }
  
  public abstract Imagen clonar();
  public abstract double[] getHistogramaNormalizado();
  public abstract void calcularHistograma();
  public abstract void setPixelAt(int x, int y, double[] pixel);
  public abstract double[] getPixelAt(int x, int y);
  public abstract byte[] getByteArray();
  public abstract void toGreyScale();
  
}