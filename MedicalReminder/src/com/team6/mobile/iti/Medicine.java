package com.team6.mobile.iti;



import java.util.*;
/**
 *
 * @author Mohamed
 */
public class Medicine {
    
    private String name;
    private String start_date;
    private String end_date;
    private String repetition;
    private String instruction;
    private String type;
    private String user_email;
    private List<TimeDto> times;
    private String desc;
    private String imageURL;
		
	
    public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
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
    
    
    
}
