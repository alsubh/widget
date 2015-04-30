package Tester;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.client.ApplicationClient;
import com.widget.businesslogic.GooPile;

import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Session Bean implementation class clientTest
 */
@Stateless
@LocalBean
public class clientTest {
		
	ApplicationClient client = new ApplicationClient();
	
	public static void main(String[] args) {
		
		clientTest test = new clientTest();
		test.FillGooPile_Test01();
	}
	
	@Test
	public void FillGooPile_Test01()
	{
		try
		{
			client.FillGooPile();
			GooPile goos = new GooPile();
			assertEquals(4, goos.count());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
}
