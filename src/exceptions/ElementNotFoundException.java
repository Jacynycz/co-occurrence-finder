package exceptions;

public class ElementNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ElementNotFoundException(String message, Throwable arg0){
		
		super(message,arg0);
		
	}
	
	public ElementNotFoundException(String message){
		
		super(message);
		
	}
	
	public ElementNotFoundException(){
		
		super();
	
	}

}
