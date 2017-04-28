package com.principal;


public class ImagenCodificadorCreador {
  private static ImagenCodificadorCreador instancia = null;
  private ImagenCodificadorCreador(){}
  
  public static ImagenCodificadorCreador getInstancia(){
    if(instancia == null){
      instancia = new ImagenCodificadorCreador();
    }
    return instancia;
  }
  
  public static ImagenCodificador crearImagenCodificador(AlgoritmoCodificacion al){
    switch(al){
      case BASE64_TOMCAT:
        return new ImagenCodificadorBase64Tomcat();
      default:
        return null;
    }
  }
  
}
