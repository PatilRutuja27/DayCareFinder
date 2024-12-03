package tda.darkarmy.daycare.dto;

public class VerifyOtpDto {
    private Long otp;
    private String email;

    public VerifyOtpDto() {
    }

    public VerifyOtpDto(Long otp, String email) {
        this.otp = otp;
        this.email = email;
    }

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "VerifyOtp{" +
                "otp=" + otp +
                ", email='" + email + '\'' +
                '}';
    }
}
