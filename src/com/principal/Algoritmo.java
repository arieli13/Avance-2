package com.principal;

/**
 * Clase abstracta para los algoritmos que se le aplicarán a una imagen.
 * 
 * @author Kevin Ramírez
 * 
 * @version 1.0
 * @since 1.0
 */
public abstract class Algoritmo {
  
  public Algoritmo(){}
  
  /**
   * Procesa la imagen, aplicándole un algoritmo definido.
   * 
   * @param imagen Imagen a ser procesada.
   * @throws Exception Cuando la imagen es nula
   */
  public abstract void ejecutar(Imagen imagen) throws Exception;
  
  /**
   * Genera el reporte del algoritmo realizado.
   * 
   * @return Reporte generado, String.
   */
  public abstract String generarReporte();
}
