package spittr.web;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spittr.data.SpitterRepository;
import spittr.domain.Spitter;
import spittr.exception.ImageUploadException;

@Controller
@RequestMapping("/spitter")
public class SpitterController {
	
	
	private SpitterRepository spitterRepository;
	
	@Autowired
	public SpitterController(SpitterRepository spitterRepository) {
		
		this.spitterRepository=spitterRepository;
	} 
	
	public SpitterController() {
		
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		
		model.addAttribute(new Spitter());//key value of model  will be spitter
		
		return "registerForm";
	}
	
	@PostMapping("/register")
	public String processRegistration(@RequestPart (required = false, value = "profilePicture") MultipartFile file, 
									  @Valid Spitter spitter, //Validate Spitter input
									  Errors errors, 
									  HttpServletRequest request, RedirectAttributes model) {
		
		if(errors.hasErrors()) {  //Return to form on validation errors
			
		    return "registerForm";
		}
		
		spitterRepository.save(spitter);
		
		if(file.getSize()!=0) {
		
		validateImage(file);
		
		String name=getFileName(file.getOriginalFilename());
		
		String fullPath= request.getServletContext().getRealPath("")+"/resources/images/" + name;
		
		saveImage(fullPath, file);
									}
		
		model.addAttribute("username",spitter.getUserName());
		
		model.addFlashAttribute("spitter", spitter);//flash attribute for redirect
		
		return "redirect:/spitter/{username}";
		
	}
	
	@GetMapping("{username}")
	public String showSpitterProfile (@PathVariable String username, Model model) {
		
		if(!model.containsAttribute("spitter")) {
		
		Spitter spitter = spitterRepository.finfByUsername(username);
		
		model.addAttribute(spitter);}
		
		return "profile";
		
		
	}
	
	private String validateImage(MultipartFile file) {
		
		if(file.getContentType().equals("image/jpeg")) return ".jpg";
		
		else if(file.getContentType().equals("image/png")) return ".png";
		
		else if(file.getContentType().equals("image/gif")) return ".gif";
		
		else throw new ImageUploadException("Wrong file type!");
	}
	
	private void saveImage(String fullPath, MultipartFile file ) throws ImageUploadException {
		
		File uploadFile=new File(fullPath);
		
		try {
			
			file.transferTo(uploadFile);
			
		} catch (IOException e) {
			
			throw new ImageUploadException("Unable to save image");
		}
		
	}
	
	private String getFileName(String fileName) {
		
		int start=-1;
		
		int textLen=fileName.length();
		
		Pattern pattern=Pattern.compile("\\\\[^\\\\]+$");
		
		Matcher match=pattern.matcher(fileName);
		
		while(match.find()) {
			
			start=match.start();
		}
		
		if(start==-1) return fileName;
		
		return fileName.substring(start, textLen);
	}


}
