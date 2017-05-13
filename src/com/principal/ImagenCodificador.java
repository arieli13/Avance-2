package com.principal;

import java.io.IOException;

/**
 * 
 * Clase abstracta para cada tipo de imagen a codificar.
 * 
 * @author Kevin Ramírez
 *
 * @version 1.0
 * 
 * @since 1.0
 */
public abstract class ImagenCodificador {
  /**
   * Decodifica una imagen y retorna su arreglo de bytes.
   * 
   * @param s Imagen codificada
   * 
   * @return Arreglo de bytes de la imagen
   */
  public abstract byte[] decodificarImagen(String s);
  
  /**
   * Codifica una imagen y retorna su String codificado.
   * 
   * @param i Imagen a codificar
   * 
   * @return String de la imagen codificada.
   * @throws IOException Cuando la imagen es nula
   */
  public abstract String codificarImagen(Imagen i) throws IOException;
}
