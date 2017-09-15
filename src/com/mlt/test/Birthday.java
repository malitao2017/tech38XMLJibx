package com.mlt.test;

public class Birthday {
    private String birthday;
    
    public Birthday(String birthday) {
        super();
        this.birthday = birthday;
    }
    //getter、setter
    public Birthday() {}
    
    public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	@Override
    public String toString() {
        return this.birthday;
    }
}