package edu.icet.filters;

import edu.icet.entity.User;
import edu.icet.service.UserService;
import edu.icet.service.impl.UserServiceImpl;
import edu.icet.utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            token = authHeader.substring(7);
            userName = jwtUtil.extractUsername(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {


        UserDetails userDetail = userService.loadUserByUserName(userName);

        if(jwtUtil.validateToken(token , userDetail )){
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetail , null);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        }
    }

      filterChain.doFilter(request,response);


    }


}
