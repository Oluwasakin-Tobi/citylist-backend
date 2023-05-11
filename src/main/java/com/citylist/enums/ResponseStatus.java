package com.citylist.enums;

public enum ResponseStatus {
	
	SUCCESSFUL("00", "Successful"),
	ERROR("99", "Error");
	
final String code, message;
    
    private ResponseStatus(String code, String message){
        this.code = code;
        this.message = message;
    }
    
    public String getCode(){
        
        return code;
    }
    
    public String getMessage(){
        
        return message;
    }

}
