package com.example.yshlapak.lightremote.database;

public class ButtonValue {

	private int id;
	private String buttonName;

    public ButtonValue(int id, String buttonName, String buttonString, String buttonHexValue, int buttonHexOption) {
        this.id = id;
        this.buttonName = buttonName;
        this.buttonString = buttonString;
        this.buttonHexValue = buttonHexValue;
        this.buttonHexOption = buttonHexOption;
    }

    private String buttonString;
	private String buttonImage;
    private String buttonHexValue;

    public int getButtonHexOption() {
        return buttonHexOption;
    }

    public void setButtonHexOption(int buttonHexOption) {
        this.buttonHexOption = buttonHexOption;
    }

    public String getButtonHexValue() {
        return buttonHexValue;
    }

    public void setButtonHexValue(String buttonHexValue) {
        this.buttonHexValue = buttonHexValue;
    }

    private int buttonHexOption;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	public String getButtonString() {
		return buttonString;
	}
	public void setButtonString(String buttonString) {
		this.buttonString = buttonString;
	}
	public String getButtonImage() {
		return buttonImage;
	}
	public void setButtonImage(String buttonImage) {
		this.buttonImage = buttonImage;
	}

    public ButtonValue(int id, String buttonName, String buttonString) {
        this.id = id;
        this.buttonName = buttonName;
        this.buttonString = buttonString;
    }

    public ButtonValue(String buttonName, String buttonString) {
        this.buttonName = buttonName;
        this.buttonString = buttonString;
    }

    public ButtonValue() {

    }

}
