# Spring Security Authentications 

Helps you in understanding basic concepts of Spring Security and Its types of Authentications.
4 Types of Authentications are convered in this Project,
1. In-Memory Authentication
2. JDBC Authentication
3. UserDetails Service Authentication
4. LDAP (Lightweight Directory Access Protocol) :- Not Covered

## Pre Requisites 
1. Must have Java & STS Installed 
2. Fare knowledge on Spring Boot Application 
3. Mysql DataBase

## Things Covered
To configure custom Spring Security we must override below Methods in Config class (in our case SecurityConfig) , annotated with @EnableWebSecurity @Configuration
1. SecurityFilterChain for applying filters accors End-Points
2. AuthenticationProvider for Custom Authentications
3. @EnableMethodSecurity(prePostEnabled = true) helps in providing role based Authentication

Types of Authentication Covered
1. In-Memory Authentication , helps in configuring UserDetails without help of DataBase configuration
2. JDBC Authentication , Makes use of JdbcUserDetailsManager which has custom repositories which creates UserDetails tables in configured DataBase
3. UserDetailsService , Helps user to configure custom User Details Tables by creating Entity and its repositories


Thanks.
Pruthvi
pruthvi28994@gmail.com