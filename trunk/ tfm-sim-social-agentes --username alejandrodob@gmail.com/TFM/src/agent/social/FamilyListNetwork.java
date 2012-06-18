package agent.social;

import java.util.ArrayList;

import agent.Man;
import agent.Person;
import agent.Woman;

public class FamilyListNetwork extends ListNetwork implements FamilyNetwork {

	private enum kinship {
		FATHER, MOTHER, SON, DAUGHTER, BROTHER, SISTER
	}
	
	@Override
	public ArrayList<Person> siblings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Man father() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Woman mother() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Person> sons() {
		// TODO Auto-generated method stub
		return null;
	}

}
