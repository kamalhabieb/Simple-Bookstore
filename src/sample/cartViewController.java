package sample;

public class cartViewController {

    private RegisteredCustomer regCustomer;

    public void initController(RegisteredCustomer registeredCustomer) {
        this.regCustomer = registeredCustomer;
    }

    public void checkourButtonPressed(){
        boolean valid = regCustomer.checkOutCart();
        System.out.println(valid);
        if (!valid){
            //todo show error
        }
    }


}
