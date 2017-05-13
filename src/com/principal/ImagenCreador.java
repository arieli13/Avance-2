package com.principal;

/**
 * Clase creadora de imágenes
 * Usa patrón de diseño Singleton.
 * 
 * @author José Pablo Navarro j.pablonavarro95@gmail.com
 *
 * @version 1.0
 * 
 * @since 1.0
 */
public class ImagenCreador {
  /**
   * Instancia de ImagenCreador estática
   */
  private static ImagenCreador instancia = null;
  
  private ImagenCreador(){}
  
  /**
   * Brinda el objeto estático de la clase.
   * @return objeto estático de la clase.
   */
  public static ImagenCreador getInstancia(){
    if(instancia == null){
      instancia = new ImagenCreador();
    }
    return instancia;
  }
  
  /**
   * Crea una imagen del tipo ingresado por parámetro.
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @param bytes Arreglo de bytes de una imagen
   * @param tipo Tipo de imagen a ser creada
   * @param nombre Nombre de la imagen
   * @param formato Formato de la imagen
   * 
   * @return Imagen creada.
   * @throws Exception  
   */
  public static Imagen crearImagen(byte[] bytes, TipoImagen tipo, String nombre, String formato) throws Exception {
    switch(tipo){
      case OPENCV:
        return new ImagenMatOpenCv(nombre, formato, bytes);
      default:
        return null;
    }
  }
  
}
