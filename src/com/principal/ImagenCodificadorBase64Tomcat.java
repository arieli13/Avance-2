package com.principal;


import org.apache.tomcat.util.codec.binary.Base64;

public class ImagenCodificadorBase64Tomcat extends ImagenCodificador{

  @Override
  public byte[] decodificarImagen(String s) {
    return Base64.decodeBase64(s);
  }

  @Override
  public String codificarImagen(Imagen i) {
    return Base64.encodeBase64String(i.getByteArray());
  }

}
