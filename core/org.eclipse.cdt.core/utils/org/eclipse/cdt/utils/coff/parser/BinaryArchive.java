package org.eclipse.cdt.utils.coff.parser;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */
 
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.cdt.core.IBinaryParser;
import org.eclipse.cdt.core.IBinaryParser.IBinaryArchive;
import org.eclipse.cdt.core.IBinaryParser.IBinaryFile;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.utils.BinaryFile;
import org.eclipse.cdt.utils.coff.PEArchive;
import org.eclipse.core.runtime.IPath;

/**
 */
public class BinaryArchive extends BinaryFile implements IBinaryArchive {

	ArrayList children;
	
	public BinaryArchive(IBinaryParser parser, IPath path) throws IOException {
		super(parser, path);
		new PEArchive(path.toOSString()).dispose(); // check file type
		children = new ArrayList(5);
	}

	/**
	 * @see org.eclipse.cdt.core.model.IBinaryParser.IBinaryArchive#getObjects()
	 */
	public IBinaryObject[] getObjects() {
		if (hasChanged()) {
			children.clear();
			PEArchive ar = null;
			try {
				ar = new PEArchive(getPath().toOSString());
				PEArchive.ARHeader[] headers = ar.getHeaders();
				for (int i = 0; i < headers.length; i++) {
					IBinaryObject bin = new ARMember(getBinaryParser(), path, headers[i]);
					children.add(bin);
				}
			} catch (IOException e) {
				//e.printStackTrace();
			}
			if (ar != null) {
				ar.dispose();
			}
			children.trimToSize();
		}
		return (IBinaryObject[])children.toArray(new IBinaryObject[0]);
	}

	/**
	 * @see org.eclipse.cdt.core.model.IBinaryParser.IBinaryFile#getType()
	 */
	public int getType() {
		return IBinaryFile.ARCHIVE;
	}

	/**
	 * @see org.eclipse.cdt.core.model.IBinaryParser.IBinaryArchive#add(IBinaryObject[])
	 */
	public void add(IBinaryObject[] objs) throws IOException {
	}

	/**
	 * @see org.eclipse.cdt.core.model.IBinaryParser.IBinaryArchive#delete(IBinaryObject[])
	 */
	public void delete(IBinaryObject[] objs) throws IOException {
	}

}
