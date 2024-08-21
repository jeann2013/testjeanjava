package demo.test.jeann.exercise.usecases.dosignin;

import demo.test.jeann.exercise.data.GlobalLogicRepository;
import demo.test.jeann.exercise.data.dtos.User;
import demo.test.jeann.exercise.usecases.dosignin.models.DoSignInRequest;
import demo.test.jeann.exercise.usecases.dosignin.models.DoSignInResponse;
import demo.test.jeann.exercise.usecases.dosignin.models.Phone;
import demo.test.jeann.exercise.utilities.jwt.JwtTokenProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static demo.test.jeann.exercise.utilities.AppConstant.ROL_ADMIN;
import static demo.test.jeann.exercise.utilities.AppConstant.ROL_USER;
import static demo.test.jeann.exercise.utilities.formats.Date.dateNow;

@Service
public class DoSignInUseCase {

  // region fields
  private final Logger logsDoSignUpUseCase;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final GlobalLogicRepository globalLogicRepository;
  private final PasswordEncoder passwordEncoder;
  // endregion

  @Autowired
  public DoSignInUseCase(
      final AuthenticationManager authenticationManager,
      final JwtTokenProvider jwtTokenProvider,
      final GlobalLogicRepository globalLogicRepository,
      final PasswordEncoder passwordEncoder) {
    this.logsDoSignUpUseCase = LogManager.getLogger(DoSignInUseCase.class);
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.globalLogicRepository = globalLogicRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public DoSignInResponse doSignIn(final DoSignInRequest doSignInRequest) {
    // EncryptPassword
    final DoSignInRequest encryptDoSignInRequest =
        new DoSignInRequest(
            doSignInRequest.getName(),
            doSignInRequest.getEmail(),
            passwordEncoder.encode(doSignInRequest.getPassword()),
            doSignInRequest.getPhones());
    // Do Log Class
    logsDoSignUpUseCase.info(
        "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Message: {}", encryptDoSignInRequest);
    // Do find email user
    User user = globalLogicRepository.findByEmail(doSignInRequest.getEmail());
    // Do create token user
    final List<String> listRoles = new ArrayList<>();
    listRoles.add(ROL_ADMIN);
    listRoles.add(ROL_USER);
    //
    final String token = jwtTokenProvider.createToken(doSignInRequest.getEmail(), listRoles);
    //
    if (user == null) {
      // Do Register User
      user =
          globalLogicRepository.save(
              new User(
                  doSignInRequest.getName(),
                  doSignInRequest.getEmail(),
                  encryptDoSignInRequest.getPassword(),
                  doSignInRequest.getPhones(),
                  dateNow(),
                  dateNow(),
                  dateNow(),
                  token,
                  listRoles,
                  true));
    }
    //
    if (user.getToken() != null) {
      // Do Log Action
      logsDoSignUpUseCase.info(
          "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: Create User, Message: {}",
          user);
      // Do authenticate user
      final Authentication doAuthentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  user.getEmail(), doSignInRequest.getPassword()));
      // Do log authenticate user
      logsDoSignUpUseCase.info(
          "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: user authenticate, Message: {}",
          doAuthentication.getAuthorities());
      // Do log create token user
      logsDoSignUpUseCase.info(
          "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: create token user");
      // Do create dateNow
      final String dateNow = dateNow();
      // Do update lastLogin data to user
      final int updateUser =
          globalLogicRepository.updateUser(user.getEmail(), token, dateNow, dateNow);
      // Do log update user
      logsDoSignUpUseCase.info(
          "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: update user, Message: {}",
          updateUser);
    }
    // Do create response sign in user
    final DoSignInResponse doSignInResponse =
        new DoSignInResponse(
            user.getId(),
            user.getCreated(),
            user.getModified(),
            user.getLastLogin(),
            user.getToken(),
            user.isActive());
    // Do log response sign in user
    logsDoSignUpUseCase.info(
        "Here I Am: Class:DoSignInUseCase, Method: doSignIn, Action: create response, Message: {}",
        doSignInResponse);
    // create token persistence
    // return response
    return doSignInResponse;
  }
}
