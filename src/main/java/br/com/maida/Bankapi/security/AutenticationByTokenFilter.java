package br.com.maida.Bankapi.security;

import br.com.maida.Bankapi.models.User;
import br.com.maida.Bankapi.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticationByTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository userRepository;

    public AutenticationByTokenFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = retrieveToken(request);

        StringBuffer url = request.getRequestURL();
        if(url.toString().contains("/accounts")){
            if(token == null) {
                acessoNegado(response);
                return;
            }
        }

        boolean valido = tokenService.isTokenValido(token);

        if (valido) {
            autenticarCliente(token);
        }

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            acessoNegado(response);
            return;
        }

    }
    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        User user = userRepository.findById(idUsuario).get();

        UsernamePasswordAuthenticationToken autentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(autentication);
    }
    private String retrieveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7, token.length());
    }
    private void acessoNegado(HttpServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("{\"message\": \"Acesso negado\"}");
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }


}
