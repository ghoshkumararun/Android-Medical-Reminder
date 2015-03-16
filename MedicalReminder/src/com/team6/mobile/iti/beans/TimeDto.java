package com.team6.mobile.iti.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author Mohamed
 */
public class TimeDto implements Parcelable {

	private int medicine_id;
	private long take_time;
	private String dose;

	public static final Parcelable.Creator<TimeDto> CREATOR = new Parcelable.Creator<TimeDto>() {
		public TimeDto createFromParcel(Parcel in) {
			return new TimeDto(in);
		}

		public TimeDto[] newArray(int size) {
			return new TimeDto[size];
		}
	};
	
	public TimeDto() {
		// TODO Auto-generated constructor stub
	}
	
	private TimeDto(Parcel in) {
        medicine_id = in.readInt();
        take_time = in.readLong();
        dose = in.readString();
    }


	public long getTake_time() {
		return take_time;
	}

	public void setTake_time(long take_time) {
		this.take_time = take_time;
	}

	public String getDose() {
		return dose;
	}

	public void setDose(String dose) {
		this.dose = dose;
	}

	public int getMedicine_id() {
		return medicine_id;
	}

	public void setMedicine_id(int medicine_id) {
		this.medicine_id = medicine_id;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		dest.writeInt(medicine_id);
		dest.writeLong(take_time);
		dest.writeString(dose);
	}

}
