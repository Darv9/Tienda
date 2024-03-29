/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.tienda.controller;

import com.tienda.tienda.enums.TipoReporteEnum;
import com.tienda.tienda.model.ReportePersonassDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tienda.tienda.service.api.ReportePersonasServiceAPI;

/**
 *
 * @author Daniel
 */
@RestController
@RequestMapping("/report")
public class ReportePersonasController {

    @Autowired
    private ReportePersonasServiceAPI reporteVentasServiceAPI;

    @GetMapping(path = "/personas/download")
    public ResponseEntity<Resource> download(@RequestParam Map<String, Object> params)
            throws JRException, IOException, SQLException {
        ReportePersonassDTO dto = reporteVentasServiceAPI.obtenerReportePersonas(params);
        InputStreamResource streamResource = new InputStreamResource(dto.getStream());
        MediaType mediaType = null;
        mediaType = MediaType.APPLICATION_PDF;
        return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
                .contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
    }
}
