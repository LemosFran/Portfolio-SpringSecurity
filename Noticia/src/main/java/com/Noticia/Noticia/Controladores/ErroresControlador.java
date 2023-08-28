/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Noticia.Noticia.Controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Franco
 */
@Controller
public class ErroresControlador implements ErrorController{
    @RequestMapping(value="/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest)
    {
        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = "";
        
        int httpErrorCode = getErrorCode(httpRequest);
        
//        LE ASIGNAMOS EL VALOR SEGUN EL CODIGO DE ERROR A "errorMsg"
        switch (httpErrorCode) {
            case 400:
                errorMsg = "El recurso solicitado no existe";
                break;
            case 403:
                errorMsg = "No tienes permiso para acceder al recurso";
                break;
            case 401:
                errorMsg = "El recurso solicitado no existe";
                break;
            case 404:
                errorMsg = "El recurso solicitado no fue encontrado";
                break;
            case 500:
                errorMsg = "Ocurrio un error interno";
                break;
            default:
                System.err.println("Error no se");
        }
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }
    
    private int getErrorCode(HttpServletRequest httpRequest)
    {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
    
    
    public String getErrorCode()
    {
        return "/error.html";
    }
    
}
