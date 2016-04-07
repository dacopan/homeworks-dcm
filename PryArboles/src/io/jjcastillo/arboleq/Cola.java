/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.jjcastillo.arboleq;

/**
 *
 * @author cisco
 */
public class Cola {
    private NodoCola inicio;
    private NodoCola fin;
    
    public Cola(){
        inicio=fin=null;
    }
    
    public void encolar(NodoArbol datos){
          NodoCola nuevo=new NodoCola(datos, null);
          if(inicio==null){
             inicio=fin=nuevo;
          }else{
             fin.setSiguiente(nuevo);
             fin=nuevo;
          }
    }
    
    public NodoArbol desencolar(){
         NodoCola aux=inicio;
         inicio=inicio.getSiguiente();
         return aux.getDatos();
    }
    
    public boolean esVacia(){
         if(inicio==null)
             return true;
         return false;
    }
    
    public void imprimir(){
       NodoCola aux=inicio;
       
       while(aux!=null){
             System.out.println(""+aux.getDatos().getDatos().toString());
             aux=aux.getSiguiente();
       }
    
    }
    
    
    
    
}
