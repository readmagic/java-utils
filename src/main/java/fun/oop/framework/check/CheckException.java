package fun.oop.framework.check;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class CheckException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private CheckError checkError;

	public CheckException(CheckError checkError) {
		super(checkError.getExceptionMessage(), checkError.getCause());
		this.checkError = checkError;
	}

	public String getStackTraceString() {
		return getStackTraceString(this);
	}

	private static String getStackTraceString(Throwable throwable) {
		if (throwable == null) {
			return null;
		}
	    StringWriter stringWriter = new StringWriter();
	    throwable.printStackTrace(new PrintWriter(stringWriter));
	    return stringWriter.toString();
	}

	public CheckError getCheckError() {
		return checkError;
	}
}