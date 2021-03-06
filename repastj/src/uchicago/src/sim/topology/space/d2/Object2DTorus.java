/*$$
 * Copyright (c) 1999, Trustees of the University of Chicago
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with 
 * or without modification, are permitted provided that the following 
 * conditions are met:
 *
 *	 Redistributions of source code must retain the above copyright notice,
 *	 this list of conditions and the following disclaimer.
 *
 *	 Redistributions in binary form must reproduce the above copyright notice,
 *	 this list of conditions and the following disclaimer in the documentation
 *	 and/or other materials provided with the distribution.
 *
 * Neither the name of the University of Chicago nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE TRUSTEES OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *$$*/
package uchicago.src.sim.topology.space.d2;

import java.io.InputStream;

import uchicago.src.sim.space.Torus;


/**
 * A discrete 2 dimensional torus of objects, accessed by x and y
 * coordinates.
 *
 * @author Nick Collier
 * @version $Revision: 1.3 $ $Date: 2004/11/03 19:51:05 $
 */


public class Object2DTorus extends Object2DGrid implements Torus {

    /**
     * Creates a new torus of the specified size.
     *
     * @param xSize the size of the x dimension
     * @param ySize the size of the y dimension
     */
    public Object2DTorus(int xSize, int ySize) {
        super("Object2DTorus",xSize, ySize);
    }

    public Object2DTorus(String type, int xSize, int ySize) {
        super(type, xSize, ySize);
    }

    /**
     * Creates a new torus from the specified file.
     *
     * @param fileName the name of the file to create the torus from
     * @param type the type of the file
     * @see uchicago.src.sim.space.Object2DGrid
     */
    public Object2DTorus(String type, String fileName) {
        super(type, fileName);
    }

    /**
     * Creates a new torus from the specified file.
     *
     * @param fileName the name of the file to create the torus from
     * @param type the type of the file
     * @see uchicago.src.sim.space.Object2DGrid
     */
    public Object2DTorus(String fileName) {
        super("Object2DTorus", fileName);
    }
    
    /**
     * Creates a new torus from the specified InputStream.
     *
     * @param stream the InputStream to create the torus from.
     * @param type the type of the file
     * @see uchicago.src.sim.space.Object2DGrid
     */
    public Object2DTorus(String type, InputStream stream) {
        super(type, stream);
    }
    
    /**
     * Creates a new torus from the specified InputStream.
     *
     * @param stream the InputStream to create the torus from.
     * @param type the type of the file
     * @see uchicago.src.sim.space.Object2DGrid
     */
    public Object2DTorus(InputStream stream) {
        super("Object2DTorus", stream);
    }

}
