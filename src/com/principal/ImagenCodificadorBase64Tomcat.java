package com.principal;

import org.apache.tomcat.util.codec.binary.Base64;
/**
 * Clase con el tipo de codificador-decodificador en base64
 * 
 * @author José Pablo Navarro j.pablonavarro95@gmail.com
 *
 * @version 1.0
 *
 * @since 1.0
 */
public class ImagenCodificadorBase64Tomcat extends ImagenCodificador{
  
  /**
   * Usa la libreria Base64 de java para decodificar la imagen
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @param s Imagen codificada en Base64
   * 
   * @return Arreglo de Bytes, la imagen decodificada
   * @throws Exception Cuando el string es nulo o vacío
   */
  @Override
  public byte[] decodificarImagen(String s) throws Exception {
    if(s == null || s == ""){
      throw new Exception("El str de la imagen a decodificar no puede ser vacío");
    }
    return Base64.decodeBase64(s);
  }

  /**
   * Usa la libreria Base64 de java para codificar la imagen
   * 
   * @author José Pablo Navarro j.pablonavarro95@gmail.com
   * 
   * @param s Imagen a ser codificada
   * 
   * @return String en Base64 de la imagen codificada.
   * @throws Exception Cuando la imagen es nula
   */
  @Override
  public String codificarImagen(Imagen i) throws Exception {
    if(i == null){
      throw new Exception("La imagen a codificar no puede ser nula");
    }
    return Base64.encodeBase64String(i.getByteArray());
  }

}
