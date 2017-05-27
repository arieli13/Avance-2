package com.principal;

/**
 * Controlador del sistema.
 * 
 * Utiliza el patrón de diseño Singleton.
 * 
 * @author Kevin Ramírez 
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
   * @author Kevin Ramírez 
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
   * Segmenta una imagen, eiqueta las células, las enumera y genera el reporte.
   * 
   * @author Kevin Ramírez
   * 
   * @param img Imagen codificada en Base64
   * @param nombre Nombre de la imagen
   * @param formato Formato de la imagen
   * @param tipo Puede ser escala de grises o RGB
   * @param minAreaValue Valor del area mínima de una célula para la segmentación de la imagen
   * @return Arreglo de String, en el índice 0: Imagen procesada en Base64, 1: Reporte.
   * @throws Exception Alguna excepción de los métodos utilizados
   */
  public String[] procesarImagen(String img, String nombre, String formato, TipoImagen tipo, int minAreaValue, int etiquetar) throws Exception{
   ImagenCodificador x = ImagenCodificadorCreador.crearImagenCodificador(AlgoritmoCodificacion.BASE64_TOMCAT);
   ImagenMatOpenCv y = (ImagenMatOpenCv) ImagenCreador.crearImagen(x.decodificarImagen(img), tipo, nombre, formato);
   y.toGreyScale();
   Kittler k = new Kittler();
   k.ejecutar(y);
   String reporte = k.generarReporte();
   Segmentacion z = new Segmentacion(minAreaValue);
   if(etiquetar==1){
     z.ejecutar(y);
   }else{
     z.ejecutarSinEtiqueta(y);
   }
   reporte+= z.generarReporte();
   ImagenMatOpenCv n = (ImagenMatOpenCv) ImagenCreador.crearImagen(y.getByteArray(), tipo, nombre, formato);
   String[] retorno = new String[2];
   retorno[0] = x.codificarImagen(n);
   retorno[1] = reporte;
   return retorno;
  }
  
  /**
   * Calcula el valor de dice entre 2 imágenes
   * @param img1 Imagen 1 para comparar
   * @param img2 Imagen 2 para comparar
   * @param nombre Nombre de las imágenes
   * @param formato Formato de las imágenes (png, jpg, etc)
   * @param tipo Tipo de imagen que se quiere crear
   *
   * @return Retorna un String conteniendo el valor dice.
   * @throws Exception Alguna excepción que causen los métodos accedidos.
   */
  public String[] calcularDice(String img1, String img2, String nombre, String formato, TipoImagen tipo) throws Exception{
   ImagenCodificador x = ImagenCodificadorCreador.crearImagenCodificador(AlgoritmoCodificacion.BASE64_TOMCAT);
   ImagenMatOpenCv img11 = (ImagenMatOpenCv) ImagenCreador.crearImagen(x.decodificarImagen(img1), tipo, nombre, formato);
   ImagenMatOpenCv img22 = (ImagenMatOpenCv) ImagenCreador.crearImagen(x.decodificarImagen(img2), tipo, nombre, formato);
   img11.toGreyScale();
   img22.toGreyScale();
   String[] retorno = new String[2];
   retorno[0] = "1";
   retorno[1] = Double.toString(img11.coeficienteDice(img22));
   return retorno;
  }
  
}
