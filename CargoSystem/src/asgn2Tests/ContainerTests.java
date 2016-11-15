package asgn2Tests;

/* Some valid container codes used in the tests below:
 * INKU2633836
 * KOCU8090115
 * MSCU6639871
 * CSQU3054389
 */

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Test;





import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.InvalidContainerException;

import org.junit.Before;

/**
@author Aeron (08667501)
*/
public class ContainerTests {
	//Implementation Here - includes tests for ContainerCode and for the actual container classes. 
	private ContainerCode testCode1,testCode2;
	private GeneralGoodsContainer testGContainer1,testGContainer2;
	private DangerousGoodsContainer testDContainer1;
	private RefrigeratedContainer testRContainer1;
	
	final String TESTCODE_1 ="MSCU6639871";
	final String TESTCODE_2 ="MSCU1234585";
	
	
	
	
	final Integer LOWER_WEIGHT =2;
	final Integer STANDARD_WEIGHT = 20;
	final Integer HIGHER_WEIGHT = 40;
	
	final Integer LOWER_CATEGORY =-10;
	final Integer STANDARD_CATEGORY = 5;
	final Integer HIGHER_CATEGORY = 40;
	
	final Integer TEMPERATURE = 2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}

	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#ContainerCode(String)}. 
	 * @throws InvalidCodeException 
	 * 
	 */
	
	@Test(expected= InvalidCodeException.class)
	public void codeNotEqual11() throws InvalidCodeException{
		//Code length ==12
		testCode1 = new ContainerCode("MSCU66398712");
		
		//Code length ==10
		testCode1 = new ContainerCode("MSCU663981");
		
		testCode1 = new ContainerCode(null);	
	}
	
	@Test(expected= InvalidCodeException.class)
	public void InvalidSerial() throws InvalidCodeException{
		//No Serial Number
		testCode1 = new ContainerCode("MSCUASDFRE1");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void InvalidDigit() throws InvalidCodeException{
		//Invalid Digit, Last Digit should be "1"
		testCode1 = new ContainerCode("MSCU6639872");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void InvalidCategory() throws InvalidCodeException{
		//No Owner Code
		testCode1 = new ContainerCode("12346639871");
		
		//Lower-case Letter
		testCode1 = new ContainerCode("mscU6639871");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void ContainerCodeLowerCaseU() throws InvalidCodeException{
		
		testCode1 = new ContainerCode("INKu2633836");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void ContainerCodeInvalidEnd() throws InvalidCodeException{
		
		testCode1 = new ContainerCode("INKU263383I");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void ContainerCodeLetter() throws InvalidCodeException{
		
		testCode1 = new ContainerCode("INKUZ633836");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void ContainerExccessOwnerCode() throws InvalidCodeException{
		
		testCode1 = new ContainerCode("SINKU633836");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void ContainerExccessSequenceNo() throws InvalidCodeException{
		
		testCode1 = new ContainerCode("SINKU6338356");
	}

	@Test(expected= InvalidCodeException.class)
	public void ContainerCAllLetter() throws InvalidCodeException{
		
		testCode1 = new ContainerCode("SINKUASDRED");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void ContainerCAllDigit() throws InvalidCodeException{
		
		testCode1 = new ContainerCode("16485312358");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void ContainerCNull1() throws InvalidCodeException{
		
		testCode1 = new ContainerCode("");
	}
	
	@Test(expected= InvalidCodeException.class)
	public void ContainerCNull2() throws InvalidCodeException{
		
		testCode1 = new ContainerCode(null);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void CodeEqualItself() throws InvalidCodeException{
		
		testCode1 = new ContainerCode(TESTCODE_1);
		assertTrue(testCode1.equals(testCode1));
		
	}
	
	@Test
	public void CodeEqualsCody() throws InvalidCodeException{
		
		testCode1 = new ContainerCode(TESTCODE_1);
		testCode2 = testCode1;
		
		assertTrue(testCode1.equals(testCode2));
		
	}
	
	@Test
	public void CodeEqualsDoubleCheck() throws InvalidCodeException{
		
		testCode1 = new ContainerCode(TESTCODE_1);
		testCode2 = testCode1;
		
		assertTrue(testCode1.equals(testCode1) && testCode1.equals(testCode2));
		
	}
	
	@Test
	public void CodeEqualsDoubleCheck1() throws InvalidCodeException{
		
		testCode1 = new ContainerCode(TESTCODE_1);
		testCode2 = testCode1;
		
		assertTrue(testCode1.equals(new ContainerCode(TESTCODE_1)) && testCode1.equals(testCode2));
		
	}
	
	@Test
	public void CodeNotEqual() throws InvalidCodeException{
		
		testCode1 = new ContainerCode(TESTCODE_1);
		assertFalse(testCode1.equals(TESTCODE_2)) ;
		
	}
	
	@Test
	public void CodeEqualNull() throws InvalidCodeException{
		
		testCode1 = new ContainerCode(TESTCODE_1);
		assertFalse(testCode1.equals("")) ;
		
	}
	
	@Test
	public void CodeEqualNull1() throws InvalidCodeException{
		
		testCode1 = new ContainerCode(TESTCODE_1);
		assertFalse(testCode1.equals(null)) ;
		
	}
	
	@Test
	public void toStringNotReturnSameStringEverytime() throws InvalidCodeException{
		testCode1 = new ContainerCode(TESTCODE_1);
		testCode2 = new ContainerCode(TESTCODE_2);
		assertTrue(testCode1.toString() == TESTCODE_1 && testCode2.toString() == TESTCODE_2);
	}
	
	
	
	
	
	/**
	 * Test method for {@link asgn2Codes.ContainerCode#equals(Object)}. 
	 * @throws InvalidCodeException,InvalidContainerException
	 */
	@Test
	public void NotEqualContainerCode()throws InvalidCodeException, InvalidContainerException{
		//Declare Container1
		testCode1 = new ContainerCode(TESTCODE_1);
		testGContainer1 = new GeneralGoodsContainer(testCode1,STANDARD_WEIGHT);
		
		//Declare Container2
		testCode2 = new ContainerCode(TESTCODE_2);
		testGContainer2= new GeneralGoodsContainer(testCode2,STANDARD_WEIGHT) ;
		//Return true if have same container code
		assertTrue(testGContainer1.equals(testGContainer1)==true);
		
		//Return false if container code not equal
		assertTrue(testGContainer1.equals(testGContainer2)==false);
	}
	
	/**
	 * Test method for {@link asgn2Containers.DangerousGoodsContainer#
	 * DangerousGoodsContainer(ContainerCode, Integer, Integer)}. 
	 * 
	 * @throws InvalidCodeException,InvalidContainerException
	 */
	@Test
	public void D_CatRangeTest()throws InvalidCodeException, InvalidContainerException{
		for(int i=1; i<10;i++){
			testDContainer1= new DangerousGoodsContainer(testCode1,STANDARD_WEIGHT,i);
		}
			
		
	}
	
	/**
	 * Test method for {@link asgn2Containers.DangerousGoodsContainer#
	 * DangerousGoodsContainer(ContainerCode, Integer, Integer)}. 
	 * 
	 * @throws InvalidCodeException,InvalidContainerException
	 */
	@Test(expected= InvalidContainerException.class)
	public void Invalid_D_CategoryLow()throws InvalidCodeException, InvalidContainerException{
		
		testDContainer1= new DangerousGoodsContainer(testCode1,STANDARD_WEIGHT,LOWER_CATEGORY);	
		
	}
	
	@Test(expected= InvalidContainerException.class)
	public void Invalid_D_CategoryHigh()throws InvalidCodeException, InvalidContainerException{
		
		
		testDContainer1= new DangerousGoodsContainer(testCode1,STANDARD_WEIGHT,HIGHER_CATEGORY);	
		
	}
	
	@Test(expected= InvalidContainerException.class)
	public void Invalid_D_CategoryNull()throws InvalidCodeException, InvalidContainerException{
		testDContainer1= new DangerousGoodsContainer(testCode1,STANDARD_WEIGHT,null);	
		
	}
	
	
	@Test(expected= InvalidContainerException.class)
	public void Invalid_R_CategoryNull()throws InvalidCodeException, InvalidContainerException{
		testRContainer1= new RefrigeratedContainer(testCode1,STANDARD_WEIGHT,null);	
		
	}
	
	
	@Test(expected= InvalidContainerException.class)
	public void Invalid_D_WeightLow()throws InvalidCodeException, InvalidContainerException{
		
		testDContainer1= new DangerousGoodsContainer(testCode1,LOWER_WEIGHT,STANDARD_CATEGORY);
	}
	
	@Test(expected= InvalidContainerException.class)
	public void Invalid_D_WeightHigh()throws InvalidCodeException, InvalidContainerException{
		
		testDContainer1= new DangerousGoodsContainer(testCode1,HIGHER_WEIGHT,STANDARD_CATEGORY);
	}
	
	
	
	/**
	 * Test method for {@link asgn2Containers.GeneralGoodsContainer#
	 * GeneralGoodsContainer(ContainerCode, Integer)} 
	 * 
	 * @throws InvalidCodeException,InvalidContainerException
	 */
	@Test(expected= InvalidContainerException.class)
	public void Invalid_G_WeightLow()throws InvalidCodeException, InvalidContainerException{
	
		
		testGContainer1= new GeneralGoodsContainer(testCode1,LOWER_WEIGHT);
		
	}
	
	@Test(expected= InvalidContainerException.class)
	public void Invalid_G_WeightHigh()throws InvalidCodeException, InvalidContainerException{
	
		testGContainer1= new GeneralGoodsContainer(testCode1,HIGHER_WEIGHT);
	}
	
	
	
	/**
	 * Test method for {@link asgn2Containers.RefrigeratedContainer#RefrigeratedContainer
	 * (ContainerCode, Integer, Integer)} 
	 * 
	 * @throws InvalidCodeException,InvalidContainerException
	 */
	@Test(expected= InvalidContainerException.class)
	public void Invalid_R_WeightLow()throws InvalidCodeException, InvalidContainerException{
	
		
		testRContainer1= new RefrigeratedContainer(testCode1,LOWER_WEIGHT,TEMPERATURE);
		
	}
	
	@Test(expected= InvalidContainerException.class)
	public void Invalid_R_WeightHigh()throws InvalidCodeException, InvalidContainerException{
	
		
		testRContainer1= new RefrigeratedContainer(testCode1,HIGHER_WEIGHT,TEMPERATURE);
	}
	
	
	
}
