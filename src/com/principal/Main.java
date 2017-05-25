package com.principal;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Main {

  public static void main(String[] args) {
    try{
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
      
      String dice = "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";
      String diceManual = "iVBORw0KGgoAAAANSUhEUgAAAE0AAABGCAYAAACJzhlzAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAGwSURBVHhe7ZjRcsMgDARx//+f3WqmTlPHYHwgQNLtTCZvib25k4i3lNL+81qafb++xG2Tyx/P1++7SXIytTEtbRbmpUnaRifOhLSa2TVSnJmkzRr6V5iq5yriqo8cV/GfcROlGo66nqakjR7AJUb+gFVJu5MzOnHv1zMj7VXShBVqsQrV0g5y8iKJM3+4nQGlATySttK2nEm1NAr7g/UEyG7Pp8mKtD0/pCE1jHZOa65nNGECZxrA43pGTNYZJg3gnzSexep4SauppbVqaoXgJa0kxOIcO4RpiPtYBML7F1kWdqbXvVxKs85dulrlhdyeIrWltqGPHKi40NJQXErTXl5MGoBbaZppC500VCzrCRBWWkt9XUvLiWmdd+6TdhbUKkwIUc9DVA9hgnyKuz/s2nB7AlAaAKUBUBoApQFQGoCatNZHyivT/ZxWEtXrcDmbrkm7S5aX5A2faR7EcREAdJVWO7Osp6170rwM+xKsJ4CKNO9pU0uaZ3HT6ml5GXCmAUyTZrm+TBqAqrRcmqwvCfWknQV52KpyBz4ePQyEMw2A0gAoDYDSACgNgNIek9I3jfV5hdXt0hoAAAAASUVORK5CYII=";
      ImagenCodificador x = ImagenCodificadorCreador.crearImagenCodificador(AlgoritmoCodificacion.BASE64_TOMCAT);
      ImagenMatOpenCv y = (ImagenMatOpenCv) ImagenCreador.crearImagen(x.decodificarImagen(dice), TipoImagen.OPENCV, "dice", "png");//Imagen Dice
      ImagenMatOpenCv z = (ImagenMatOpenCv) ImagenCreador.crearImagen(x.decodificarImagen(diceManual), TipoImagen.OPENCV, "diceManual", "png");//Imagen Dice manual
      y.toGreyScale();
      Kittler k = new Kittler();
      k.ejecutar(y);
      Segmentacion m = new Segmentacion();
      y.actualizarHistograma();
      z.toGreyScale();
      z.actualizarHistograma();
      m.ejecutarSinEtiqueta(y);
   
      z.actualizarHistograma();
      displayImage( Mat2BufferedImage( z.getImagen() ) );
      displayImage( Mat2BufferedImage( y.getImagen() ) );
      System.out.println(y.coeficienteDice(z));
      
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
   }
  
  public static BufferedImage Mat2BufferedImage(Mat m) {
    // Fastest code
    // output can be assigned either to a BufferedImage or to an Image

    int type = BufferedImage.TYPE_BYTE_GRAY;
    if ( m.channels() > 1 ) {
        type = BufferedImage.TYPE_3BYTE_BGR;
    }
    int bufferSize = m.channels()*m.cols()*m.rows();
    byte [] b = new byte[bufferSize];
    m.get(0,0,b); // get all the pixels
    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    System.arraycopy(b, 0, targetPixels, 0, b.length);  
    return image;
}
  
  public static void displayImage(BufferedImage img2) {

    //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
    ImageIcon icon=new ImageIcon(img2);
    JFrame frame=new JFrame();
    frame.setLayout(new FlowLayout());        
    frame.setSize(img2.getWidth(null)+50, img2.getHeight(null)+50);     
    JLabel lbl=new JLabel();
    lbl.setIcon(icon);
    frame.add(lbl);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}

}
