package assignment.individualtrack.business.intefaces;

import assignment.individualtrack.domain.Player.LoginRequest;
import assignment.individualtrack.domain.Player.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest request);
}
