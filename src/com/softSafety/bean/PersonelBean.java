package com.softSafety.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.softSafety.model.Personel;

import service.PersonelService;

@ManagedBean(name = "personelBean")
@SessionScoped
public class PersonelBean implements Serializable {
	
	private static final long serialVersionUID = 5222727202791347953L;
	private int id;
	private String age;
	private String country;
	private String mail;
	private String name;
	private String surname;
	
	private boolean isSucces;
	
	private List<Personel> personelList ;

	@ManagedProperty("#{personelService}")
    private PersonelService service;
	
	private Personel personel;
	private String personelIdToBeUpdate;
	private String errorMessage;
 
    @PostConstruct
    public void init(){
    	System.out.println("init ppost consturctor");
        try {
			personelList = service.getPersonelList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
    public void search(String name, String surname,String age,String mail,String country ){
    	System.out.println("name: " + name);
    	System.out.println("surname: " + surname);
    	System.out.println("age: " + age);
    	System.out.println("mail: " + mail);
    	System.out.println("country: " + country);
    	
    	personel = new Personel();
		personel.setName(name);
		personel.setSurname(surname);
		personel.setAge(age);
		personel.setMail(mail);
		personel.setCountry(country);
    	
    	personelList = service.search(personel);
    	
    }

	public void savePersonel() {
		
		try {
			System.out.println("savePersonel");
			System.out.println("---------------------------");
			System.out.println("name: " + name);
			System.out.println("surname: " + surname);
			System.out.println("age: " + age);
			System.out.println("mail: " + mail);
			System.out.println("country: " + country);
			System.out.println("---------------------------");
			
			personel = new Personel();
			personel.setName(name);
			personel.setSurname(surname);
			personel.setAge(age);
			personel.setMail(mail);
			personel.setCountry(country);
			
			isSucces = service.save(personel);
			if (isSucces) {
				System.out.println(personel.getName()+" eklendi.");
				personelList = service.getPersonelList();
				clearFields();
				
			}else System.out.println("Ekleme işleminde hata");
			
		} catch (Exception e) {
			
			errorMessage = e.toString();
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null,"/hata?faces-redirect=true");
			
			
		}
		
	}
	
	public void getPersonelToBeUpdate(String id){
		System.out.println("id: " + id);
		personelIdToBeUpdate = id;
		
		Personel personelTobeUpdated = service.getPersonelById(id);
		System.out.println("name: " + personelTobeUpdated.getName());
		System.out.println("name: " + personelTobeUpdated.getName());
		System.out.println("name: " + personelTobeUpdated.getName());
		System.out.println("name: " + personelTobeUpdated.getName());
		
		name = personelTobeUpdated.getName();
		surname = personelTobeUpdated.getSurname();
		age = personelTobeUpdated.getAge();
		mail = personelTobeUpdated.getMail();
		country = personelTobeUpdated.getCountry();
		
	}
	
	public void updatePersonel(String name, String surname,String age,String mail,String country) throws SQLException{
		System.out.println("updatePersonel");
		service.update(personelIdToBeUpdate, name, surname, age, mail, country);
		personelList = service.getPersonelList();
	}
	
	public void clearFields() throws SQLException {
		System.out.println("clearFields");
		name="";
		surname="";
		age="";
		mail="";
		country="";
		personelList = service.getPersonelList();
	}

	public void deletePersonel(Integer id) throws SQLException{
		System.out.println("id: " + id);
		boolean successDelete = service.delete(id);
		if (successDelete) {
			personelList = service.getPersonelList();
		} else System.out.println("delete failed");
	}
	

	public void destroyWorld() {
		addMessage("System Error", "Please try again later.");
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Personel> getPersonelList() {
		return personelList;
	}

	public void setPersonelList(List<Personel> personelList) {
		this.personelList = personelList;
	}

	public void setService(PersonelService service) {
		this.service = service;
	}
	
	public PersonelService getService() {
		return service;
	}


	public Object getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


}
