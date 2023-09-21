//Juega como un Servlet
package com.project.consorcio.controller;  //En spring no hay Servlet, se usa el controller

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.consorcio.entity.Medicamento;
import com.project.consorcio.entity.TipoMedicamento;
import com.project.consorcio.service.MedicamentoServices;
import com.project.consorcio.service.TipoMedicamentoServices;

@Controller //Indica que la clase es un controlador, por lo tanto permite recibir
			//peticiones de los clientes y enviar respuesta.

@RequestMapping("/medicamento") //Permite crear una direccion url o ruta para acceder al controlador
				
public class MedicamentoController {  //El controlador trabaja con el servicio
	
	@Autowired
	private MedicamentoServices servicioMed;
	
	@Autowired
	private TipoMedicamentoServices servicioTipo;
	
	
	//Model es una interfaz que permite crear atributos que luego seran anviados a la pagina
	@RequestMapping("/lista") 
	public String index(Model model) { //llamamos a una direccion url, el mètodo debe devolver una
		//Cadena(String) / Añadimos el Model(interfaz)
		
		//Atributos
		model.addAttribute("medicamentos", servicioMed.listarTodos());
		model.addAttribute("tipos",servicioTipo.listarTodos());
		
		return "medicamento";
	}
	
	@RequestMapping("/grabar")
	public String grabar(
			
			//@RequestParam  permite recuperar valores que se encuentran los controles del formulario(cajas,checkbox,radio,etcc)
			@RequestParam("codigo") Integer cod,
			@RequestParam("nombre") String nom,
			@RequestParam("descripcion") String des,
			@RequestParam("stock") int stock,
			@RequestParam("precio") double pre,
			@RequestParam("fecha") String fec,
			@RequestParam("tipo") int codTipo,
			
			RedirectAttributes redirect  //Genera un json
			) { //Se recupera lo que hay en la caja y lo mandamos a guardar en el parametro llamado "nombre"
		
		try {
			
			//Crear objeto de la entidad mdicamento
			Medicamento med= new Medicamento();
			
			//Setear atributos del objeto med con los parametros
			med.setNombre(nom);
			med.setDescripcion(des);
			med.setStock(stock);
			med.setPrecio(pre);
			med.setFecha(LocalDate.parse(fec));
			
			//Crear un objeto de la entidad TipoMedicamento
			TipoMedicamento tm= new TipoMedicamento();
			
			//Setear atributo "codigo" del objeto "tm" con el parametro codTipo
			tm.setCodigo(codTipo);
			
			//Invocar al mètodo setTipo y enviar el objeto "tm"
			med.setTipo(tm);
			
			//Validar parametro "cod"
			if(cod==0) {
				//Invocar mètodo registrar
				servicioMed.registrar(med);
				
				//Crear atributo de tipo flash
				redirect.addFlashAttribute("MENSAJE","Medicamento registrado");
			}
			else {
				//Setear atributo codigo, solo cuando es actualizacion
				med.setCodigo(cod);
				servicioMed.actualizar(med);
				redirect.addFlashAttribute("MENSAJE","Medicamento actualizado");
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/medicamento/lista"; //redireccionamos para que nos vuelva a mandar a la pagina principal
	}
	
	
	//Crear ruta o direccion url para buscar medicamento segùn còdigo
	@RequestMapping("/buscar")
	@ResponseBody //Devuelve un json de medicamento
	public  Medicamento buscar(@RequestParam("codigo") Integer cod) {
		
		return servicioMed.buscarPorID(cod);
		
	}
	
	
	
	
	
	
	
}
