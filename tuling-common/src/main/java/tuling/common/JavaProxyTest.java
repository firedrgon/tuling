package tuling.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaProxyTest {
	public static void main(String[] args) {
		UserServiceImp userService = new UserServiceImp();
		UserService proxy = (UserService) Proxy.newProxyInstance(JavaProxyTest.class.getClassLoader(), 
				new Class[] {UserService.class} , new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("前置通知");
				Object result = null;
				try {
					result = method.invoke(userService, args);
				} catch (Exception e) {
					System.out.println("异常通知");
				}
				System.out.println("后置通知");
				return result;
			}
		});
		
		proxy.getUser();
	}
}
