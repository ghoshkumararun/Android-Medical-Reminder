/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;



import java.util.*;


/**
 *
 * @author Mohamed
 */
public class Medicine {
	
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private long start_date;
	private long end_date;
	private String repetition;
	private String instruction;
	private String type;
	private String user_email;
	private List<TimeDto> times;
	private String desc;
	private String imageUrl;
	private int medState;
	private int isTaken;
	
	public int getMedState() {
		return medState;
	}

	public void setMedState(int medState) {
		this.medState = medState;
	}

	public String getDesc() {
		return desc;
	}

	public Medicine() {
		// TODO Auto-generated constructor stub
	}

	

	

	public String getName() {
		return name;

	}

	public void setName(String name) {
		this.name = name;
	}

	public long getStart_date() {
		return start_date;
	}

	public void setStart_date(long start_date) {
		this.start_date = start_date;
	}

	public long getEnd_date() {
		return end_date;
	}

	public void setEnd_date(long end_date) {
		this.end_date = end_date;
	}

	public String getRepetition() {
		return repetition;
	}

	public void setRepetition(String repetition) {
		this.repetition = repetition;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public List<TimeDto> getTimes() {
		return times;
	}

	public void setTimes(List<TimeDto> times) {
		this.times = times;
	}

	

	public void setDesc(String string) {
		
		desc = string;
	}

	public void setImageURL(String string) {
		
		imageUrl = string;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getIsTaken() {
		return isTaken;
	}

	public void setIsTaken(int isTaken) {
		this.isTaken = isTaken;
	}
	
	
	
}
