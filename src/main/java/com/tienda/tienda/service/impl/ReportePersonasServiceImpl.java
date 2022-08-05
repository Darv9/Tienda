/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda.tienda.service.impl;

import com.tienda.tienda.commons.JasperReportManager;
import com.tienda.tienda.enums.TipoReporteEnum;
import com.tienda.tienda.model.ReportePersonassDTO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tienda.tienda.service.api.ReportePersonasServiceAPI;

/**
 *
 * @author Daniel
 */
@Service
public class ReportePersonasServiceImpl implements ReportePersonasServiceAPI {

	@Autowired
	private JasperReportManager reportManager;

	@Autowired
	private DataSource dataSource;

	@Override
	public ReportePersonassDTO obtenerReportePersonas(Map<String, Object> params)
			throws JRException, IOException, SQLException {
		String fileName = "reporte_clientes";
		ReportePersonassDTO dto = new ReportePersonassDTO();
		String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx"
				: ".pdf";
		dto.setFileName(fileName + extension);
		ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params,
				dataSource.getConnection());
		byte[] bs = stream.toByteArray();
		dto.setStream(new ByteArrayInputStream(bs));
		dto.setLength(bs.length);

		return dto;
	}

}
