package com.principal;

/**
 * Clase creadora de codificadores/decodificadores. 
 * Usa patr�n de dise�o Singleton.
 * 
 * @author Kevin Ram�rez
 *
 * @version 1.0
 * 
 * @since 1.0
 */
public class ImagenCodificadorCreador {
  
  /**
   * Instancia de ImagenCodificadorCreador est�tica
   */
  private static ImagenCodificadorCreador instancia = null;
  
  private ImagenCodificadorCreador(){}
  
  /**
   * Brinda el objeto est�tico de la clase.
   * @return objeto est�tico de la clase.
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
   * @author Kevin Ram�rez
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
