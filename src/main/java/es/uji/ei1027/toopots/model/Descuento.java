package es.uji.ei1027.toopots.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Descuento {
	
	private String field;
	private String value;
	private float discountValue;
	private Descuento anotherDiscount;
	
	public float getPriceWithDiscount(Cliente cliente, float price){
		if (isValidDiscount(cliente)){
			price *= discountValue;
		}
		if (anotherDiscount != null){
			price = anotherDiscount.getPriceWithDiscount(cliente, price);
		}
		return price;
	}
	
	private boolean isValidDiscount(Cliente cliente){
		Method method = getMethod(cliente, field);
		String clientValue = invokeMethod(cliente, method);
		return value.equals(clientValue);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public float getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(float discountValue) {
		this.discountValue = discountValue;
	}

	public Descuento getAnotherDiscount() {
		return anotherDiscount;
	}

	public void setAnotherDiscount(Descuento anotherDiscount) {
		this.anotherDiscount = anotherDiscount;
	}
	
	
	private Method getMethod(Cliente cliente, String methodName){
		Method method = null;
		methodName = "get" + methodName;
		try {
		  method = cliente.getClass().getMethod(methodName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return method;
	}
	
	private String invokeMethod(Cliente cliente, Method method){
		String s = null;
		try {
			  s = method.invoke(cliente).toString();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) { 
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return s;
	}
	

}
