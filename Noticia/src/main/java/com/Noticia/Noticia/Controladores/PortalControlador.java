/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Noticia.Noticia.Controladores;

import Excepciones.MiExcepcion;
import com.Noticia.Noticia.Entidades.Usuario;
import com.Noticia.Noticia.Servicio.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Franco
 */
@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String index()
    {
        return "index.html";
    }
    
    @GetMapping("/registrar")
    public String registrar()
    {
        return "registro.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo, MultipartFile archivo) throws MiExcepcion
    {
        try {
            usuarioServicio.registrar(archivo ,nombre, email, password, password2);
            modelo.put("exito", "Ha sido registrado con éxito");
            return "registro.html";
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            
            return "registro.html";
        }
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo)
    {
        if(error != null)
        {
            modelo.put("error", "usuario o contraseña invalido");
        }
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session)
    {
        Usuario logeado = (Usuario) session.getAttribute("usuariosession");
        
        if(logeado.getRol().toString().equals("ADMIN"))
        {
            return "redirect:/admin/dashboard";
        }
        
        return "inicio.html";
    }
    
  
    //VIDEO 11 ver la imagen de perfil
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/perfil")//no hace falta el {id} porque teneoms acceso a trves de nuestra session
    public String perfil(ModelMap modelo, HttpSession session) // a traves de la sesio recuperar los datos del usuario
    {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");//guardomos todos los datos del usuario que esta logeado
        modelo.put("usuario", usuario);//ponemos / mandamos esos datos para que se complete el html
        return "usuario_modificar.html";
    }
    
   
}