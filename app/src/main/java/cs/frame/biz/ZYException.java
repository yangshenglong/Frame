package cs.frame.biz;


public class ZYException extends Exception {

	private static final long serialVersionUID = 153504232797165313L;

	public ZYException(){
		super();
	}
	
	public ZYException(Throwable e){
		super(e);
	}
	
	public ZYException(String message){
		super(message);
	}
	
	public ZYException(String message, Throwable e){
		super(message, e);
	}
}
