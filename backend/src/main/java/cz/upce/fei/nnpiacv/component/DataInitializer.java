package cz.upce.fei.nnpiacv.component;

import cz.upce.fei.nnpiacv.domain.Profile;
import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.repository.UserRepository;
import cz.upce.fei.nnpiacv.repository.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public DataInitializer(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Try to find the user with id 1, and create it if not found
        User user = userRepository.findById(1L).orElseGet(() -> {
            User newUser = new User("admin@example.com", "adminPassword");
            return userRepository.save(newUser);
        });

        // Create a profile for the user
        Profile profile = new Profile("Admin User", "This is the admin's bio", user);
        profileRepository.save(profile);

        // Log the profile creation
        System.out.println("Profile created for user: " + user.getEmail());
    }

}
