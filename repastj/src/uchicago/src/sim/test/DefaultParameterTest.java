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
package uchicago.src.sim.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import uchicago.src.sim.engine.SimpleModel;
import uchicago.src.sim.parameter.ParameterSetter;
import uchicago.src.sim.parameter.ParameterSetterFactory;

/**
 * Tests DefaultParameter setter.
 */
public class DefaultParameterTest extends TestCase {

  public class TestSimModel extends SimpleModel {

    private int intA = 0;
    private String strB = "foo";
    private boolean booleanC = false;
    private float floatD = 3.21f;

    public TestSimModel() {
      this.params = new String[]{"IntA", "StrB", "BooleanC", "FloatD"};
    }

    public int getIntA() {
      return intA;
    }

    public void setIntA(int intA) {
      this.intA = intA;
    }

    public String getStrB() {
      return strB;
    }

    public void setStrB(String strB) {
      this.strB = strB;
    }

    public boolean isBooleanC() {
      return booleanC;
    }

    public void setBooleanC(boolean booleanC) {
      this.booleanC = booleanC;
    }

    public float getFloatD() {
      return floatD;
    }

    public void setFloatD(float floatD) {
      this.floatD = floatD;
    }
  }

  private TestSimModel simModel;
  private ParameterSetter setter;

  public DefaultParameterTest(String name) {
    super(name);
  }

  public void setUp() {
    try {
      setter = ParameterSetterFactory.createParameterSetter("./TestParams.txt");
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    simModel = new TestSimModel();
  }

  public void testSetParams() {
    setter.setModelParameters(simModel);
    assertEquals(10, simModel.getIntA());
    assertEquals("bob", simModel.getStrB());
    assertEquals(true, simModel.isBooleanC());
    assertEquals(2342.23423f, simModel.getFloatD(), 0);
  }

  public void testNextParams() {
    int runs = 0;
    int count = 1;
    int intExp = 10;
    boolean boolExp = true;
    String strExp = "bob";
    while (setter.hasNext()) {
      //System.out.println("count = " + count);
      setter.setNextModelParameters(simModel);

      if (!setter.hasNext()) break;
      assertEquals(2342.23423f, simModel.getFloatD(), 0);
      if (count % 2 == 0) {
        boolExp = false;
      } else
        boolExp = true;

      if (count == 3) strExp = "bill";
      if (count == 5) strExp = "cormac";

      if (count == 7) {
        intExp += 10;
        strExp = "bob";
        boolExp = true;
        count = 1;
      }

      assertEquals("count = " + count, intExp, simModel.getIntA());
      assertEquals("count = " + count, strExp, simModel.getStrB());
      assertEquals("count = " + count, boolExp, simModel.isBooleanC());

      count++;
      runs++;
    }

    assertEquals(18, runs);
  }

  public void testGetDynNames() {
    ArrayList list = setter.getDynamicParameterNames();
    assertEquals(3, list.size());
    assertTrue(list.contains("IntA"));
    assertTrue(list.contains("StrB"));
    assertTrue(list.contains("BooleanC"));
  }

  public void testIsParameter() {
    assertTrue(setter.isParameter("inta"));
    assertTrue(setter.isParameter("IntA"));

    assertTrue(!setter.isParameter("foo"));
  }

  public void testDefaultModelParameters() {
    Map m = setter.getDefaultModelParameters(simModel);
    assertEquals(5, m.size());

    // parameters return their value's as Strings.
    assertEquals(m.get("StrB"), "bob");
    assertEquals(m.get("IntA"), "10.0");
    assertEquals(m.get("BooleanC"), "true");
    assertEquals(m.get("FloatD"), "2342.23423");
  }

  public static junit.framework.Test suite() {
    return new TestSuite(DefaultParameterTest.class);
  }
}

