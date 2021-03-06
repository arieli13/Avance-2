package com.principal;

import java.io.IOException;

/**
   * Esta clase representa todos los datos
   * de una imagen cargada para ser usada
   * por los algoritmos, accediendo a todos
   * los datos de la img.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   * 
   * @version 1.0
   * 
   * @since 1.0
   */
public abstract class Imagen implements Cloneable {
  /**
   * N�mero de filas de la imagen
   */
  protected int filas;
  
  /**
   * N�mero de columnas de la imagen
   */
  protected int columnas;
  
  /**
   * Nombre de la imagen
   */
  protected String nombre;
  
  /**
   * Formato de la imagen
   */
  protected String formato;
  
  /**
   * Histograma de la imagen
   */
  protected double[] histograma;
  
  /**
   * Tipo de canales de la imagen.
   */
  protected Canales canales;
  
  public Imagen(String nombre, String formato) throws Exception{
    if(nombre == "" || formato == ""){
        throw new Exception("El nombre y el formato no pueden ser nulos");
    }
    this.nombre = nombre;
    this.formato = formato;
  }
  
  /**
   * Cambia nombre de la imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   * 
   * @param nombre Nuevo nombre de la imagen
   * @throws Exception Cuando el nombre es nulo
   */
  public void setNombre(String nombre) throws Exception{
    if(nombre == ""){
      throw new Exception("El nombre no puede ser nulo");
    }
    this.nombre = nombre;
  }
  
  /**
   * Cambia formato de la imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   * 
   * @param formato Nuevo formato de la imagen
   * @throws Exception 
   */
  public void setFormato(String formato) throws Exception{
    if(formato == ""){
      throw new Exception("El formato no puede ser nulo");
    }
    this.formato = formato;
  }
  
  /**
   * Cambia n�mero de filas de la imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   * 
   * @param filas Nuevo n�mero de filas
   * @throws Exception Cuando las filas menores o iguales a 0
   */
  public void setFilas(int filas) throws Exception{
    if(filas<=0){
      throw new Exception("Las filas no pueden ser nulas");
    }
    this.filas = filas;
  }
  
  /**
   * Cambia n�mero de columnas de la imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com
   * 
   * @param columnas Nuevo n�mero de columnas
   * @throws Exception Cuando las columnas son menores o iguales a 0
   */
  public void setColumnas(int columnas) throws Exception{
    if(columnas<=0){
      throw new Exception("Las columnas tienen que ser mayores a 0");
    }
    this.columnas = columnas;
  }
  
  /**
   * Cambia canales de una imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   * 
   * @param canales Nuevos canales de la imagen
   */
  public void setCanales(Canales canales){
    this.canales = canales;
  }
  
  /**
   * Brinda el nombre de una imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   * 
   * @return Nombre de la imagen
   */
  public String getNombre(){
      return this.nombre;
  }
  
  /**
   * Brinda el formato de una imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   * 
   * @return Formato de la imagen
   */
  public String getFormato(){
      return this.formato;
  }
  
  /**
   * Brinda el n�mero de filas de la imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   *  
   * @return N�mero de filas de la imagen
   */
  public int getFilas(){
    return this.filas;
  }
  
  /**
   * Brinda el n�mero de columnas de la imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   *  
   * @return N�mero de columnas de la imagen
   */
  public int getColumnas(){
    return this.columnas;
  }
  
  /**
   * Brinda canales de la imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   *  
   * @return Canales de la imagen
   */
  public Canales getCanales(){
    return this.canales;
  }
  
  /**
   * Brinda el histograma de la imagen
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   *  
   * @return Histograma de la imagen
   */
  public double[] getHistograma(){
      return this.histograma;
  }
  
  /**
   * Actualiza el histograma con los nuevos datos de la imagen.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   */
  public void actualizarHistograma(){
      calcularHistograma();
  }
  
  /**
   * Calcula el coeficiente de Dice entre la imagen actual y otra ingresada por par�metro.
   * 
   * @author Ariel Rodr�guez arieli13.10@gmail.com 
   *  
   * @param img Imagen para comparar con la actual y obtener el coeficiente de Dice
   *  
   * @return coeficiente de dice.
   * @throws Exception Cuando la imagen es nula o las filas y columnas no son las mismas
   */
  public double coeficienteDice(Imagen img) throws Exception{
   if(img == null){
     throw new Exception("No se puede comparar una imagen con otra nula");
   }
   if(filas!=img.getFilas() || columnas!=img.getColumnas()){
     throw new Exception("Las im�genes deben ser de iguales dimensiones.");
   }
   double numerador = 0;
   double denominador = 0;
   for(int i = 0; i<filas;i++){
    for(int y = 0; y<columnas;y++){
 
     numerador+= (((img.getPixelAt(i, y)[0]) != 0)?1:0)* ((getPixelAt(i, y)[0]!=0)?1:0);
     denominador+=(((img.getPixelAt(i, y)[0]) != 0)?1:0)+((getPixelAt(i, y)[0]!=0)?1:0);
    }
   }
   numerador*=2;
   return numerador/denominador;
  }
  
  public abstract Imagen clonar() throws Exception;
  public abstract double[] getHistogramaNormalizado();
  public abstract void calcularHistograma();
  public abstract void setPixelAt(int x, int y, double[] pixel);
  public abstract double[] getPixelAt(int x, int y);
  public abstract byte[] getByteArray() throws IOException;
  public abstract void toGreyScale() throws Exception;
  
}