package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spittr.data.SpittleRepository;

@Controller
@RequestMapping("/spittles")
public class SpittleController {
	
	private SpittleRepository spittleRepository;
	
	@Autowired
	public SpittleController(SpittleRepository spittleRepository) {
		
		this.spittleRepository=spittleRepository;
	}
	
	@GetMapping
	public String getSpittles(Model model) {
		
		model.addAttribute(spittleRepository.findSpittles(6, 20));//key of model is spittleList
	
		return "spittles";
		
	}
	
	

}
