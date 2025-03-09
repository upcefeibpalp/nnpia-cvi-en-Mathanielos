package cz.upce.fei.nnpiacv.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Získání hlavičky Authorization
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Pokud není hlavička nebo nezačíná "Bearer ", pokračujeme dál bez autentizace
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Extrakce samotného tokenu (bez "Bearer ")
        String token = authHeader.substring(7);

        // Ověření tokenu
        if (!jwtUtil.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Získání emailu z tokenu
        String email = jwtUtil.extractEmail(token);

        // Vytvoření UserDetails pro Spring Security
        UserDetails userDetails = User.withUsername(email)
                .password("") // Heslo zde není potřeba, protože JWT již zajišťuje autentizaci
                .authorities("USER") // Lze upravit podle rolí
                .build();

        // Vytvoření autentizačního objektu
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Nastavení autentizace do Spring Security kontextu
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Pokračujeme v řetězci filtrů
        chain.doFilter(request, response);
    }
}
