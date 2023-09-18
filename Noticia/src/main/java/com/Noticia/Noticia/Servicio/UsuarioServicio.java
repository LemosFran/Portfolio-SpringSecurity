/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Noticia.Noticia.Servicio;

import Excepciones.MiExcepcion;
import com.Noticia.Noticia.Entidades.Imagen;
import com.Noticia.Noticia.Entidades.Usuario;
import com.Noticia.Noticia.Enumeraciones.Rol;
import com.Noticia.Noticia.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Franco
 */
@Service
public class UsuarioServicio implements UserDetailsService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    
    
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, Rol rol, String email, String password, String password2) throws MiExcepcion
    {
        validar(nombre, email, password, password2);
        
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(rol);
        usuario.setFechaAlta(new Date());
        
//        set de imagen de perfil al usuario
        Imagen imagen = imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);
        
        usuarioRepositorio.save(usuario);
        
    }
    
    public void elimiarUsuario(String id) throws MiExcepcion
    {
        if(id != null)
        {
            usuarioRepositorio.deleteById(id);
        }else
        {
            throw new MiExcepcion("el id esta vacio o nulo");
        }
    }
    
   
    public List<Usuario> listarUsuarios()
    {
        List<Usuario> usuarios = new ArrayList();
        usuarios = usuarioRepositorio.findAll();
        
        return usuarios;
    }
    
    @Transactional
    public void modificarRol(String id)
    {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        
        if(respuesta.isPresent())
        {
            Usuario usuario = respuesta.get();
            
            if(usuario.getRol().equals(Rol.USER))
            {
                usuario.setRol(Rol.ADMIN);
            }else if(usuario.getRol().equals(Rol.ADMIN))
            {
                usuario.setRol(Rol.USER);;
            }
            
        }
    }
    
    public Usuario getOne(String id)
    {
        return usuarioRepositorio.getOne(id);
    }
    
     public void validar(String nombre, String email, String password, String password2) throws MiExcepcion
            
    {
        if(nombre == null || nombre.isEmpty())
        {
            throw new MiExcepcion("El nombre no puede estar vacio");
        }
        if(email == null || email.isEmpty())
        {
            throw new MiExcepcion("El email no puede estar vacio");
        }
        if(password == null || password.length() <= 5)
        {
            throw new MiExcepcion("La contraseña no puede estar vacia y debe tener mas de 5 digitos");
        }
        if(!password.equals(password2))
        {
            throw new MiExcepcion("Las contraseñas deben coincidir");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        
        if(usuario != null)
        {
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().toString());
            permisos.add(p);
            
            //Capturamos los datos del usuario que inicio sesion
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
            
        }else{
                return null;
        }
    }
    
}
