package es.uji.ei1027.toopots.model;

public class DescuentoManager {
	
	private static DescuentoManager dm;
	
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
	
	public static DescuentoManager getDescuentoManager(){
		if (dm == null){
			dm = new DescuentoManager();
			settingDescuentos();
		}
		return dm;
	}
	
	private static void settingDescuentos(){
		Descuento d = new Descuento();
		d.setDiscountValue(0.5f);
		d.setField("Sexo");
		d.setValue("M");
		dm.setDescuento(d);
	}

}
