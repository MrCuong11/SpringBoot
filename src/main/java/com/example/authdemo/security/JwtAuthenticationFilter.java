package com.example.authdemo.security;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// nhiệm vụ của class này là lọc request và kiểm tra token có hợp lệ không
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // lấy jwtService và userDetailsService từ JwtService và UserDetailsService
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // lọc request
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        // nếu authHeader là null hoặc không bắt đầu với "Bearer " thì bỏ qua
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // bỏ qua 7 kí tự đầu tiên của authHeader
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        // nếu username không phải là null và authentication là null thì load user details
        // nếu token hợp lệ thì set authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // nếu token hợp lệ thì set authentication
            if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                // set details cho authentication
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // set authentication cho security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // tiếp tục filter chain
        filterChain.doFilter(request, response);
    }
}
