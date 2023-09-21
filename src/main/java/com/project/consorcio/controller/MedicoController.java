package com.project.consorcio.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.consorcio.entity.Distrito;
import com.project.consorcio.entity.Especialidad;

import com.project.consorcio.entity.Medico;
import com.project.consorcio.entity.Sede;

import com.project.consorcio.service.DistritoServices;
import com.project.consorcio.service.EspecialidadServices;

import com.project.consorcio.service.MedicoServices;
import com.project.consorcio.service.SedeServices;


@Controller

@RequestMapping("/medico")

public class MedicoController {

	@Autowired
	private MedicoServices servicioMedico;
	
	@Autowired
	private SedeServices servicioSede;
	
	@Autowired
	private DistritoServices servicioDis;
	
	@Autowired
	private EspecialidadServices servicioEsp;
	
	
	//Model es una interfaz que permite crear atributos que luego seran anviados a la pagina
	@RequestMapping("/lista") 
	public String index(Model model) { //llamamos a una direccion url, el mètodo debe devolver una
		//Cadena(String) / Añadimos el Model(interfaz)
		
		//Atributos
		model.addAttribute("medicos", servicioMedico.listarTodos());
		model.addAttribute("distrito",servicioDis.listarTodos());
		model.addAttribute("sede",servicioSede.listarTodos());
		model.addAttribute("especialidad",servicioEsp.listarTodos());
		
		return "medico";
	}
	
	@RequestMapping("/grabar")
	public String grabar(
			
			
			@RequestParam("codigo") Integer cod,
			@RequestParam("nombre") String nom,
			@RequestParam("apellido") String ape,
			@RequestParam("fechaNac") String fec,
			@RequestParam("sexo") String sex,
			@RequestParam("estado") String est,
			@RequestParam("sueldo") double sue,
			@RequestParam("direccion") String dir,
			@RequestParam("distrito") int codDis,
			@RequestParam("sede") int codSede,
			@RequestParam("especialidad") int codEsp,
			
			RedirectAttributes redirect  //Genera un json
			) { 
		
		try {
			
			//Crear objeto de la entidad mdicamento
			Medico medi= new Medico();
			
			//Setear atributos del objeto med con los parametros
			medi.setNombre(nom);
			medi.setApellido(ape);
			medi.setSexo(sex);
			medi.setEstado(est);
			medi.setSueldo(sue);
			medi.setDireccion(dir);
			medi.setFechaNac(LocalDate.parse(fec));
			
			
			//Crear un objeto de la entidad---------------------- DISTRITO------------------
			Distrito di= new Distrito();
			Sede sed = new Sede();
			Especialidad es = new Especialidad();
			
			//Setear atributo "codigo" del objeto "tm" con el parametro codDis
			di.setCodigo(codDis);
			sed.setCodigo(codSede);
			es.setCodigo(codSede);
			//Invocar al mètodo setTipo y enviar el objeto "tm"
			medi.setDistrito(di);
			medi.setSede(sed);
			medi.setEspecialidad(es);
			//Validar parametro "cod"
			if(cod==0) {
				//Invocar mètodo registrar
				servicioMedico.registrar(medi);
				
				//Crear atributo de tipo flash
				redirect.addFlashAttribute("MENSAJE","Medico registrado");
			}
			else {
				//Setear atributo codigo, solo cuando es actualizacion
				medi.setCodigo(cod);
				servicioMedico.actualizar(medi);
				redirect.addFlashAttribute("MENSAJE","Medico actualizado");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/medico/lista"; 
	}
	
	
	//Crear ruta o direccion url para buscar medicamento segùn còdigo
	@RequestMapping("/buscar")
	@ResponseBody 
	public  Medico buscar(@RequestParam("codigo") Integer cod) {
		
		return servicioMedico.buscarPorID(cod);
		
	}
}
