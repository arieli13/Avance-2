package com.principal;

/**
 * Clase creadora de codificadores/decodificadores. 
 * Usa patrón de diseño Singleton.
 * 
 * @author Kevin Ramírez
 *
 * @version 1.0
 * 
 * @since 1.0
 */
public class ImagenCodificadorCreador {
  
  /**
   * Instancia de ImagenCodificadorCreador estática
   */
  private static ImagenCodificadorCreador instancia = null;
  
  private ImagenCodificadorCreador(){}
  
  /**
   * Brinda el objeto estático de la clase.
   * @return objeto estático de la clase.
   */
  public static ImagenCodificadorCreador getInstancia(){
    if(instancia == null){
      instancia = new ImagenCodificadorCreador();
    }
    return instancia;
  }
  
  /**
   * Retorna una instancia de un codificador/decodificador
   * 
   * @author Kevin Ramírez
   * 
   * @param al Tipo de algoritmo que quiere crear
   * 
   * @return Instancia de un codificador/decodificador.
   */
  public static ImagenCodificador crearImagenCodificador(AlgoritmoCodificacion al){
    switch(al){
      case BASE64_TOMCAT:
        return new ImagenCodificadorBase64Tomcat();
      default:
        return null;
    }
  }
  
}
