package tda.darkarmy.daycare.dto;


public class UserDto {
    private String name;
    private String email;
    private String contactNumber;
    private String password;
    private String role;
    private String address;
    private Boolean isVerified = false;

    public UserDto() {
    }

    public UserDto(String name, String email, String contactNumber, String password, String role, String address) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.role = role;
        this.address = address;
    }

    public UserDto(String name, String email, String contactNumber, String password, String role, String address, Boolean isVerified) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.role = role;
        this.address = address;
        this.isVerified = isVerified;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
