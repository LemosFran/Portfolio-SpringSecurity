/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Noticia.Noticia.Servicio;

import Excepciones.MiExcepcion;
import com.Noticia.Noticia.Entidades.Imagen;
import com.Noticia.Noticia.Repositorios.ImagenRepositorio;
import com.Noticia.Noticia.Repositorios.UsuarioRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Franco
 */
@Service
public class ImagenServicio {
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
    
    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MiExcepcion
    
    {
      if(archivo != null)
      {
          try {
              Imagen imagen = new Imagen();
              
              imagen.setMime(archivo.getContentType());
              imagen.setNombre(archivo.getName());
              imagen.setContenido(archivo.getBytes());
              
              return imagenRepositorio.save(imagen);
              
          } catch (Exception e) {
              System.err.println(e.getMessage());
          }
      }
      return null;
    }
    
    public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiExcepcion
    {
        if(archivo != null)
        {
            try {
                Imagen imagen = new Imagen();
                
                if(idImagen != null)
                {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);
                    if(respuesta.isPresent())
                    {
                       imagen = respuesta.get();
                    }
                }
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
             return imagenRepositorio.save(imagen);
                
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
}
