/**********************************************************************
 * Copyright (c) 2002,2003 Rational Software Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v0.5
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v05.html
 * 
 * Contributors: 
 * IBM Rational Software - Initial API and implementation
***********************************************************************/
package org.eclipse.cdt.internal.core.parser;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.cdt.core.parser.IToken;
import org.eclipse.cdt.core.parser.ITokenDuple;

/**
 * @author jcamelon
 *
 */
public class TokenDuple implements ITokenDuple {

	public TokenDuple( IToken first, IToken last )
	{
		firstToken = first; 
		lastToken = last; 
	}
	protected final IToken firstToken, lastToken; 
	/**
	 * @return
	 */
	public IToken getFirstToken() {
		return firstToken;
	}

	/**
	 * @return
	 */
	public IToken getLastToken() {
		return lastToken;
	}
	
	public Iterator iterator()
	{
		return new TokenIterator(); 
	}
	
	private class TokenIterator implements Iterator
	{
		private IToken iter = TokenDuple.this.firstToken;

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return ( iter != null );
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Object next() {
			if( ! hasNext() )
				throw new NoSuchElementException();
			IToken temp = iter;
			if( iter == lastToken )
				iter = null; 
			else
				iter = iter.getNext();
			return temp;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			throw new UnsupportedOperationException(); 			
		}
		
	}

	public String toString() 
	{
		StringBuffer buff = new StringBuffer(); 
		IToken iter = firstToken; 
		for( ; ; )
		{
			buff.append( iter.getImage() );
			if( iter == lastToken ) break;
			iter = iter.getNext();
		}
		return buff.toString();
	}
	
	public boolean isIdentifier()
	{
		return ( firstToken == lastToken );
	}

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.parser.ITokenDuple#length()
     */
    public int length()
    {
        int count = 0; 
        Iterator i = iterator();
        while( i.hasNext() )
        {
        	++count;
        	i.next();
        }
        return count;
    }

    /* (non-Javadoc)
     * @see org.eclipse.cdt.core.parser.ITokenDuple#getSubDuple(int, int)
     */
    public ITokenDuple getSubrange(int startIndex, int endIndex)
    {
		return new TokenDuple( getToken( startIndex ), getToken( endIndex) );
    }

    /**
     * @param endIndex
     */
    public IToken getToken(int index)
    {
        if( index < 0 || index >= length() ) return null;
        Iterator i = iterator();
        int count = 0;  
        while( i.hasNext() )
        {
        	IToken r = (IToken)i.next();
        	if( count == index )
        		return r;
        	++count; 
        }
        return null;
    }
	
}
