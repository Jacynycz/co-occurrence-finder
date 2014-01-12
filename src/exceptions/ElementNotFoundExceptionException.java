package exceptions;

public class ElementNotFoundExceptionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ElementNotFoundExceptionException(String message, Throwable arg0){
		
		super(message,arg0);
		
	}
	
	public ElementNotFoundExceptionException(String message){
		
		super(message);
		
	}
	
	public ElementNotFoundExceptionException(){
		
		super();
	
	}

}
