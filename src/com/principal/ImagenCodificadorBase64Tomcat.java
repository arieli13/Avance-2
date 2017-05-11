package com.principal;


import org.apache.tomcat.util.codec.binary.Base64;
/**
 * Clase con el tipo de codificador-decodificador en base64
 * @author ariel
 *
 */
public class ImagenCodificadorBase64Tomcat extends ImagenCodificador{
  
  //Usa la libreria Base64 de java para decodificar o codificar la imagen
  @Override
  public byte[] decodificarImagen(String s) {
    return Base64.decodeBase64(s);
  }

  @Override
  public String codificarImagen(Imagen i) {
    return Base64.encodeBase64String(i.getByteArray());
  }

}
