/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dtt.filters.JwtFilter;
import jakarta.ws.rs.HttpMethod;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author MR TU
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.dtt.controllers",
    "com.dtt.repositories",
    "com.dtt.services",
    "com.dtt.filters"
})
public class SpringSecurityConfigs {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HandlerMappingIntrospector
            mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers(
                        "/login", "/oauth2/**", "/register", "/users/register", "/users/update",
                        "/api/public/**",
                        "/faculties", "/classes", "/api/faculties/**",
                        "/js/**",
                        "/my-activities",
                        "/api/activities", "/api/activities/**",
                        "/api/activity-registrations",
                        "/api/public/check-email",
                        "/api/users",
                        "/api/faculties",
                        "/api/users/faculty/*/classes",
                        "/api/activities/{activityId}/likes", "/api/activities/{id}/likes/count",
                        "/api/login"
                ).permitAll()
                .requestMatchers(
                        "/api/users/activity-registration",
                        "/api/users/activity-registration/**",
                        "/api//activities/*/comments", "/api/activities/{id}/comments",
                        "/api/evidences/**",
                        "/api/activities/{activityId}/likes",
                        "/api/secure/profile",
                        "/api/missing-reports/create", "/api/missing-reports", "/api/missing-reports/{id}",
                        "/api/my-activities", "/api/my-activities/**",
                        "/api/notifications",
                        "/api/users/students", "/api/users/students/**",
                        "/api/training-points/create",
                        "/api/users/{id}/notifications", "/api/users/{id}/notifications/mark-all-read"
                ).hasAnyRole("ADMIN", "STAFF", "STUDENT")
                // Restricted endpoints for ADMIN and STAFF
                .requestMatchers(
                        "/api/export", "/api/export/**",
                        "/api/missing-reports",
                        "/api/attendances/**", "/api/attendances",
                        "/api/missing-reports/confirm/**", "/api/missing-reports/reject/{id}", "/api/missing-reports/reject-after-confirm/**",
                        "/api/statistics",
                        "/api/training-points", "/api/training-points/**", "/api/training-points/confirm/**", "/api/training-points/reject/**", "/api/training-points/reject-after-approved/**",
                        "/training-points",
                        "/statistics"
                ).hasAnyRole("ADMIN", "STAFF")
                // Restricted endpoints for ADMIN only
                .requestMatchers(
                        "/api/classes/**",
                        "/api/emails/**",
                        "/api/faculties/**",
                        "/api/users/**",
                        "/", "/home", "/activities/**", "/add",
                        "/users/**", "/emails/**", "/missing-reports",
                        "/classes/**", "/faculties/**"
                ).hasRole("ADMIN")
                // All other requests require authentication
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
                )
                .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
                );

        return http.build();
    }

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dq1oo3fod",
                        "api_key", "216276187471198",
                        "api_secret", "IPwc-sSRfgqIY30pkisZ_SBINC8",
                        "secure", true));
        return cloudinary;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Gốc hợp lệ (frontend)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Các phương thức HTTP
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Các header được phép
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Order(0)
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("tu2004latoi@gmail.com");
        mailSender.setPassword("qiky upgh diyx zyst");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
