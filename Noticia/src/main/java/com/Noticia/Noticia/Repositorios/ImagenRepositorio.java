/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Noticia.Noticia.Repositorios;

import com.Noticia.Noticia.Entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Franco
 */
@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
}
