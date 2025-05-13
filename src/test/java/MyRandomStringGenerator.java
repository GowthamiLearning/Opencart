import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

public class MyRandomStringGenerator 
{

	public static void main(String[] args) {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z').build();
		 String randomAlphaNumbers = generator.generate(9);
			 System.out.println(randomAlphaNumbers);
			 
	}

}


