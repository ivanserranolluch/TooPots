package es.uji.ei1027.toopots.model;

public class DescuentoManager {
	
	private Descuento discount;
	
	public float getPriceWithDiscount(Cliente cliente, float price){
		if (cliente != null && discount != null){
			price = discount.getPriceWithDiscount(cliente, price);
		}
		return price;
	}
	
	public void setDescuento(Descuento discount){
		if (this.discount != null){
			this.discount.setAnotherDiscount(discount);
		} else {
			this.discount = discount;
		}
	}

}
