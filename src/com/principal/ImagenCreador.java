package com.principal;


public class ImagenCreador {
  
  private static ImagenCreador instancia = null;
  private ImagenCreador(){}
  
  public static ImagenCreador getInstancia(){
    if(instancia == null){
      instancia = new ImagenCreador();
    }
    return instancia;
  }
  
  public static Imagen crearImagen(byte[] bytes, TipoImagen tipo, String nombre, String formato){
    switch(tipo){
      case OPENCV:
        return new ImagenMatOpenCv(nombre, formato, bytes);
      default:
        return null;
    }
  }
  
}
