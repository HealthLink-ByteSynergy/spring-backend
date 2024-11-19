package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CustomEntity;
import com.example.demo.entity.DoctorEntity;
import com.example.demo.entity.DoctorModelEntity;
import com.example.demo.entity.PatientEntity;
import com.example.demo.entity.Role;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.InvalidFormatException;
import com.example.demo.exception.UserDuplicateEmailException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserWrongPasswordException;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final DoctorService doctorService;
    private final PatientRepository patientRepository;
  
    @Override
    public String signup(UserEntity user) throws UserDuplicateEmailException, InvalidFormatException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        String userId = UUIDService.getUUID();
        Role role=user.getRole();
        UserEntity userEntity=new UserEntity();
        if(role!=null){
            userEntity=UserEntity
                .builder()
                .id(userId)
                .username(user.getUser())
                .email(user.getEmail())
                .password(encodedPassword)
                .role(role)
                .build();
        }
        else {
            userEntity=UserEntity
                .builder()
                .id(userId)
                .username(user.getUser())
                .email(user.getEmail())
                .password(encodedPassword)
                .role(Role.USER)
                .build();
        }

        try{
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicateEmailException("Email already exists");
        }
        return jwtService.generateToken(userEntity);
    }

    @Override
    public String login(UserEntity user) throws UserNotFoundException, UserWrongPasswordException {
        System.out.println("Authenticating user: " + user.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );
        }catch(AuthenticationException e) {
            throw new UserWrongPasswordException("Wrong password");
        }

        System.out.println("Authentication successful: " + user.getEmail());
        UserEntity userEntity = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
        return jwtService.generateToken(userEntity);
    }

    @Override
    public UserEntity getUserDetails(String token) throws UserNotFoundException {
        String userEmailFromCookie = jwtService.extractUserEmail(token);
        Optional<UserEntity> user=userRepository.findByEmail(userEmailFromCookie);
        if(user.isPresent()){
            return user.get();
        }
        else throw new UserNotFoundException("User doesn't Exist");
    }

    @Override
    public String doctorSignup(CustomEntity customuser) throws UserDuplicateEmailException, InvalidFormatException {
        UserEntity user=customuser.getUserEntity();
        DoctorModelEntity doctor=customuser.getDoctorEntity();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        String userId = UUIDService.getUUID();
        UserEntity userEntity=new UserEntity();
        
        userEntity=UserEntity
            .builder()
            .id(userId)
            .username(user.getUser())
            .email(user.getEmail())
            .password(encodedPassword)
            .role(Role.DOCTOR)
            .build();

        try{
            UserEntity newUser=userRepository.save(userEntity);

            List<String> specializations=doctor.getSpecialization();
            String spec="";
            for(int i=0;i<specializations.size();i++){
                spec+=specializations.get(i)+",";
            }

            PatientEntity patientEntity=new PatientEntity();
            patientEntity.setPatientId(UUIDService.getUUID());
            patientEntity.setUserEntity(newUser);

            patientRepository.save(patientEntity);

            DoctorEntity newDoctor=new DoctorEntity();
            newDoctor.setDoctorId(UUIDService.getUUID());
            newDoctor.setIsAvailable(doctor.getIsAvailable());
            newDoctor.setLicenseNumber(doctor.getLicenseNumber());
            newDoctor.setPhoneNumber(doctor.getPhoneNumber());
            newDoctor.setUserEntity(newUser);    
            newDoctor.setSpecialization(spec);
            newDoctor.setPatientEntity(patientEntity);
                
            doctorService.saveDoctorDetails(newDoctor);

        } catch (DataIntegrityViolationException e) {
            throw new UserDuplicateEmailException("Email already exists");
        }
        return jwtService.generateToken(userEntity);
    }
}
