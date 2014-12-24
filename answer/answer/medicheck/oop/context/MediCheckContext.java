package answer.medicheck.oop.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import quiz.medicheck.MedicalCheck;

import answer.medicheck.oop.filter.Filter;
import answer.medicheck.oop.map.Mapper;

/**
 * config.txt 파일에 정의된 filter, mapper, sorter 클래스 이름(full qualified name)을 이용해서
 * 인스턴스를 생성하는 클래스입니다.
 * 
 * 아래 코드에서는 java의 reflection 을 사용하고 있습니다.
 * 
 * http://stackoverflow.com/questions/37628/what-is-reflection-and-why-is-it-useful
 * http://stackoverflow.com/questions/13411426/java-how-to-call-method-by-reflection-with-primitive-types-as-arguments
 * http://www.mkyong.com/java/how-to-use-reflection-to-call-java-method-at-runtime/
 * http://kmongcom.wordpress.com/2014/03/15/%EC%9E%90%EB%B0%94-%EB%A6%AC%ED%94%8C%EB%A0%89%EC%85%98%EC%97%90-%EB%8C%80%ED%95%9C-%EC%98%A4%ED%95%B4%EC%99%80-%EC%A7%84%EC%8B%A4/
 * 
 * 
 * 
 * @author chminseo
 *
 */
public class MediCheckContext {
	
	private HashMap<String, Object> moduleMap = new HashMap<>();

	public MediCheckContext(String moduleConfigPath) {
		initModules(moduleConfigPath);
	}

	void initModules(String moduleConfigPath) {
		Scanner scanner = new Scanner(this.getClass().getClassLoader().getResourceAsStream(moduleConfigPath));
		/*
		 * Perl에서 사용되는 정규 표현식
		 * http://buedt.tistory.com/23
		 * 
		 * "[=\\n]" - 문자 '=' 와 줄바꿈 문자(\n)를 delimeter로 사용함.
		 */
		scanner.useDelimiter(Pattern.compile("[=\\n]"));
		while ( scanner.hasNext() ) {
			String key = scanner.next().trim().toLowerCase();
			if ( "".equals(key)) {
				// empty line, just skip.
				continue;
			}
			String fqName = scanner.next().trim();
			int leftParenPos = fqName.indexOf('(');
			if ( leftParenPos > 0 ) {
				/*
				 * TODO 내림차순 comparator 구현의 true false 부분만 반영합니다. 
				 */
				int rightParenPos = fqName.indexOf(')',leftParenPos);
				String bool = fqName.substring(leftParenPos+1, rightParenPos);
				register(this.getClass().getClassLoader(), 
						key, 
						fqName.substring(0, leftParenPos), /* 파라미터 (..) 앞까지만 잘라냄 */
						bool);
			} else {				
				register(this.getClass().getClassLoader(), key, fqName) ;
			}
			
		}
		scanner.close();
	}
	
	private void register ( ClassLoader cLoader, String key, String fqName, String bool) {
		try {
			Class<?> cls = cLoader.loadClass(fqName);
			Constructor<?> [] cs = cls.getConstructors();
			Constructor<?> constructor = cls.getConstructor(Boolean.TYPE);
			Object instance = constructor.newInstance(Boolean.valueOf(bool));
			moduleMap.put(key, instance);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	private void register ( ClassLoader cLoader, String key, String fqName) {
		if ( cLoader == null) {
			cLoader = this.getClass().getClassLoader();
		}
		try {
			Class<?> cls = cLoader.loadClass(fqName);
			Object instance = cls.newInstance();
			moduleMap.put(key, instance);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public Filter<MedicalCheck> getFilter ( String filterName) {
		@SuppressWarnings("unchecked")
		Filter<MedicalCheck> filter = (Filter<MedicalCheck>) 
				moduleMap.get("filter." + filterName);
		if ( filter == null ){
			// 예외를 던질까?
		}
		return filter;
	}
	
	public <T> Mapper<T, MedicalCheck> getMapper ( String mapperName) {
		@SuppressWarnings("unchecked")
		Mapper<T, MedicalCheck> mapper = (Mapper<T, MedicalCheck>) 
				moduleMap.get("mapper." + mapperName.toLowerCase());
		if ( mapper == null) {
			// 예외를 던질까?
		}
		return mapper;
	}
	
	public Comparator<MedicalCheck> getComparator( String compName) {
		@SuppressWarnings("unchecked")
		Comparator<MedicalCheck> comparator = (Comparator<MedicalCheck>)
				moduleMap.get("sorter."+compName.toLowerCase());
		if ( comparator == null) {
			// 예외를 던질까?
		}
		return comparator;
	}
	
	
}
