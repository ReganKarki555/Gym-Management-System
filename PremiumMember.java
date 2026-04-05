public class PremiumMember extends GymMember {
    private final double premiumCharge = 50000.0;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

    public PremiumMember(int id, String name, String location, String phone, String email, String gender, String DOB, 
                        String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.personalTrainer = personalTrainer;
        this.isFullPayment = false;
        this.paidAmount = 0.0;
        this.discountAmount = 0.0;
    }

    // Accessor methods
    public double getPremiumCharge() { return premiumCharge; }
    public String getPersonalTrainer() { return personalTrainer; }
    public boolean getIsFullPayment() { return isFullPayment; }
    public double getPaidAmount() { return paidAmount; }
    public double getDiscountAmount() { return discountAmount; }

    @Override
    public void markAttendance() {
        if (activestatus) {
            attendance++;
            loyaltyPoints += 5.0;
        }
    }

    public String payDueAmount(double amount) {
        if (isFullPayment) {
            return "Payment is already full.";
        }
        paidAmount += amount;
        if (paidAmount > premiumCharge) {
            double excess = paidAmount - premiumCharge;
            paidAmount = premiumCharge;
            return "Payment exceeds premium charge. Excess amount: " + excess;
        }
        isFullPayment = (paidAmount == premiumCharge);
        double remainingAmount = premiumCharge - paidAmount;
        return "Payment successful. Remaining amount: " + remainingAmount;
    }

    public String calculateDiscount() {
        if (isFullPayment) {
            discountAmount = premiumCharge * 0.10;
            return "Discount calculated: " + discountAmount;
        } else {
            discountAmount = 0.0;
            return "No discount applicable. Payment is not full.";
        }
    }

    public void revertPremiumMember() {
        super.resetMember();
        personalTrainer = "";
        isFullPayment = false;
        paidAmount = 0.0;
        discountAmount = 0.0;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: " + paidAmount);
        System.out.println("Is Full Payment: " + isFullPayment);
        System.out.println("Remaining Amount: " + (premiumCharge - paidAmount));
        if (isFullPayment) {
            System.out.println("Discount Amount: " + discountAmount);
        }
    }
}