package org.mapua;

public class MandatoryTaxContribution {
    // Encapsulation: Private static final fields for tax brackets
    private static final long NO_WITHHOLDING_TAX = 20832;
    private static final long FIRST_STAGE_WITHHOLDING_TAX = 33333;
    private static final long SECOND_STAGE_WITHHOLDING_TAX = 66667;
    private static final long THIRD_STAGE_WITHHOLDING_TAX = 166667;
    private static final long FOURTH_STAGE_WITHHOLDING_TAX = 666667;

    // Encapsulation: Private static final fields for SSS contributions
    private static final long[] SSS_CONTRIBUTIONS = {
            3250, 3750, 4250, 4750, 5250, 5750, 6250, 6750, 7250, 7750,
            8250, 8750, 9250, 9750, 10250, 10750, 11250, 11750, 12250,
            12750, 13250, 13750, 14250, 14750, 15250, 15750, 16250, 16750,
            17250, 17750, 18250, 18750, 19250, 19750, 20250, 20750, 21250,
            21750, 22250, 22750, 23250, 23750, 24250, 24750
    };

    // Abstraction: Public method to compute withholding tax
    public static double computeWithHoldingTax(double salary) {
        if (salary <= NO_WITHHOLDING_TAX) return 0;
        else if (salary <= FIRST_STAGE_WITHHOLDING_TAX)
            return (salary - NO_WITHHOLDING_TAX) * 0.20;
        else if (salary <= SECOND_STAGE_WITHHOLDING_TAX)
            return (salary - FIRST_STAGE_WITHHOLDING_TAX) * 0.25 + 2500;
        else if (salary <= THIRD_STAGE_WITHHOLDING_TAX)
            return (salary - SECOND_STAGE_WITHHOLDING_TAX) * 0.30 + 10833;
        else if (salary <= FOURTH_STAGE_WITHHOLDING_TAX)
            return (salary - THIRD_STAGE_WITHHOLDING_TAX) * 0.32 + 40833;
        else return (salary - FOURTH_STAGE_WITHHOLDING_TAX) * 0.35 + 200833;
    }

    // Abstraction: Public method to get SSS contribution based on salary
    public static double getSSSContributionBySalary(double salary) {
        for (int i = 0; i < SSS_CONTRIBUTIONS.length; i++) {
            if (salary < SSS_CONTRIBUTIONS[i]) {
                return 135.00 + (i * 22.50); // SSS contribution formula
            }
        }
        return 1125.00; // Default maximum contribution
    }

    // Abstraction: Public method to compute Pag-IBIG contribution
    public static double getPagibigContributionBySalary(double salary) {
        if (salary < 1000) return 0.00;
        else if (salary <= 1500) return salary * 0.01;
        else return Math.min(salary * 0.02, 100.00); // Cap at PHP 100
    }

    // Abstraction: Public method to compute PhilHealth contribution
    public static double getPhilHealthContributionBySalary(double salary) {
        if (salary <= 10000) return Math.min(salary * 0.03, 300.00);
        else if (salary <= 59999.99) return Math.min(salary * 0.03, 1800.00);
        else return 1800.00; // Maximum contribution
    }
}