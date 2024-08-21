package demo.test.jeann.exercise.utilities.jwt;

import demo.test.jeann.exercise.data.GlobalLogicRepository;
import demo.test.jeann.exercise.data.dtos.User;
import demo.test.jeann.exercise.utilities.jwt.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  private GlobalLogicRepository users;

  public CustomUserDetailsService(GlobalLogicRepository users) {
    this.users = users;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    CustomUserDetails customUserDetails = null;

    final User user = users.findByEmail(username);
    if (user != null) {
      customUserDetails =
          new CustomUserDetails(user.getEmail(), user.getPassword(), user.getRoles());
    }

    return customUserDetails;
  }
}
