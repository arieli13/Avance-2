package com.principal;


public abstract class ImagenCodificador {
  public abstract byte[] decodificarImagen(String s);
  public abstract String codificarImagen(Imagen i);
}
