package answer.medicheck.oop.map;

import quiz.medicheck.MedicalCheck;

public class BmiMapper implements Mapper<Double, MedicalCheck> {

	@Override
	public Double map(MedicalCheck type) {
		double weight = type.getWeight();
		double height = type.getHeight();
		double bmi = weight / ((height/100) * (height/100));
		return bmi;
	}

	
}
