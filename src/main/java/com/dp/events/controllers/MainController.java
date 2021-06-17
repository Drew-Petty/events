package com.dp.events.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp.events.models.Event;
import com.dp.events.models.User;
import com.dp.events.services.EventService;
import com.dp.events.services.UserService;

@Controller
public class MainController {
	private final UserService userService;
	private final EventService eventService;
	private ArrayList<String> states = new ArrayList<String>(Arrays.asList("AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"));
	private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
	public MainController(UserService userService,EventService eventService) {
		this.eventService=eventService;
		this.userService = userService;
	}
	@RequestMapping("/")
	public String welcome(Model model) {
		model.addAttribute("user",new User());
		model.addAttribute("states", states);
		return "welcome.jsp";
	}
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user")User user,BindingResult result,HttpSession session, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("states", states);
			return "welcome.jsp";
		}else {
			userService.registerUser(user);
			session.setAttribute("user", user);
			return "redirect:/home";
		}
	}
	@RequestMapping("/login")
	public String login(@RequestParam("email")String email, @RequestParam("password")String password,RedirectAttributes redirect, HttpSession session) {
		User user = userService.findUserByEmail(email);		
		if(user==null || userService.authenticate(password, user)==false) {
			redirect.addFlashAttribute("error", "invalid credentials");
			return "redirect:/";
		}else {
			session.setAttribute("user", user);
			return "redirect:/home";
		}
	}
	@RequestMapping("/home")
	public String home(Model model, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			redirect.addFlashAttribute("error", "Please Register or Log in.");
			return "redirect:/";
		}
		List<Event> inState = eventService.inStateEvents(user.getState());
		List<Event> outOfState = eventService.outOfStateEvents(user.getState());
		model.addAttribute("inState", inState);
		model.addAttribute("outOfState", outOfState);
		model.addAttribute("format", format);
		model.addAttribute("event", new Event());
		model.addAttribute("states", states);
		model.addAttribute("user", user);
		return "home.jsp";
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	@RequestMapping(value = "/createEvent", method=RequestMethod.POST)
	public String createEvent(@Valid @ModelAttribute("event")Event event,BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			redirect.addFlashAttribute("error", "Please Register or Log in.");
			return "redirect:/";
		}
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate localDate = LocalDate.now();
		Date today = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		List<Event> inState = eventService.inStateEvents(user.getState());
		List<Event> outOfState = eventService.outOfStateEvents(user.getState());
		model.addAttribute("inState", inState);
		model.addAttribute("outOfState", outOfState);
		model.addAttribute("states", states);
		model.addAttribute("user", user);
		if(event.getDate()!=null && event.getDate().compareTo(today)<0) {
			result.addError(new FieldError("dateInPast", "dateInPast", "the event date is in the past"));
		}
		if (result.hasErrors()) {
			return "home.jsp";
		}else { 
			eventService.saveEvent(event);
			return "redirect:/home";
		}
	}
	@RequestMapping("/delete/{id}")
	public String deleteEvent(@PathVariable("id")Long id, HttpSession session, RedirectAttributes redirect) {
		eventService.deleteEvent(id);
		return "redirect:/home";
	}
	@RequestMapping("/edit/{id}")
	public String editEvent(@PathVariable("id")Long id, Model model, RedirectAttributes redirect, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			redirect.addFlashAttribute("error", "Please Register or Log in.");
			return "redirect:/";
		}
		model.addAttribute("states", states);
		model.addAttribute("event", eventService.findEventById(id));
		return "editEvent.jsp";
	}
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
	public String updateEvent(@Valid @ModelAttribute("event")Event event, BindingResult result, Model model) {
		model.addAttribute("states", states);
		model.addAttribute("event", event);
		if(result.hasErrors()) {
			return "editEvent.jsp";
		}else {
			eventService.saveEvent(event);
			return "redirect:/home";
		}
	}
	@RequestMapping("/join/{id}")
	public String joinEvent(@PathVariable("id")Long id, HttpSession session) {
		Event event = eventService.findEventById(id);
		User user = (User) session.getAttribute("user");
		eventService.joinEvent(event, user);
		return "redirect:/home";
	}
	@RequestMapping("/cancel/{id}")
	public String leaveEvent(@PathVariable("id")Long id, HttpSession session) {
		Event event = eventService.findEventById(id);
		User user = (User) session.getAttribute("user");
		User u = userService.findUserById(user.getId());
		
		eventService.leaveEvent(event, u);
		return "redirect:/home";
	}
	@RequestMapping("/event/{id}")
	public String  viewEvent(@PathVariable("id")Long id, HttpSession session, RedirectAttributes redirect, Model model) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			redirect.addFlashAttribute("error", "Please Register or Log in.");
			return "redirect:/";
		}
		Event event = eventService.findEventById(id);
		model.addAttribute("user", user);
		model.addAttribute("event", event);
		return "viewEvent.jsp";
	}
	@RequestMapping("/message/{id}")
	public String addMessage(HttpSession session, @PathVariable("id")Long id, @RequestParam("comment")String comment, RedirectAttributes redirect) {
		Event event = eventService.findEventById(id);
		User user = (User) session.getAttribute("user");
		String message = user.getFirstName();
		message = message.concat(" says: "+comment);
		if (comment.length()==0) {
			redirect.addFlashAttribute("error", "please enter a comment");
			return "redirect:/event/"+id;
		}
		eventService.addComment(event, message);
		return "redirect:/event/"+id;
	}
}
