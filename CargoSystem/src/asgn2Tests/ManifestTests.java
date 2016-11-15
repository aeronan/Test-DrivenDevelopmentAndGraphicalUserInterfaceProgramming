package asgn2Tests;







import org.junit.Before;
import org.junit.Test;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.InvalidContainerException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;
import static org.junit.Assert.*;

/**
@Author : Zhao Zhen Tang (09262555)
*/
public class ManifestTests {
	
	private CargoManifest testManifest;
	
	private GeneralGoodsContainer testGContainer1,testGContainer2;
	private DangerousGoodsContainer testDContainer1,testDContainer2;
	private RefrigeratedContainer testRContainer1,testRContainer2;
	private ContainerCode[] TESTCODE;
	
	
	
	final Integer STANDARD_WEIGHT = 30;
	
	final Integer SHIP_STACK_MAX = 5;
	final Integer SHIP_HEIGHT_MAX = 3;
	final Integer SHIP_WEIGHT_MAX = 300;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {		
	//	testManifest= new CargoManifest(SHIP_STACK_MAX,SHIP_HEIGHT_MAX, SHIP_WEIGHT_MAX);		
		TESTCODE = new ContainerCode[7];
		TESTCODE[0] = new ContainerCode("MSCU6639871");
		TESTCODE[1] = new ContainerCode("MSCU1234585");
		TESTCODE[2]=  new ContainerCode("MSCU5678514");
		TESTCODE[3]=  new ContainerCode("MSCU8461203");
		TESTCODE[4]=  new ContainerCode("IEWU6647210");
		TESTCODE[5]=  new ContainerCode("MQGU1636370");
		TESTCODE[5]=  new ContainerCode("DMWU2802559");
		
		
		testGContainer1= new GeneralGoodsContainer(TESTCODE[0],STANDARD_WEIGHT);
		testGContainer2= new GeneralGoodsContainer(TESTCODE[1],STANDARD_WEIGHT);
		testDContainer1= new DangerousGoodsContainer(TESTCODE[2],STANDARD_WEIGHT,5);
		testDContainer2= new DangerousGoodsContainer(TESTCODE[3],STANDARD_WEIGHT,5);
		testRContainer1= new RefrigeratedContainer(TESTCODE[4],STANDARD_WEIGHT,2);
		testRContainer2= new RefrigeratedContainer(TESTCODE[5],STANDARD_WEIGHT,2);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#
	 * CargoManifest(Integer, Integer, Integer)}. 
	 * 
	 * Throw Exception if NumberStack is negative value
	 * Author : Zhen
	 * @throws ManifestException
	 */
	@Test(expected= ManifestException.class)
	public void negativeNumStack() throws ManifestException{
		testManifest=new CargoManifest(-5,3,150);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#
	 * CargoManifest(Integer, Integer, Integer)}. 
	 * 
	 * Throw Exception if MaxHeight is negative value
	 * Author : Zhen
	 * @throws ManifestException
	 */
	@Test(expected= ManifestException.class)
	public void negativeMaxHeight() throws ManifestException{
		testManifest=new CargoManifest(5,-3,150);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#
	 * CargoManifest(Integer, Integer, Integer)}. 
	 * 
	 * Throw Exception if Max Weight is negative value
	 * 
	 * @throws ManifestException
	 */
	@Test(expected= ManifestException.class)
	public void negativeMaxWeight() throws ManifestException{
		testManifest=new CargoManifest(5,3,-150);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#
	 * CargoManifest(Integer, Integer, Integer)}. 
	 * 
	 * Test entered Manifest Value
	 * 
	 * @throws ManifestException
	 */
	@Test(expected= ManifestException.class)
	public void testGoodManifest() throws ManifestException{
		testManifest=new CargoManifest(2,1,60);
		
		//Stack 0 Height 0 Weight =30
		testManifest.loadContainer(testGContainer1);
		//Stack 1 Height 0 Weight =60
		testManifest.loadContainer(testGContainer2);
		
		//Stack 2 Height 0 Weight =90, throw exception
		testManifest.loadContainer(testDContainer1);
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if adding this container would exceed the ship's weight limit
	 * Ship Weight Limit =40
	 * Add Container1(40-30 =10)
	 * Add Container2(10-30=-20) throw Exception
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void SGExceedWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,10);
		
		testManifest.loadContainer(testGContainer1);
		
		
	}
	
	
	@Test(expected= ManifestException.class)
	public void GExceedWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,40);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testGContainer2);
		
	}
	
	@Test(expected= ManifestException.class)
	public void DExceedWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,40);
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testDContainer2);
		
	}
	
	@Test(expected= ManifestException.class)
	public void RExceedWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,40);
		
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(testRContainer2);
		
	}
	
	@Test(expected= ManifestException.class)
	public void GDExceedWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,40);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testDContainer2);
		
	}
	
	@Test(expected= ManifestException.class)
	public void GRExceedWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,40);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testRContainer2);
		
	}
	
	@Test(expected= ManifestException.class)
	public void DRExceedWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,40);
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testRContainer2);
		
	}
	
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add two G container with all same value
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void G_DuplicateCodeSameType() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testGContainer1);
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add two G container with same code but different weight
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void G_DuplicateCodeSameTypeDiffWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(new GeneralGoodsContainer(TESTCODE[0], 10));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add two D container with all same value
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void D_DuplicateCodeSameType() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testDContainer1);
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add two D container with all same value
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void D_DuplicateCodeSameTypeDifferentWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(new DangerousGoodsContainer(TESTCODE[2],10,5));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add two R container with all same value
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void R_DuplicateCodeSameType() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(testRContainer1);
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add two R container with same code and type but different weight
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void R_DuplicateCodeSameTypeDifferentWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(new DangerousGoodsContainer(TESTCODE[4],10,5));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add same D and G container with same code and weight
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void SameCodeDiffType() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(new DangerousGoodsContainer(TESTCODE[0],STANDARD_WEIGHT,5));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add same D and G container with same code but different weight
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void DuplicateCodeDiffTypeDiffWeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(new DangerousGoodsContainer(TESTCODE[0],20,5));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add same G and R container with same code and weight
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void SameCodeDiffType2() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(new RefrigeratedContainer(TESTCODE[0],STANDARD_WEIGHT,5));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add same G and R container with same code but different weight
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void DuplicateCodeDiffTypeDiffWeight2() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(new RefrigeratedContainer(TESTCODE[0],20,5));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add same D and R container with same code and weight
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void SameCodeDiffType3() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(new RefrigeratedContainer(TESTCODE[2],STANDARD_WEIGHT,5));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if duplicate code
	 * Add same D and R container with same code but different weight
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void DuplicateCodeDiffTypeDiffWeight3() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(new RefrigeratedContainer(TESTCODE[2],20,5));
		
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * Load 2 G Container but insufficient space.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void GSameConatinerNoSpace() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(1,1,150);
		
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testGContainer2);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * Load 2 D Container but insufficient space.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void DSameConatinerNoSpace() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(1,1,150);
		
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testDContainer2);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * Load 2 R Container but insufficient space.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void RSameConatinerNoSpace() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(1,1,150);
		
		
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(testRContainer2);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * D and G Container but only got 1 stack.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void DifferentContainerNoSpace1() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(1,3,150);
		
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testDContainer1);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * G and R Container but only got 1 stack.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void DifferentContainerNoSpace2() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(1,3,150);
		
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testRContainer1);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * D and R Container but only got 1 stack.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void DifferentContainerNoSpace3() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(1,3,150);
		
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testRContainer1);
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * G Container exceed max Height.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void GMaxHeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(2,2,500);
		
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(testGContainer2);
		testManifest.loadContainer(new GeneralGoodsContainer(TESTCODE[6],STANDARD_WEIGHT));
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * D Container exceed max Height.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void DMaxHeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(2,2,150);
		
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(testDContainer2);
		testManifest.loadContainer(new DangerousGoodsContainer(TESTCODE[6],STANDARD_WEIGHT,5));
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * ManifestException if no space in Manifest
	 * R Container exceed max Height.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void RMaxHeight() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(2,2,150);
		
		
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testRContainer2);
		testManifest.loadContainer(new RefrigeratedContainer(TESTCODE[6],STANDARD_WEIGHT,2));
		
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * Test normal load container
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void SequenceLoadContainer() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
				
		//Load Container
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testRContainer1);
		
		//Get nearest Ship stack
		FreightContainer[] Stack0 = testManifest.toArray(0);
		FreightContainer[] Stack1 = testManifest.toArray(1);
		FreightContainer[] Stack2 = testManifest.toArray(2);
		
		//AssultTrue if ContainerExist at stack1,height0(bottom of ship)
		assertTrue("Container G not Loaded onto Ship",Stack0[0].getCode()==testGContainer1.getCode());
		assertTrue("Container D not Loaded onto Ship",Stack1[0].getCode()==testDContainer1.getCode());
		assertTrue("Container R not Loaded onto Ship",Stack2[0].getCode()==testRContainer1.getCode());
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * Test random load different type container
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void RandomLoadContainer() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
				
		//Load Container
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(testDContainer2);
		
		//Get nearest Ship stack
		FreightContainer[] Stack0 = testManifest.toArray(0);
		FreightContainer[] Stack1 = testManifest.toArray(1);
		FreightContainer[] Stack2 = testManifest.toArray(2);
		
		//AssultTrue if ContainerExist at stack1,height0(bottom of ship)
		assertTrue("Container G not Loaded onto Ship",Stack0[0].getCode()==testGContainer1.getCode());
		assertTrue("Container D1 not Loaded onto Ship",Stack1[0].getCode()==testDContainer1.getCode());
		assertTrue("Container R not Loaded onto Ship",Stack2[0].getCode()==testRContainer1.getCode());
		assertTrue("Container D2 not on same place",Stack1[1].getCode()==testDContainer2.getCode());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * 
	 * Add two G Container in two 1 maxHeight automatic to next stack
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void GAutoNextStack() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(2,1,150);
		
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testGContainer2);
		FreightContainer[] Stack0 = testManifest.toArray(0);
		FreightContainer[] Stack1 = testManifest.toArray(1);
		
		assertTrue("Container not exist at specific Stack",Stack0[0].getCode()==testGContainer1.getCode());
		assertTrue("Container not exist at specific Stack",Stack1[0].getCode()==testGContainer2.getCode());
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * 
	 * Add two D Container in two 1 maxHeight automatic to next stack
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void DAutoNextStack() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(2,1,150);
		
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testDContainer2);
		FreightContainer[] Stack0 = testManifest.toArray(0);
		FreightContainer[] Stack1 = testManifest.toArray(1);
		
		assertTrue("Container not exist at specific Stack",Stack0[0].getCode()==testDContainer1.getCode());
		assertTrue("Container not exist at specific Stack",Stack1[0].getCode()==testDContainer2.getCode());
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * 
	 * Add two R Container in two 1 maxHeight automatic to next stack
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void RAutoNextStack() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(2,1,150);
		
		
		testManifest.loadContainer(testRContainer1);
		testManifest.loadContainer(testRContainer2);
		FreightContainer[] Stack0 = testManifest.toArray(0);
		FreightContainer[] Stack1 = testManifest.toArray(1);
		
		assertTrue("Container not exist at specific Stack",Stack0[0].getCode()==testRContainer1.getCode());
		assertTrue("Container not exist at specific Stack",Stack1[0].getCode()==testRContainer2.getCode());
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
	 * 
	 * 
	 * First Container must located at stack 0
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void firstContainerlocation() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(2,1,150);
		
		
		testManifest.loadContainer(testGContainer1);
		
		FreightContainer[] Stack0 = testManifest.toArray(0);
		assertTrue("Container not exist at specific Stack",Stack0[0].getCode()==testGContainer1.getCode());
		
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#unloadContainer(ContainerCode)}
	 * 
	 * ManifestException if unloaded container is at bottom of a stack
	 * Throw manifest if container not accessible.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void ContainerNotOnTop() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		//Below
		testManifest.loadContainer(testGContainer1);
		//Upper
		testManifest.loadContainer(testGContainer2);
		
		//Check Location
		FreightContainer[] Stack0 = testManifest.toArray(0);
		assertTrue("testGContainer1 not loaded on stack 0 Height 0",Stack0[0]==testGContainer1);
		assertTrue("testGContainer2 not loaded on stack 0 Height 1",Stack0[1]==testGContainer2);
		
		//Unload Below
		testManifest.unloadContainer(TESTCODE[0]);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#unloadContainer(ContainerCode)}
	 * 
	 * ManifestException if unloaded container is at bottom of a stack
	 * Throw manifest if container not exist.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test(expected= ManifestException.class)
	public void UnloadNotExistContainer() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		//Below
		testManifest.loadContainer(testGContainer1);
		
		//Upper
		testManifest.loadContainer(testGContainer2);
		
		//Check Location
		FreightContainer[] Stack0 = testManifest.toArray(0);
		assertTrue("testGContainer1 not loaded on stack 0 Height 0",Stack0[0]==testGContainer1);
		assertTrue("testGContainer2 not loaded on stack 0 Height 1",Stack0[1]==testGContainer2);
		
		//Unload Not Exist Container
		testManifest.unloadContainer(TESTCODE[6]);
	}
	
	
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#unloadContainer(ContainerCode)}
	 * 
	 * AssertTrue if container is unloaded for
	 * Same Type Container.
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void testUnloadContainer() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		
		testManifest.loadContainer(testGContainer1);
		Integer locatedCon1 = testManifest.whichStack(TESTCODE[0]);
		assertFalse("Container1 not loaded to ship",locatedCon1== null);
		
		
		testManifest.loadContainer(testGContainer2);
		Integer locatedCon2 = testManifest.whichStack(TESTCODE[1]);
		assertFalse("Container2 not loaded to ship",locatedCon2== null);
		
		testManifest.unloadContainer(TESTCODE[1]);
		assertTrue("Container hasnot been removed from ship",testManifest.whichStack(TESTCODE[1])== null);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#whichStack(ContainerCode)}
	 * 
	 * AssertTrue return stack is true
	 * Same Type Container.
	 * 
	 * Author : Zhen
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void testWhichStack() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testDContainer1);
		testManifest.loadContainer(testGContainer1);
		
		assertTrue("testDContainer not located at Stack 0.",testManifest.whichStack(TESTCODE[2])==0);
		assertTrue("testGContainer not located at Stack 1.",testManifest.whichStack(TESTCODE[0])==1);
		assertTrue("Didn't return null for not exist", testManifest.whichStack(TESTCODE[3])==null);
	}
	
	/**
	 * Test method for {@link asgn2Manifests.CargoManifest#howHigh(ContainerCode)}
	 * 
	 * AssertTrue return height is at same stack
	 * Same Type Container.
	 * 
	 *Author : Zhen
	 *   
	 * @throws ManifestException, InvalidContainerException 
	 */
	@Test
	public void testHowHigh() throws ManifestException, InvalidContainerException{
		testManifest=new CargoManifest(5,3,150);
		
		testManifest.loadContainer(testGContainer1);
		testManifest.loadContainer(testGContainer2);
		testManifest.loadContainer(testDContainer1);
		
		assertTrue("testGContainer1 != 0.",testManifest.howHigh(TESTCODE[0])==0);
		assertTrue("testGContainer2 != 1",testManifest.howHigh(TESTCODE[1])==1);
		assertTrue("testDContainer1 != 0",testManifest.howHigh(TESTCODE[2])==0);
		assertTrue("Didn't return null for not exist", testManifest.howHigh(TESTCODE[3])==null);
	}

	/**
     * Test method for {@link asgn2Manifests.CargoManifest#loadContainer(FreightContainer)}
     * Author : Aeron
     * ManifestException if no space in Manifest
     * 2 Container with same type, but insufficient space.
     * 
     * @throws ManifestException, InvalidContainerException 
     */
    @Test(expected= ManifestException.class)
    public void TestToArrayWithException() throws ManifestException, InvalidContainerException{
        testManifest=new CargoManifest(1,1,150);
        testManifest.toArray(10);        
    }
    
   
}
