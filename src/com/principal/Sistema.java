package com.principal;

/**
 * Controlador del sistema.
 * 
 * Utiliza el patr�n de dise�o Singleton.
 * 
 * @author Kevin Ram�rez 
 * @version 1.0
 * @since 1.0
 */
public class Sistema {
  
  /**
   * Instancia Singleton de la clase
   */
  private static Sistema instancia = null;
  
  public Sistema(){}
  
  /**
   * Retorna la instancia del sistema.
   * 
   * @author Kevin Ram�rez 
   * 
   * @return Instancia del Sistema.
   */
  public static Sistema getInstancia(){
    if(instancia == null){
      instancia = new Sistema();
    }
    return instancia;
  }
  
  /**
   * Procesa un lote de im�genes.
   * 
   * @author Kevin Ram�rez 
   * 
   * Usa {@link #procesarImagen(String, String, String, TipoImagen)} para procesar cada imagen enviada por par�metro.
   * 
   * @param s lote de im�genes, im�genes en base64 separadas por el caracter: <.
   * 
   * @return lote de im�genes procesadas con su reporte, en base64, 
   * separadas por el caracter < entre im�genes y > entre imagen y reporte.
   */
  public String procesarLote(String[] s){
    for(int i = 0; i<s.length;i++){
      procesarImagen(s[i], "N", "png", TipoImagen.OPENCV);
    }
    return "";
  }
  
  /**
   * Segmenta una imagen, eiqueta las c�lulas, las enumera y genera el reporte.
   * 
   * @author Kevin Ram�rez
   * 
   * @param img Imagen codificada en Base64
   * @param nombre Nombre de la imagen
   * @param formato Formato de la imagen
   * @param tipo Puede ser escala de grises o RGB
   * @return Arreglo de String, en el �ndice 0: Imagen procesada en Base64, 1: Reporte.
   */
  public String[] procesarImagen(String img, String nombre, String formato, TipoImagen tipo){
    try{
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
    }catch(Exception e){
      String[] ret = new String[2];
      ret[0] = "-";
      ret[1] = e.getMessage();
      return ret;
    }
  }
  
}
