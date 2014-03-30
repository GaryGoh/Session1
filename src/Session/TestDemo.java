package Session;

public class TestDemo {
	
	public static void main(String[] args) throws Exception{
		Operation operation = new Operation();
		ExpectedList expectedList = new ExpectedList();
		operation.get_file();
		operation.get_expected_list(expectedList);
		operation.print_file(expectedList);
	}

	
}
