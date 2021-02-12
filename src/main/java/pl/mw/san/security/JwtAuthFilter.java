package pl.mw.san.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JWTTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = getTokenFromRequest(httpServletRequest);
            if (StringUtils.hasText(token) && jwtTokenService.validateToken(token)) {
                Long userId = jwtTokenService.getUserIdFromToken(token);

                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception ex){
            logger.error("An error: " + ex.getMessage() + " has occured." );
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

    private String getTokenFromRequest (HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        return (StringUtils.hasText(header) && header.startsWith("Bearer ")) ? header.substring(7, header.length()) : null;


    }
}
