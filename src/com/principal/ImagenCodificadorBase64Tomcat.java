package com.principal;
import java.io.IOException;

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
   */
  @Override
  public byte[] decodificarImagen(String s) {
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
   * @throws IOException Cuando la imagen es nula
   */
  @Override
  public String codificarImagen(Imagen i) throws IOException {
    return Base64.encodeBase64String(i.getByteArray());
  }

}
