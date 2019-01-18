package cs.frame.biz;

@SuppressWarnings("serial")
public class OperationTimeOutException extends ZYException {

	public OperationTimeOutException(){
		super();
	}
	
	public OperationTimeOutException(String message) {
		super(message);
	}
	
	public OperationTimeOutException(Throwable e){
		super(e);
	}
}
