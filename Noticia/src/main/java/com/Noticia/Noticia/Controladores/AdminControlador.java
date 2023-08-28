/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Noticia.Noticia.Controladores;

import Excepciones.MiExcepcion;
import com.Noticia.Noticia.Entidades.Usuario;
import com.Noticia.Noticia.Servicio.UsuarioServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Franco
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/dashboard")
    public String panelAdministrador()
    {
        return "panel.html";
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo)
    {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "usuario_list.html";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String modificarRol(@PathVariable String id)
    {
        usuarioServicio.modificarRol(id);
        
        return "redirect:/admin/lista";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo)
    {
        modelo.put("usuario", usuarioServicio.getOne(id));
        
        return "usuario_eliminar.html";
    }
    
    
    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable String id, ModelMap modelo)
    {
        try {
            usuarioServicio.elimiarUsuario(id);
            modelo.put("exito", "Se elimino la noticia con exito");
            return "redirect:../lista";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "usuario_eliminar.html";
        }
    }
    
}
