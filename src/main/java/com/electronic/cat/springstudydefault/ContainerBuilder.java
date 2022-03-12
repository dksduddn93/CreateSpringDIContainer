package com.electronic.cat.springstudydefault;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class ContainerBuilder {

    public void aa() throws ClassNotFoundException {
        // package 하위에 있는 class를 찾는 코드 작성
        // guava
        // [ ] ClassLoader로 package 하위의 모든 class를 런타임에서 가져오기
        // 아래 코드를 좀 더 일반적인 코드로 추상화하기 ClassLoader
        // java의 package는 directoryName
        // package . => /
        ClassLoader.getSystemClassLoader().getResourceAsStream("packageName")
                // 파일을 읽은 다음에
        // class를 찾아아됨
        Class<?> testClass = Class.forName("com.electronic.cat.springstudydefault.TestClass");

        // Test Class도생성 injected test 클래스도 생성

        try {
            // 자바 런타임에서 객체 생성
            Constructor<?> testConstructor = testClass.getConstructor();
            System.out.println("Class.forName : " + testConstructor.getName());

            Object node = testConstructor.newInstance();
            Annotation[] annotations = node.getClass().getAnnotations();
            Optional<Method> first = Arrays.stream(node.getClass().getMethods()) //생성된 오브젝트에서 메서드 가져온다음
                    .filter(method -> Arrays.stream(method.getAnnotations()).anyMatch(annotation -> annotation.annotationType() == PostConstruct.class)).findFirst();
            // first가 있으면 여기서 호출해준다.
            // 조립
            if ( first.isPresent()) {
                first.get().invoke(node);
            }

            // InjectedTestClass 탐색
            Class<?> injectedTestClass = Class.forName("com.electronic.cat.springstudydefault.InjectedTestClass");

            // 런타임 InjectedTestClass 생성자. testClass 주입
            Constructor<?> injectedTestClassConstructor = injectedTestClass.getConstructor(testClass);

            // 인스턴스 생성
            Object node2 = injectedTestClassConstructor.newInstance(node);

            Annotation[] annotations2 = node2.getClass().getAnnotations();
            Optional<Method> first2 = Arrays.stream(node2.getClass().getMethods()) //생성된 오브젝트에서 메서드 가져온다음
                    .filter(method -> Arrays.stream(method.getAnnotations()).anyMatch(annotation -> annotation.annotationType() == PostConstruct.class)).findFirst();

            if (first2.isPresent()) {
                first2.get().invoke(node2);
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
