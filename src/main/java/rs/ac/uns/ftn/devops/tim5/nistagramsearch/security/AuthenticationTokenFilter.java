package rs.ac.uns.ftn.devops.tim5.nistagramsearch.security;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.kafka.security.CheckToken;
import rs.ac.uns.ftn.devops.tim5.nistagramsearch.model.kafka.UserSecurity;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private CheckToken checkToken;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String authToken = httpRequest.getHeader("Authorization");

            UserSecurity userSecurity = checkToken.check(authToken);
            if (userSecurity.getUsername() != null) {
                List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority(userSecurity.getRole()));
                UserDetails userDetails = new org.springframework.security.core.userdetails
                        .User(userSecurity.getUsername(), "", grantedAuthorities);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }


}
