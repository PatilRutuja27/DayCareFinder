package tda.darkarmy.daycare.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        @Column(name = "email", unique = true)
        private String email;
        private String contactNumber;
        private String password;
        private String newPassword;
        private String role;
        private String address;
        private Boolean isVerified;
        private Long otp;

        public User() {
        }

        public User(Long id, String name, String email, String contactNumber, String password, String role, String address) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.contactNumber = contactNumber;
                this.password = password;
                this.role = role;
                this.address = address;
        }

        public String getNewPassword() {
                return newPassword;
        }

        public void setNewPassword(String newPassword) {
                this.newPassword = newPassword;
        }

        public Boolean getVerified() {
                return isVerified;
        }

        public void setVerified(Boolean verified) {
                isVerified = verified;
        }

        public Long getOtp() {
                return otp;
        }

        public void setOtp(Long otp) {
                this.otp = otp;
        }



        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
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
                return "User{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", email='" + email + '\'' +
                        ", contactNumber='" + contactNumber + '\'' +
                        ", password='" + password + '\'' +
                        ", role='" + role + '\'' +
                        ", address='" + address + '\'' +
                        ", isVerified=" + isVerified +
                        ", otp=" + otp +
                        '}';
        }
}

