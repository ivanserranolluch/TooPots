package es.uji.ei1027.toopots.model;

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
		return true;
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
	
	

}
