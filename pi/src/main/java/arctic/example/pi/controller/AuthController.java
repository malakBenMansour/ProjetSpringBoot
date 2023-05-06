package arctic.example.pi.controller;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;

import arctic.example.pi.entity.*;
import arctic.example.pi.jwt.JwtUtils;
import arctic.example.pi.payload.request.LoginRequest;
import arctic.example.pi.payload.request.SignupRequest;
import arctic.example.pi.payload.response.*;
import arctic.example.pi.repository.ConfirmationTokenRepository;
import arctic.example.pi.repository.OrganisationRepository;
import arctic.example.pi.repository.RoleRepository;
import arctic.example.pi.repository.UserRepository;
import arctic.example.pi.security.UserDetailsImpl;
import arctic.example.pi.service.UserService;
import arctic.example.pi.userFunction.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;


import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
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
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	@Value("${google.id}")
	private String idClient;

	@Value("${secretPsw}")
	String secretPsw;
	private final JavaMailSender javaMailSender;


	int n = 5;

String email;

	public AuthController(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
//login
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		if (userDetails.getUser().isStateuser() == true) {
			return ResponseEntity.ok(
					new JwtResponse(jwt, userDetails.getUser().getId(), userDetails.getUsername(),
							userDetails.getUser().getEmail(), roles
							, userDetails.getUser().isStateuser(), userDetails.getUser().getName(),
							userDetails.getUser().getPrenom(),
							userDetails.getUser().getTel()));
		} else {
return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public AccountResponse registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getAddress(), signUpRequest.getTel(),
				signUpRequest.getName(), signUpRequest.getPrenom(),signUpRequest.getBirth());


		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		/*if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.CLIENT);
			roles.add(userRole);

		} else {*/
			strRoles.forEach(role -> {
				switch (role) {
				case "ROLE_ADMIN":
					Role adminRole = roleRepository.findByName(ERole.ADMIN);
					roles.add(adminRole);

					break;
				case "agent":
					Role vendRole = roleRepository.findByName(ERole.AGENT);
					roles.add(vendRole);
					/*Organisation organisation = organisationRepository.findById(signUpRequest.getOrganisationId())
							.orElseThrow(() -> new RuntimeException("Organisation not found with id: " + signUpRequest.getOrganisationId()));
					user.setOrganisation(organisation);*/
					break;
				case "client":
					Role achetRole = roleRepository.findByName(ERole.CLIENT);
			        roles.add(achetRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.CLIENT);
					roles.add(userRole);
				}
			}
			);
		//}

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
			//user.setStateuser(false);
			user.setRoles(roles);
			userRepository.save(user);
			accountResponse.setResult(1);
			//user.setStateuser(false);
			ConfirmationToken confirmationToken = new ConfirmationToken(user);
			confirmationTokenRepository.save(confirmationToken);
			SimpleMailMessage mailMessage
					= new SimpleMailMessage();
			mailMessage.setFrom("mohamedaziz.benzarti@esprit.tn");
			mailMessage.setTo(user.getEmail());
			mailMessage.setText("To confirm your account, please click here : "
					+"http://localhost:8090/api-auth/confirm-account?token="+confirmationToken.getConfirmationToken());

			mailMessage.setSubject("Complete Registration!");
			javaMailSender.send(mailMessage);

		}
		log.info("" + ResponseEntity.ok(new MessageResponse("User registered successfully!")));
		return accountResponse;
	}

	// Social Login
	@PostMapping("/google")
	public LoginResponse loginWithGoogle(@RequestBody TokenDto tokenDto) throws IOException {

		NetHttpTransport transport = new NetHttpTransport();
		JacksonFactory factory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier.Builder ver = new GoogleIdTokenVerifier.Builder(transport, factory)
				.setAudience(Collections.singleton(idClient));

		log.info("googleIdTokeen" + tokenDto.getValue());
		String tokenValue = tokenDto.getValue();
		if (tokenValue == null) {
			throw new IllegalArgumentException("Token value cannot be null");
		}
		//GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(), tokenValue);
		GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(), tokenDto.getValue());
		GoogleIdToken.Payload payload = googleIdToken.getPayload();
		//String username = payload.get("given_name").toString().concat("-").toString().concat(getAlphaNumericString(n));
		String username = payload.get("given_name").toString();
		String firstName = payload.get("given_name").toString();
		String lastName = payload.get("family_name").toString();
		// return new ResponseEntity<>(payload, HttpStatus.OK);


		return login(payload.getEmail(), username,firstName,lastName);

	}
	static String getAlphaNumericString(int n) {

		String randomNumber = "0123456789";
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (randomNumber.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(randomNumber.charAt(index));
		}

		return sb.toString();
	}
	private LoginResponse login(String email, String username, String prenom ,String nom) {
		boolean result = userService.ifEmailExist(email); // t // f
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.CLIENT);
		roles.add(userRole);
		if (!result) {

			User user = new User();
			user.setEmail(email);
			user.setPassword(encoder.encode("root1234"));
			user.setUsername(username);
			user.setStateuser(true);
			user.setPrenom(prenom);
			user.setName(nom);
			user.setRoles(roles);
			log.info("test",user);
			userRepository.save(user);
		}
		JwtLogin jwtLogin = new JwtLogin();
		jwtLogin.setUsername(username);
		jwtLogin.setPassword("root1234");
		jwtLogin.setNom(nom);
		jwtLogin.setPrenom(prenom);

		log.info("jwt:" + jwtLogin.getUsername());
		log.info("jwt:" + jwtLogin.getPassword());

		return jwtUtils.login(jwtLogin);
	}
	@PostMapping("/facebook")
	public LoginResponse loginWithFacebook(@RequestBody TokenDto tokenDto) {
		Facebook facebook = new FacebookTemplate(tokenDto.getValue());
		String[] data = { "email", "name" };
		User userFacebook = facebook.fetchObject("me", User.class, data);
		// String


		String username = userFacebook.getName().concat(getAlphaNumericString(n));

		return loginfb(userFacebook.getEmail(),username);
	}

	private LoginResponse loginfb(String email, String username) {
		boolean result = userService.ifEmailExist(email); // t // f
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(ERole.CLIENT);
		roles.add(userRole);
		if (!result) {
			User user = new User();
			user.setEmail(email);
			user.setPassword(encoder.encode("root1234"));
			user.setUsername(username);
			user.setStateuser(true);
			user.setRoles(roles);
			userRepository.save(user);
		}
		JwtLogin jwtLogin = new JwtLogin();
		jwtLogin.setUsername(username);
		jwtLogin.setPassword("root1234");


		log.info("jwt:" + jwtLogin.getUsername());
		log.info("jwt:" + jwtLogin.getPassword());

		return jwtUtils.login(jwtLogin);
	}
	private User createUser(String email) {
		User user = new User();
		user.setEmail(email);
		user.setName(("malak"));
		user.setPassword(encoder.encode("root123"));

		return userService.saveUser(user);
	}


	//confirm
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken)
	{
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if(token != null)
		{
			User user = userRepository.findByEmail(token.getUserEntity().getEmail());
			user.setStateuser(true);
			userRepository.save(user);
			return ResponseEntity.ok(new MessageResponse("Account verified! User registered successfully!"));

		}
		else
		{
			return ResponseEntity.ok(new MessageResponse("Error: Invalid Link!"));

		}
	}




	/*@PostMapping("/facebook")
	public ResponseEntity<LoginResponse> loginWithFacebook(@RequestBody TokenDto tokenDto) throws Exception {
		Facebook facebook = new FacebookTemplate(tokenDto.getValue());
		String [] data = {"email"};
		org.springframework.social.facebook.api.User user = facebook.fetchObject("me", org.springframework.social.facebook.api.User.class,data);

		email = user.getEmail();
		User userFace = new User();
		if(userService.ifEmailExist(email)){
			userFace = userService.getUserByMail(email);
		} else {
			userFace = createUser(email);
		}
		//
		JwtLogin jwtLogin = new JwtLogin();
		jwtLogin.setUsername(user.getFirstName());
		jwtLogin.setPassword(user.getEmail());

		return new ResponseEntity<LoginResponse>(jwtUtils.login(jwtLogin), HttpStatus.OK);
	}*/


	/*@PostMapping("/google")
	public ResponseEntity<LoginResponse> loginWithGoogle(@RequestBody TokenDto tokenDto) throws Exception {
		//System.out.println("pass " + password);
		NetHttpTransport transport = new NetHttpTransport();
		JacksonFactory factory = JacksonFactory.getDefaultInstance();
		GoogleIdTokenVerifier.Builder ver =
				new GoogleIdTokenVerifier.Builder(transport,factory)
						.setAudience(Collections.singleton(idClient));
		GoogleIdToken googleIdToken = GoogleIdToken.parse(ver.getJsonFactory(),tokenDto.getValue());
		GoogleIdToken.Payload payload = googleIdToken.getPayload();
		email = payload.getEmail();
		User user = new User();
		if(userService.ifEmailExist(email)){
			user = userService.getUserByMail(email);
		} else {
			user = createUser(email);
		}
		///////////////////////////
		JwtLogin jwtLogin = new JwtLogin();
		//jwtLogin.setEmail(user.getEmail());
	jwtLogin.setPrenom(user.getPrenom());
		//jwtLogin.setPassword(password);
		///////////////////////////

		return new ResponseEntity<LoginResponse>(jwtUtils.login(jwtLogin), HttpStatus.OK);
	}
*/

	// Session
	@GetMapping("/user")
	public User getCurrentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User userOptional = userRepository.findByUsername(username);
		//User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new User(userOptional.getId()); // User.getId() // getName()

	}
}
