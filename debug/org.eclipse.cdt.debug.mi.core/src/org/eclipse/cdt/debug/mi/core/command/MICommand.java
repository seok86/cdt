/*
 *(c) Copyright QNX Software Systems Ltd. 2002.
 * All Rights Reserved.
 * 
 */

package org.eclipse.cdt.debug.mi.core.command;

/**
 * 
 * Represents a MI command.
 */
public class MICommand extends Command {
	final String[] empty = new String[0];
	String[] options = empty;
	String[] parameters = empty;
	String operation = "";

	public MICommand(String oper) {
		this.operation = oper;
	}

	public MICommand(String oper, String[] param) {
		this.operation = oper;
		this.parameters = param;
	}

	public MICommand(String oper, String[] opt, String[] param) {
		this.operation = oper;
		this.options = opt;
		this.parameters = param;
	}

	/**
	 * Returns the operation of this command.
	 * 
	 * @return the operation of this command
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * Returns an array of command's options. An empty collection is 
	 * returned if there are no options.
	 * 
	 * @return an array of command's options
	 */
	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] opt) {
		options = opt;
	}

	/**
	 * Returns an array of command's parameters. An empty collection is 
	 * returned if there are no parameters.
	 * 
	 * @return an array of command's parameters
	 */
	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] p) {
		parameters = p;
	}

	protected String optionsToString() {
		StringBuffer sb = new StringBuffer();
		if (options != null && options.length > 0) {
			for (int i = 0; i < options.length; i++) {
				// If the option contains a space according to
				// GDB/MI spec we must surround it with double quotes.
				if (options[i].indexOf('\t') != -1 || options[i].indexOf(' ') != -1) {
					sb.append(' ').append('"').append(options[i]).append('"');
				} else {
					sb.append(' ').append(options[i]);
				}
			}
		}
		return sb.toString().trim();
	}

	protected String parametersToString() {
		StringBuffer buffer = new StringBuffer();
		if (parameters != null && parameters.length > 0) {
			// According to GDB/MI spec
			// Add a "--" separator if any parameters start with "-"
			if (options != null && options.length > 0) {
				for (int i = 0; i < parameters.length; i++) {
					if (parameters[i].startsWith("-")) {
						buffer.append('-').append('-');
						break;
					}
				}
			}

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < parameters.length; i++) {
				// We need to escape the double quotes and the backslash.
				sb.setLength(0);
				String param = parameters[i];
				for (int j = 0; j < param.length(); j++) {
					char c = param.charAt(j);
					if (c == '"' || c == '\\') {
						sb.append('\\');
					}
					sb.append(c);
				}

				// If the string contains spaces instead of escaping
				// surround the parameter with double quotes.
				if (containsWhitespace(param)) {
					sb.insert(0, '"');
					sb.append('"');
				}
				// In JDK-1.4.x a new method was added to StringBuffer
				// StringBuffer.append(StringBuffer).  This is not in
				// JDK-1.3.x, So when compiling with JDK-1.4.x
				// Running on JDK-1.3.x will not work.
				// We need to force the toString().
				buffer.append(' ').append(sb.toString());
			}
		}
		return buffer.toString().trim();
	}

	public String toString() {
		StringBuffer command = new StringBuffer(getToken() + getOperation());
		String opt = optionsToString();
		if (opt.length() > 0) {
			command.append(' ').append(opt);
		}
		String p = parametersToString();
		if (p.length() > 0) {
			command.append(' ').append(p);
		}
		command.append('\n');
		return command.toString();
	}

	boolean containsWhitespace(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (Character.isWhitespace(s.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
