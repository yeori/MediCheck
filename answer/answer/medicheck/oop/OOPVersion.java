package answer.medicheck.oop;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import answer.medicheck.oop.filter.BoyFilter;
import answer.medicheck.oop.filter.Filter;
import answer.medicheck.oop.filter.GirlFilter;
import answer.medicheck.oop.map.BmiMapper;
import answer.medicheck.oop.map.HeightMapper;
import answer.medicheck.oop.map.Mapper;
import answer.medicheck.oop.map.WeightMapper;
import answer.medicheck.oop.soring.HeightComparator;
import quiz.medicheck.MedicalCheck;

/**
 * 
 * 이름 : 서용열
 * 
 * 개발 : 8년 - 2년
 * 
 * 애플리케이션 - 미들웨어, ndrive( ndoc, excell, powerpoint )
 * 
 * 지금 - 한글파일을 웹브라우저에서 편지할 수 있는 프로그램(혼자)
 *      - 짬짬이 과외를 합니다.
 *      
 * 전공 - Test Egineer (시스템 개발을 하는데 테스트 가능성을 극대화해야함)
 *      - QA 는 아닙니다(제품의 결과가 스펙과 맞는지 보는 사람들)
 * 
 * 1. filtering - 일련의 집합(collection)에서 원하는 element들만 뽑아내는 역할.
 * 
 * 2. mapping - 특정 대상에서 원하는 하나의 값을 뽑아내는 역할
 * 
 * 3. sorting - java에서 제공해줌.
 * 
 * 
 * 해볼 것
 * 
 * 1. printStudentByWeight와 printStudentByHeight 를 추상화해서 하나의 메소드를 사용하게 해봅시다.
 * 
 * 2. 다중정렬
 *    
 * @author Suhkyung
 *
 */
public class OOPVersion {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		String sboyFilter = "answer.medicheck.oop.filter.BoyFilter"; // full qualified name
		Class cls = Class.forName(sboyFilter);
		
		Filter<MedicalCheck> boyFilter = (Filter<MedicalCheck>) cls.newInstance();
		
		List<MedicalCheck> medicalCheckList = loadMedicalData();
		
		
		double avrWeight = average(medicalCheckList, new WeightMapper());
		double avrHeight = average( medicalCheckList, new HeightMapper());
		
		System.out.println("전체 학생의 평균 키     : " + avrHeight);
		System.out.println("전체 학생의 평균 몸무게 : " + avrWeight);
		
		// 전체 학생의 평균키와 평균몸무게
//		printAverageHeight(medicalCheckList, "전체학생 평균 키");
//		printAverageWeight(medicalCheckList, "전체학생 평균 몸무게");
		
		// 성별 평균키 출력
		
		double avrGirlHeight = average ( filtering ( medicalCheckList, new GirlFilter()), new HeightMapper());
		double avrBoyHeight = average ( filtering ( medicalCheckList, new BoyFilter()), new HeightMapper());
		System.out.println("여학생의 평균 키     : " + avrGirlHeight);
		System.out.println("남학생의 평균 키 : " + avrBoyHeight);
		
		
		double avrGrilBmi = average ( filtering(medicalCheckList, new GirlFilter()), new BmiMapper());
		System.out.println("여학생들 bmi 평균 : " + avrGrilBmi);
		System.out.println();
		
		printBoysHeight(medicalCheckList, "남학생들 평균 키");
		printGirlsHeight(medicalCheckList, "여학생들 평균 키");
		
		// 키가 가장 작은 학생, 가장 무거운 학생
		printLowestStudent(medicalCheckList, "가장 키가 작은 학생");		
		printBiggestStudent(medicalCheckList, "가장 무거운 학생");

		
		// 정렬 - 키의 오름차순으로 출력하기 .... < ... < ....
		printStudentByHeight(medicalCheckList, "키순서(오름차순)");

		// 정렬 - 몸무게의 오름차순으로 출력하기
		printStudentByWeight(medicalCheckList, "몸무게순서(오름차순)");

		/*도전 과제 : 다중정렬
		 * 
		 * input.txt에는 키가 같은 학생이 세 명 있습니다.
		 * 
		 *  2 2006  박경혁 180.3cm
		 *  3 2008  장혁수 180.3cm
		 *  4 2009  임근형 180.3cm
		 * 
		 * 키의 내림차순으로 출력할 때 키가 같은 경우 이름의 오름차순으로
		 * 출력해달라는 요구사항이 추가되었습니다.(즉 아래처럼...)
		 * 
		 *  2 2006  박경혁 180.3cm
		 *  3 2009  임근형 180.3cm
		 *  4 2008  장혁수 180.3cm
		 * 
		 * 위와 같이 출력될 수 있도록 Comparator구현을 만들어보세요.
		 * 
		 */
	}
	
	public static List<MedicalCheck> filtering ( List<MedicalCheck> mChecks, Filter<MedicalCheck> filter ) {
		
		ArrayList<MedicalCheck> checks = new ArrayList<MedicalCheck>();
		for(int i = 0 ; i < mChecks.size(); i++) {
			MedicalCheck mc = mChecks.get(i);
			if ( filter.isAcceptable(mc)) {
				checks.add(mc);
			}
		}
	
		return checks;
	}
	/**
	 * 
	 * @param mChecks
	 * @return
	 */
	public static double average (List<MedicalCheck> mChecks, Mapper<Double, MedicalCheck> mapper ) {
		double total = 0.0;
		
		for( int i = 0 ;  i < mChecks.size(); i++) {
			total += mapper.map(mChecks.get(i));
		}
		
		return total / mChecks.size();
	}
	/**
	 * b/medicheck/input.txt 를 읽어서 학생들의 신체검사 데이터를 List로 반환하는 메소드
	 * @return 신체검사 정보가 담긴 List
	 * @throws FileNotFoundException
	 */
	private static List<MedicalCheck> loadMedicalData() throws FileNotFoundException {

		ArrayList<MedicalCheck> medicalCheckList = new ArrayList<MedicalCheck>();
		
		ClassLoader cloader = OOPVersion.class.getClassLoader();
		InputStream fileStream = cloader.getResourceAsStream("quiz/medicheck/input.txt");
		Scanner scanner = new Scanner(fileStream);
		while (scanner.hasNext()) {
			MedicalCheck s = new MedicalCheck(
					scanner.next(), // 학생 번호 
					scanner.next(), // 이름 
					scanner.next(), // 성별
					scanner.nextDouble(), // 키 
					scanner.nextDouble()); // 몸무게
			medicalCheckList.add(s);
		}
		scanner.close();
		return medicalCheckList;
	}
	
	
	
	/**
	 * 전체 학생의 평균 키를 구해 화면으로 출력하는 메소드
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printAverageHeight(
			List<MedicalCheck> medicalCheckList,
			String title ) {
		
		double totalH = 0;
		for (int i = 0; i < medicalCheckList.size(); i++) {
			totalH += medicalCheckList.get(i).getHeight();
		}
		
		totalH = totalH / medicalCheckList.size();
		// 화면으로 출력
		System.out.println(String.format("[%s]", title));
		System.out.println(String.format(" %.2f cm",totalH) );
		System.out.println();
	}
	
	/**
	 * 전체 학생의 평균 몸무게를 구해 화면으로 출력하는 메소드
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printAverageWeight(
			List<MedicalCheck> medicalCheckList,
			String title) {
		
		double totalW = 0;
		for (int i = 0; i < medicalCheckList.size(); i++) {
			totalW += medicalCheckList.get(i).getWeight();
		}
		totalW = totalW / medicalCheckList.size();
		// 화면으로 출력
		System.out.println(String.format("[%s]", title));
		System.out.println(String.format(" %f kg",totalW) );
		System.out.println();
	}
	
	/**
	 * 주어진 신체검사 정보 중 여학생들의 평균키를 화면으로 출력
	 * @param medicalCheckList 모든 학생들의 신체검사 정보
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printGirlsHeight(
			List<MedicalCheck> medicalCheckList, 
			String title) {
		
		List<MedicalCheck> girls = new ArrayList<MedicalCheck>();
		for (int i = 0; i < medicalCheckList.size(); i++) {
			if (medicalCheckList.get(i).getSex().equals("F")) {
				girls.add(medicalCheckList.get(i));
			}
		}
		// 화면으로 출력
		printAverageHeight(girls, title);
	}
	
	/**
	 * 주어진 신체검사 정보 중 남학생들의 평균키를 화면으로 출력
	 * @param medicalCheckList 모든 학생들의 신체검사 정보
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printBoysHeight(
			List<MedicalCheck> medicalCheckList,
			String title) {
		
		List<MedicalCheck> boys = new ArrayList<MedicalCheck>();
		for (int i = 0; i < medicalCheckList.size(); i++) {
			if (medicalCheckList.get(i).getSex().equals("M")) {
				boys.add(medicalCheckList.get(i));
			}
		}
		// 화면으로 출력
		printAverageHeight(boys, title);
	}
	
	/**
	 * 가장 키가 작은 학생을 화면에 출력
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printLowestStudent(
			List<MedicalCheck> medicalCheckList, 
			String title) {
		
		MedicalCheck lowestData = medicalCheckList.get(0);
		for (int i = 0; i < medicalCheckList.size(); i++) {
			MedicalCheck ith_student = medicalCheckList.get(i);

			if (ith_student.getHeight() < lowestData.getHeight()) {
				lowestData = ith_student;
			}
		}

		// 화면으로 출력
		System.out.println(String.format("[%s]", title));
		System.out.println(String.format(" %s %s : %.1f", 
				lowestData.getStudentID(), 
				lowestData.getName(), 
				lowestData.getHeight()));
		System.out.println();
	}
	
	/**
	 * 가장 무거운 학생을 화면에 출력
	 * @param medicalCheckList 각 학생의 신체검사 정보를 모은 리스트
	 * @param title 화면 출력시 보여질 타이틀
	 */
	private static void printBiggestStudent(
			List<MedicalCheck> medicalCheckList,
			String title) {

		MedicalCheck biggest = medicalCheckList.get(0);
		for (int i = 0; i < medicalCheckList.size(); i++) {
			MedicalCheck ith_student = medicalCheckList.get(i);
			if (ith_student.getWeight() > biggest.getWeight()) {
				biggest = medicalCheckList.get(i);
			}
		}
		// 화면으로 출력
		System.out.println(String.format("[%s]", title));
		System.out.println(String.format(" %s %s : %.1f", 
				biggest.getStudentID(), 
				biggest.getName(), 
				biggest.getWeight()));
		System.out.println();
	}
	

	
	/**
	 * 몸무게의 내림차순으로 출력을 해주는 메소드
	 * @param medicalCheckList
	 * @param title
	 */
	private static void printStudentByWeight(
			List<MedicalCheck> medicalCheckList,
			String title) {
		
		HeightComparator comp = new HeightComparator();
		Collections.sort(medicalCheckList, comp); // students.sort(comp);

		// 화면으로 출력
		System.out.printf("[%s]\n", title);
		for( int i = 0 ; i < medicalCheckList.size(); i++) {
			MedicalCheck aStudentData = medicalCheckList.get(i);
			System.out.println(String.format("%2d %4s %4s %3.1fcm",
					(i+1),
					aStudentData.getStudentID(),
					aStudentData.getName(),
					aStudentData.getHeight()));
		}
		System.out.println();
	}
	/**
	 * 키의 오름차순으로 출력을 해주는 메소드
	 * @param medicalCheckList
	 * @param title
	 */
	private static void printStudentByHeight(
			List<MedicalCheck> medicalCheckList,
			String title) {
		
		HeightComparator comp = new HeightComparator();
		Collections.sort(medicalCheckList, comp); // students.sort(comp);

		// 화면으로 출력
		System.out.printf("[%s]\n", title);
		for( int i = 0 ; i < medicalCheckList.size(); i++) {
			MedicalCheck aStudentData = medicalCheckList.get(i);
			System.out.println(String.format("%2d %4s %4s %3.1fcm",
					(i+1),
					aStudentData.getStudentID(),
					aStudentData.getName(),
					aStudentData.getHeight()));
		}
		System.out.println();
	}

}
