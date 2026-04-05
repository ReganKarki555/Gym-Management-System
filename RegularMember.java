public class RegularMember extends GymMember {
    private final int attendanceLimit = 30;
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;

    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, 
                        String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.referralSource = referralSource;
        this.isEligibleForUpgrade = false;
        this.removalReason = "";
        this.plan = "basic";
        this.price = 6500.0;
    }

    // Accessor methods
    public int getAttendanceLimit() { return attendanceLimit; }
    public boolean getIsEligibleForUpgrade() { return isEligibleForUpgrade; }
    public String getRemovalReason() { return removalReason; }
    public String getReferralSource() { return referralSource; }
    public String getPlan() { return plan; }
    public double getPrice() { return price; }

    @Override
    public void markAttendance() {
        if (activestatus) {
            attendance++;
            loyaltyPoints += 5.0;
            if (attendance >= attendanceLimit) {
                isEligibleForUpgrade = true;
            }
        }
    }

    public double getPlanPrice(String plan) {
        switch (plan.toLowerCase()) {
            case "basic":
                return 6500.0;
            case "standard":
                return 12500.0;
            case "deluxe":
                return 18500.0;
            default:
                return -1.0;
        }
    }

    public String upgradePlan(String newPlan) {
        if (!isEligibleForUpgrade) {
            return "Member is not eligible for upgrade.";
        }
        if (newPlan.equalsIgnoreCase(plan)) {
            return "Member is already subscribed to " + newPlan + " plan.";
        }
        double newPrice = getPlanPrice(newPlan);
        if (newPrice == -1.0) {
            return "Invalid plan selected.";
        }
        plan = newPlan;
        price = newPrice;
        return "Plan upgraded to " + newPlan + " successfully.";
    }

    public void revertRegularMember(String reason) {
        super.resetMember();
        isEligibleForUpgrade = false;
        plan = "basic";
        price = 6500.0;
        removalReason = reason;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Plan: " + plan);
        System.out.println("Price: " + price);
        if (!removalReason.isEmpty()) {
            System.out.println("Removal Reason: " + removalReason);
        }
    }
}