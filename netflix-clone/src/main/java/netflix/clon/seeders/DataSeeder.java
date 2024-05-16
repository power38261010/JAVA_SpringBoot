package com.netflix.clon.seeders;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.netflix.clon.model.Subscription;
import com.netflix.clon.repository.SubscriptionRepository;
import com.netflix.clon.service.MovieService;
import com.netflix.clon.service.SubscriptionService;
import com.netflix.clon.service.UserService;



/**
 *
 * @author alejandro
 */
@Component
public class DataSeeder {

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public DataSeeder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostConstruct
    @Transactional
    public void seedData() {
        Faker faker = new Faker();
        String[] url_images = {"https://resizing.flixster.com/P3ITKQPm33gw4K73O9DYCOfyZ4E=/ems.cHJkLWVtcy1hc3NldHMvbW92aWVzLzQ1Zjg4NDFhLWMxY2EtNDU3NC04OTNmLTQ4MTJiMzc3Y2EwZC5qcGc=","https://m.media-amazon.com/images/M/MV5BMjMwOTg0MzU4MV5BMl5BanBnXkFtZTgwODk2NjY3NTM@._V1_FMjpg_UX1000_.jpg","https://i.blogs.es/485f6e/la-cocina-poster-netflix/650_1200.jpeg","","https://hips.hearstapps.com/hmg-prod/images/best-movies-on-netflix-the-mother-6478b59716093.jpg?crop=1xw:1xh;center,top&resize=980:*","https://images.ctfassets.net/4cd45et68cgf/aXpogGacw8gMlQ1myxRWp/c0957190d2702297172da00f40504d26/EN_TheStranger_Main_Vertical_RGB_PRE.jpeg?w=2000","https://dnm.nflximg.net/api/v6/2DuQlx0fM4wd1nzqm5BFBi6ILa8/AAAAQdgH3RALdcp56UKYjGvh4BNjeYx0P7FSUtRlSLGZKovtkdBk2t3REeqhksKzacXzUOjwB-JiReDl7_Z325iB4hkpNjNEeWY5oTJBRP8MIS9OVOfsQnWGsWhIMASTHuGkF5E2D64Iw_jDQ0CLxHnic1fx.jpg?r=cb5","https://dnm.nflximg.net/api/v6/2DuQlx0fM4wd1nzqm5BFBi6ILa8/AAAAQQcSyUZXYH0zIJq5_WrcSpUssMlnlebBWLLTZFPPsME7WHre1BTNYwGb1AfHeubWJB0GW5BVPEbzSFoeVySfP3t7TGXWSV7_3GwiruRSReFlK-CY3lwV8gn0p1rHMX1C-h-XzvuMowAxuCGFUy17lyE8.jpg?r=0e1","https://dnm.nflximg.net/api/v6/WNk1mr9x_Cd_2itp6pUM7-lXMJg/AAAABTC2JsuNgSQgERC4agDGf6dogj_kAv-Gox_jgmPIfSeB-wKve6yn5LbXA6lw26OUm0dzzXG0VLmUeajNYBRQ9hSHauFRxVCYXxhylk5YmplXSGSaqxyz9c1KWBmN0fgmNK6kSQ.jpg?r=c6c","https://images.ctfassets.net/4cd45et68cgf/3tCOzPxx4SdNL8gBcC8Dp6/1a6ba2f129270f78d44cb3be6a123687/EN-GB_Paradise_Main_Vertical_RGB_PRE__1_.jpg"};
        String url_trailer = "https://www.youtube.com/watch?v=cFS4Zcd_kb8&ab_channel=astounding";

        Subscription started = subscriptionService.findOrCreateSubscription("Started", 9.99);
        Subscription premium = subscriptionService.findOrCreateSubscription("Premium", 19.99);

           // Use these managed entities for further operations
        if (started == null) {
            started = subscriptionRepository.save(new Subscription("Started", 9.99));
        }
        if (premium == null) {
            premium = subscriptionRepository.save(new Subscription("Premium", 19.99));
        }

        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                userService.findOrCreateUser("admin", passwordEncoder.encode("admin1234"), true, premium);
            } else if (i % 2 == 0) {
                userService.findOrCreateUser(faker.name().username(), passwordEncoder.encode(faker.internet().password()), false, started);
            } else {
                userService.findOrCreateUser(faker.name().username(), passwordEncoder.encode(faker.internet().password()), false, premium);
            }
        }

        for (int i = 0; i < 10; i++) {
            Set<Subscription> subscriptions = new HashSet<>();
            if (i % 2 == 0) {
                subscriptions.add(started);
            } else {
                subscriptions.add(premium);
            }
            movieService.findOrCreateMovie(faker.book().title(), faker.lorem().paragraph(), faker.book().genre(), url_images[i], url_trailer, subscriptions);
        }

        System.out.println("Seeders creados exitosamente.");
    }
}

