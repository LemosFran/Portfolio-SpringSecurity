/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Noticia.Noticia.Controladores;

import Excepciones.MiExcepcion;
import com.Noticia.Noticia.Entidades.Noticia;
import com.Noticia.Noticia.Servicio.NoticiaServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Franco
 */
@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {
    @Autowired
    private NoticiaServicio noticiaServicio;
    
    
    @GetMapping("/registrar")
    public String registrar()
    {
        
        return "noticia_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long id,@RequestParam String titulo,@RequestParam String cuerpo, ModelMap modelo)
    {
        try {
            noticiaServicio.craerNoticia(id, titulo, cuerpo);
            modelo.put("exito", "La noticia se cargo con exito");
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
        }
       return "panel.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo)
    {
        List<Noticia> noticias = noticiaServicio.listarNoticias();
        
        modelo.addAttribute("noticias", noticias);
        
        return "noticia_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo)
    {
        modelo.put("noticia", noticiaServicio.getOne(id));
        return "noticia_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, String titulo, String cuerpo,ModelMap modelo)
    {
        try {
            noticiaServicio.modificarNoticia(id, titulo, cuerpo);
            return "redirect:../lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_modificar.html";
        }
    }
    
    // Método para mostrar la vista de confirmación de eliminación
@GetMapping("/eliminar/{id}")
public String eliminar(@PathVariable Long id, ModelMap modelo) {
    modelo.put("noticia", noticiaServicio.getOne(id));
    return "noticia_eliminar.html";
}

// Método para procesar la solicitud de eliminación
@PostMapping("/eliminar/{id}")
public String eliminarNoticia(@PathVariable Long id, ModelMap modelo) {
    try {
        noticiaServicio.eliminarNoticia(id);
        modelo.put("exito", "La noticia se ha eliminado correctamente");
        return "redirect:../lista"; // Redirecciona a la lista de noticias después de la eliminación exitosa.
    } catch (MiExcepcion ex) {
        modelo.put("error", ex.getMessage());
        return "noticia_eliminar.html";
    }
}

    
}
