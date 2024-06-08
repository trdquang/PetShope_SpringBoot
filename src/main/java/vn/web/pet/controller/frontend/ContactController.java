package vn.web.pet.controller.frontend;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vn.web.pet.dto.Contact;
import vn.web.pet.dto.Jw28Constant;

@Controller
@RequestMapping ("/contact/")
public class ContactController implements Jw28Constant {
	
	@RequestMapping (value = "view", method = RequestMethod.GET)
	public String contact() { //action method: xu ly 1 request tu browswer
		return "frontend/contact/contact";
	}
	
	
	@RequestMapping (value = "send", method = RequestMethod.POST)
	public String send(final HttpServletRequest request) { //action method: xu ly 1 request tu browswer
		
//		String name = request.getParameter("txtName");
//		String email = request.getParameter("txtEmail");
		
		Contact contact = new Contact(
				request.getParameter("txtName"), 
				request.getParameter("txtEmail"), 
				request.getParameter("txtMobile"), 
				request.getParameter("txtAddress"), 
				request.getParameter("txtMessage"));
		
//		System.out.println("Your name: " + name);
//		System.out.println("Your email: " + email);
		System.out.println("Your name: " + contact.getName());
		System.out.println("Your email: " + contact.getEmail());
		
		return "frontend/contact/view";
	}
	
	@RequestMapping (value = "edit", method = RequestMethod.GET)
	public String contactEdit(Model model) { //action method: xu ly 1 request tu browswer
		
		Contact contact = new Contact(
				"Tung Quang", 
				"quangtung@yahoo.com", 
				"0987654378", 
				"Mai Dich - Cau Giay", 
				"Giao hang cham");
		
		//Day doi tuong sang view
		model.addAttribute("contact", contact);
		return "frontend/contact/contact-edit";
	}
/*	
	@RequestMapping (value = "save-edit", method = RequestMethod.POST)
	public String saveEditContact(final HttpServletRequest request) { //action method: xu ly 1 request tu browswer
		
//		String name = request.getParameter("txtName");
//		String email = request.getParameter("txtEmail");
		
		Contact contact = new Contact(
				request.getParameter("txtName"), 
				request.getParameter("txtEmail"), 
				request.getParameter("txtMobile"), 
				request.getParameter("txtAddress"), 
				request.getParameter("txtMessage"));
		
//		System.out.println("Your name: " + name);
//		System.out.println("Your email: " + email);
		System.out.println("Your name: " + contact.getName());
		System.out.println("Your email: " + contact.getEmail());
		
		return "frontend/contact/contact-edit";
	}
*/	
	@RequestMapping (value = "save-edit", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> saveEditAjax(
			@RequestBody Contact contact) {
		System.out.println("Your name: " + contact.getName());
		System.out.println("Your email: " + contact.getEmail());
		
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("code", 200);
		jsonResult.put("message", "Thong tin cua " + 
						contact.getName() + " da duoc luu");
		
		return ResponseEntity.ok(jsonResult);
	}
	
	@RequestMapping (value = "view-sf", method = RequestMethod.GET)
	public String contactSf(final Model model) { //action method: xu ly 1 request tu browswer
		
		Contact contact = new Contact();
		
		model.addAttribute("contact", contact);
		
		return "frontend/contact/contact-sf";
	}
	
	@RequestMapping (value = "send-sf", method = RequestMethod.POST)
	public String contactSendSf(
			final Model model,
			@ModelAttribute("contact") Contact contact,
			@RequestParam("contactFile") MultipartFile file
			) throws IOException { 
		
		System.out.println("Your name: " + contact.getName());
		System.out.println("Your email: " + contact.getEmail());
		
		if (file != null && file.getOriginalFilename() != null) {
			//Luu file vao thu muc UploadFiles/ContactFiles/
			String path = FOLDER_UPLOAD + "ContactFiles/" +
							file.getOriginalFilename();
			File saveFile = new File(path);
			file.transferTo(saveFile);
		}
		return "frontend/contact/contact-sf";
	}
	
	@RequestMapping (value = "edit-sf", method = RequestMethod.GET)
	public String contactEditSf(final Model model) { //action method: xu ly 1 request tu browswer
		
		Contact contact = new Contact(
				"Tung Quang", 
				"quangtung@yahoo.com", 
				"0987654378", 
				"Mai Dich - Cau Giay", 
				"Giao hang cham");
		
		model.addAttribute("contact", contact);
		
		return "frontend/contact/contact-sf-edit";
	}
}
