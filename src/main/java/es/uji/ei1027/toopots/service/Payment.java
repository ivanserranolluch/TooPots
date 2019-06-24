package es.uji.ei1027.toopots.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Payment implements PaymentService {

    @Override
    public Integer getConfirmacion() {
        simulacion();
        Random random = new Random(848995);
        return random.nextInt(100000);
    }

    private void simulacion(){
        System.out.println(" ");
        System.out.print("Procesando...");
        for (int i=0; i<5; i++){
            System.out.print(".");

            try {
                TimeUnit.SECONDS.sleep((long)1);
            } catch (InterruptedException e) {

            }
        }
        System.out.println(" ");
    }
}
