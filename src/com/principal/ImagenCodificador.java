package com.principal;

/**
 * 
 * Clase abstracta para cada tipo de imagen a codificar.
 * @author ariel
 *
 */
public abstract class ImagenCodificador {
  //Metodos abstractos codificar y decodificar para cada tipo de imagen.
  public abstract byte[] decodificarImagen(String s);
  public abstract String codificarImagen(Imagen i);
}
