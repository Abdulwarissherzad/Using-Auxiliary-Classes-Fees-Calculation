package exerciseFees;

public class businesLogic {
	 String name;
	 String facultyName;
	 String mealF;
	 int NoOfCource;
	
	public businesLogic(String pname, String pfacultyName , int pNoOfcource, String pmealF) {
		this.name = pname;
		this.facultyName = pfacultyName;
		this.NoOfCource = pNoOfcource;
		this.mealF = pmealF;
		
	}
	public String calculat(){
		 int courceFee = 0;
		 if(facultyName.equalsIgnoreCase("Engineering"))
			 courceFee = 500;
		 if(facultyName.equalsIgnoreCase("Low"))
			 courceFee =400;
		 if(facultyName.equalsIgnoreCase("Econemy"))
			 courceFee = 420;
		 if(facultyName.equalsIgnoreCase("Medecal"))
			 courceFee = 480;
		String rtu = "";
		rtu = name +" , "+ facultyName + " , " +NoOfCource ;
		rtu = "\n"+ "fee cource:"+NoOfCource*courceFee;
		
		return rtu;
	}
	public String mealPcal() {
		int mealmoney = 0;
		if(mealF == "meal1");
			mealmoney = 1500;
		if(mealF == "meal2")
			mealmoney = 1000;
	String	retur =""; 
	retur=""+ mealmoney;
		return retur;
		
	}

}

/*همچنان این طور نوشته هم میتوانیم
 *  if(facultyName == "Engineering")
			 courceFee = 500;
	 if(facultyName == "Low" )
			 courceFee =400;
 */
