package com.principal;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Conexion
 */
@WebServlet("/Conexion")
public class Conexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Conexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String userName = request.getParameter("test").trim();
      if(userName == null || "".equals(userName)){
          userName = "Guest";
      }
      
      String greetings = "Hello " + userName;
      
      response.setContentType("text/plain");
      response.getWriter().write(greetings);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  try{
	    String s = request.getReader().readLine();
	    String[] dividido = s.split("<");
	    if(dividido[0].charAt(0) == '0'){ // Si es 0 entonces se aplica la segmentación, sino se aplica Dice
	      dividido[0] = dividido[0].substring(1);
	      String[] imagen = dividido[0].split(",");
	      Sistema x = Sistema.getInstancia();
	      String[] nombreFormato = dividido[1].split("\\.");
	      response.setContentType("text/plain");
	      long inicio = System.currentTimeMillis();
	      String[] respuesta = x.procesarImagen(imagen[1], nombreFormato[0], nombreFormato[1], TipoImagen.OPENCV, Integer.parseInt(dividido[2]), Integer.parseInt(dividido[3]));
	      long fin   = System.currentTimeMillis();
	      long totalTime = fin - inicio;
	      response.getWriter().write(imagen[0]+","+respuesta[0]+"<"+respuesta[1]+"<"+dividido[1]+"<"+totalTime);
	    }else{ //Se aplica Dice
	      dividido[0] = dividido[0].substring(1);
	      String[] imagen1 = dividido[0].split(",");
	      String[] imagen2 = dividido[1].split(",");
          String nombre = dividido[2];
          response.setContentType("text/plain");
          Sistema x = Sistema.getInstancia();
          String[] respuesta = x.calcularDice(imagen1[1], imagen2[1], nombre, "png", TipoImagen.OPENCV);
          response.getWriter().write(nombre+";"+respuesta[1]);
	    }
	  }catch(Exception e){
	    response.getWriter().write("-<"+e.getMessage());
	  }
	}

}
