package com.team6.mobile.iti.beans;

import java.util.*;

import android.os.Parcel;
import android.os.Parcelable;


public class Medicine implements Parcelable {

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
	private int isTaken;
	private String doseQuantity;

	public String getDoseQuantity() {
		return doseQuantity;
	}

	public void setDoseQuantity(String doseQuantity) {
		this.doseQuantity = doseQuantity;
	}

	public Medicine() {
		// TODO Auto-generated constructor stub
	}

	public static final Parcelable.Creator<Medicine> CREATOR = new Parcelable.Creator<Medicine>() {
		public Medicine createFromParcel(Parcel in) {
			return new Medicine(in);
		}

		public Medicine[] newArray(int size) {
			return new Medicine[size];
		}
	};

	private Medicine(Parcel in) {
		name = in.readString();
		start_date = in.readLong();
		end_date = in.readLong();
		repetition = in.readString();
		instruction = in.readString();
		type = in.readString();
		user_email = in.readString();
		times  = in.readArrayList(null);
		desc = in.readString();
		imageUrl = in.readString();
		isTaken = in.readInt();		
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

	public void setRepetition(String  intervalArr) {
		this.repetition = intervalArr;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeLong(start_date);
		dest.writeLong(end_date);
		dest.writeString(repetition);
		dest.writeString(instruction);
		dest.writeString(type);
		dest.writeString(user_email);
		dest.writeList(times);
		dest.writeString(desc);
		dest.writeString(imageUrl);
		dest.writeInt(isTaken);

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

	public String getDesc() {
		return desc;
	}

	public int getIsTaken() {
		return isTaken;
	}

	public void setIsTaken(int isTaken) {
		this.isTaken = isTaken;
	}

}
