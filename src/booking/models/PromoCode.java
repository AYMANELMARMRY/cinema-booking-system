package booking.models;

/**
 *
 * @author Mohamed Ayman
 */
public class PromoCode {
    
    private int id;
    private String code;
    private int discount;

    /**
     * Default constructor
     */
    public PromoCode() {
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public int getDiscount() {
        return discount;
    }

    /**
     *
     * @param discount
     */
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    

}
