package answer.medicheck.no_oop;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import quiz.medicheck.MedicalCheck;


/**
 * OOP를 적용하지 않은 구현입니다.
 * 
 * 이 구현은 메소드 곳곳에 중복된 코드가 많이 등장하며 유연하지 않습니다.
 * 
 * 아래의 코드를 참고해서 다음의 요구사항을 추가로 구현해보세요.
 * 
 * 1. 가장 가벼운 학생을 출력
 * 2. 두번째로 키가 큰 학생은 누구인가?
 * 3. 요청 결과를 report.txt 라는 파일로 저장하고 싶다면?
 * 
 * 
 * OOP를 적용하면 코드를 간결하면서도 유연하게 수정할 수 있습니다.
 * 
 *
 */
public class NoOOPVersion {

	public static void main(String[] args) throws FileNotFoundException {
		
		List<MedicalCheck> medicalCheckList = loadMedicalData();
		
		// 전체 학생의 평균키와 평균몸무게
		printAverageHeight(medicalCheckList, "전체학생 평균 키");
		printAverageWeight(medicalCheckList, "전체학생 평균 몸무게");
		
		// 성별 평균키 출력
		printBoysHeight(medicalCheckList, "남학생들 평균 키");
		printGirlsHeight(medicalCheckList, "여학생들 평균 키");
		
		// 키가 가장 작은 학생, 가장 무거운 학생
		printLowestStudent(medicalCheckList, "가장 키가 작은 학생");		
		printBiggestStudent(medicalCheckList, "가장 무거운 학생");

		// 정렬 - 키의 오름차순으로 출력하기 .... < ... < ....
		printStudentByHeight(medicalCheckList, "키순서(내림차순)");

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
	
	/**
	 * b/medicheck/input.txt 를 읽어서 학생들의 신체검사 데이터를 List로 반환하는 메소드
	 * @return 신체검사 정보가 담긴 List
	 * @throws FileNotFoundException
	 */
	private static List<MedicalCheck> loadMedicalData() throws FileNotFoundException {

		ArrayList<MedicalCheck> medicalCheckList = new ArrayList<MedicalCheck>();
		
		ClassLoader cloader = NoOOPVersion.class.getClassLoader();
		InputStream fileStream = cloader.getResourceAsStream("b/medicheck/input.txt");
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
	
	private static void printStudentByWeight(
			List<MedicalCheck> medicalCheckList,
			String title) {

		Comparator<MedicalCheck> c = new WeightComparator();
		Collections.sort(medicalCheckList, c);

		// 화면으로 출력
		System.out.printf("[%s]\n", title);
		for (int i = 0; i < medicalCheckList.size(); i++) {
			MedicalCheck aStudentData = medicalCheckList.get(i);
			System.out.println(String.format("%2d %4s %4s %3.1fcm", (i + 1),
					aStudentData.getStudentID(), aStudentData.getName(),
					aStudentData.getWeight()));
		}
		System.out.println();
	}
	
	
	/**
	 * 몸무게를 비교하는 comparator
	 *
	 */
	static class WeightComparator implements Comparator<MedicalCheck> {

		public int compare(MedicalCheck o1, MedicalCheck o2) {
			
			double Weight1 = o1.getWeight();
			double Weight2 = o2.getWeight();
			if (Weight1 > Weight2) {
				return 1;
			} else if (Weight2 > Weight1) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * 키를 비교하는 comparator
	 *
	 */
	static class HeightComparator implements Comparator<MedicalCheck> {

		public int compare(MedicalCheck s1, MedicalCheck s2) {
			
			double h1 = s1.getHeight();
			double h2 = s2.getHeight();

			if (h1 > h2) {
				return -1;
			} else if (h2 > h1) {
				return 1;
			} else {
				return 0;
			}
		}
	}


}