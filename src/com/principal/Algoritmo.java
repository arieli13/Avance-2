package com.principal;


public abstract class Algoritmo {
  
  public Algoritmo(){}
  //Metodos abstractos para todos los algoritmos
  public abstract void ejecutar(Imagen imagen);
  public abstract String generarReporte();
}
