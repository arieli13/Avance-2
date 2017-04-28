package com.principal;

public class Sistema {
  
  private static Sistema instancia = null;
  
  public Sistema(){}
  
  public static Sistema getInstancia(){
    if(instancia == null){
      instancia = new Sistema();
    }
    return instancia;
  }
  
  public String procesarLote(String[] s){
    for(int i = 0; i<s.length;i++){
      procesarImagen(s[i], "N", "png", TipoImagen.OPENCV);
    }
    return "";
  }
  
  public String[] procesarImagen(String img, String nombre, String formato, TipoImagen tipo){
    ImagenCodificador x = ImagenCodificadorCreador.crearImagenCodificador(AlgoritmoCodificacion.BASE64_TOMCAT);
    ImagenMatOpenCv y = (ImagenMatOpenCv) ImagenCreador.crearImagen(x.decodificarImagen(img), tipo, nombre, formato);
    y.toGreyScale();
    Kittler k = new Kittler();
    k.ejecutar(y);
    String reporte = k.generarReporte();
    Segmentacion z = new Segmentacion();
    z.ejecutar(y);
    reporte+= z.generarReporte();
    ImagenMatOpenCv n = (ImagenMatOpenCv) ImagenCreador.crearImagen(y.getByteArray(), tipo, nombre, formato);
    String[] retorno = new String[2];
    retorno[0] = x.codificarImagen(n);
    retorno[1] = reporte;
    return retorno;
  }
  
}
