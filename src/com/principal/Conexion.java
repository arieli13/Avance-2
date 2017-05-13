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
		// TODO Auto-generated method stub
	  String s = request.getReader().readLine();
      String[] dividido = s.split("<");
      String[] imagen = dividido[0].split(",");
      Sistema x = Sistema.getInstancia();
      String[] nombreFormato = dividido[1].split("\\.");
      response.setContentType("text/plain");
      String[] respuesta = x.procesarImagen(imagen[1], nombreFormato[0], nombreFormato[1], TipoImagen.OPENCV);
      if(respuesta[0] == "-"){
        response.getWriter().write(respuesta[0]+"<"+respuesta[1]);
      }else{
        response.getWriter().write(imagen[0]+","+respuesta[0]+"<"+respuesta[1]+"<"+dividido[1]);
      }
      
	}

}
