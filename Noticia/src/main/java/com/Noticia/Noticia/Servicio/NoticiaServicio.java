/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Noticia.Noticia.Servicio;

import Excepciones.MiExcepcion;
import com.Noticia.Noticia.Entidades.Noticia;
import com.Noticia.Noticia.Repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Franco
 */
@Service
public class NoticiaServicio {
    @Autowired
    private NoticiaRepositorio noticiaRepositorio;
    
    @Transactional
    public void craerNoticia(Long id, String titulo, String cuerpo) throws MiExcepcion
    {
        validar(id, titulo, cuerpo);
        Noticia noticia = new Noticia();
        noticia.setId(id);
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        
        noticiaRepositorio.save(noticia);
        
    }
    
    public List<Noticia> listarNoticias()
    {
        List<Noticia> noticias = new ArrayList();
        noticias = noticiaRepositorio.findAll();
        return noticias;
    }
    
    
    @Transactional
    public void modificarNoticia(Long id, String titulo, String cuerpo) throws MiExcepcion
    {
        validar(id, titulo, cuerpo);
        Optional<Noticia> resp = noticiaRepositorio.findById(id);
        if(resp.isPresent())
        {
            Noticia noticia = resp.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            
            noticiaRepositorio.save(noticia);
        }
        
    }
    
    public Noticia getOne(Long id)
    {
      return  noticiaRepositorio.getOne(id);
    }
    
    public void eliminarNoticia(Long id) throws MiExcepcion
    {
        if(id == null)
        {
            throw new MiExcepcion("El id no puede estar vacio");
        }
        noticiaRepositorio.deleteById(id);
    }
    
    public void validar(Long id, String titulo, String cuerpo) throws MiExcepcion
    {
        if(id == null)
        {
            throw new MiExcepcion("El ID no puede ser nulo");
        }
        if(titulo == null || titulo.isEmpty() )
        {
            throw new MiExcepcion("El titulo no puede ser nulo estar vacia");
        }
        if(cuerpo == null || cuerpo.isEmpty())
        {
            throw new MiExcepcion("El cuerpo no puede estar vacio");
        }
    }
}
