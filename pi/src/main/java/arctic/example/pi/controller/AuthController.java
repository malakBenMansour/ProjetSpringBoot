package arctic.example.pi.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import arctic.example.pi.entity.Organisation;
import arctic.example.pi.entity.Role;
import arctic.example.pi.entity.User;
import arctic.example.pi.jwt.JwtUtils;
import arctic.example.pi.payload.request.LoginRequest;
import arctic.example.pi.payload.request.SignupRequest;
import arctic.example.pi.payload.response.JwtResponse;
import arctic.example.pi.payload.response.MessageResponse;
import arctic.example.pi.repository.OrganisationRepository;
import arctic.example.pi.repository.RoleRepository;
import arctic.example.pi.repository.UserRepository;
import arctic.example.pi.security.UserDetailsImpl;
import arctic.example.pi.service.UserService;
import arctic.example.pi.userFunction.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import arctic.example.pi.entity.ERole;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api-auth")
@Slf4j
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserService userService;
	@Autowired
	OrganisationRepository organisationRepository;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getUser().getId(), userDetails.getUsername(),
						userDetails.getUser().getEmail(), roles
						,userDetails.getUser().isStateuser(),userDetails.getUser().getNom(),
						userDetails.getUser().getPrenom(),
						userDetails.getUser().getTel()));
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public AccountResponse registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getAddress(), signUpRequest.getTel(),
				signUpRequest.getNom(), signUpRequest.getPrenom(),signUpRequest.getBirth());


		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.CLIENT);
			roles.add(userRole);

		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ADMIN);
					roles.add(adminRole);

					break;
				case "agent":
					Role vendRole = roleRepository.findByName(ERole.AGENT);
					roles.add(vendRole);
					break;
				case "client":
					Role achetRole = roleRepository.findByName(ERole.CLIENT);
					roles.add(achetRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.CLIENT);
					roles.add(userRole);
				}
			});
		}

		AccountResponse accountResponse = new AccountResponse();
		boolean result = userRepository.existsByEmail(signUpRequest.getEmail());
		if (result) {
			accountResponse.setResult(0);

		} else {
			if (strRoles.contains("agent")) {
				Organisation organisation = organisationRepository.findById(signUpRequest.getOrganisationId())
						.orElseThrow(() -> new RuntimeException("Organisation not found with id: " + signUpRequest.getOrganisationId()));
				user.setOrganisation(organisation);
			}
			user.setStateuser(true);
			user.setRoles(roles);
			userRepository.save(user);
			accountResponse.setResult(1);

		}
		log.info("" + ResponseEntity.ok(new MessageResponse("User registered successfully!")));
		return accountResponse;
	}
}
