package asgn2Codes;

import asgn2Exceptions.InvalidCodeException;

/* Note to self:
 * Use option "-noqualifier java.lang" when generating Javadoc from this
 * file so that, for instance, we get "String" instead of
 * "java.lang.String".
 */

/**
 * This class provides cargo container codes in a format similar to that
 * prescribed by international standard ISO 6346.  (The only difference
 * is that we use a simpler algorithm for calculating the check digit.)
 * <p>
 * A container code is an 11-character string consisting of the following
 * fields:
 * <ul>
 * <li>
 * An <em>Owner Code</em> which uniquely identifies the company that owns
 * the container.  The owner code consists of three upper-case letters.
 * (To ensure uniqueness in practice, owner codes must be registered at
 * the <em>Bureau International des Containers</em> in Paris.)
 * </li>
 * <li>
 * A <em>Category Identifier</em> which identifies the type of container.
 * The identifier is one of three letters, 'U', 'J' or 'Z'.  For our
 * purposes the category identifier is <em>always</em> 'U', which denotes a
 * freight container.
 * </li>
 * <li>
 * A <em>Serial Number</em> used by the owner to uniquely identify the
 * container.  The number consists of six digits.
 * </li>
 * <li>
 * A <em>Check Digit</em> used to ensure that the number has not been
 * transcribed incorrectly.  It consists of a single digit derived from the
 * other 10 characters according to a specific algorithm, described below.
 * </li>
 * </ul>
 * <p>
 * <strong>Calculating the check digit:</strong> ISO 6346 specifies a
 * particular algorithm for deriving the check digit from the
 * other 10 characters in the code.  However, because this algorithm is
 * rather complicated, we use a simpler version for this assignment.
 * Our algorithm works as follows:
 * <ol>
 * <li>
 * Each of the 10 characters in the container code (excluding the check
 * digit) is treated as a number.  The digits '0' to '9' each have
 * their numerical value.  The upper case alphabetic letters 'A' to
 * 'Z' are treated as the numbers '0' to '25', respectively.
 * </li>
 * <li>
 * These 10 numbers are added up.
 * </li>
 * <li>
 * The check digit is the least-significant digit in the sum produced
 * by the previous step.
 * </li>
 * </ol>
 * For example, consider container code <code>MSCU6639871</code> which
 * has owner code <code>MSC</code>, category identifier <code>U</code>,
 * serial number <code>663987</code> and check digit <code>1</code>.  We can 
 * confirm that this code is valid by treating the letters as numbers (e.g.,
 * '<code>M</code>' is 12, '<code>S</code>' is 18, etc) and calculating the
 * following sum.
 * <p>
 * <center>
 * 12 + 18 + 2 + 20 + 6 + 6 + 3 + 9 + 8 + 7 = 91
 * </center>
 * <p>
 * The check digit is then the least-sigificant digit in the number 91,
 * i.e., '<code>1</code>', thus confirming that container code 
 * <code>MSCU6639871</code> is valid.
 * 
 * @author Zhao Zhen Tang (09262555) 
 * @version 1.0
 */ 
public class ContainerCode {


	private String code;
    /**
	 * Constructs a new container code.
	 * 
	 * @param code the container code as a string
	 * @throws InvalidCodeException if the container code is not eleven characters long; if the
	 * Owner Code does not consist of three upper-case letters; if the
	 * Category Identifier is not 'U'; if the Serial Number does not consist
	 * of six digits; or if the Check Digit is incorrect.
	 */
	public ContainerCode(String code) throws InvalidCodeException {
	    
	    if(code==null||code.length()!=11){
	        throw new InvalidCodeException("Invalid container code :Lenght must equal 11");
	    }
	    
	    char a=code.charAt(0);
	    char b=code.charAt(1);
	    char c=code.charAt(2);
	    
	    if(!Character.isUpperCase(a)||!Character.isUpperCase(b)||!Character.isUpperCase(c)){
	        throw new InvalidCodeException("Invalid container code:Code didn't start with 3 Upper Case Letters");
	    }
	    
	    char d=code.charAt(3);
	    if(d!='U'){
	        throw new InvalidCodeException("Invalid container code:Letter U didn't exist");
	    }
	    
	    for(int i=4;i<9;i++){
	        if(code.charAt(i)<'0'||code.charAt(i)>'9'){	            
	            throw new InvalidCodeException("Invalid container code: Didn't consist 6 Digit");
	        }
	    }
	    
	    int sum=(a-65)+(b-65)+(c-65)+(d-65);
	    for(int i=4;i<10;i++){
	        sum+=code.charAt(i)-48;
	    }
	    
	    if(sum%10!=code.charAt(10)-48){
	        throw new InvalidCodeException("Invalid container code: Incorrect Check Digit");
	    }
	    
		
	    this.code=code;	    
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  code;
	}

	
	/**
	 * Returns true iff the given object is a container code and has an
	 * identical value to this code.
	 * 
	 * @param obj an object
	 * @return true if the given object is a container code with the
	 * same string value as this code, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {	    
	    return obj instanceof ContainerCode&& obj.toString().equals(toString());
	}
}

