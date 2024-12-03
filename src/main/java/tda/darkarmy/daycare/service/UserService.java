package tda.darkarmy.daycare.service;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tda.darkarmy.daycare.config.JwtTokenProvider;
import tda.darkarmy.daycare.dto.LoginRequest;
import tda.darkarmy.daycare.dto.LoginResponse;
import tda.darkarmy.daycare.dto.PasswordResetRequest;
import tda.darkarmy.daycare.dto.UserDto;
import tda.darkarmy.daycare.exception.ResourceNotFoundException;
import tda.darkarmy.daycare.exception.UserAlreadyExistsException;
import tda.darkarmy.daycare.model.User;
import tda.darkarmy.daycare.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MailSenderService mailSenderService;

    public User signup(UserDto userDto) throws MessagingException {

        User user = userRepository.findByEmail(userDto.getEmail());
        if(user!=null && user.getVerified()) throw new UserAlreadyExistsException("User with given email already exists");

        if(user!=null) userRepository.deleteById(user.getId());

        user = new User();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setContactNumber(userDto.getContactNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAddress(userDto.getAddress());
        user.setVerified(userDto.getVerified());

        Long otp = new Random().nextLong(9999 - 1000 + 1) + 1000;
        user.setOtp(otp);

        mailSenderService.send(user, otp.toString());
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest){
        User user =  userRepository.findByEmail(loginRequest.getEmail());

        if(user==null) throw new ResourceNotFoundException("User with given email not found.");
        if(!user.getVerified()) throw new ResourceNotFoundException("User is not verified yet.");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new LoginResponse(token, user);
    }

    public String verifyOtp(String email, Long otp){
        User user =  userRepository.findByEmail(email);
        System.out.println(user.getOtp()+" "+otp);
        if(user.getOtp().longValue()==otp.longValue()){
            user.setVerified(true);
            userRepository.save(user);
            return "Otp verified successfully";
        }else{
            return "Invalid otp";
        }
    }

    public User updateUser(UserDto userDto, Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setContactNumber(userDto.getContactNumber());
        user.setRole(userDto.getRole());
        user.setAddress(userDto.getAddress());
        return userRepository.save(user);
    }

    public User changePassword(PasswordResetRequest resetRequest) throws MessagingException {

        User user = userRepository.findByEmail(resetRequest.getEmail());
        if(user == null){
            throw new ResourceNotFoundException("No user found with given email.");
        }
        Long otp = new Random().nextLong(9999 - 1000 + 1) + 1000;
        user.setOtp(otp);

        mailSenderService.send(user, otp.toString());
        user.setNewPassword(passwordEncoder.encode(resetRequest.getPassword()));
        return userRepository.save(user);
    }


    public User getLoggedInUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("User Details:  "+userDetails.getUsername());
        return userRepository.findByEmail(userDetails.getUsername());
    }

    public Optional<User> getUser(Long userId){
        return userRepository.findById(userId);
    }

    public String deleteUser(){
        User user = getLoggedInUser();
        userRepository.deleteById(user.getId());

        return "User deleted successfully";
    }

    public String deleteById(Long id) {
        if(getUser(id).isEmpty()) throw new ResourceNotFoundException("User not found");
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "All users deleted successfully";
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void createUser(UserDto userDto){

        User user = userRepository.findByEmail(userDto.getEmail());
        if(user==null) {
            user = new User();

            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setRole(userDto.getRole());
            user.setContactNumber(userDto.getContactNumber());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setAddress(userDto.getAddress());
            user.setVerified(userDto.getVerified());
            userRepository.save(user);
        }

    }

    public String verifyOtpPassword(String email, Long otp) {
        User user =  userRepository.findByEmail(email);
        System.out.println(user.getOtp()+" "+otp);
        if(user.getOtp().longValue()==otp.longValue()){
            user.setPassword(user.getNewPassword());
            userRepository.save(user);
            return "Otp verified successfully";
        }else{
            return "Invalid otp";
        }
    }
}
